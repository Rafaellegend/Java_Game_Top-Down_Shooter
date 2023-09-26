package com.patopunchstudio.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

  public static Clip clip;
  public static long clipTimePosition;

  public static Clip play(String name, String function) {
    if (function == "start") {
      try {
        File musicPath = new File(name);
        if (musicPath.exists()) {
          AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
          clip = AudioSystem.getClip();
          clip.open(audioInput);
          clip.start();
          return clip;
        } else {
          System.out.println("Can`t find file");
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }else if(function == "resume") {
      try {
        File musicPath = new File(name);
        if (musicPath.exists()) {
          AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
          clip = AudioSystem.getClip();
          clip.open(audioInput);
          clip.setMicrosecondPosition(clipTimePosition);
          clip.start();
          return clip;
        } else {
          System.out.println("Can`t find file");
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    return null;
  }

  public static Clip loop(String name, String function) {
    if (function == "start") {
      try {
        File musicPath = new File(name);
        if (musicPath.exists()) {
          AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
          clip = AudioSystem.getClip();
          clip.open(audioInput);
          clip.loop(Clip.LOOP_CONTINUOUSLY);
          clip.start();
        } else {
          System.out.println("Can`t find file");
        }
      } catch (Exception e) {
      }

    } else if (function == "resume") {
      try {
        File musicPath = new File(name);
        if (musicPath.exists()) {
          AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
          clip = AudioSystem.getClip();
          clip.open(audioInput);
          clip.loop(Clip.LOOP_CONTINUOUSLY);
          clip.setMicrosecondPosition(clipTimePosition);
          clip.start();
        } else {
          System.out.println("Can`t find file");
        }
      } catch (Exception e) {
      }
    }
    return null;
  }

  public static void stop() {
    clip.stop();
  }

  public static void pause() {
    clipTimePosition = clip.getMicrosecondPosition();
    clip.stop();
  }
}
