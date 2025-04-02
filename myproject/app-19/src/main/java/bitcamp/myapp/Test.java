package bitcamp.myapp;

import java.util.ArrayList;

class My {
  public static int plus(int a, int b) {return a + b;}
  public static int minus(int a, int b) {return a - b;}
  public static long plus2(long a, long b) {return a + b;}
  public static short plus3(short a, short b) {return (short)(a + b);}
}

class My2 {
  public int plus(int a, int b) {return a + b;}
  public int minus(int a, int b) {return a - b;}
}

public class Test {

  // functional interface
  interface Calculator {
    int compute(int a, int b);
  }

  interface Calculator2 {
    void compute(int a, int b);
  }

  static void m1() {
    class MyCalculator implements Calculator {
      public int compute(int a, int b) {
        return a + b;
      }
    }
    Calculator calc = new MyCalculator();
    System.out.println(calc.compute(10, 20));
  }

  static void m2() {
    Calculator calc = new Calculator() {
      public int compute(int a, int b) {
        return a + b;
      }
    };
    System.out.println(calc.compute(10, 20));
  }

  static void m3() {
    Calculator calc = (a, b) -> a + b;
    System.out.println(calc.compute(10, 20));
  }

  static void m4() {
    Calculator calc = My::minus; // 스태틱 메서드 레퍼런스
    System.out.println(calc.compute(10, 20));
  }

  static void m5() {
    My2 obj = new My2();
    Calculator calc = obj::minus; // 인스턴스 메서드 레퍼런스
    System.out.println(calc.compute(10, 20));
  }

  static void m6() {
    Calculator2 calc = My::plus;
    calc.compute(10, 20);

    Calculator2 cal2 = new Calculator2() {
      public void compute(int a, int b) {
        My.plus(a, b);
      }
    };

  }

  public static void main(String[] args) throws Exception{
    m1();
    m2();
    m3();
    m4();
    m5();
  }

}
