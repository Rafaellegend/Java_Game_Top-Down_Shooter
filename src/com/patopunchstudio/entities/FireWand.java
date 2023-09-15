package com.patopunchstudio.entities;

import com.patopunchstudio.graphics.Spritesheet;

public class FireWand extends Weapon{

  public FireWand(int x, int y, int width, int height) {
    super(x, y, width, height);
    setWeaponStatus(2,0,0);
    setSprite(Spritesheet.fireWand_EN);
  }
  
}
