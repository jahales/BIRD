#ifndef MODEL_HPP
#define MODEL_HPP

#include <vector>
#include <ostream>
#include <string>
#include <sstream>
#include "database.hpp"
#include "database.hpp"
#include "math.hpp"

class Output;

class ModelState
{
public:

   double T;
   Vector S;
   Vector P;
   Vector L;
   Quaternion Q;

   ModelState& operator=(const ModelState& rhs);
   const ModelState operator+(const ModelState& other) const;
   ModelState& operator+=(const ModelState& rhs);
   const ModelState operator-(const ModelState& other) const;
   ModelState& operator-=(const ModelState& rhs);
   const ModelState operator*(const ModelState& other) const;
   ModelState& operator*=(const ModelState& rhs);
   const ModelState operator*(double n) const;
   ModelState& operator*=(double n);
   friend std::ostream& operator<<(std::ostream &out, ModelState& state);

   ModelState() : T(0), S(0, 0, 0), P(0, 0, 0), L(0, 0, 0), Q(0, 0, 0, 0)
   {

   }
};

class Model
{
protected:

   Database* m_db;

public:

   Model(Database* db) : m_db(db) { }
   void Integrate(double dt, ModelState& state, double recordInterval, std::vector<ModelState>& history);
   virtual bool IsFinished(double t, const ModelState& state) = 0;
   virtual ModelState Differentiate(double t, const ModelState& state) = 0;
   Output GetOutput(double t, const ModelState& state);
   std::vector<Output> GetOutput(const std::vector<ModelState>& state);
};

class LaunchModel : public Model
{
public:

   LaunchModel(Database* db) : Model(db) { }
   bool IsFinished(double t, const ModelState& state);
   ModelState Differentiate(double t, const ModelState& state);
};

class AscentModel : public Model
{
public:

   AscentModel(Database* db) : Model(db) { }
   bool IsFinished(double t, const ModelState& state);
   ModelState Differentiate(double t, const ModelState& state);
};

class ParachuteModel : public Model
{
public:

   ParachuteModel(Database* db) : Model(db) { }
   bool IsFinished(double t, const ModelState& state);
   ModelState Differentiate(double t, const ModelState& state);
};

class Output
{
public:

   double Time;
   double Altitude;
   double VerticalVelocity;
   double VerticalAcceleration;
   double TotalVelocity;
   double TotalAcceleration;
   double PositionUpwind;
   double PositionParallelToWind;
   double LateralDistance;
   double LateralDirection;
   double LateralVelocity;
   double LateralAcceleration;
   double Latitude;
   double Longitude;
   double GravitationalAcceleration;
   double AngleOfAttack;
   double RollRate;
   double YawRate;
   double PitchRate;
   double Mass;
   double PropellantMass;
   double LongitudinalMomentOfInertia;
   double RotationalMomentOfInertia;
   double CPLocation;
   double CGLocation;
   double StabilityMarginCalibers;
   double MachNumber;
   double ReynoldsNumber;
   double Thrust;
   double DragForce;
   double DragCoefficient;
   double AxialDragCoefficient;
   double FrictionDragCoefficient;
   double PressureDragCoefficient;
   double BaseDragCoefficient;
   double NormalForceCoefficient;
   double PitchMomentCoefficient;
   double YawMomentCoefficient;
   double SideForceCoefficient;
   double RollMomentCoefficient;
   double RollForcingCoefficient;
   double RollDampingCoefficient;
   double PitchDampingCoefficient;
   double ReferenceLength;
   double ReferenceArea;
   double VerticalOrientationZenith;
   double LateralOrientationAzimuth;
   double WindVelocity;
   double AirTemperature;
   double AirPressure;
   double SpeedOfSound;

   Output()
   {
      Time = 0;
      Altitude = 0;
      VerticalVelocity = 0;
      VerticalAcceleration = 0;
      TotalVelocity = 0;
      TotalAcceleration = 0;
      PositionUpwind = 0;
      PositionParallelToWind = 0;
      LateralDistance = 0;
      LateralDirection = 0;
      LateralVelocity = 0;
      LateralAcceleration = 0;
      Latitude = 0;
      Longitude = 0;
      GravitationalAcceleration = 0;
      AngleOfAttack = 0;
      RollRate = 0;
      YawRate = 0;
      PitchRate = 0;
      Mass = 0;
      PropellantMass = 0;
      LongitudinalMomentOfInertia = 0;
      RotationalMomentOfInertia = 0;
      CPLocation = 0;
      CGLocation = 0;
      StabilityMarginCalibers = 0;
      MachNumber = 0;
      ReynoldsNumber = 0;
      Thrust = 0;
      DragForce = 0;
      DragCoefficient = 0;
      AxialDragCoefficient = 0;
      FrictionDragCoefficient = 0;
      PressureDragCoefficient = 0;
      BaseDragCoefficient = 0;
      NormalForceCoefficient = 0;
      PitchMomentCoefficient = 0;
      YawMomentCoefficient = 0;
      SideForceCoefficient = 0;
      RollMomentCoefficient = 0;
      RollForcingCoefficient = 0;
      RollDampingCoefficient = 0;
      PitchDampingCoefficient = 0;
      ReferenceLength = 0;
      ReferenceArea = 0;
      VerticalOrientationZenith = 0;
      LateralOrientationAzimuth = 0;
      WindVelocity = 0;
      AirTemperature = 0;
      AirPressure = 0;
      SpeedOfSound = 0;
   }

