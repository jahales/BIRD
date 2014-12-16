#include "simulator.hpp"
#include "settings.hpp"
#include "database.hpp"
#include <iostream>
#include <string>
using namespace std;
#include <chrono>
#include <random>
#include <sstream>
#include <cstdlib>

void ConsoleMode();
void DisplayHelp();

int main(int argc, char** argv)
{
   if (argc < 3)
   {
      ConsoleMode();
      return 0;
   }

   for (int i = 1; i < argc; i++)
   {
      cout << argv[i] << endl;
   }
   
   Simulator simulator(argv[2]);

   if (string(argv[1]) == "mc")
   {
      simulator.RunMonteCarlo(atoi(argv[3]));
   }
   else if (string(argv[1]) == "run")
   {
      SimulationResult result;
      simulator.Run(true, false, 0.01, 0.1, result);
   }

   return 0;
}

void ConsoleMode()
{
   cout << "BYU-I Rocket Simulator by Jacob Hales" << endl << endl;
   DisplayHelp();
   string file = "..\\Simulation\\Simple Rocket (0).xml";

   while (true)
   {      
      string line;
      cout << "\n> ";
      getline(cin, line);

      if (line == "clear")
      {
         system("cls");
      }
      else if (line == "db")
      {
         /*db.ExportDragProfile("C:\\Temp\\DragProfile.csv");
         db.ExportAerodynamics("C:\\Temp\\Aerodynamics.csv");
         db.ExportDynamics("C:\\Temp\\Dynamics.csv");*/
      }
      else if (line == "help")
      {
         DisplayHelp();
      }
      else if (line == "monte")
      {
         Simulator simulator(file);
         simulator.RunMonteCarlo(1000);
      }
      else if (line == "quit")
      {
         return;
      }
      else if (line == "test")
      {
                 
      }
      else if (line == "run")
      {         
         Simulator simulator(file);
         SimulationResult result;
         simulator.Run(true, false, 0.0001, 0.1, result);
      }
      else
      {
         cout << "Unrecognized command: " << line << endl;         
      }
   }
}

void DisplayHelp()
{
   cout << "Available commands:" << endl << endl
      << "db: output the contents of the database" << endl
      << "clear: clears the display" << endl
      << "help: displays this help menu" << endl
      << "quit: quits the program" << endl
      << "run: runs the current simulation" << endl
      << "test: runs a predefined test" << endl;
}
