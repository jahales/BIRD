#ifndef PART_HPP
#define PART_HPP

#include "dynamics.hpp"

/******************************************************************************
* Part: represents a part of a rocket (engine, nose cone, etc.)
******************************************************************************/
class Part
{
public:
   std::string Name;
   double M;
   double X;
   double L;
   virtual double Xcm() { return X + L / 2; }
   virtual double Ix() = 0;
   virtual double Iy() = 0;
   virtual double Iz() = 0;

   double Ix(double x) { return IxyzParallelAxis(Ix(), M, x, Xcm()); }
   double Iy(double x) { return IxyzParallelAxis(Iy(), M, x, Xcm()); }
   double Iz(double x) { return IxyzParallelAxis(Iz(), M, x, 0); }

   Part(double m, double x, double l) : M(m), X(x), L(l)
   {

   }
};

/******************************************************************************
* Represents a cylinder-shaped part
******************************************************************************/
class CylinderPart : public Part
{
public:
   double D1, D2;
   double Ix() { return IxyCylinder(M, R1(), R2(), L); }
   double Iy() { return IxyCylinder(M, R1(), R2(), L); }
   double Iz() { return IzCylinder(M, R1(), R2()); }
   double R1() { return D1 / 2.0; }
   double R2() { return D2 / 2.0; }

   CylinderPart() : Part(0, 0, 0), D1(0), D2(0)
   {

   }

   CylinderPart(double m, double x, double l, double d1, double d2) 
      : Part(m, x, l), D1(d1), D2(d2)
   {
   }
};

/******************************************************************************
* Represents a cube-shaped part
******************************************************************************/
class CubePart : public Part
{
public:
   double W;
   double Ix() { return IxCube(M, W, L); }
   double Iy() { return IyCube(M, W, L); }
   double Iz() { return IzCube(M, W, W); }

   CubePart(double m, double x, double l, double w)
      : Part(m, x, l), W(w)
   {
   }
};

/******************************************************************************
* Represents a conical frustrum-shaped part
******************************************************************************/
class ConicalFrustrumPart : public Part
{
public:
   double D1, D2, W;
   double Ix() { return IxyCylinder(M, R1(), R2(), L); }
   double Iy() { return IxyCylinder(M, R1(), R2(), L); }
   double Iz() { return IzCylinder(M, R1(), R2()); }
   double R1() { return D1 / 2.0; }
   double R2() { return D2 / 2.0; }

   ConicalFrustrumPart(double m, double x, double l, double d1, double d2, 
      double w)
      : Part(m, x, l), W(w), D1(d1), D2(d2)
   {
   }
};

/******************************************************************************
* Represents the nose of a rocket
******************************************************************************/
class NosePart : public Part
{
public:
   double D;
   double Xcm() { return (2.0 / 3.0) * L; }
   double Ix() { return IxyCone(M, R(), L); }
   double Iy() { return IxyCone(M, R(), L); }
   double Iz() { return IzCone(M, R()); }
   double R() { return D / 2.0; }
   virtual Shape GetShape() = 0;

   NosePart(double m, double x, double l, double d) : Part(m, x, l), D(d)
   {
   }
};

/******************************************************************************
* Conical nose
******************************************************************************/
class ConicalNosePart : public NosePart
{
public:
   Shape GetShape() { return Shape::Cone; }

   ConicalNosePart(double m, double x, double l, double d) : NosePart(m, x, l, d) 
   {
   }
};

/******************************************************************************
* Ogive nose nose
******************************************************************************/
class OgiveNosePart : public NosePart
{
public:
   Shape GetShape() { return Shape::Ogive; }

   OgiveNosePart(double m, double x, double l, double d)
      : NosePart(m, x, l, d)
   {
   }
};

/******************************************************************************
* Parabolic nose nose
******************************************************************************/
class ParabolicNosePart : public NosePart
{
public:
   Shape GetShape() { return Shape::Parabloid; }

   ParabolicNosePart(double m, double x, double l, double d)
      : NosePart(m, x, l, d)
   {
   }
};

/******************************************************************************
* Represents the fins of a rocket
******************************************************************************/
class FinSet : public Part
{
public:

   int N;
   double W, Df, Lt, Lm, Ls, Lts;

   double Ix() { return IxCube(M, W, L); }
   double Iy() { return IyCube(M, W, L); }
   double Iz() { return IzCube(M, W, W); }

   FinSet(double m, double x, double l, int n, double w, double df, 
      double lt, double lm, double ls, double lts) : Part(m, x, l),
      N(n), W(w), Df(df), Lt(lt), Lm(lm), Ls(ls), Lts(lts)
   {

   }
};

#endif