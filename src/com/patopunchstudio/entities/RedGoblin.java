package com.patopunchstudio.entities;

import com.patopunchstudio.graphics.Spritesheet;

public class RedGoblin extends Enemy {

  public RedGoblin(int x, int y) {
    super(x* 32, y* 32, 5, 2, 4, Spritesheet.enemy_red);
  }
  
}
