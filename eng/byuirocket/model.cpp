#include "model.hpp"
#include "common.hpp"

using namespace std;

ModelState& ModelState::operator=(const ModelState& rhs)
{
   if (this != &rhs)
   {
      T = rhs.T;
      S = rhs.S;
      P = rhs.P;
      L = rhs.L;
      Q = rhs.Q;
   }

   return *this;
}

const ModelState ModelState::operator+(const ModelState& other) const
{
   return ModelState(*this) += other;
}

ModelState& ModelState::operator+=(const ModelState& rhs)
{
   S += rhs.S;
   P += rhs.P;
   L += rhs.L;
   Q = Quaternion(
      Q.W + rhs.Q.W, 
      Q.X + rhs.Q.X,
      Q.Y + rhs.Q.Y,
      Q.Z + rhs.Q.Z);
   return *this;
}

const ModelState ModelState::operator-(const ModelState& other) const
{
   return ModelState(*this) -= other;
}

ModelState& ModelState::operator-=(const ModelState& rhs)
{
   S -= rhs.S;
   P -= rhs.P;
   L -= rhs.L;
   Q = Quaternion(
      Q.W - rhs.Q.W,
      Q.X - rhs.Q.X,
      Q.Y - rhs.Q.Y,
      Q.Z - rhs.Q.Z);
   return *this;
}

const ModelState ModelState::operator*(const ModelState& other) const
{
   return ModelState(*this) *= other;
}

ModelState& ModelState::operator*=(const ModelState& rhs)
{
   S = Vector(S.X * rhs.S.X, S.Y * rhs.S.Y, S.Z * rhs.S.Z);
   P = Vector(P.X * rhs.P.X, P.Y * rhs.P.Y, P.Z * rhs.P.Z);
   L = Vector(L.X * rhs.L.X, L.Y * rhs.L.Y, L.Z * rhs.L.Z);
   Q = Quaternion(
      Q.W * rhs.Q.W,
      Q.X * rhs.Q.X,
      Q.Y * rhs.Q.Y,
      Q.Z * rhs.Q.Z);
   return *this;
}

const ModelState ModelState::operator*(double n) const
{
   return ModelState(*this) *= n;
}

ModelState& ModelState::operator*=(double n)
{
   S *= n;
   P *= n;
   L *= n;
   Q = Quaternion(Q.W * n, Q.X * n, Q.Y * n, Q.Z * n);
   return *this;
}

std::ostream& operator<<(std::ostream &out, ModelState& state)
{
   out << state.T << ", "
      << state.S.X << ", "
      << state.S.Y << ", "
      << state.S.Z << ", "
      << state.P.X << ", "
      << state.P.Y << ", "
      << state.P.Z << ", "
      << state.L.X << ", "
      << state.L.Y << ", "
      << state.L.Z << ", "
      << state.Q.W << ", "
      << state.Q.X << ", "
      << state.Q.Y << ", "
      << state.Q.Z << std::endl;
   return out;
}

void Model::Integrate(double dt, ModelState& state, double recordInterval, std::vector<ModelState>& history)
{
   for (double dt2 = dt / 2.0, nextRecord = recordInterval; !IsFinished(state.T, state); state.T += dt)
   {
      auto k1 = Differentiate(state.T, state);
      auto k2 = Differentiate(state.T + dt2, state + k1 * dt2);
      auto k3 = Differentiate(state.T + dt2, state + k2 * dt2);
      auto k4 = Differentiate(state.T + dt, state + k3 * dt);
      state += (k1 + k2 * 2.0 + k3 * 2.0 + k4) * (dt / 6.0);
      state.Q.normalize();

      if (state.T >= nextRecord)
      {
         history.push_back(state);
         nextRecord += recordInterval;
      }
   }

   if (history.size() > 0 && history.back().T < state.T)
   {
      history.push_back(state);
   }
}

bool LaunchModel::IsFinished(double t, const ModelState& state)
{
   return t > 0 && ((state.S.Norm() > m_db->Lrl) || (state.T > m_db->Tthrust));
}

