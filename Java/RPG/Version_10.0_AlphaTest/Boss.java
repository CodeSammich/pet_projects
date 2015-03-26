import java.util.Random;

public class Boss extends Goon {
  public Random r = new Random();

	public Boss(int health, int mana, int critChance) {
		this.health = health;
		this.mana = mana;
		this.damage = 10;
		this.critChance = critChance;
	}

  public void approach() {
    System.out.println( "You hear a thud.. and a loud roar!" );
    System.out.println();
    System.out.println( "Before you towers a black dragon!" );
    System.out.println( "Its crimson eyes burns into your soul!");
    System.out.println( "The air shakes as it flaps its wings and attacks!");
  }

	public void attack(Superhero hero, int damage) {
		// Critical Chance
		if (100 - critChance < r.nextInt(100)) {
			damage *= 2;
			System.out.println("The dragon hits " + Main.name + " for a critical hit! It deals " + damage + " damage!");
			critical = true;
		}

		// Attack
		int attackChoice = r.nextInt(100); //Percent chance for Dragon to do certain attacks

		if (fireCharging) { //To activate second turn of Fire Breath Attack
			fireBreath(hero, damage);
		}
    else if (attackChoice <= 10 && mana >= 50) { // Fire Breath Charge + Attack 10% chance - 50 MP
			fireCharging = true;
      System.out.println("The dragon's eyes light up as his mouth turns a bright amber.");
      System.out.println("Intense heat engulfs " + Main.name);
			System.out.println("The dragon is about to use a powerful attack!");
		}
    else if (attackChoice > 10 && attackChoice <= 40 && mana >= 25) { // Tail Whip 30% chance - 25 MP
			tailWhip(hero, damage);
		} 
    else { // Blunt Attack
			hero.health -= damage;
			System.out.println("The dragon slashed at the hero for " + damage + " damage!");
		}
	}

  //Different Special Attacks
	public void fireBreath(Superhero hero, int damage) { // Fire Breath Attack
    System.out.println("The dragon spewed burning fire on " + Main.name);
		System.out.println(Main.name + " takes " + damage * 3 + " damage!");
		hero.health -= 3 * damage;
		fireCharging = false;
		mana -= 50;
  }

	public void tailWhip(Superhero hero, int damage) {
    System.out.println("The dragon spun its spiky tail and whipped " + Main.name + "!");
		System.out.println(Main.name + " takes " + damage * 1.5 + " damage!");
		hero.health -= (1.5 * damage);
		mana -= 25;
  }

  //Death
	public void die() {
		if (health <= 0) {
			dead = true;
		}
	}
}
