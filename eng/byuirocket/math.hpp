#ifndef MATH_HPP
#define MATH_HPP

class Vector;
class Matrix;
class Quaternion;

class Matrix
{
private:

   double mA[9];

   int ToIndex(int row, int col) const
   {
      return row * 3 + col;
   }

public:

   Matrix()
   {
   }

   Matrix(const Matrix& other)
   {
      (*this) = other;
   }

   Matrix DiagonalInverse() const
   {
      Matrix m = Zero();
      m(0, 0) = 1.0 / mA[ToIndex(0, 0)];
      m(1, 1) = 1.0 / mA[ToIndex(1, 1)];
      m(2, 2) = 1.0 / mA[ToIndex(2, 2)];
      return m;
   }

   Matrix Transpose() const
   {
      Matrix m;

      for (int row = 0; row < 3; row++)
      {
         for (int col = 0; col < 3; col++)
         {
            m.mA[ToIndex(col, row)] = mA[ToIndex(row, col)];
         }
      }

      return m;
   }

   static Matrix Zero()
   {
      Matrix m;
      memset(m.mA, 0, sizeof(double) * 9);
      return m;
   }

   double& operator()(const int row, const int col)
   {
      return mA[ToIndex(row, col)];
   }

   Matrix & operator=(const Matrix &rhs)
   {
      for (int i = 0; i < 9; mA[i++] = rhs.mA[i]);
      return *this;
   }

   Matrix & operator+=(const Matrix &rhs)
   {
      for (int i = 0; i < 9; mA[i++] += rhs.mA[i]);
      return *this;
   }

   Matrix & operator-=(const Matrix &rhs)
   {
      for (int i = 0; i < 9; mA[i++] -= rhs.mA[i]);
      return *this;
   }

   Matrix & operator*=(const Matrix &rhs)
   {
      Matrix m = Zero();

      for (int row = 0; row < 3; row++)
      {
         for (int col = 0; col < 3; col++)
         {
            for (int k = 0, i = ToIndex(row, col); k < 3; k++)
            {
               m.mA[i] += mA[ToIndex(row, k)] * rhs.mA[ToIndex(k, col)];
            }
         }
      }

      (*this) = m;

      return *this;
   }

   Matrix & operator*=(const double &rhs)
   {
      for (int i = 0; i < 9; mA[i++] *= rhs);
      return *this;
   }

   Matrix & operator/=(const double &rhs)
   {
      for (int i = 0; i < 9; mA[i++] /= rhs);
      return *this;
   }

   const Matrix operator+(const Matrix &other) const
   {
      return Matrix(*this) += other;
   }

   const Matrix operator-(const Matrix &other) const
   {
      return Matrix(*this) -= other;
   }

   const Matrix operator*(const Matrix &other) const
   {
      return Matrix(*this) *= other;
   }

   const Matrix operator*(const double &other) const
   {
      return Matrix(*this) *= other;
   }

   const Matrix operator/(const double &other) const
   {
      return Matrix(*this) /= other;
   }
};

class Vector
{
public:

   double X;
   double Y;
   double Z;

   Vector() { }
   Vector(const Vector& v) : X(v.X), Y(v.Y), Z(v.Z) { }
   Vector(double x, double y, double z) : X(x), Y(y), Z(z) { }

   double Norm() const
   {
      return sqrt(X * X + Y * Y + Z * Z);
   }

   void Normalize()
   {
      *this /= Norm();
   }

   Vector Normalized()
   {
      return *this / Norm();
   }

   double Dot(const Vector v) const
   {
      return X * v.X + Y * v.Y + Z * v.Z;
   }

   Vector Cross(const Vector v) const
   {
      return Vector(Y * v.Z - Z * v.Y, Z * v.X - X * v.Z, X * v.Y - Y * v.X);
   }

   static Vector Zero()
   {
      return Vector(0, 0, 0);
   }

   Vector & operator=(const Vector &rhs)
   {
      X = rhs.X;
      Y = rhs.Y;
      Z = rhs.Z;
      return *this;
   }

   Vector & operator+=(const Vector &rhs)
   {
      X += rhs.X;
      Y += rhs.Y;
      Z += rhs.Z;
      return *this;
   }

   Vector & operator-=(const Vector &rhs)
   {
      X -= rhs.X;
      Y -= rhs.Y;
      Z -= rhs.Z;
      return *this;
   }

   Vector & operator*=(const double &rhs)
   {
      X *= rhs;
      Y *= rhs;
      Z *= rhs;
      return *this;
   }

   Vector & operator/=(const double &rhs)
   {
      X /= rhs;
      Y /= rhs;
      Z /= rhs;
      return *this;
   }

   const Vector operator+(const Vector &other) const
   {
      return Vector(*this) += other;
   }

   const Vector operator-(const Vector &other) const
   {
      return Vector(*this) -= other;
   }

   const Vector operator*(const double &other) const
   {
      return Vector(*this) *= other;
   }

   const Vector operator/(const double &other) const
   {
      return Vector(*this) /= other;
   }

   friend Vector operator*(double d, const Vector v)
   {
      return Vector(v) *= d;
   }

   friend Vector operator*(Matrix m, Vector v)
   {
      return Vector(
         m(0, 0) * v.X + m(0, 1) * v.Y + m(0, 2) * v.Z,
         m(1, 0) * v.X + m(1, 1) * v.Y + m(1, 2) * v.Z,
         m(2, 0) * v.X + m(2, 1) * v.Y + m(2, 2) * v.Z);
   }
};

class Quaternion
{
public:

   double W;
   double X;
   double Y;
   double Z;

   Quaternion()
   {

   }

   Quaternion(double w, double x, double y, double z) : W(w), X(x), Y(y), Z(z)
   {
   }

   double Norm()
   {
      return sqrt(W * W + X * X + Y * Y + Z * Z);
   }

   void normalize()
   {
      double n = Norm();
      W /= n;
      X /= n;
      Y /= n;
      Z /= n;
   }

   Matrix toRotationMatrix() const
   {
      Matrix m;
      double s = sin(W);
      double X2 = sqr(X);
      double Y2 = sqr(Y);
      double Z2 = sqr(Z);
      m(0, 0) = 1 - 2 * (Y2 - Z2);
      m(0, 1) = 2 * (X * Y - W * Z);
      m(0, 2) = 2 * (X * Z + W * Y);
      m(1, 0) = 2 * (X * Y + W * Z);
      m(1, 1) = 1 - 2 * (X2 - Z2);
      m(1, 2) = 2 * (Y * Z - W * X);
      m(2, 0) = 2 * (X * Z - W * Y);
      m(2, 1) = 2 * (Y * Z + W * X);
      m(2, 2) = 1 - 2 * (X2 - Y2);
      return m;
   }

   Vector vec() const
   {
      return Vector(X, Y, Z);
   }

};

#endif