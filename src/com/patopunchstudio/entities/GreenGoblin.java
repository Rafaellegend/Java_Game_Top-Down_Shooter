package com.patopunchstudio.entities;

import com.patopunchstudio.graphics.Spritesheet;

public class GreenGoblin extends Enemy{

  public GreenGoblin(int x, int y) {
    super(x* 32, y* 32, 3, 5, 2);
    setSprite(Spritesheet.enemy_green);
  }
  
  
}
