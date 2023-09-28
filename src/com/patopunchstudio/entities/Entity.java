package com.patopunchstudio.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.patopunchstudio.graphics.Camera;

public class Entity {

  protected double x;
  protected double y;
  protected int z;
  protected int width;
  protected int height;

  private BufferedImage sprite;

  private int maskx, masky, mask_width, mask_height;

  public Entity(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    this.maskx = 0;
    this.masky = 0;
    this.mask_width = width;
    this.mask_height = height;
  }

  public void setSprite(BufferedImage sprite) {
    this.sprite = sprite;
  }

  public void setMask(int maskx, int masky, int mask_width, int mask_height) {
    this.maskx = maskx;
    this.masky = masky;
    this.mask_width = mask_width;
    this.mask_height = mask_height;
  }

  public int getX() {
    return (int) this.x;
  }

  public int getY() {
    return (int) this.y;
  }

  public int getWidth() {
    return (int) this.width;
  }

  public int getHeight() {
    return (int) this.height;
  }

  public BufferedImage getSprite() {
    return this.sprite;
  }

  public void tick() {

  }

  public static boolean isColliding(Entity e1, Entity e2) {
    Rectangle e1mask = new Rectangle((int) e1.getX() + e1.maskx, (int) e1.getY() + e1.masky, e1.mask_width,
        e1.mask_height);
    Rectangle e2mask = new Rectangle((int) e2.getX() + e2.maskx, (int) e2.getY() + e2.masky, e2.mask_width,
        e2.mask_height);
    if(e1mask.intersects(e2mask) && e1.z == e2.z) {
      System.out.println(e1.z+" - "+e2.z);
      return true;
    }        
    return false;
  }

  public void render(Graphics g) {
    g.drawImage(sprite, this.getX() - Camera.X, this.getY() - Camera.Y, this.width, this.height, null);

    // g.setColor(Color.RED);
    // g.fillRect(this.getX() + this.maskx - Camera.X, this.getY() + this.masky -
    // Camera.Y, mask_width, mask_height);
  }

}
