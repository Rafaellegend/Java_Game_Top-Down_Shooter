package com.patopunchstudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.patopunchstudio.graphics.Camera;
import com.patopunchstudio.graphics.Spritesheet;

public class Tile {
  public static int Flavor=64;
  public static BufferedImage Tile_Floor1 = Spritesheet.getTile(0+Flavor, 16, 16, 16);
  public static BufferedImage Tile_Floor2 = Spritesheet.getTile(0+Flavor, 48, 16, 16);
  public static BufferedImage Tile_Floor3 = Spritesheet.getTile(16+Flavor, 16, 16, 16);
  public static BufferedImage Tile_Wall = Spritesheet.getTile(16+Flavor, 0, 16, 16);


  private BufferedImage sprite;
  private int x, y;
 
  public Tile(int x, int y, BufferedImage sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
  }

  public void render(Graphics g) {
    g.drawImage(sprite, x - Camera.X, y - Camera.Y, 32, 32, null);

  }
}
