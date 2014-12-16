#ifndef CURVE_HPP
#define CURVE_HPP

#include <vector>
#include <algorithm>

class Curve
{
private:

   std::vector<double> mX;
   std::vector<double> mY;
   std::vector<double> mD;
   std::vector<double> mI;

   double Interpolate(double v, const std::vector<double>& x, const std::vector<double>& y)
   {
      if (v <= x[0])
      {
         return y[0];
      }

      if (v >= x[x.size() - 1])
      {
         return y[y.size() - 1];
      }

      int i = lower_bound(x.begin(), x.end(), v) - x.begin() - 1;

      return y[i] + (y[i + 1] - y[i]) * (v - x[i]) / (x[i + 1] - x[i]);
   }

public:

   Curve(const std::vector<double>& x, const std::vector<double>& y) 
      : mX(x), mY(y)
   {
      mD.push_back(0);
      mI.push_back(0);

      for (size_t i = 1; i < x.size(); i++)
      {
         double dx = x[i] - x[i - 1];
         double dy = y[i] - y[i - 1];
         mD.push_back((y[i] - y[i - 1]) / (x[i] - x[i - 1]));
         mI.push_back(mI[i - 1] + (y[i - 1] + 0.5 * dy) * dx);
      }
   }

   bool InBounds(double x)
   {
      return !(x < mX[0] || x > mX[mX.size() - 1]);
   }

   double Value(double x)
   {
      if (!InBounds(x))
      {
         throw std::out_of_range("Input x value is out of range.");
      }

      return Interpolate(x, mX, mY);
   }

   double Integral()
   {
      return mI[mI.size() - 1];
   }

   double Integral(double x)
   {
      return Integral(mI[0], x);
   }

   double Integral(double x1, double x2)
   {
      if (!InBounds(x1) || !InBounds(x2))
      {
         throw std::out_of_range("Input x value is out of range.");
      }

      return Interpolate(x2, mX, mI) - Interpolate(x1, mI, mI);
   }

   double Derivative(double x)
   {
      if (!InBounds(x))
      {
         throw std::out_of_range("Input x value is out of range.");
      }

      return Interpolate(x, mX, mD);
   }
};

#endif