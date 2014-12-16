#ifndef COMMON_HPP
#define COMMON_HPP

#include <cmath>
#include <ctime>
#include <sstream>
#include <iostream>

const double PI = 3.1415926535897932384626433832795028841971693993751058209749;

inline double sqr(double a) { return a * a; }
inline double cube(double a) { return a * a * a; }
inline double min(double a, double b) { return a < b ? a : b; }
inline double max(double a, double b) { return a > b ? a : b; }

inline double sum(double *a, int n)
{
   double result = 0;

   for (int i = 0; i < n; i++)
   {
      result += a[i];
   }

   return result;
}

inline std::string TimeStamp()
{
   #pragma warning( disable : 4996 )
   std::stringstream ss;
   time_t now = time(0);
   tm *t = localtime(&now);
   ss << 1900 + t->tm_year;
   if (t->tm_mon + 1 < 10) ss << 0;
   ss << t->tm_mon + 1;
   if (t->tm_mday < 10) ss << 0;
   ss << t->tm_mday;
   if (t->tm_hour < 10) ss << 0;
   ss << t->tm_hour;
   if (t->tm_min < 10) ss << 0;
   ss << t->tm_min;
   if (t->tm_sec < 10) ss << 0;
   ss << t->tm_sec;
   return ss.str();
}

inline double BoundedAcos(double val)
{
   if (val > 1.0)
   {
      return 0;
   }

   if (val < -1.0)
   {
      return PI;
   }

   return acos(val);
}

inline void FatalError(std::string message)
{
   std::cerr << "Fatal error: " << message << std::endl;
   exit(1);
}

#endif