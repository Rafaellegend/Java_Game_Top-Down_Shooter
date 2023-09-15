package com.patopunchstudio.entities;

import com.patopunchstudio.graphics.Spritesheet;

public class GreenGoblin extends Enemy{

  public GreenGoblin(int x, int y) {
    super(x* 32, y* 32, 3, 1, 2, Spritesheet.enemy_green);
  }
  
}