   std::string Header()
   {
      std::stringstream ss;

      ss << "Time, "
         << "Altitude, "
         << "VerticalVelocity, "
         << "VerticalAcceleration, "
         << "TotalVelocity, "
         << "TotalAcceleration, "
         << "PositionUpwind, "
         << "PositionParallelToWind, "
         << "LateralDistance, "
         << "LateralDirection, "
         << "LateralVelocity, "
         << "LateralAcceleration, "
         << "Latitude, "
         << "Longitude, "
         << "GravitationalAcceleration, "
         << "AngleOfAttack, "
         << "RollRate, "
         << "YawRate, "
         << "PitchRate, "
         << "Mass, "
         << "PropellantMass, "
         << "LongitudinalMomentOfInertia, "
         << "RotationalMomentOfInertia, "
         << "CPLocation, "
         << "CGLocation, "
         << "StabilityMarginCalibers, "
         << "MachNumber, "
         << "ReynoldsNumber, "
         << "Thrust, "
         << "DragForce, "
         << "DragCoefficient, "
         << "AxialDragCoefficient, "
         << "FrictionDragCoefficient, "
         << "PressureDragCoefficient, "
         << "BaseDragCoefficient, "
         << "NormalForceCoefficient, "
         << "PitchMomentCoefficient, "
         << "YawMomentCoefficient, "
         << "SideForceCoefficient, "
         << "RollMomentCoefficient, "
         << "RollForcingCoefficient, "
         << "RollDampingCoefficient, "
         << "PitchDampingCoefficient, "
         << "ReferenceLength, "
         << "ReferenceArea, "
         << "VerticalOrientationZenith, "
         << "LateralOrientationAzimuth, "
         << "WindVelocity, "
         << "AirTemperature, "
         << "AirPressure, "
         << "SpeedOfSound" << std::endl;

      return ss.str();
   }

   std::string Row()
   {
      std::stringstream ss;

      ss << Time << ", "
         << Altitude << ", "
         << VerticalVelocity << ", "
         << VerticalAcceleration << ", "
         << TotalVelocity << ", "
         << TotalAcceleration << ", "
         << PositionUpwind << ", "
         << PositionParallelToWind << ", "
         << LateralDistance << ", "
         << LateralDirection << ", "
         << LateralVelocity << ", "
         << LateralAcceleration << ", "
         << Latitude << ", "
         << Longitude << ", "
         << GravitationalAcceleration << ", "
         << AngleOfAttack << ", "
         << RollRate << ", "
         << YawRate << ", "
         << PitchRate << ", "
         << Mass << ", "
         << PropellantMass << ", "
         << LongitudinalMomentOfInertia << ", "
         << RotationalMomentOfInertia << ", "
         << CPLocation << ", "
         << CGLocation << ", "
         << StabilityMarginCalibers << ", "
         << MachNumber << ", "
         << ReynoldsNumber << ", "
         << Thrust << ", "
         << DragForce << ", "
         << DragCoefficient << ", "
         << AxialDragCoefficient << ", "
         << FrictionDragCoefficient << ", "
         << PressureDragCoefficient << ", "
         << BaseDragCoefficient << ", "
         << NormalForceCoefficient << ", "
         << PitchMomentCoefficient << ", "
         << YawMomentCoefficient << ", "
         << SideForceCoefficient << ", "
         << RollMomentCoefficient << ", "
         << RollForcingCoefficient << ", "
         << RollDampingCoefficient << ", "
         << PitchDampingCoefficient << ", "
         << ReferenceLength << ", "
         << ReferenceArea << ", "
         << VerticalOrientationZenith << ", "
         << LateralOrientationAzimuth << ", "
         << WindVelocity << ", "
         << AirTemperature << ", "
         << AirPressure << ", "
         << SpeedOfSound << std::endl;

      return ss.str();
   }
};

#endif