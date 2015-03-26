import java.util.Random;

public class Ninja extends Superhero {

  public Ninja(int health, int mana, int critChance) {
    super(health, mana, critChance);
    this.health = health;
    this.damage = 15;
    this.critChance = critChance;
    dead = false;
    critical = false;
    
    //Dodge
    dodgeChance = 80;  //a little higher bc ninjas are fast, an advantage to choosing to be a ninja
    avoid = false;
    
    blockChance = 75; //higher bc ninjas are fast, another advantage to choosing to be a ninja
    counterChance = 50;
  }
  
  //Hero dies
  public void die() {
    if (health <= 0) {
      dead = true;
      System.out.println("The world fades to black...");
      System.out.println("I am sorry Sensei.. I have failed you.. ");
    }
  }
  
  //NINJA CHOP
  public void chop(Enemy villain) {
    System.out.println( Main.name + " used Ninja Chop!");
    
    //Critical Chance
    if (100 - critChance < r.nextInt(100)) {
	    System.out.println( Main.name + " hit the enemy for a critical hit!");
	    damage *= 2;
	    critical = true;
    }
    
    //Attack
    villain.health -= damage;
    System.out.println( Main.name + " ninja-chopped the enemy for " + damage + " damage.");
    
    //Reset to Original Damage
    if (critical) {
      damage /= 2;
      critical = false;
    }
  }
  
  //SNEAK ATTACK
  public void sneak(Enemy villain) {
    System.out.println( Main.name + " used Sneak Attack!");	

    if (mana >=5) { //Higher Crit Chance for Goon than Boss
      if (villain.getClass().getName() == "Boss") { //True if Boss
        critChance = 75;
	    }
	    else if (villain.getClass().getName() == "Goon") { //True if Goon
        critChance = 85;
	    }

      //Critical Chance if true
	    if (100 - critChance < r.nextInt(100)) {
        damage *= 2;
        System.out.println( "Your sneak attack caught the enemy off guard! You hit the enemy for a critical hit! You dealt " + damage + " damage!");
        critical = true;
	    }
	    else {
        System.out.println(Main.name + " snuck up on the enemy and dealt " + damage + " damage!");
	    }
	    
	    //Do damage
	    villain.health -= damage;
      
	    //Reset to Original Damage
	    if (critical) {
        damage /= 2;
        critical = false;
	    }
	    
	    mana -= 10;
    }
    else {
	    System.out.println( "You may not use Sneak Attack because you are out of mana.");
    }
  }
  
  public void dodge(Enemy villain) {
    System.out.println( Main.name + " used Dodge!");
    
    if (mana >= 10) {
	    if (100-dodgeChance < r.nextInt(100)) { //If Avoided
        avoid = true;
        System.out.println("The enemy tried to hit " + Main.name + " but " + Main.name + " swiftly evaded the attack!");
	    }
	    else { //If not avoided
        System.out.println(Main.name + " could not avoid the enemy's attack.");
	    }

	    mana -= 10;
    }
    
    else { //If out of mana
	    System.out.println( "You may not use Dodge because you are out of mana.");
    }
  }
}
