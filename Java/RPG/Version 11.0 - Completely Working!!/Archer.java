import java.util.*;

public class Archer extends Superhero {
  
  public Archer(int health, int mana, int critChance) {
    super(health, mana, critChance);
    this.health = health;
    this.damage = 15;
    this.critChance = critChance;
    dead = false;
    critical = false;                                                  
    dodgeChance = 60;                                             
    avoid = false;
    blockChance = 40;                                                             
    counterChance = 50;
    poison = false;
  }
  
  public Archer() {
    super(100, 10, 10 ); //health = 100, mana = 10, critchance = 10
  }
  
  public void shoot( Enemy villain, int damage) {                     
    if (100 - critChance < r.nextInt(100)) { //If Critical Attack
      damage = damage * 3;
      
      System.out.println(Main.name + " score a critical hit! " + Main.name + " attacked the monster for " + damage + " damage");     
    }            
    else  { //Regular Attack
      System.out.println(Main.name + " shot the monster for " + damage + " damage");
    }
      villain.health -= damage;
  }
  
  public void poison( Enemy villain, int damage) {
    if (mana >= 10) {
      villain.health -= damage;  
      System.out.println( Main.name + " shot a poison arrow!" );
      mana -= 10;

      poison = true;
    }	
	}
} 
