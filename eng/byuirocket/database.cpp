#include "database.hpp"
#include "part.hpp"
#include "csv.hpp"

using namespace std;

Database::Database(bool random, Settings& settings) : m_random(random), m_settings(&settings)
{
   Load(settings);
   SetT(0);
   SetS(Vector(0, 0, 0));
   SetA(0);
   SetV(0);
}

Database::~Database()
{
   for (auto kvp : m_parts)
   {
      delete kvp.second;
   }

   m_parts.clear();
}

Part* Database::GetPart(std::string name)
{
   try
   {
      return m_parts.at(name);
   }
   catch (out_of_range ex)
   {
      throw invalid_argument("Unable to locate specifed part in database: "
         + name);
   }
}

void Database::Load(Settings& settings)
{
   LoadEnvironment(settings.Get("Environment"));
   LoadParachute(settings.Get("Parachute"));
   LoadLaunchRail(settings.Get("LaunchRail"));
   LoadThrust(settings.Get("Thrust"));
   LoadParts(settings.GetParts());
   InitializeThrustDatabase();
}

double Database::LoadItem(ItemMenu& menu, std::string name)
{
   auto item = menu.Get(name);
   double val = 0.0;
   double err = 0.0;

   try
   {
      val = stod(item.Value);
   }
   catch (invalid_argument ex)
   {
      val = 0.0;
   }

   try
   {
      err = stod(item.Error);
   }
   catch (invalid_argument ex)
   {
      err = 0.0;
   }

   if (m_random && err > 0)
   {
      return m_settings->GaussianRandom(val, err);
   }
   else
   {
      return val;
   }
}

void Database::LoadEnvironment(ItemMenu& menu)
{
   Tatm = LoadItem(menu, "Temperature");
   Elevation = LoadItem(menu, "Elevation");
   Wind = Vector(
      LoadItem(menu, "Wx"), 
      LoadItem(menu, "Wy"), 
      LoadItem(menu, "Wz"));
}

void Database::LoadParachute(ItemMenu& menu)
{
   Pap = LoadItem(menu, "Ap");
   Pcd = LoadItem(menu, "Cd");
}

void Database::LoadLaunchRail(ItemMenu& menu)
{
   Lrl = LoadItem(menu, "Length");
   Lrcf = LoadItem(menu, "Cf");
   Lru = Vector(
      LoadItem(menu, "Ux"),
      LoadItem(menu, "Uy"),
      LoadItem(menu, "Uz")).Normalized();
}

void Database::LoadThrust(ItemMenu& menu)
{
   // Load the time and thrust force data 
   DataCsvReader csv(menu.AsString("ThrustFile"));
   vector<double> row;

   vector<DynamicsEntry> thrust;
   m_thrust.clear();

   while (csv.NextRow(row))
   { 
      m_thrust.push_back(DynamicsEntry(row[0], row[1]));
   }

   for (double t = 0, maxT = (m_thrust.end() - 1)->T; t <= maxT + 0.01; t += .01)
   {
      DynamicsEntry entry;
      InterpolateDynamics(t, entry);
      thrust.push_back(entry);
   }

   m_thrust = thrust;
}

void Database::InitializeThrustDatabase()
{
   // Calculate the total impulse (used to calculate the remaining mass)
   double totalImpulse = 0;
   double tlast = 0;

   for (auto entry : m_thrust)
   {
      totalImpulse += entry.F * (entry.T - tlast);
      tlast = entry.T;
   }

   // Update the dynamic variables for each thrust entry
   auto fuel = GetPart("Fuel");
   double impulse = 0.0;   
   double mfuel = fuel->M;
   tlast = 0.0;

   for (auto& entry : m_thrust)
   {
      // Set the current impulse and mass of the remaining fuel
      impulse += entry.F * (entry.T - tlast);
      fuel->M = mfuel * (1 - impulse / totalImpulse);

      // Calculate the mass and center of mass
      for (auto kvp : m_parts)
      {
         entry.M += kvp.second->M;
         entry.Xcm += kvp.second->M * kvp.second->Xcm();
      }

      entry.Xcm /= entry.M;

      // Calculate the moment of inertia tensor
      for (auto kvp : m_parts)
      {
         double temp1 = kvp.second->Ix(entry.Xcm);
         double temp2 = kvp.second->Ix();
         entry.Ix += kvp.second->Ix(entry.Xcm);
         entry.Iy += kvp.second->Iy(entry.Xcm);
         entry.Iz += kvp.second->Iz(0);
      }

      tlast = entry.T;
   }

   Tthrust = m_thrust[m_thrust.size() - 1].T;
   fuel->M = mfuel;
}

