package com.patopunchstudio.world;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.patopunchstudio.graphics.Spritesheet;

public class Blocks extends Rectangle{
  public Blocks(int x, int y) {
    super(x,y,32,32);
  }
  
  public void render(Graphics g) {
    g.drawImage(Spritesheet.tileWall, x, y,32,32, null);
  }
}
