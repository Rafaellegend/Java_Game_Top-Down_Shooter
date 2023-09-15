package com.patopunchstudio.graphics;

public class Camera {
  public static int X;
  public static int Y;

  public static int clamp(int actual, int min,int max){
    if(actual < min){
      actual = min;
    }
    if(actual > max){
      actual = max;
    }
    return actual;
  }
}
