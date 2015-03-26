import java.util.Random;

public class Goon extends Enemy {

  public Random r = new Random();
 
  public Goon(){
    super();
  }
  
  public void approach(){
    System.out.println("A wild Goon of level " + level + "  approaches!");
  }
  
  public void attack(Superhero hero, int damage){    
    if (100 - r.nextInt(100) > 80 ){ //20% chance to miss
      System.out.println("Goon could not deal any damge!");
    }
    else if(100 - r.nextInt(100) > 80) { //20% chance to do a critical hit
      damage *= 2;
      System.out.println("The goon delivers a critical hit for " + damage + " damage!");
    }
    else {
      System.out.println("Goon delivers a blow for " + damage + " damage!");
    }

    hero.health -= damage;
  }
  
  public void flee(){
    System.out.println("The Goon got scared...he has now fled!");
    flee = true;
  }
  
  public void die(){
    if (health <= 0) {
	    dead = true;
	    System.out.println("The Goon has died! Wooohooo!");
    }
  }
}
