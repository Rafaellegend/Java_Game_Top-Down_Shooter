package com.patopunchstudio.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pause {
  public String[] options = { "Continuar", "Menu" };

  public int currentOption = 0;
  public int maxOption = options.length - 1;
  public String text;
  public int textWidth;
  public int posH;
  public int posV;
  public boolean up, down, execute;
  public int damage = 0, atkSpd = 0, critChance = 0, critDamage = 0;

  public void tick() {
    if(Game.gameState == "PAUSE"){Sound.backgroundMusic.pause();}
    if (execute) {
      if (currentOption == 0) {
        Game.gameState = "NORMAL";
        Sound.backgroundMusic.resume();
      }
      if (currentOption == 1) {
        Game.gameState = "MENU";
      }
      execute = false;
    }

    if (up) {
      currentOption--;
      if (currentOption < 0)
        currentOption = maxOption;
      up = false;
    }
    if (down) {
      currentOption++;
      if (currentOption > maxOption)
        currentOption = 0;
      down = false;
    }
    if (Game.player.wpm) {
      damage = (int) Game.player.equiped.damage;
      atkSpd = (int) Game.player.equiped.projetileSpeed;
      critChance = (int) Game.player.equiped.critChance;
      critDamage = (int) Game.player.equiped.critDamage;
    }

  }

  public void render(Graphics g) {
    if (Game.gameState == "PAUSE") {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(new Color(0, 0, 0, 100));
      g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
      Game.ui.stringRender(g, "Game Paused", new Font("arial", Font.BOLD, 40), 0, -60, Color.WHITE);
      for (int i = 0; i < options.length; i++) {
        Game.ui.stringRender(g, options[i], new Font("arial", Font.PLAIN, 20), 0, (50 * i), Color.WHITE);
      }
      Game.ui.stringRender(g, ">", new Font("arial", Font.PLAIN, 20), -60, (50 * currentOption), Color.WHITE);

      Game.ui.stringRender(g, "Damage: " + damage, new Font("arial", Font.BOLD, 16), -265, 50, Color.WHITE);
      Game.ui.stringRender(g, "Attack Speed: " + atkSpd, new Font("arial", Font.BOLD, 16), -248, 100, Color.WHITE);
      Game.ui.stringRender(g, "Critical Chance: " + critChance, new Font("arial", Font.BOLD, 16), -237, 150,
          Color.WHITE);
      Game.ui.stringRender(g, "Criticla Damage: " + critDamage, new Font("arial", Font.BOLD, 16), -235, 200,
          Color.WHITE);
    }
  }
}
