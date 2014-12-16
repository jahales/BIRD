#ifndef PHYSICS_HPP
#define PHYSICS_HPP

#include "common.hpp"
#include <iostream>
#include <string>
#include <sstream>

enum Shape
{
   Unknown, Cylinder, Tube, Cube, ConicalFrustrum, Trapezoid, Ogive, Cone, Parabloid
};

inline Shape StringToShape(std::string type)
{
   if (type == "Cylinder")
   {
      return Shape::Cylinder;
   }
   else if (type == "Cube")
   {
      return Shape::Cube;
   }
   else if (type == "Ogive")
   {
      return Shape::Ogive;
   }
   else if (type == "Cone")
   {
      return Shape::Cone;
   }
   else if (type == "Parabloid")
   {
      return Shape::Parabloid;
   }
   else if (type == "Trapezoid")
   {
      return Shape::Trapezoid;
   }
   else if (type == "ConicalFrustum")
   {
      return Shape::ConicalFrustrum;
   }

   throw std::invalid_argument("Unknown shape: " + type);
}

/******************************************************************************
* Applies the parallel axis theorem to a given moment of inertia
*    i: original moment of inertia
*    m: mass of the object
*    x: position of the axis
*    xcm: center of mass of the object
******************************************************************************/
inline double IxyzParallelAxis(double i, double m, double x, double xcm)
{
   return i + m * sqr(abs(x - xcm));
}

/******************************************************************************
* Determines the x and y moment of inertia of a cone
*    m: mass
*    r: radius of the base
*    l: length
******************************************************************************/
inline double IxyCone(double m, double r, double l)
{
   return m * ((sqr(l) / 10.0) + (3.0 * sqr(r) / 20.0));
}

/******************************************************************************
* Determines the z moment of inertia of a cone
*    m: mass
*    r: radius of the base
******************************************************************************/
inline double IzCone(double m, double r)
{
   return 3.0 * m * sqr(r) / 10.0;
}

/******************************************************************************
* Determines the x  moment of inertia of a cube
*    m: mass
*    y: y length
*    z: z length
******************************************************************************/
inline double IxCube(double m, double y, double z)
{
   return m * (sqr(y) + sqr(z)) / 12.0;
}

/******************************************************************************
* Determines the y  moment of inertia of a cube
*    m: mass
*    x: x length
*    z: z length
******************************************************************************/
inline double IyCube(double m, double x, double z)
{
   return m * (sqr(x)+ sqr(z)) / 12.0;
}

/******************************************************************************
* Determines the z  moment of inertia of a cube
*    m: mass
*    x: x length
*    y: y length
******************************************************************************/
inline double IzCube(double m, double x, double y)
{
   return m * (sqr(x) + sqr(y)) / 12.0;
}

/******************************************************************************
* Determines the x and y moment of inertia of a cylinder
*    m: mass
*    r1: inner radius
*    r2: outer radius
*    h: height
******************************************************************************/
inline double IxyCylinder(double m, double r1, double r2, double h)
{
   return (m / 12.0) * (3.0 * (sqr(r2) + sqr(r1)) + sqr(h));
}

/******************************************************************************
* Determines the z moment of inertia of a cylinder
*    m: mass
*    r1: inner radius
*    r2: outer radius
******************************************************************************/
inline double IzCylinder(double m, double r1, double r2)
{
   return (m / 2.0) * (sqr(r2) + sqr(r1));
}

/******************************************************************************
* Approximates axial force based on the drag and normal force coefficients
*    cd: coefficient of drag force
*    cn: coefficient of normal force
*    a: angle of attack
******************************************************************************/
inline double DragToAxialForce(double cd, double cn, double a)
{
   return cd * cos(a);
   /*double c1 = cos(a);
   double c2 = sin(a);
   double c3 = cd * cos(a);
   double c4 = 0.5 * cn * sin(2 * a);
   double c5 = 1 - sqr(sin(a));
   return (cd * cos(a) - 0.5 * cn * sin(2 * a)) / (1 - sqr(sin(a)));*/
}

/******************************************************************************
* Reynolds number (air approximation)
*    rho: atmospheric density
*    v: apparent velocity vector
*    l: characteristic dimension (total body length or fin mid chord)
*    t: atmospheric temperature
******************************************************************************/
inline double Reynolds(double rho, double v, double l, double t)
{
   const double lambda = 1.512041288e-6; // Sutherland gas constant for air
   const double c = 120.0; // Sutherland's constant
   double mu = lambda * pow(t, 3.0 / 2.0) / (t + c);
   return rho * v * l / mu;
}

