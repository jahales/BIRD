#ifndef SIMULATOR_HPP
#define SIMULATOR_HPP

#include <fstream>
#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
#include <numeric>
#include "database.hpp"
#include "dynamics.hpp"
#include "pugixml.hpp"
#include "model.hpp"
#include "settings.hpp"
using namespace std;
using namespace pugi;

#include "settings.hpp"
#include "database.hpp"

inline void Stdev(double& mean, double& stdev, vector<double> v)
{
   double sum = std::accumulate(v.begin(), v.end(), 0.0);
   mean = sum / v.size();

   double sq_sum = std::inner_product(v.begin(), v.end(), v.begin(), 0.0);
   stdev = std::sqrt(sq_sum / v.size() - mean * mean);
}

struct SimulationResult
{
   ModelState Launch;
   ModelState Apogee;
   ModelState Landing;
   std::vector<ModelState> History;
};

class Simulator
{
private:

   Settings* m_settings;

   void Print(std::vector<Output>& a, std::vector<Output>& b, std::vector<Output>& c)
   {      
      std::ofstream fout(m_settings->Get("Settings").AsString("OutputFileName"));

      if (fout.fail())
      {
         std::cerr << "Failed to open output file." << std::endl;
      }

      Output o;

      fout << o.Header();

      for (auto o : a)
      {
         fout << o.Row();
      }

      for (auto o : b)
      {
         fout << o.Row();
      }

      for (auto o : c)
      {
         fout << o.Row();
      }
   }

public:

   void Run(bool exportCsv, bool monteCarlo, double dt, double recordInterval, SimulationResult& result);
   void GenerateFlightPlot();
   void RunMonteCarlo(int iterations);
   SimulationResult Run(bool mc, double dt);

   Simulator(std::string settingsFile)
   {
      m_settings = new Settings(settingsFile);
   }
};

void Simulator::Run(bool exportCsv, bool monteCarlo, double dt, double recordInterval, SimulationResult& result)
{
   ModelState state;
   state.T = 0;
   state.P = Vector(0, 0, 0);
   state.L = Vector(0, 0, 0);
   state.S = Vector(0, 0, 0);
   state.Q = Quaternion(1, 0, 0, 0);

   Database m_db(monteCarlo, *m_settings);

   LaunchModel launch(&m_db);
   launch.Integrate(dt, state, recordInterval, result.History);
   result.Launch = state;

   AscentModel ascent(&m_db);
   ascent.Integrate(dt, state, recordInterval, result.History);
   result.Apogee = state;

   if (exportCsv)
   {
      std::cout << "Apogee: " << state.S.Z << ", " << state.S.X << ", " << state.S.Y << std::endl;
   }

   ParachuteModel parachute(&m_db);
   parachute.Integrate(dt, state, recordInterval, result.History);
   result.Landing = state;

   if (exportCsv)
   {
      std::cout << "Landing position: " << state.S.X << ", " << state.S.Y << std::endl << std::endl;
   }

   if (exportCsv)
   {
      Print(ascent.GetOutput(result.History), std::vector<Output>(), std::vector<Output>());
   }
}

