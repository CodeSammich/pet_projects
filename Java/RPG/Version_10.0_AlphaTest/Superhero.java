import java.util.Random;

public class Superhero {
	public int health;
	public int mana;
	public int damage;
	public int critChance; // Percent Chance
	public int dodgeChance; // Dodge Chance Percent (Decreases after each
	public int blockChance; // Block Percent
	public int counterChance; // Counter Percent

	public boolean dead; // Is it dead?
	public boolean critical;
	public boolean avoid; // Does the hero avoid/block the attack?
  public boolean poison;

	public Random r = new Random();

	public Superhero(int health, int mana, int critChance) {
		this.health = health;
		this.mana = mana;
		this.damage = 10;
		this.critChance = critChance;

		dead = false;
		critical = false;
		avoid = false;
    poison = false;

		dodgeChance = 75;
		blockChance = 50;
		counterChance = 50;
	}

  
  	public void punch(Enemy villain, int damage) {
		// Critical Chance
		if (100 - critChance < r.nextInt(100)) {
			System.out.println("The Hero hit the monster for a critical hit!");
			damage *= 2;
			critical = true;
		}

		// Attack
		villain.health -= damage;

		// Reset to Original Damage
		if (critical) {
			damage /= 2;
			critical = false;
		}
	}
 
	public void dodge(Enemy villain) {
		if (mana >= 10) {
			if (100 - dodgeChance < r.nextInt(100)) {
				avoid = true;
				System.out.println("The Enemy tried to hit the hero!");
				System.out.println("The hero avoided the monster's attack!");
				System.out.println("The Enemy hurt itself in anger!");
				villain.health -= 20;
			} else {
				System.out.println("The hero could not avoid the attack!");
			}

			mana -= 10;
		}
	}

	public void block() {
		if (100 - blockChance < r.nextInt(100)) {
			avoid = true;
			System.out.println(Main.name + " successfully blocked the attack!");
		} else {
			System.out.println(Main.name + " could not block the attack!");
		}
	}

	// Hero Dies
	public void die() {
			dead = true;
  }

  //To be overwritten by subclasses (Check with group)
  public void chop(Enemy v) {}
  public void sneak(Enemy v) {}
  public void shoot(Enemy v, int d) {}
  public void poison(Enemy v, int d) {}
}
