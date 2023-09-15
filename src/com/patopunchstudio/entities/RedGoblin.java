package com.patopunchstudio.entities;

import com.patopunchstudio.graphics.Spritesheet;

public class RedGoblin extends Enemy {

  public RedGoblin(int x, int y) {
    super(x* 32, y* 32, 5, 10, 4);
    setSprite(Spritesheet.enemy_red);
  }
  
}
