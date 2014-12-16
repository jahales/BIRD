#ifndef CSV_HPP
#define CSV_HPP

#include <fstream>
#include <iostream>
#include <string>
#include <map>
#include <vector>

class DataCsvReader
{
private:
   
   std::ifstream m_fileStream;

public:

   DataCsvReader(std::string file)
   {
      m_fileStream.open(file);

      if (m_fileStream.fail())
      {
         throw std::invalid_argument(file.c_str());
      }

      std::string header;
      getline(m_fileStream, header);
   }

   ~DataCsvReader()
   {
      m_fileStream.close();
   }

   bool NextRow(std::vector<double>& row)
   {
      if (m_fileStream.eof())
      {
         return false;
      }

      std::string buffer;
      std::getline(m_fileStream, buffer);
      row.clear();

      for (auto p = strtok((char*)buffer.c_str(), ","); p != nullptr; p = strtok(NULL, ","))
      {
         row.push_back(strtod(p, NULL));
      }

      return row.size() > 0;
   }
};

#endif
