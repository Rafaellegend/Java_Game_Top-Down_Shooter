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
  
  public static List<Bullet> bullets = new ArrayList<Bullet>();
  public static Random rand = new Random();
  public static UI ui;

  public Game() {
    this.addKeyListener(this);
    this.addMouseListener(this);
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    NewGame();

  }

  public static void NewGame(){
    entities.clear();
    enemies.clear();
    entities = new ArrayList<Entity>();
    enemies = new ArrayList<Enemy>();
    new Spritesheet();
    ui = new UI();
    world = new World("/map_small.png");
    return;
  }

  public void tick() {
    player.tick();

    for (int i = 0; i < enemies.size(); i++) {
      enemies.get(i).tick();
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
    g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
    world.render(g);
    player.render(g);

    for (int i = 0; i < enemies.size(); i++) {
      enemies.get(i).render(g);
    }
    for (int i = 0; i < entities.size(); i++) {
      entities.get(i).render(g);
    }
    ui.render(g);
    bs.show();
  }

  public static void main(String[] args) {
    Game game = new Game();
    JFrame frame = new JFrame();

    frame.add(game);
    frame.setTitle("Mini Zelda");
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
    int frames =0;
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

      if(System.currentTimeMillis() - timer >= 1000){
        System.out.println("FPS: " + frames);
        frames =0;
        timer +=1000;
      }
      // try {
      //   Thread.sleep(1000 / 60);
      // } catch (InterruptedException e) {
      //   e.printStackTrace();
      // }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      player.right = true;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      player.left = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
      player.up = true;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
      player.down = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_J) {
      player.shoot = true;
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
    throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
  }

  @Override
  public void mousePressed(MouseEvent e) {
    player.shoot = true;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
  }
}
