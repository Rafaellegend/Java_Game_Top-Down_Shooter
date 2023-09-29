package com.patopunchstudio.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
  public String[] options = { "Novo Jogo", "Carregar", "Sair" };

  public int currentOption = 0;
  public int maxOption = options.length - 1;
  public String text;
  public int textWidth;
  public int posH;
  public int posV;
  public boolean up, down, execute;

  public static boolean saveExist = false;
  public static boolean saveGame = false;


  public void tick() {
    File saveFile = new File("save.txt");
    if(saveFile.exists()){
      saveExist = true;
    }
    else{
      saveExist = false;
    }
    if (execute) {
      if (currentOption == 0) {
        Game.gameState = "NORMAL";
        Game.current_Level = 1;
        String newWorld = "map_" + Game.current_Level + ".png";
        Game.NewGame(newWorld);
        saveFile.delete();
      }else if( (currentOption == 1)){
        if(saveFile.exists()){
          String save = loadGame(10);
          applySave(save);
        }
      }
      else if (currentOption == 2) {
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

  public static void applySave(String str) {
    String[] spl = str.split("/");
    for (int i = 0; i < spl.length; i++) {
      String[] spl2 = spl[i].split(":");
      switch(spl2[0])
      {
         case "level":
         Game.NewGame("map_" + spl2[1] + ".png");
         Game.gameState = "NORMAL";
         Game.player.pause = false;
      }
    }
  }

  public static String loadGame(int encode){
    String line = "";
    File saveFile = new File("save.txt");
    if(saveFile.exists()){
      try {
        String singleLine = null;
        BufferedReader reader = new BufferedReader(new FileReader(saveFile));
        try {
          while((singleLine = reader.readLine()) != null){
            String[] trans = singleLine.split(":");
            char[] val = trans[1].toCharArray();
            trans[1] = "";
            for (int i = 0; i < val.length; i++) {
              val[i]-=encode;
              trans[1]+=val[i];
            }
            line+=trans[0];
            line+=":";
            line+=trans[1];
            line+="/";
          }
        } catch (IOException e) {
        }
      } catch (FileNotFoundException e) {
      }
    }
    return line;
  }

  public static void saveGame(String[] val1, int[] val2, int encode) {
    BufferedWriter write = null;
    try {
      write = new BufferedWriter(new FileWriter("save.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < val1.length; i++) {
      String current = val1[i];
      current += ":";
      char[] value = Integer.toString(val2[i]).toCharArray();
      for (int n = 0; n < value.length; n++) {
        value[n] += encode;
        current += value[n];
      }
      try {
        write.write(current);
        if (i < val1.length - 1){
          write.newLine();
        }
      } catch (IOException e) {

      }
    }
    try {
      write.flush();
      write.close();
    } catch (IOException e) {
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