/******************************************************************************
* Interpolates two thrust entries for a given time
*    t: time of burn for the rocket
*    e1: thrust entry below time t
*    e2: thrust entry above time t
*    a: weight for e1
*    b: weight for e2
******************************************************************************/
void Database::InterpolateDynamics(double t, DynamicsEntry& e)
{
   auto n = m_thrust.size() - 1;
   auto min = m_thrust[0].T;
   auto max = m_thrust[n].T;

   // Make sure specified time is in bounds
   if (t > max || t < min)
   {
      e = (t > max) ? m_thrust[n] : m_thrust[0];
      return;
   }

   // Find the index before and after the specified time
   auto dt = (max - min) / n;
   auto i2 = (size_t)(t / dt);
   i2 = i2 > 0 ? i2 : 1;
   i2 = i2 < n ? i2 : n - 1;

   for (; m_thrust[i2].T > t && i2 > 1; i2--)
      ;

   for (; m_thrust[i2].T < t && i2 < n - 1; i2++)
      ;

   // Use a linear interpolation before and after the data point
   size_t i1 = i2 - 1;
   auto e1 = &m_thrust[i1];
   auto e2 = &m_thrust[i2];
   auto b = (t - e1->T) / (e2->T - e1->T);
   auto a = 1 - b;

   e.T = t;
   e.F = a * e1->F + b * e2->F;

   if (e.F < 0)
   {
      e.F = 0;
   }

   e.M = a * e1->M + b * e2->M;
   e.Ix = a * e1->Ix + b * e2->Ix;
   e.Iy = a * e1->Iy + b * e2->Iy;
   e.Iz = a * e1->Iz + b * e2->Iz;
   e.Xcm = a * e1->Xcm + b * e2->Xcm;
}

void Database::LoadParts(std::map<std::string, ItemMenu>& parts)
{
   for (auto kvp : parts)
   {
      m_parts[kvp.first] = CreatePart(kvp.second);
   }

   auto nose = (NosePart*)GetPart("Nose");
   Geometry.ln = nose->L;
   Geometry.dn = nose->D;
   Geometry.nose = nose->GetShape();
   Geometry.Xb = Geometry.ln;

   auto body = (CylinderPart*)GetPart("Body");
   Geometry.lb = body->L;
   Geometry.db = body->D2;
   Geometry.Xc = Geometry.ln + Geometry.lb;

   auto fins = (FinSet*)GetPart("Fins");
   Geometry.n = fins->N;
   Geometry.df = fins->Df;
   Geometry.lr = fins->L;
   Geometry.lm = fins->Lm;
   Geometry.lt = fins->Lt;
   Geometry.ls = fins->Ls;
   Geometry.lts = fins->Lts;
   Geometry.Tf = fins->W;
   Geometry.Xf = fins->X;

   auto tail = (ConicalFrustrumPart*)GetPart("Tail");
   Geometry.du = tail->D1;
   Geometry.dd = tail->D2;
   Geometry.lc = tail->L;
   Geometry.ltr = Geometry.ln + Geometry.lb + Geometry.lc;

   Geometry.Update();

   Xrb = Geometry.ltr;
   Arb = Geometry.Ar;
}

Part* Database::CreatePart(ItemMenu& menu)
{
   auto shape = StringToShape(menu.AsString("Shape"));
   auto m = LoadItem(menu, "Mass");
   auto x = LoadItem(menu, "Offset");
   auto l = LoadItem(menu, "Length");

   switch (shape)
   {
   case Shape::Cylinder:
      return new CylinderPart(m, x, l, LoadItem(menu, "InnerDiameter"),
         LoadItem(menu, "OuterDiameter"));
   case Shape::Cube:
      return new CubePart(m, x, l, LoadItem(menu, "Width"));
   case Shape::ConicalFrustrum:
      return new ConicalFrustrumPart(m, x, l, LoadItem(menu, "LowerDiameter"),
         LoadItem(menu, "UpperDiameter"), LoadItem(menu, "Thickness"));
   case Shape::Trapezoid:
      return new FinSet(m, x, l, menu.AsInt("Count"),
         LoadItem(menu, "Thickness"), LoadItem(menu, "BodyDiameter"),
         LoadItem(menu, "TipChord"), LoadItem(menu, "MidChord"),
         LoadItem(menu, "Span"), LoadItem(menu, "TotalSpan"));
   case Shape::Ogive:
      return new OgiveNosePart(m, x, l, LoadItem(menu, "Diameter"));
   case Shape::Cone:
      return new ConicalNosePart(m, x, l, LoadItem(menu, "Diameter"));
   case Shape::Parabloid:
      return new ParabolicNosePart(m, x, l, LoadItem(menu, "Diameter"));
   default:
      throw invalid_argument("Unexpected shape: " + shape);
   }
}

void Database::SetT(double t)
{
   if (t == m_t)
   {
      return;
   }

   InterpolateDynamics(t, m_dynamics);
   Ft = m_dynamics.F;
   M = m_dynamics.M;
   Xcm = m_dynamics.Xcm;
   I = Matrix::Zero();
   I(0, 0) = m_dynamics.Ix;
   I(1, 1) = m_dynamics.Iy;
   I(2, 2) = m_dynamics.Iz;

   m_t = t;
}

void Database::SetS(const Vector& s)
{
   if (s.Z == m_z)
   {
      return;
   }

   // Update the atmospheric conditions
   const double p0 = 101325;
   const double rho0 = 1.225;
   const double T0 = 288.15;
   const double a0 = 340.294;
   const double g0 = 9.80665;
   const double R = 287.04;

   Tatm = T0 - 6.5 * s.Z / 1000.0;
   P = p0 * pow(1 - 0.0065 * s.Z / T0, 5.2561);
   Rho = P / (R * Tatm);

   m_z = s.Z;
}

void Database::SetA(double a)
{
   if (a == m_a)
   {
      return;
   }

   Xcp = Geometry.Xcp(a);

   m_a = a;
}

void Database::SetV(double v)
{
   if (v == m_v)
   {
      return;
   }

   Re = Reynolds(Rho, v, Xrb, Tatm);
   Ma = Mach(v, Tatm);
   Cn = Geometry.Cn(m_a, Re, Ma);
   Ca = Geometry.Ca(m_a, Re, Ma, Cn);

   m_v = v;
}