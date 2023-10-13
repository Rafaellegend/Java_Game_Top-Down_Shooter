package com.patopunchstudio.world;

public class Vector2I {
  public int x,y;

  public Vector2I(int x, int y){
    this.x = x;
    this.y = y;
  }

  public boolean equal(Object object){
    Vector2I vec = (Vector2I) object;
    if(vec.x == this.x && vec.y == this.y){
      return true;
    }
    return false;
  }
}