/******************************************************************************
* Mach number (for air)
*    v: apparent velocity
*    t: atmospheric temperature
******************************************************************************/
inline double Mach(double v, double t)
{
   const double g = 1.4; // Ratio of specific heats for air
   const double r = 287.0; // Gas constant for air (diff. in specific heats)
   return v / sqrt(g * r * t);
}

/******************************************************************************
* Corrects drag coefficients based on mach number
*    c: drag coefficient value
*    m: mach number
******************************************************************************/
inline double CompressibilityCorrection(double c, double m)
{
   if (m < 0.8)
   {
      return c / sqrt(1 - sqr(m));
   }
   else if (m > 1.1)
   {
      return c / sqrt(sqr(m) - 1);
   }
   else
   {
      return c / sqrt(1 - sqr(0.8));
   }
}

/******************************************************************************
* Viscous drag coefficient
*    re: Reynolds number
******************************************************************************/
inline double ViscousDrag(double re)
{
   const double Re0 = 5e5; // Critical Reynolds number
   double c1 = (1.328 / sqrt(re));

   if (re <= Re0)
   {
      return re >= 1 ? c1 : 0; // TODO: is this a good assumption?
   }
   else
   {
      double c2 = (0.074 / pow(re, 1.0 / 5.0));
      return  c2 - (Re0 * (c2 - c1) / re);
   }
}

/******************************************************************************
* Polynomial approximation for delta alpha drag component
*    a: angle of attack
******************************************************************************/
inline double Delta(double a)
{
   const double c[] = { 0.0001, 23.991, -289.02, 1950.5, -7337.5, 14290.0,
      -11198.0 };

   double result = 0;

   for (int i = 0; i <= 6; i++)
   {
      result += c[i] * pow(a, i);
   }

   return 0;
   return result;
}

/******************************************************************************
* Polynomial approximation for eta alpha drag component
*    a: angle of attack
******************************************************************************/
inline double Eta(double a)
{
   const double c[] = { 4.0e-5, 21.168, -293.39, 2111.4, -8071.8, 15624.0,
      -12060.0 };

   double result = 0;

   for (int i = 0; i <= 6; i++)
   {
      result += c[i] * pow(a, i);
   }

   return 0;
   return result;
}

/******************************************************************************
* Variables determined by the geometry of a rocket
******************************************************************************/
struct RocketGeometry
{
   const double K = 1.0;
   Shape nose;
   double Xb;
   double Xf;
   double Xc;
   double ln;
   double lb;
   double lc;
   double lr;
   double lm;
   double lt;
   double ls;
   double lts;
   double ltr;
   double dn;
   double db;
   double df;
   double du;
   double dd;
   double n;
   double Tf;
   double Ap;
   double Ar;
   double KCna1;
   double KCna2;
   double KXcpa1;
   double KXcpa2;

   void Display();
   void Update();

   double ApN()
   {
      switch (nose)
      {
      case Shape::Cone: return 0.5 * ln * dn;
      case Shape::Ogive: return (2.0 / 3.0) * ln * dn;
      case Shape::Parabloid: return (2.0 / 3.0) * ln * dn;
      default: return 0;
      }
   }
   double ApB() { return db * lb; }
   double ApT() { return lc * (du + dd) / 2.0; }
   double ApF() { return 0; }

   double Afe() { return 0.5 * (lr + lt) * ls; }
   double Afp() { return Afe() + 0.5 * df * lr; }

   double NCnaN() { return 2.0; }
   double NCnaB() { return 0.0; }
   double NCnaT() { return 2.0 * (sqr(dd / dn) - sqr(du / dn)); }
   double NCnaF()
   {
      double kfb = 1 + (df / 2.0) / (ls + df / 2.0);
      double c1 = 4.0 * n * sqr(ls / dn);
      double c2 = 1 + sqrt(1 + sqr(2 * lm / (lr + lt)));
      return kfb * c1 / c2;
   }

   double LCnaN() { return K * ApN() / Ar; }
   double LCnaB() { return K * ApB() / Ar; }
   double LCnaT() { return K * ApT() / Ar; }
   double LCnaF() { return 0.0; }

