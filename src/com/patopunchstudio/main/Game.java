package com.patopunchstudio.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.patopunchstudio.entities.Bullet;
import com.patopunchstudio.entities.Enemy;
import com.patopunchstudio.entities.Entity;
import com.patopunchstudio.entities.Player;
import com.patopunchstudio.graphics.Spritesheet;
import com.patopunchstudio.graphics.UI;
import com.patopunchstudio.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

  public static int WIDTH = 640, HEIGHT = 480;
  public static int SCALE = 3;
  public static Player player;
  public static World world;
  public static List<Enemy> enemies = new ArrayList<Enemy>();
  public static List<Entity> entities = new ArrayList<Entity>();
  public static int current_Level = 1;
  public static int max_Level = 3;

  public static List<Bullet> bullets = new ArrayList<Bullet>();
  public static Random rand = new Random();
  public static UI ui;
  public static String gameState = "MENU";
  public static Menu menu;
  public static Pause pause;
  public static Sound sound;
  // public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Daydream.ttf");
  // public static Font newfont;
  private boolean restartGame = false;
  private boolean saveGame = false;

  public Game() {
    // try {
    //   newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(12f);
    // } catch (FontFormatException e) {
    //   e.printStackTrace();
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }

    //Sound.backgroundMusic.loop();
    this.addKeyListener(this);
    this.addMouseListener(this);
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    NewGame("map_" + current_Level + ".png");
  }

  public static void NewGame(String map) {
    entities.clear();
    enemies.clear();
    bullets.clear();
    entities = new ArrayList<Entity>();
    enemies = new ArrayList<Enemy>();
    bullets = new ArrayList<Bullet>();
    new Spritesheet();
    ui = new UI();
    world = new World("/" + map);
    menu = new Menu();
    pause = new Pause();
    return;
  }

  public void tick() {
    if (gameState == "NORMAL") {
      if (this.saveGame) {
        this.saveGame = false;
        String[] opt1 = { "level" };
        int[] opt2 = { current_Level };
        Menu.saveGame(opt1, opt2, 10);
        System.out.println("Salvado com sucesso");
      }
      player.tick();
      for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).tick();
      }

      if (enemies.size() == 0) {
        System.out.println("Passou de Fase");
        current_Level++;
        if (current_Level > max_Level) {
          current_Level = 1;
          Sound.finish.play();
        }
        String newWorld = "map_" + current_Level + ".png";
        NewGame(newWorld);
      }
    } else if (gameState == "GAMEOVER") {
      // System.out.println("Game Over!");
      Game.ui.frames++;
      if (Game.ui.frames == 40) {
        Game.ui.frames = 0;
        if (Game.ui.showMessageGameOver) {
          Game.ui.showMessageGameOver = false;
        } else {
          Game.ui.showMessageGameOver = true;
        }
      }
      if (restartGame) {
        this.restartGame = false;
        gameState = "NORMAL";
        current_Level = 1;
        String newWorld = "map_" + current_Level + ".png";
        NewGame(newWorld);
      }
    } else if (gameState == "MENU") {
      menu.tick();
    } else if (gameState == "PAUSE") {
      pause.tick();
    }

  }

  public void render() {
    BufferStrategy bs = this.getBufferStrategy();

    if (bs == null) {
      this.createBufferStrategy(3);
      return;
    }

    Graphics g = bs.getDrawGraphics();

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    world.render(g);
    player.render(g);

    for (int i = 0; i < enemies.size(); i++) {
      enemies.get(i).render(g);
    }
    for (int i = 0; i < entities.size(); i++) {
      entities.get(i).render(g);
    }
    ui.render(g);
    if (gameState == "MENU") {
      menu.render(g);
    }
    if (gameState == "PAUSE") {
      pause.render(g);
    }
    bs.show();
  }

  public static void main(String[] args) {
    Game game = new Game();
    JFrame frame = new JFrame();

    frame.add(game);
    frame.setTitle("Top-Down Shooter");
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setResizable(false);

    new Thread(game).start();
  }

  public synchronized void start() {

  }

  public synchronized void stop() {

  }

  @Override
  public void run() {
    requestFocus();
    long lastTime = System.nanoTime();
    double amountofTicks = 60.0;
    double ns = 1000000000 / amountofTicks;
    double delta = 0;
    int frames = 0;
    double timer = System.currentTimeMillis();
    while (true) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      if (delta >= 1) {
        tick();
        render();
        frames++;
        delta--;
      }

      if (System.currentTimeMillis() - timer >= 1000) {
        System.out.println("FPS: " + frames);
        frames = 0;
        timer += 1000;
      }
      // try {
      // Thread.sleep(1000 / 60);
      // } catch (InterruptedException e) {
      // e.printStackTrace();
      // }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      if (gameState == "NORMAL")
        player.right = true;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      if (gameState == "NORMAL")
        player.left = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
      if (gameState == "NORMAL")
        player.up = true;
      if (gameState == "MENU")
        menu.up = true;
      if (gameState == "PAUSE")
        pause.up = true;

    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
      if (gameState == "NORMAL")
        player.down = true;
      if (gameState == "MENU")
        menu.down = true;
      if (gameState == "PAUSE")
        pause.down = true;

    }
    if (e.getKeyCode() == KeyEvent.VK_J) {
      player.shoot = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (gameState == "GAMEOVER")
        restartGame = true;
      if (gameState == "MENU")
        menu.execute = true;
      if (gameState == "PAUSE")
        pause.execute = true;

    }
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      if (gameState == "GAMEOVER") {
        gameState = "MENU";
      }
      if (gameState == "NORMAL") {
        System.out.println("Pausado");
        player.pause = true;
      }
      if (gameState == "MENU") {
        System.exit(1);
      }
      if (gameState == "PAUSE") {
        pause.execute = true;
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      if (gameState == "NORMAL") {
        player.jump = true;
      }
    }

    if (e.getKeyCode() == KeyEvent.VK_P) {
      if (gameState == "NORMAL") {
        this.saveGame = true;
      }
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      player.right = false;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      player.left = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
      player.up = false;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
      player.down = false;
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    player.mouseShoot = true;
    player.mX = e.getX();
    player.mY = e.getY();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
