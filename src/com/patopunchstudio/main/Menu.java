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
    if (execute) {
      if (currentOption == 0) {
        Game.gameState = "NORMAL";
        Game.current_Level = 1;
        String newWorld = "map_" + Game.current_Level + ".png";
        Game.NewGame(newWorld);
      }
      execute=false;
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

    text = "Mage in the Maze";
    g.setColor(Color.WHITE);
    g.setFont(new Font("arial", Font.BOLD, 40));
    textWidth = g.getFontMetrics().stringWidth(text);
    posH = (Game.WIDTH / 2) - (textWidth / 2);
    posV = (Game.HEIGHT / 2) + 40;
    g.drawString(text, posH, posV - 100);

    g.setColor(Color.WHITE);
    g.setFont(new Font("arial", Font.PLAIN, 20));
    for (int i = 0; i < options.length; i++) {
      textWidth = g.getFontMetrics().stringWidth(text);
      posH = (Game.WIDTH / 2) - (textWidth / 2);
      posV = (Game.HEIGHT / 2) + 20;
      g.drawString(options[i], posH, posV + (50 * i));
    }
    g.drawString(">", posH - 50, posV + (50 * currentOption));
  }
}
