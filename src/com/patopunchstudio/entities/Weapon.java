package com.patopunchstudio.entities;

public class Weapon extends Entity{
  public double damage;
  public double critChance;
  public double critDamage;

  public Weapon(int x, int y, int width, int height) {
    super(x, y, width, height);
    this.damage = 1;
    this.critDamage = 10;
    this.critChance = 0.5;
  }
  public void setWeaponStatus(double damage, double critDamage, double critChance) {
    this.damage = damage;
    this.critDamage = critDamage;
    this.critChance = critChance;

  }
  
}
