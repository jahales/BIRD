#include "settings.hpp"
#include "pugixml.hpp"
#include <iostream>
#include <chrono>

using namespace std;
using namespace pugi;

double AttributeToDouble(const xml_node& node, const string& name)
{
   try
   {
      return stod(node.attribute(name.c_str()).value());
   }
   catch (invalid_argument)
   {
      return 0.0;
   }
}

Settings::Settings(string file) 
   : m_generator((int)chrono::system_clock::now().time_since_epoch().count())
{
   Load(file);
}

void Settings::Load(string file)
{
   xml_document doc;
   auto result = doc.load_file(file.c_str());

   if (result == 0)
   {
      throw exception(result.description());
   }

   for (auto child : doc.child("Simulation").children("Menu"))
   {
      ParseItemMenu(child);
   }
}

void Settings::ParseItemMenu(const xml_node& node)
{
   ItemMenu menu;
   menu.Name = node.attribute("Name").value();
   menu.Description = node.attribute("Description").value();
   menu.Type = node.attribute("Type").value();

   for (auto child : node.children("Item"))
   {
      ItemElement item;
      item.Name = child.attribute("Name").value();
      item.Value = child.attribute("Value").value();
      item.Error = child.attribute("Error").value();
      item.Description = child.attribute("Description").value();
      menu.Items[item.Name] = item;
   }

   m_menus[menu.Name] = menu;
}

ItemElement ItemMenu::Get(std::string item)
{
   try
   {
      return Items.at(item);
   }
   catch (std::out_of_range ex)
   {
      throw std::out_of_range("Unable to locate item " + item + " in menu " + Name);
   }
}

std::string ItemMenu::AsString(std::string item)
{
   return Get(item).Value;
}

double ItemMenu::AsDouble(std::string item)
{
   try
   {
      return stod(AsString(item));
   }
   catch (std::invalid_argument ex)
   {
      return 0.0;
   }
}

int ItemMenu::AsInt(std::string item)
{
   return (int)AsDouble(item);
}

ItemMenu Settings::Get(std::string name)
{
   try
   {
      return m_menus.at(name);
   }
   catch (std::out_of_range ex)
   {
      throw std::out_of_range("Unable to locate menu " + name + " in settings");
   }
}


std::map<std::string, ItemMenu> Settings::GetParts()
{
   map<string, ItemMenu> parts;

   for (auto kvp : m_menus)
   {
      if (kvp.second.Type != "Part")
      {
         continue;
      }

      parts[kvp.second.Name] = kvp.second;
   }

   return parts;
}

double Settings::GaussianRandom(double mean, double stdev)
{
   std::normal_distribution<double> distribution(mean, stdev);
   return abs(distribution(m_generator));
}