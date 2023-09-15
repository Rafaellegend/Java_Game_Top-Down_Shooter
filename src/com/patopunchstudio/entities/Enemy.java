package com.patopunchstudio.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.patopunchstudio.graphics.Camera;
import com.patopunchstudio.main.Game;
import com.patopunchstudio.world.World;

public class Enemy extends Rectangle {

  public double spd = 2;
  public double life = 2;
  public boolean right, up, down, left;
  public double damage = 1.5;
  public int actualSprite = 0;
  public int dirX = 0;
  public int dirY = 0;
  public int curAnimation = 0;
  public int curFrames = 0, targetFrames = 15;
  public boolean shoot = false;
  public static List<Bullet> bullets = new ArrayList<Bullet>();
  private BufferedImage[] sprite;
  private int maskx, masky, mask_width,mask_height;

  public Enemy(int x, int y,double life, double damage, double spd, BufferedImage[] sprite) {
    super(x, y, 32, 32);
    this.sprite = sprite;
    this.life = life;
    this.damage = damage;
    this.spd = spd;

    this.maskx = 0;
    this.masky = 0;
    this.mask_width = width;
    this.mask_height = height;
  }
  public void setMask(int maskx,int masky,int mask_width,int mask_height){
    this.maskx = maskx;
    this.masky = masky;
    this.mask_width = mask_width;
    this.mask_height = mask_height;
  }

  public void chasePlayer() {
    Player p = Game.player;
    if (isCollidingwithPlayer() == false) {
      if (x < p.x && World.isFree((int) (x + spd), y) && !isColliding((int) (x + spd), y)) {
        if (Game.rand.nextInt(100) < 50)
          x += spd;
      } else if (x > p.x && World.isFree((int) (x - spd), y) && !isColliding((int) (x - spd), y)) {
        if (Game.rand.nextInt(100) < 50)
          x -= spd;
      }
      if (y < p.y && World.isFree(x, (int) (y + spd)) && !isColliding(x, (int) (y + spd))) {
        if (Game.rand.nextInt(100) < 50)
          y += spd;
      } else if (y > p.y && World.isFree(x, (int) (y - spd)) && !isColliding(x, (int) (y - spd))) {
        if (Game.rand.nextInt(100) < 50)
          y -= spd;
      } 
    }else{
        //Colisão com Player
        if(Game.rand.nextInt(100) < 10 && !Game.player.isDamaged){
          if(Game.rand.nextInt(100) < 10){
            Game.player.life -= (int)(damage*2);
            Game.player.isDamaged = true;
            //System.out.println("DANO CRITICO!");
          }else{
            Game.player.life -= (int)(damage);
            Game.player.isDamaged = true;
          }
          
          //System.out.println("Life: "+ Game.player.life);
        }
        

      }
  }

  public void tick() {
    boolean moved = true;
    chasePlayer();

    if (moved) {
      curFrames++;
      if (curFrames == targetFrames) {
        curFrames = 0;
        curAnimation++;
        if (curAnimation == sprite.length) {
          curAnimation = 0;
        }
      }
    }
    if (shoot) {
      shoot = false;
      bullets.add(new Bullet(x, y, dirX, dirY));
    }
    for (int i = 0; i < bullets.size(); i++) {
      bullets.get(i).tick();
    }
  }

  public boolean isColliding(int x, int y) {
    Rectangle enemyCollider = new Rectangle(x, y, World.tile_Size, World.tile_Size);
    for (int i = 0; i < Game.enemies.size(); i++) {
      Enemy e = Game.enemies.get(i);
      if (e == this)
        continue;
      Rectangle targetEnemy = new Rectangle(e.x, e.y, World.tile_Size, World.tile_Size);
      if (enemyCollider.intersects(targetEnemy)) {
        return true;
      }
    }
    return false;
  }

  public boolean isCollidingwithPlayer() {
    Rectangle enemyCollider = new Rectangle(x + maskx, y + masky, mask_width, mask_height);
    Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32, 32);

    return enemyCollider.intersects(player);
  }

  public void render(Graphics g) {
    g.drawImage(sprite[curAnimation], x - Camera.X, y - Camera.Y, 32, 32, null);
    for (int i = 0; i < bullets.size(); i++) {
      bullets.get(i).render(g);
    }

  }
}