ModelState LaunchModel::Differentiate(double t, const ModelState& state)
{
   m_db->SetT(state.T);
   m_db->SetS(state.S);
   m_db->SetA(0);

   auto v = state.P + m_db->Wind;
   auto vnorm = v.Norm();  
   auto u = m_db->Lru;
   m_db->SetV(vnorm);   

   auto drag = 0.5 * m_db->Rho * sqr(vnorm) * m_db->Arb;
   auto cfn = drag * m_db->Cn;
   auto cfa = drag * m_db->Ca;
   auto cft = m_db->Ft;
   auto cfg = m_db->Gu * m_db->M / sqr(m_db->Rearth + state.S.Z);

   auto fn = Vector::Zero();
   auto fa = -cfa * u;
   auto ft = cft * u;
   auto fg = -cfg * u;
   auto fd = Vector::Zero(); // TODO: linear drag

   ModelState dstate;
   dstate.T = state.T;
   dstate.S = state.P;
   dstate.P = (fd + fa + ft + fg) / m_db->M;
   dstate.Q = Quaternion(0, 0, 0, 0);
   dstate.L = Vector::Zero();

   if (dstate.P.Z < 0)
   {
      dstate.S = Vector::Zero();
      dstate.P = Vector::Zero();
   }

   return dstate;
}

bool AscentModel::IsFinished(double t, const ModelState& state)
{
   return state.P.Z < 0;
}

ModelState AscentModel::Differentiate(double t, const ModelState& state)
{
   m_db->SetT(state.T);
   m_db->SetS(state.S);

   auto r = state.Q.toRotationMatrix();
   auto u = r * m_db->AxisR0;

   auto w = state.L;
   auto wnorm = w.Norm();
   auto what = (wnorm > 0) ? (Vector)(w / wnorm) : u;

   auto v = Vector(0, 0, 0);
   auto vnorm = (double)0.0;
   auto vhat = Vector(0, 0, 0);
   auto vcm = state.P + m_db->Wind;
   auto xbar = 0.0;
   auto tw = sin(acos(u.Dot(what))) * u.Cross(w);

   for (double a = 0, aold = INFINITY; abs(a - aold) > 0.001; aold = a)
   {
      v = vcm + xbar * tw;
      vnorm = v.Norm();
      vhat = (vnorm > 0) ? (Vector)(v / vnorm) : u;
      a = BoundedAcos(vhat.Dot(u));
      m_db->SetA(a);
      xbar = abs(m_db->Xcp - m_db->Xcm);
   }

   m_db->SetV(vnorm);

   auto drag = 0.5 * m_db->Rho * sqr(vnorm) * m_db->Arb;
   auto cfn = drag * m_db->Cn;
   auto cfa = drag * m_db->Ca;
   auto cft = m_db->Ft;
   auto cfg = m_db->Gu * m_db->M / sqr(m_db->Rearth + state.S.Z);

   Vector fn = cfn * u.Cross(u.Cross(vhat));
   auto fa = -cfa * u;
   auto ft = cft * u;
   auto fg = -cfg * m_db->AxisR0;

   auto tn = cfn * xbar * u.Cross(vhat);
   auto sdot = 0.5 * w.Dot(state.Q.vec());
   auto vdot = 0.5 * (state.Q.W * w + w.Cross(state.Q.vec()));

   ModelState dstate;
   dstate.T = state.T;
   dstate.S = state.P;
   dstate.Q = Quaternion(sdot, vdot.X, vdot.Y, vdot.Z);
   dstate.P = (fn + fa + ft + fg) / m_db->M;
   dstate.L = r * m_db->I.DiagonalInverse() * r.Transpose() * tn;

   return dstate;
}

bool ParachuteModel::IsFinished(double t, const ModelState& state)
{
   return state.S.Z <= 0;
}

ModelState ParachuteModel::Differentiate(double t, const ModelState& state)
{
   m_db->SetT(state.T);
   m_db->SetS(state.S);
   m_db->SetA(0);

   auto v = state.P + m_db->Wind;
   auto vnorm = v.Norm();
   auto vhat = v / vnorm;

   auto fd = -0.5 * m_db->Rho * sqr(vnorm) * m_db->Pcd * m_db->Pap * vhat;
   auto fg = -m_db->Gu * m_db->M / sqr(m_db->Rearth + state.S.Z) * m_db->AxisR0;

   ModelState dstate;
   dstate.T = state.T;
   dstate.S = state.P;
   dstate.Q = Quaternion(1, 0, 0, 0);
   dstate.P = (fd + fg) / m_db->M;
   dstate.L = Vector(0, 0, 0);

   return dstate;
}

