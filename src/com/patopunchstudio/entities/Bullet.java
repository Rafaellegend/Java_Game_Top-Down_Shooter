package com.patopunchstudio.entities;

import java.awt.Graphics;

import com.patopunchstudio.graphics.Spritesheet;
import com.patopunchstudio.main.Game;
import com.patopunchstudio.world.World;

public class Bullet extends Entity {
  public double dirX = 0;
  public double dirY = -1;
  public double spd = 8;
  public double size = 1;

  private int curframes = 0;
  private int frames = 8;

  public Bullet(int x, int y, double dirX, double dirY, Weapon wpm) {
    super(x, y, 10, 10);
    this.dirX = dirX;
    this.dirY = dirY;
    this.spd = wpm.projetileSpeed;
    this.size = wpm.projetileSize;
  }

  public void tick() {
    if (World.isFree((int) x, (int) y)) {
      x += spd * dirX;
      y += spd * dirY;
      frames++;
      if (curframes == frames) {
        Game.bullets.remove(this);
        return;
      }
    } else {
      Game.bullets.remove(this);
      return;
    }

  }

  public void render(Graphics g) {
    g.drawImage(Spritesheet.magicBullet, (int) x, (int) y, (int) (16 * size), (int) (16 * size), null);
  }
}
