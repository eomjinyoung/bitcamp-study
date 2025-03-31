package bitcamp.myapp;

import java.util.ArrayList;

class Gasolin {}
class Diesel {}

class CombuEngine<T> {
  T energy;
}

class ElectricEngine {}

class Car<T> {
  T engine;
}

public class Test {
  public static void main(String[] args) throws Exception{
    CombuEngine<Diesel> engine = new CombuEngine<>();
    Car<ElectricEngine> car1 = new Car<>();
    Car<CombuEngine<Diesel>> car2 = new Car<>();
    Car<CombuEngine<Gasolin>> car3 = new Car<>();
    m1(car2);
    m2(car3);
  }

  static void m1(Car<CombuEngine<Diesel>> car) {
  }
  static void m2(Car<CombuEngine<Gasolin>> car) {
  }
}