std::vector<Output> Model::GetOutput(const vector<ModelState>& state)
{
   vector<Output> output;

   for (ModelState m : state)
   {
      output.push_back(GetOutput(m.T, m));
   }

   return output;
}

Output Model::GetOutput(double t, const ModelState& state)
{
   Output o;

   m_db->SetT(state.T);
   m_db->SetS(state.S);

   auto r = state.Q.toRotationMatrix();
   auto u = r * m_db->AxisR0;

   auto w = r * m_db->I.DiagonalInverse() * r.Transpose() * state.L;
   auto wnorm = w.Norm();
   auto what = (wnorm > 0) ? (Vector)(w / wnorm) : u;

   auto v = Vector(0, 0, 0);
   auto vnorm = (double)0.0;
   auto vhat = Vector(0, 0, 0);
   auto vcm = state.P + m_db->Wind;
   auto xbar = 0.0;
   auto tw = sin(acos(u.Dot(what))) * u.Cross(w);
   double a = 0;

   for (double aold = INFINITY; abs(a - aold) > 0.001; aold = a)
   {
      v = vcm + xbar * tw;
      vnorm = v.Norm();
      vhat = (vnorm > 0) ? (Vector)(v / vnorm) : u;
      a = BoundedAcos(vhat.Dot(u));
      m_db->SetA(a);
      xbar = abs(m_db->Xcp - m_db->Xcm);
   }

   m_db->SetV(vnorm);

   auto drag = 0.5 * m_db->Rho * sqr(vnorm) * m_db->Arb;
   auto cfn = drag * m_db->Cn;
   auto cfa = drag * m_db->Ca;
   auto cft = m_db->Ft;
   auto cfg = m_db->Gu * m_db->M / sqr(m_db->Rearth + state.S.Z);

   Vector fn = cfn * u.Cross(u.Cross(vhat));
   auto fa = -cfa * u;
   auto ft = cft * u;
   auto fg = -cfg * m_db->AxisR0;

   auto tn = cfn * xbar * u.Cross(vhat);
   auto sdot = 0.5 * w.Dot(state.Q.vec());
   auto vdot = 0.5 * (state.Q.W * w + w.Cross(state.Q.vec()));

   ModelState dstate;
   dstate.T = state.T;
   dstate.S = state.P;
   dstate.Q = Quaternion(sdot, vdot.X, vdot.Y, vdot.Z);
   dstate.P = (fn + fa + ft + fg) / m_db->M;
   dstate.L = tn;

   o.Time = t;
   o.Altitude = state.S.Z;
   o.VerticalVelocity = state.P.Z;
   o.VerticalAcceleration = dstate.P.Z;
   o.TotalVelocity = state.P.Norm();
   o.TotalAcceleration = dstate.P.Norm();
   o.PositionUpwind = state.S.X;
   o.PositionParallelToWind = state.S.Y;
   o.LateralDistance = Vector(state.S.X, state.S.Y, 0).Norm();
   o.LateralDirection = 0;
   o.LateralVelocity = Vector(state.P.X, state.P.Y, 0).Norm();
   o.LateralAcceleration = Vector(dstate.P.X, dstate.P.Y, 0).Norm();
   o.Latitude = 0;
   o.Longitude = 0;
   o.GravitationalAcceleration = m_db->Gu / sqr(m_db->Rearth + state.S.Z);
   o.AngleOfAttack = a;
   o.RollRate = w.X;
   o.YawRate = w.Y;
   o.PitchRate = w.Z;
   o.Mass = m_db->M;
   o.PropellantMass = 0;
   o.LongitudinalMomentOfInertia = m_db->I(0, 0);
   o.RotationalMomentOfInertia = m_db->I(2, 2);
   o.CPLocation = m_db->Xcp;
   o.CGLocation = m_db->Xcm;
   o.StabilityMarginCalibers = 0;
   o.MachNumber = m_db->Ma;
   o.ReynoldsNumber = m_db->Re;
   o.Thrust = m_db->Ft;
   o.DragForce = (fn + fa).Norm();
   o.AxialDragCoefficient = m_db->Ca;
   o.NormalForceCoefficient = m_db->Cn;
   o.ReferenceArea = m_db->Geometry.Ap;
   o.ReferenceLength = m_db->Geometry.ltr;
   o.AirTemperature = m_db->Tatm;
   o.AirPressure = m_db->P;
   o.Altitude = state.S.Z;
   o.WindVelocity = m_db->Wind.Norm();

   return o;
}