   double NXcpN()
   {
      switch (nose)
      {
      case Shape::Cone: return (2.0 / 3.0) * ln;
      case Shape::Ogive: return 0.466 * ln;
      case Shape::Parabloid: return (1.0 / 2.0) * ln;
      default: return 0;
      }
   }
   double NXcpB() { return 0.0; }
   double NXcpT()
   {
      if (du == 0 || dd == 0 || lc == 0) return 0;
      return Xc + (lc / 3.0) * (1 + ((1 - du / dd) / (1 - sqr(du / dd))));
   }
   double NXcpF()
   {
      double c1 = (lm * (lr + 2.0 * lt)) / (3.0 * (lr + lt));
      double c2 = (1.0 / 6.0) * (lr + lt - (lr * lt) / (lr + lt));
      return Xf + c1 + c2;
   }

   double LXcpN()
   {
      switch (nose)
      {
      case Shape::Cone: return (2.0 / 3.0) * ln;
      case Shape::Ogive: return (5.0 / 8.0) * ln;
      case Shape::Parabloid: return (3.0 / 5.0) * ln;
      default: return 0;
      }
   }
   double LXcpB() { return Xb + 0.5 * lb; }
   double LXcpT()
   {
      if (du == 0 || dd == 0 || lc == 0) return 0;
      return Xc + (lc / 3.0) * ((du + 2.0 * dd) / (du + dd));
   }
   double LXcpF() { return 0.0; }

   double DragBody(double cf)
   {
      double c1 = 1.0;
      double c2 = 60.0 / pow(ltr / db, 3);
      double c3 = 0.0025 * (lb / db);
      double c4 = 2.7 * (ln / db);
      double c5 = 4.0 * (lb / db);
      double c6 = 2.0 * (1 - (dd / db)) * (lc / db);
      return (c1 + c2 + c3) * (c4 + c5 + c6) * cf;
   }
   double DragBase(double cdfb)
   {
      if (cdfb == 0) return 0;
      double dddb = (du == 0 || dd == 0 || lc == 0) ? 1.0 : dd / db;
      return 0.029 * pow(dddb, 3) / sqrt(cdfb);
   }
   double DragFin(double cf)
   {
      double afp = Afp();
      double c1 = (1.0 + 2.0 * (Tf / lm));
      double c2 = 4.0 * n * afp / (PI * sqr(df));
      return 2.0 * cf * c1 * c2;
   }
   double DragInterference(double cf)
   {
      double afp = Afp();
      double afe = Afe();
      double c1 = (1.0 + 2.0 * (Tf / lm));
      double c2 = 4.0 * n * (afp - afe) / (PI * sqr(df));
      return 2.0 * cf * c1 * c2;
   }
   double DragAlphaBody(double a)
   {
      double delta = Delta(a);
      double eta = Eta(a);

      double c1 = 2 * delta * sqr(a);
      double c2 = 3.6 * cube(a) * eta * ((1.36 * ltr) - (0.55 * ln)) /
         (PI * db);
      return c1 + c2;
   }
   double DragAlphaFin(double a)
   {
      double afp4 = Afp() * Afp() * Afp() * Afp();
      double afe4 = Afe() * Afe() * Afe() * Afe();
      double rs = lts / df;
      double kfb = 0.8065 * sqr(rs) + 1.1553 * rs;
      double kbf = 0.1935 * sqr(rs) + 0.8174 * rs + 1.0;
      double c1 = sqr(a) / (PI * sqr(df));
      double c2 = 1.2 * afp4;
      double c3 = 3.12 * (kfb + kbf - 1.0) * afe4;
      return c1 * (c2 + c3);
   }

   double Xcp(double a);
   double Cn(double a, double r, double m);
   double Ca(double a, double r, double m, double cn);

   void StabilityConstants(double& kcna1, double& kcna2, double& kxcpa1, double& kxcpa2);
};

inline void RocketGeometry::Update()
{
   Ar = PI * sqr(dn / 2);
   Ap = ApB() + ApF() + ApN() + ApT();
   StabilityConstants(KCna1, KCna2, KXcpa1, KXcpa2);
}

