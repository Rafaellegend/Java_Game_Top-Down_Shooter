package com.patopunchstudio.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
  public static Sound backgroundMusic = new Sound("res/music.wav");
  public static Sound hit = new Sound("res/hit.wav");
  public static Sound pickup = new Sound("res/pickup.wav");
  public static Sound shoot = new Sound("res/shoot.wav");
  public static Sound finish = new Sound("res/finish.wav");

  public Clip clip;
  public long clipTimePosition, clipTimeLength;

  public Sound(String file) {
    System.out.println(file);
    try {
      File musicPath = new File(file);
      if (musicPath.exists()) {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioInput);
        this.clipTimeLength = this.clip.getMicrosecondLength();
      } else {
        System.out.println("Can`t find file");
      }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public Clip play() {
    try {
      this.clipTimePosition = 0;
      this.clip.setMicrosecondPosition(clipTimePosition);
      this.clipTimePosition = this.clip.getMicrosecondPosition();
      this.clip.start();

      if (this.clipTimePosition >= this.clipTimeLength) {
        System.out.println(this.clipTimePosition >= this.clipTimeLength);
        this.clipTimePosition = 0;
        this.clip.setMicrosecondPosition(clipTimePosition);
      }
      return clip;

    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public Clip loop() {
    try {
      this.clip.loop(Clip.LOOP_CONTINUOUSLY);
      this.clip.start();
      return clip;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public void stop() {
    this.clip.stop();
  }

  public Clip resume() {
    try {
      this.clip.setMicrosecondPosition(clipTimePosition);
      this.clip.start();
      return clip;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public void pause() {
    this.clipTimePosition = this.clip.getMicrosecondPosition();
    this.clip.stop();
  }
}
