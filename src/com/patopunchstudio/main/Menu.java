package com.patopunchstudio.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
  public String[] options = { "Novo Jogo", "Carregar", "Sair" };

  public int currentOption = 0;
  public int maxOption = options.length - 1;
  public String text;
  public int textWidth;
  public int posH;
  public int posV;
  public boolean up, down, execute;

  public void tick() {
    if (execute) {
      if (currentOption == 0) {
        Game.gameState = "NORMAL";
        Game.current_Level = 1;
        String newWorld = "map_" + Game.current_Level + ".png";
        Game.NewGame(newWorld);
      }
      if(currentOption == 2){
        System.exit(1);
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
  }

  public void render(Graphics g) {
    if (Game.gameState == "MENU") {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
      Game.ui.stringRender(g, "Mage Dungeon", new Font("arial", Font.BOLD, 40), 0, -60, Color.WHITE);
      for (int i = 0; i < options.length; i++) {
        Game.ui.stringRender(g, options[i], new Font("arial", Font.PLAIN, 20), 0, (50 * i), Color.WHITE);
      }
      Game.ui.stringRender(g, ">", new Font("arial", Font.PLAIN, 20), -60, (50 * currentOption), Color.WHITE);
    }
  }
}
