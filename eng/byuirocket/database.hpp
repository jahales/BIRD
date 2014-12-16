#ifndef DATABASE2_HPP
#define DATABASE2_HPP

#include "settings.hpp"
#include "part.hpp"
#include <random>
#include "math.hpp"

/***************************************************************************
* Time-based rocket data entry (related to time-thrust curve)
***************************************************************************/
struct DynamicsEntry
{
   double T;   // Time
   double F;   // Force of thrust
   double M;   // Mass
   double Xcm; // Center of mass
   double Ix;  // Moment of inertia (x)
   double Iy;  // Moment of inertia (y)
   double Iz;  // Moment of inertia (z)
   DynamicsEntry() : T(0), F(0), M(0), Xcm(0), Ix(0), Iy(0), Iz(0) { }
   DynamicsEntry(double t, double f) 
      : T(t), F(f), M(0), Xcm(0), Ix(0), Iy(0), Iz(0) { }
};

class Database
{
private:

   std::map<std::string, Part*> m_parts;
   std::vector<DynamicsEntry> m_thrust;

   double m_t = -1; // Time
   double m_a = -1; // Angle of attack
   double m_v = -1; // Relative velocity
   double m_z = -1; // Height
   bool m_random = false; // True to add random offsets to all values 
   DynamicsEntry m_dynamics;
   Settings* m_settings;
   
   Vector S; // Position

   void Load(Settings& settings);   
   void LoadEnvironment(ItemMenu& menu);
   void LoadParachute(ItemMenu& menu);
   void LoadLaunchRail(ItemMenu& menu);
   void LoadThrust(ItemMenu& menu);
   void LoadParts(std::map<std::string, ItemMenu>& parts);
   double LoadItem(ItemMenu& menu, std::string name);
   Part* CreatePart(ItemMenu& menu);
   void InitializeThrustDatabase();
   void InterpolateDynamics(double t, DynamicsEntry& e);

public:

   RocketGeometry Geometry;   

   const Vector AxisY0 = Vector(1, 0, 0);
   const Vector AxisP0 = Vector(0, 1, 0);
   const Vector AxisR0 = Vector(0, 0, 1);
   
   double Gu = 398600.4418e9; // Gravitational constant
   double Rearth = 6.371e6; // Mean radius of the earth
   double Elevation; // Elevation
   double Ft; // Thrust
   double M; // Mass
   double Ma; // Mach number
   double Re; // Reynolds number
   double Cn; // Coefficient of normal drag
   double Ca; // Coefficient of axial drag
   double Xcm; // Center of mass
   double Xcp; // Center of pressure
   double Xrb; // Rocket body length
   double Arb; // Rocket body cross-sectional area
   double Pap; // Parachute planform area
   double Pcd; // Parachute coefficient of drag
   double Lrl; // Launch rail length
   double Lrcf; // Launch rail coefficient of friction
   double Rho; // Atmospheric density
   double Tatm; // Atmostpheric temperature
   double Tthrust; // Maximum thrust time
   double P; // Air pressure
   Vector Wind; // Wind speed vector
   Vector Lru; // Launch rail orientation vector
   Matrix I; // Moment of intertia matrix

   Database(bool random, Settings& settings);
   ~Database();

   Part* GetPart(std::string name);
   void SetT(double t);
   void SetS(const Vector& s);
   void SetA(double a);
   void SetV(double v);
};

#endif
