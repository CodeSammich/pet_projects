import java.util.*;

public class Enemy{
  
  public int health;
  public int damage;
  public int level;
  public int mana;
  public int critChance;

  public boolean dead;
  public boolean flee;
  public boolean fireCharging;
  public boolean critical;

  public Random r = new Random();

  public Enemy(){
    
    health = 100;
    mana = 0;
    damage = 10 + (level * 2);
    level = r.nextInt(10);
    dead = false;
    flee = false;
    critical = false;
    fireCharging = false;
  }
  
  public Enemy (int h, int l, int d){
    
    health = h;
    level = l;
    damage = d;
    
  }

  public void die() {
    dead = true;
  }
}