void Simulator::RunMonteCarlo(int iterations)
{
   double dt = 0.001;
   double recordInterval = 0.1;
   vector<SimulationResult> results;

   for (size_t i = 0; i < iterations; i++)
   {
      results.push_back(SimulationResult());
      Run(false, true, dt, recordInterval, results.back());
      
      if (i % 100 == 0 && i > 0)
      {
         std::cout << i << " / " << iterations << std::endl;
      }
   }

   std::cout << std::endl;

   // Make all of the results have the same history size
   int maxHistory = 0;

   for (size_t i = 0; i < results.size(); i++)
   {
      if ((int)results[i].History.size() > maxHistory) maxHistory = results[i].History.size();
   }

   for (size_t i = 0; i < results.size(); i++)
   {
      while ((int)results[i].History.size() < maxHistory) results[i].History.push_back(results[i].Landing);
   }

   // Calculate the mean trajectory
   SimulationResult mean;  
   mean.History.resize(maxHistory);

   for (size_t i = 0; i < results.size(); i++)
   {
      mean.Launch += results[i].Launch;
      mean.Apogee += results[i].Apogee;
      mean.Landing += results[i].Landing;

      for (size_t j = 0; j < mean.History.size(); j++)
      {
          mean.History[j] += results[i].History[j];
      }
   }

   mean.Launch *= 1.0 / results.size();
   mean.Apogee *= 1.0 / results.size();
   mean.Landing *= 1.0 / results.size();

   for (size_t i = 0; i < mean.History.size(); i++)
   {
      mean.History[i] *= 1.0 / results.size();
   }

   // Calculate the standard deviation
   SimulationResult stdev;
   stdev.History.resize(maxHistory);

   for (size_t i = 0; i < results.size(); i++)
   {
      ModelState d1 = mean.Launch - results[i].Launch;
      ModelState d2 = mean.Apogee - results[i].Apogee;
      ModelState d3 = mean.Landing - results[i].Landing;
      stdev.Launch += d1 * d1;
      stdev.Apogee += d2 * d2;
      stdev.Landing += d3 * d3;

      for (size_t j = 0; j < stdev.History.size(); j++)
      {
         ModelState d = mean.History[j] - results[i].History[j];
         stdev.History[j] += d * d;
      }
   }

   stdev.Launch *= 1.0 / (results.size() - 1);
   stdev.Launch.S = Vector(sqrt(stdev.Launch.S.X), sqrt(stdev.Launch.S.Y), sqrt(stdev.Launch.S.Z));
   stdev.Launch.P = Vector(sqrt(stdev.Launch.P.X), sqrt(stdev.Launch.P.Y), sqrt(stdev.Launch.P.Z));
   stdev.Launch.L = Vector(sqrt(stdev.Launch.L.X), sqrt(stdev.Launch.L.Y), sqrt(stdev.Launch.L.Z));

   stdev.Apogee *= 1.0 / (results.size() - 1);
   stdev.Apogee.S = Vector(sqrt(stdev.Apogee.S.X), sqrt(stdev.Apogee.S.Y), sqrt(stdev.Apogee.S.Z));
   stdev.Apogee.P = Vector(sqrt(stdev.Apogee.P.X), sqrt(stdev.Apogee.P.Y), sqrt(stdev.Apogee.P.Z));
   stdev.Apogee.L = Vector(sqrt(stdev.Apogee.L.X), sqrt(stdev.Apogee.L.Y), sqrt(stdev.Apogee.L.Z));

   stdev.Landing *= 1.0 / (results.size() - 1);
   stdev.Landing.S = Vector(sqrt(stdev.Landing.S.X), sqrt(stdev.Landing.S.Y), sqrt(stdev.Landing.S.Z));
   stdev.Landing.P = Vector(sqrt(stdev.Landing.P.X), sqrt(stdev.Landing.P.Y), sqrt(stdev.Landing.P.Z));
   stdev.Landing.L = Vector(sqrt(stdev.Landing.L.X), sqrt(stdev.Landing.L.Y), sqrt(stdev.Landing.L.Z));

   for (size_t i = 0; i < mean.History.size(); i++)
   {
      stdev.History[i] *= 1.0 / (maxHistory - 1);
      stdev.History[i].S = Vector(sqrt(stdev.History[i].S.X), sqrt(stdev.History[i].S.Y), sqrt(stdev.History[i].S.Z));
      stdev.History[i].P = Vector(sqrt(stdev.History[i].P.X), sqrt(stdev.History[i].P.Y), sqrt(stdev.History[i].P.Z));
      stdev.History[i].L = Vector(sqrt(stdev.History[i].L.X), sqrt(stdev.History[i].L.Y), sqrt(stdev.History[i].L.Z));
   }

   // Print out the statistics
   cout << std::endl << "Results" << std::endl << "----------------------------------------" << std::endl;

   cout << "Apogee: " << std::endl
      << "X: " << mean.Apogee.S.X << " +/- " << stdev.Apogee.S.X << std::endl
      << "Y: " << mean.Apogee.S.Y << " +/- " << stdev.Apogee.S.Y << std::endl
      << "Z: " << mean.Apogee.S.Z << " +/- " << stdev.Apogee.S.Z << std::endl << std::endl;

   cout << "Landing: " << std::endl
      << "X: " << mean.Landing.S.X << " +/- " << stdev.Landing.S.X << std::endl
      << "Y: " << mean.Landing.S.Y << " +/- " << stdev.Landing.S.Y << std::endl
      << "Z: " << mean.Landing.S.Z << " +/- " << stdev.Landing.S.Z << std::endl << std::endl;


   std::ofstream fout(m_settings->Get("Settings").AsString("OutputFileName"));

   if (fout.fail())
   {
      std::cerr << "Failed to open output file." << std::endl;
   }

   fout << "t, X, dX, Y, dY, Z, dZ" << endl;

   for (int i = 0; i < maxHistory; i++)
   {
      ModelState h = mean.History[i];
      ModelState s = stdev.History[i];

      fout << i * recordInterval << ", " << h.S.X << ", " << s.S.X << ", " << h.S.Y << ", " << s.S.Y << ", " << h.S.Z << ", " << s.S.Z << endl;
   }
}

#endif