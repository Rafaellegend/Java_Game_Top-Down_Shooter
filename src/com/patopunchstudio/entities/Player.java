package com.patopunchstudio.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.patopunchstudio.graphics.Camera;
import com.patopunchstudio.graphics.Spritesheet;
import com.patopunchstudio.main.Game;
import com.patopunchstudio.world.World;

public class Player extends Entity {

  public Player(int x, int y, int width, int height, BufferedImage sprite) {
    super(x, y, width, height);
  }

  public double spd = 4;
  public double life = 100;
  public double mana = 50;
  public int maxlife = 100;
  public int maxmana = 100;
  public double manaRegen = 1;
  public int manaFrame = 0;
  public double invencibilityFrame = 15;
  public boolean right, up, down, left, wpm;
  public boolean isDamaged = false;
  public double atkSpeed = 1;
  public int atkCooldown = 0;
  public boolean atkInCooldown = false;
  public int damageFrame = 0;
  public int actualSprite = 0;
  public int dirX = 0, dirY = 0;
  public int curAnimation = 0;
  public int curFrames = 0, targetFrames = 15;
  public boolean shoot = false;
  public Weapon equiped;

  public void tick() {
    if (life <= 0) {
      life = 0;
      Game.NewGame();
    }

    boolean moved = false;
    if (right && World.isFree((int) (getX() + spd), getY())) {
      actualSprite = 2;
      dirX = 1;
      dirY = 0;
      moved = true;
      x += spd;
    } else if (left && World.isFree((int) (getX() - spd), getY())) {
      actualSprite = 3;
      dirX = -1;
      dirY = 0;
      moved = true;
      x -= spd;
    }
    if (up && World.isFree(getX(), (int) (getY() - spd))) {
      actualSprite = 1;
      dirX = 0;
      dirY = -1;
      moved = true;
      if (right || left) {
        y -= spd / 2;
      } else {
        y -= spd;
      }
    } else if (down && World.isFree(getX(), (int) (getY() + spd))) {
      actualSprite = 0;
      dirX = 0;
      dirY = 1;
      moved = true;
      if (right || left) {
        y += spd / 2;
      } else {
        y += spd;
      }
    }
    if (moved) {
      curFrames++;
      if (curFrames == targetFrames) {
        curFrames = 0;
        curAnimation++;
        if (curAnimation == Spritesheet.player_front.length) {
          curAnimation = 0;
        }
      }
    }
    if (shoot) {
      shoot = false;
      if (wpm && mana > 0 && mana > equiped.manaConsumption) {
        if (!atkInCooldown) {
          mana -= equiped.manaConsumption;
          if (right || dirX == 1) {
            Game.bullets.add(new Bullet(getX() + 12, getY(), dirX, dirY, equiped));
          } else if (left || dirX == -1) {
            Game.bullets.add(new Bullet(getX(), getY(), dirX, dirY, equiped));
          } else if (up || dirY == -1) {
            Game.bullets.add(new Bullet(getX() + 16, getY() + 12, dirX, dirY, equiped));
          } else if (down || dirY == 1) {
            Game.bullets.add(new Bullet(getX(), getY() - 12, dirX, dirY, equiped));
          }
          atkInCooldown = true;
        } else {
          atkCooldown++;
          if (atkCooldown >= atkSpeed) {
            atkCooldown = 0;
            atkInCooldown = false;
          }
        }
      }
    }
    for (int i = 0; i < Game.bullets.size(); i++) {
      Game.bullets.get(i).tick();
    }
    checkItems();
    if (isDamaged) {
      this.damageFrame++;
      if (damageFrame == invencibilityFrame) {
        this.damageFrame = 0;
        isDamaged = false;
      }
    }

    if (mana < maxmana) {
      this.manaFrame++;
      if (manaFrame == (int) (100 - manaRegen)) {
        mana += manaRegen;
        this.manaFrame = 0;
      }
    }

    Camera.X = Camera.clamp(getX() - (Game.WIDTH / 2), 0, World.WIDTH * 32 - Game.WIDTH);
    Camera.Y = Camera.clamp(getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 32 - Game.HEIGHT);
  }

  public void checkItems() {
    for (int i = 0; i < Game.entities.size(); i++) {
      Entity actual = Game.entities.get(i);
      // LifeGem
      if (actual instanceof LifeGem) {
        if (Entity.isColliding(this, actual)) {
          if (life < 100) {
            life += 10;
            if (life > 100)
              life = 100;
            Game.entities.remove(actual);
          }
        }
      }
      // ManaGem
      if (actual instanceof ManaGem) {
        if (Entity.isColliding(this, actual)) {
          if (mana < 100) {
            mana += 10;
            if (mana > 100)
              mana = 100;
            Game.entities.remove(actual);
          }
        }
      }
      //
      if (actual instanceof Weapon) {
        if (Entity.isColliding(this, actual)) {
          wpm = true;
          equiped = (Weapon) actual;
          System.out.println("damage:" + equiped.damage);
          Game.entities.remove(actual);
        }
      }
    }
  }

  public void render(Graphics g) {
    if (!isDamaged) {
      if (down || actualSprite == 0) {
        g.drawImage(Spritesheet.player_front[curAnimation], getX() - Camera.X, getY() - Camera.Y, 32, 32, null);
        if (wpm) {
          g.drawImage(Spritesheet.fireWand_EN, getX() - Camera.X - 10, getY() - Camera.Y, 32, 32, null);
        }
      } else if (up || actualSprite == 1) {
        if (wpm) {
          g.drawImage(Spritesheet.fireWand_EN, getX() - Camera.X + 10, getY() - Camera.Y, 32, 32, null);
        }
        g.drawImage(Spritesheet.player_back[curAnimation], getX() - Camera.X, getY() - Camera.Y, 32, 32, null);

      } else if (right || actualSprite == 2) {
        if (wpm) {
          g.drawImage(Spritesheet.fireWand_EN, getX() - Camera.X + 10, getY() - Camera.Y, 32, 32, null);
        }
        g.drawImage(Spritesheet.player_horizontal[curAnimation], getX() - Camera.X, getY() - Camera.Y, 32, 32, null);
      } else if (left || actualSprite == 3) {
        g.drawImage(Spritesheet.player_horizontal[curAnimation], getX() + 32 - Camera.X, getY() - Camera.Y, -32, 32,
            null);
        if (wpm) {
          g.drawImage(Spritesheet.fireWand_EN, getX() - Camera.X - 5, getY() - Camera.Y, 32, 32, null);
        }
      }
    } else {
      g.drawImage(Spritesheet.player_damaged, this.getX() - Camera.X, this.getY() - Camera.Y, 32, 32, null);
      if (wpm) {
        g.drawImage(Spritesheet.fireWand_EN, getX() - Camera.X - 13, getY() - Camera.Y - 13, 32, 32, null);
      }
    }
    for (int i = 0; i < Game.bullets.size(); i++) {
      Game.bullets.get(i).render(g);
    }

  }
}
