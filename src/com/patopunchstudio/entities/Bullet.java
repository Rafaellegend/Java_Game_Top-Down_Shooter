package com.patopunchstudio.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.patopunchstudio.graphics.Spritesheet;

public class Bullet extends Rectangle{
  public int dirX = 0;
  public int dirY = -1;
  public int spd = 8;

  public int frames = 0;

  public Bullet(int x, int y, int dirX,int dirY) {
    super(x,y,10,10);
    this.dirX = dirX;
    this.dirY = dirY;
  }

  public void tick(){
    x+=spd*dirX;
    y+=spd*dirY;
    frames++;

    if(Player.bullets.size()==4){
      Player.bullets.remove(0);
    }
    if(frames == 60){
      Player.bullets.remove(this);
      return;
    }
  }

  public void render(Graphics g) {
    g.drawImage(Spritesheet.magicBullet, x, y,16,16, null);
  }
}
