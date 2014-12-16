#ifndef SETTINGS_HPP
#define SETTINGS_HPP

#include <string>
#include <vector>
#include <map>
#include <random>
#include "pugixml.hpp"
#include "dynamics.hpp"

struct ItemElement
{
   std::string Name;
   std::string Value;
   std::string Error;
   std::string Description;
};

struct ItemMenu
{
   std::string Name;  
   std::string Description;
   std::string Type;
   std::map<std::string, ItemElement> Items;

   ItemElement Get(std::string item);
   std::string AsString(std::string item);
   double AsDouble(std::string item);
   int AsInt(std::string item);
};

class Settings
{
private:

   std::mt19937 m_generator;
   std::map<std::string, ItemMenu> m_menus;

   void Load(std::string file);
   void ParseItemMenu(const pugi::xml_node& node);

public:

   Settings(std::string file);

   ItemMenu Get(std::string name);
   std::map<std::string, ItemMenu> GetParts();

   double GaussianRandom(double mean, double stdev);
};

#endif