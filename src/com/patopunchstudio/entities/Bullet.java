package com.patopunchstudio.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.patopunchstudio.graphics.Spritesheet;

public class Bullet extends Rectangle{
  public int dirX = 0;
  public int dirY = -1;
  public double spd = 8;

  private int curframes = 0;
  private int frames = 30;

  public Bullet(int x, int y, int dirX,int dirY) {
    super(x,y,10,10);
    this.dirX = dirX;
    this.dirY = dirY;
  }

  public void tick(){
    x+=(int)(spd*dirX);
    y+=(int)(spd*dirY);
    frames++;
    if(curframes == frames){
      Player.bullets.remove(this);
      return;
    }
  }

  public void render(Graphics g) {
    g.drawImage(Spritesheet.magicBullet, x, y,16,16, null);
  }
}
