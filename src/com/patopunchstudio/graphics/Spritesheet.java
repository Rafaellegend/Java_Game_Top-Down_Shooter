package com.patopunchstudio.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
  public static BufferedImage spritesheet;
  public static BufferedImage tileset;
  public static BufferedImage[] player_front;
  public static BufferedImage[] player_back;
  public static BufferedImage[] player_horizontal;
  public static BufferedImage player_damaged;
  public static BufferedImage[] enemy_green;
  public static BufferedImage[] enemy_red;
  public static BufferedImage tileWall;
  public static BufferedImage magicBullet;
  public static BufferedImage lifeGem_EN;
  public static BufferedImage manaGem_EN;
  public static BufferedImage fireWand_EN;


  public Spritesheet() {
    try {
      spritesheet = ImageIO.read(getClass().getResource("/spritesheet1.png"));
      tileset = ImageIO.read(getClass().getResource("/tileset.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    player_front = new BufferedImage[2];
    player_front[0] = Spritesheet.getSprite(0, 0, 16, 16);
    player_front[1] = Spritesheet.getSprite(16, 0, 16, 16);
    player_back = new BufferedImage[2];
    player_back[0] = Spritesheet.getSprite(64, 0, 16, 16);
    player_back[1] = Spritesheet.getSprite(80, 0, 16, 16);
    player_horizontal = new BufferedImage[2];
    player_horizontal[0] = Spritesheet.getSprite(32, 0, 16, 16);
    player_horizontal[1] = Spritesheet.getSprite(48, 0, 16, 16);
    player_damaged = Spritesheet.getSprite(80, 16, 16, 16);

    enemy_green = new BufferedImage[2];
    enemy_green[0] = Spritesheet.getSprite(0, 16, 16, 16);
    enemy_green[1] = Spritesheet.getSprite(16, 16, 16, 16);

    enemy_red= new BufferedImage[2];
    enemy_red[0] = Spritesheet.getSprite(0, 32, 16, 16);
    enemy_red[1] = Spritesheet.getSprite(16, 32, 16, 16);

    magicBullet = Spritesheet.getSprite(100, 4, 8, 8);

    tileWall = Spritesheet.getSprite(0, 112, 16, 16);

    lifeGem_EN = Spritesheet.getSprite(96, 16, 16, 16);
    manaGem_EN = Spritesheet.getSprite(112, 16, 16, 16);
    fireWand_EN = Spritesheet.getSprite(112, 0, 16, 16);

  }

  public static BufferedImage getSprite(int x, int y, int width, int height) {
    return spritesheet.getSubimage(x, y, width, height);
  }

  public static BufferedImage getTile(int x, int y, int width, int height) {
    return tileset.getSubimage(x, y, width, height);
  }

}
