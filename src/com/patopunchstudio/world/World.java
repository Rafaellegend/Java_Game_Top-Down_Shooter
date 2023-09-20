package com.patopunchstudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.patopunchstudio.entities.GreenGoblin;
import com.patopunchstudio.entities.LifeGem;
import com.patopunchstudio.entities.ManaGem;
import com.patopunchstudio.entities.Player;
import com.patopunchstudio.entities.RedGoblin;
import com.patopunchstudio.entities.Weapon;
import com.patopunchstudio.graphics.Camera;
import com.patopunchstudio.graphics.Spritesheet;
import com.patopunchstudio.main.Game;

public class World {
  public static Tile[] tiles;
  public static int WIDTH, HEIGHT;
  public static final int tile_Size = 32;

  public static List<Blocks> blocks = new ArrayList<Blocks>();

  public World(String path) {
    try {
      BufferedImage map = ImageIO.read(getClass().getResource(path));
      tiles = new Tile[map.getWidth() * map.getHeight()];
      WIDTH = map.getWidth();
      HEIGHT = map.getHeight();
      int[] pixels = new int[map.getWidth() * map.getHeight()];
      map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
      for (int xx = 0; xx < map.getWidth(); xx++) {
        for (int yy = 0; yy < map.getHeight(); yy++) {
          int pixelAtual = pixels[xx + (yy * map.getWidth())];
          if (pixelAtual == 0xFF000000) {
            // Floor - Black Pixel
            if (new Random().nextInt(100) < 40) {
              tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor1);
            } else if (new Random().nextInt(100) < 1) {
              tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor3);
            } else {
              tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);
            }

          } else if (pixelAtual == 0xFFFFFFFF) {
            // Wall - White Pixel
            tiles[xx + (yy * WIDTH)] = new WallTile(xx * 32, yy * 32, Tile.Tile_Wall);
          } else if (pixelAtual == 0xFFFF0000) {
            // Enemy - Red Pixel
            tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);
            if (new Random().nextInt(100) < 50) {
              Game.enemies.add(new GreenGoblin(xx, yy));
            }else{
              Game.enemies.add(new RedGoblin(xx, yy));
            }
          }
          else if (pixelAtual == 0xFF0000ff) {
            // Player - blue Pixel
            tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);
            Game.player = new Player(xx * 32, yy * 32, 32, 32, Spritesheet.player_front[0]);
          } else if (pixelAtual == 0xFFffff00) {
            // Weapon - Yellow Pixel
            tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);
            Weapon wpm = new Weapon(xx * 32, yy * 32, 32, 32);
            wpm.setSprite(Spritesheet.fireWand_EN);
            wpm.setWeaponStatus(2,0,0, 5, 8, 1);
            Game.entities.add(wpm);
          } else if (pixelAtual == 0xFF00ff00) {
            // Life Gem - Green Pixel
            tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);
            LifeGem gem = new LifeGem(xx * 32, yy * 32, 32, 32);
            gem.setSprite(Spritesheet.lifeGem_EN);
            gem.setMask(6, 6, 20, 20);
            Game.entities.add(gem);
          } else if (pixelAtual == 0xFFff00ff) {
            // Mana Gem / Ammo - Pink Pixel
            tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);
            ManaGem gem = new ManaGem(xx * 32, yy * 32, 32, 32);
            gem.setSprite(Spritesheet.manaGem_EN);
            gem.setMask(6, 6, 20, 20);
            Game.entities.add(gem);;
          } else {
            tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.Tile_Floor2);

          }
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isFree(int xNext, int yNext) {
    int x1 = xNext / tile_Size;
    int y1 = yNext / tile_Size;
    int x2 = (xNext + tile_Size - 1) / tile_Size;
    int y2 = yNext / tile_Size;
    int x3 = xNext / tile_Size;
    int y3 = (yNext + tile_Size - 1) / tile_Size;
    int x4 = (xNext + tile_Size - 1) / tile_Size;
    int y4 = (yNext + tile_Size - 1) / tile_Size;

    return !(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile ||
        tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile ||
        tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile ||
        tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile);
  }

  public void render(Graphics g) {
    int xstart = Camera.X / 32;
    int ystart = Camera.Y / 32;

    int xfinal = xstart + (Game.WIDTH / 32);
    int yfinal = ystart + (Game.HEIGHT / 32);

    for (int xx = xstart; xx <= xfinal; xx++) {
      for (int yy = ystart; yy <= yfinal; yy++) {
        if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
          continue;
        Tile tile = tiles[xx + (yy * WIDTH)];
        tile.render(g);
      }
    }
  }
}