inline void RocketGeometry::StabilityConstants(double& kcna1, double& kcna2, double& kxcpa1, double& kxcpa2)
{
   double ncna[] = { NCnaB(), NCnaF(), NCnaN(), NCnaT() };
   double lcna[] = { LCnaB(), LCnaF(), LCnaN(), LCnaT() };
   double nxcp[] = { NXcpB(), NXcpF(), NXcpN(), NXcpT() };
   double lxcp[] = { LXcpB(), LXcpF(), LXcpN(), LXcpT() };
   kcna1 = kcna2 = kxcpa1 = kxcpa2 = 0;

   for (int i = 0; i < 4; i++)
   {
      kcna1 += ncna[i];
      //kcna2 += lcna[i];
      kxcpa1 += ncna[i] * nxcp[i];
      //kxcpa2 += lcna[i] * lxcp[i];
   }

   kcna2 = kxcpa2 = 0;
}

/******************************************************************************
* Calculate the center of pressure on the rocket
*    a: angle of attack
*    cp: center of pressure
******************************************************************************/
inline double RocketGeometry::Xcp(double a)
{
   return (KXcpa1 + (KXcpa2 * a)) / (KCna1 + (KCna2 * a));
}

/******************************************************************************
* Calculate the total normal drag coefficient and center of pressure
*    g: rocket geometry
*    a: angle of attack
*    r: Reynolds number
*    m: mach number
*    cn: coefficient of normal drag
*    cp: center of pressure
******************************************************************************/
inline double RocketGeometry::Cn(double a, double r, double m)
{
   auto cn = (KCna1 + (KCna2 * a)) * a;
   return CompressibilityCorrection(cn, m);
}

/******************************************************************************
* Calculate the total axial drag coefficient
*    g: rocket geometry
*    a: angle of attack
*    r: Reynolds number
*    m: mach number
*    cn: coefficient of normal drag
*    ca: coefficient of axial drag
******************************************************************************/
inline double RocketGeometry::Ca(double a, double r, double m, double cn)
{
   double cf = ViscousDrag(r);
   double cdfb = DragBody(cf);
   double cdb = DragBase(cdfb);
   double cdf = DragFin(cf);
   double cdi = DragInterference(cf);
   double cdba = DragAlphaBody(a);
   double cdfa = DragAlphaFin(a);
   double cd = cdfb + cdb + cdf + cdi + cdba + cdfa;
   double ca = DragToAxialForce(cd, cn, a);
   double cca = CompressibilityCorrection(ca, m);
   return cca;
}

inline void RocketGeometry::Display()
{
   double v = 30;
   double t = 273.15 + 20;
   double rho = 1.225;
   double r = Reynolds(rho, v, ltr, t);
   double a = 0.01;
   double m = Mach(v, t);
   double cf = ViscousDrag(r);
   double xcp = Xcp(a);
   double cn = Cn(a, r, m);
   double ca = Ca(a, r, m, cn);

   std::cout << "Rocket Geometry: "
      << "\n\nPlanform Areas"
      << "\n - ApN: " << ApN()
      << "\n - ApB: " << ApB()
      << "\n - ApT: " << ApT()
      << "\n - ApF: " << ApF()
      << "\n - Afe: " << Afe()
      << "\n - Afp: " << Afp()
      << "\n\nStability Derivatives"
      << "\n - CnaN: " << NCnaN()
      << "\n - CnaB: " << NCnaB()
      << "\n - CnaT: " << NCnaT()
      << "\n - CnaF: " << NCnaF()
      << "\n\nNorm Force CP"
      << "\n - NcpN: " << NXcpN()
      << "\n - NcpB: " << NXcpB()
      << "\n - NcpT: " << NXcpT()
      << "\n - NcpF: " << NXcpF()
      << "\n\nLift Force CP"
      << "\n - LcpN: " << LXcpN()
      << "\n - LcpB: " << LXcpB()
      << "\n - LcpT: " << LXcpT()
      << "\n - LcpF: " << LXcpF()
      << "\n\nDrag Force"
      << "\n - DragB: " << DragBody(cf)
      << "\n - DragA: " << DragBase(DragBody(cf))
      << "\n - DragF: " << DragFin(cf)
      << "\n - DragI: " << DragInterference(cf)
      << "\n - DragBA: " << DragAlphaBody(a)
      << "\n - DragFA: " << DragAlphaFin(a)
      << "\n\nResults"
      << "\n - Cn: " << cn
      << "\n - Ca: " << ca
      << "\n - Xcp: " << xcp
      << std::endl;
}

#endif