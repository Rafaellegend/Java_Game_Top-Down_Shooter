package com.patopunchstudio.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.patopunchstudio.graphics.Camera;
import com.patopunchstudio.main.Game;
import com.patopunchstudio.world.World;

public class Enemy extends Entity {

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
  public boolean isDamaged;
  public int damageFrame = 0;
  public static List<Bullet> bullets = new ArrayList<Bullet>();
  private BufferedImage[] sprite;
  private BufferedImage sprite_damaged;
  private int maskx, masky, mask_width, mask_height;

  public Enemy(int x, int y, double life, double damage, double spd) {
    super(x, y, 32, 32);
    this.life = life;
    this.damage = damage;
    this.spd = spd;

    this.maskx = 0;
    this.masky = 0;
    this.mask_width = width;
    this.mask_height = height;
  }

  public void setSprite(BufferedImage[] sprite, BufferedImage sprite_damaged) {
    this.sprite = sprite;
    this.sprite_damaged = sprite_damaged;
  }

  public void setMask(int maskx, int masky, int mask_width, int mask_height) {
    this.maskx = maskx;
    this.masky = masky;
    this.mask_width = mask_width;
    this.mask_height = mask_height;
  }

  public void chasePlayer() {
    Player p = Game.player;
    if (isCollidingwithPlayer() == false) {
      if (x < p.x && World.isFree((int) (getX() + spd), getY()) && !isColliding((int) (getX() + spd), getY())) {
        if (Game.rand.nextInt(100) < 50)
          x += spd;
      } else if (x > p.x && World.isFree((int) (getX() - spd), getY()) && !isColliding((int) (getX() - spd), getY())) {
        if (Game.rand.nextInt(100) < 50)
          x -= spd;
      }
      if (y < p.y && World.isFree(getX(), (int) (getY() + spd)) && !isColliding(getX(), (int) (getY() + spd))) {
        if (Game.rand.nextInt(100) < 50)
          y += spd;
      } else if (y > p.y && World.isFree(getX(), (int) (getY() - spd)) && !isColliding(getX(), (int) (getY() - spd))) {
        if (Game.rand.nextInt(100) < 50)
          y -= spd;
      }
    } else {
      // Colisão com Player
      if (Game.rand.nextInt(100) < 10 && !Game.player.isDamaged) {
        if (Game.rand.nextInt(100) < 10) {
          //Game.player.life -= (int) (damage * 2);
          Game.player.isDamaged = true;
          // System.out.println("DANO CRITICO!");
        } else {
          //Game.player.life -= (int) (damage);
          Game.player.isDamaged = true;
        }

        // System.out.println("Life: "+ Game.player.life);
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
    if (isDamaged) {
      this.damageFrame++;
      if (damageFrame == 8) {
        this.damageFrame = 0;
        isDamaged = false;
      }
    }
    collidingBullet();
    if (life <= 0) {
      this.damageFrame++;
      if (damageFrame == 6) {
        this.damageFrame = 0;
        destroySelf();
      }
      
    }
    if (shoot) {
      shoot = false;
      // bullets.add(new Bullet(getX(), getY(), dirX, dirY,equiped));
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
      Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.tile_Size, World.tile_Size);
      if (enemyCollider.intersects(targetEnemy)) {
        return true;
      }
    }
    for (int i = 0; i < Game.entities.size(); i++) {
      Entity e = Game.entities.get(i);
      if (e == this)
        continue;
      Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.tile_Size, World.tile_Size);
      if (enemyCollider.intersects(targetEnemy)) {
        return true;
      }
    }
    return false;
  }

  public boolean destroySelf() {
    Game.enemies.remove(this);
    return true;
  }

  public boolean isCollidingwithPlayer() {
    Rectangle enemyCollider = new Rectangle(getX() + maskx, getY() + masky, mask_width, mask_height);
    Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32, 32);

    return enemyCollider.intersects(player);
  }

  public boolean collidingBullet() {
    for (int i = 0; i < Game.bullets.size(); i++) {
      Entity e = Game.bullets.get(i);
      if (e instanceof Bullet) {
        if (Entity.isColliding(this, e)) {
          isDamaged = true;
          life -= Game.player.equiped.damage;
          Game.bullets.remove(i);
          return true;
        }
      }
    }
    return false;
  }

  public void render(Graphics g) {
    if (!isDamaged) {
      g.drawImage(sprite[curAnimation], getX() - Camera.X, getY() - Camera.Y, 32, 32, null);
    } else {
      g.drawImage(sprite_damaged, getX() - Camera.X, getY() - Camera.Y, 32, 32, null);
    }

  }
}
