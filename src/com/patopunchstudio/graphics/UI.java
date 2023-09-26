package com.patopunchstudio.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.patopunchstudio.main.Game;

public class UI {
  public String text;
  public int textWidth;
  public int posH;
  public int posV;
  public int frames;
  public boolean showMessageGameOver = true;


  public void stringRender(Graphics g, String string, Font font, int marginW, int marginH, Color color) {
    text = string;
    g.setFont(font);
    textWidth = g.getFontMetrics().stringWidth(text);
    posH = (Game.WIDTH / 2) - (textWidth / 2) + marginW;
    posV = (Game.HEIGHT / 2) + marginH;
    g.setColor(color);
    g.drawString(text, posH, posV);
  }

  public void render(Graphics g) {
    g.setFont(new Font("arial", Font.BOLD, 13));
    // Life Bar
    g.setColor(Color.BLACK);
    g.fillRect(20, 20, (Game.player.maxlife / Game.player.maxlife) * 100, 20);
    g.setColor(Color.RED);
    if (Game.player.life > 0) {
      g.fillRect(20, 20, (int) ((Game.player.life / Game.player.maxlife) * 100), 20);
    }
    g.setColor(Color.WHITE);
    g.drawString((int) Game.player.life + "/" + Game.player.maxlife, 50, 35);
    // Mana Bar
    g.setColor(Color.BLACK);
    g.fillRect(520, 20, (Game.player.maxmana / Game.player.maxmana) * 100, 20);
    g.setColor(Color.BLUE);
    g.fillRect(520, 20, (int) ((Game.player.mana / Game.player.maxmana) * 100), 20);
    g.setColor(Color.WHITE);
    g.drawString((int) Game.player.mana + "/" + Game.player.maxmana, 550, 35);
    // Weapon Bar
    g.setColor(Color.BLACK);
    g.fillRect(570, 400, 50, 50);
    if (Game.player.wpm == true) {
      g.setColor(Color.WHITE);
      // g.drawString("Arma", 578, 430);
      g.drawImage(Game.player.equiped.getSprite(), 578, 410, 32, 32, null);
    }
    g.setColor(Color.WHITE);
    g.drawString("J", 575, 445);

    // Game Over Screen
    if (Game.gameState == "GAMEOVER") {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(new Color(0, 0, 0, 100));
      g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

      stringRender(g, "Game Over", new Font("arial", Font.BOLD, 40), 0, 0, Color.WHITE);

      if (this.showMessageGameOver == true) {
        stringRender(g, "Pressione 'Enter' para reiniciar", new Font("arial", Font.BOLD, 18), 0, 40, Color.WHITE);
        stringRender(g, "Pressione 'Esc' para voltar ao menu", new Font("arial", Font.BOLD, 18), 0, 80, Color.WHITE);
      }

    }

  }

}
