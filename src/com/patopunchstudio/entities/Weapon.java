package com.patopunchstudio.entities;

public class Weapon extends Entity{
  public double damage;
  public double critChance;
  public double critDamage;
  public double manaConsumption;
  public double projetileSpeed;
  public double projetileSize;

  public Weapon(int x, int y, int width, int height) {
    super(x, y, width, height);
    this.damage = 1;
    this.critDamage = 10;
    this.critChance = 0.5;
    this.manaConsumption = 1;
    this.projetileSpeed = 8;
    this.projetileSize = 1;
  }
  public void setWeaponStatus(double damage, double critDamage, double critChance, double manaConsumption, double projetileSpeed, double projetileSize) {
    this.damage = damage;
    this.critDamage = critDamage;
    this.critChance = critChance;
    this.manaConsumption = manaConsumption;
    this.projetileSpeed = projetileSpeed;
    this.projetileSize = projetileSize;
  }
  
}
