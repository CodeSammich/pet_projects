import java.util.Random;

public class Monster {
	public int health;
	public int mana;
	public int damage;
	public int critChance;
	public Random r = new Random();
	public boolean dead;
	public boolean critical;
	public boolean fireCharging; // Fire Charging Attack

	public Monster(int health, int mana, int critChance) {
		this.health = health;
		this.mana = mana;
		this.damage = 10;
		this.critChance = critChance;

		dead = false;
		critical = false;
	}

	public void attack(Superhero hero, int damage) {
		// Critical Chance
		if (100 - critChance < r.nextInt(100)) {
			System.out.println("The monster hits the hero for a critical hit!");
			damage *= 2;
			critical = true;
		}

		// Attack
		int attackChoice = r.nextInt(100);
		if (fireCharging) {
			fireBreath(hero, damage);
		} else if (attackChoice <= 10) { // Fire Breath Charge Chance
			fireCharging = true;
			System.out
					.println("The Monster is about to use a powerful attack!");
		} else if (attackChoice > 10 && attackChoice <= 40) { // Tail Whip
			tailWhip(hero, damage);
		} else { // Blunt Attack
			hero.health -= damage;

			// Attack Message
			System.out.println("The Monster slashed at the hero for " + damage
					+ " damage!");
			// System.out.println("Our hero has " + hero.health + " health!");
		}
	}
 ///// DO MANA FOR MONSTER
	public void fireBreath(Superhero hero, int damage) { // Fire Breath Attack
		if (mana >= 50) {
		System.out.println("The Monster spewed fire on the hero!");
		System.out.println("The Hero takes " + damage * 3 + " damage!");
		hero.health -= 3 * damage;
		fireCharging = false;
		mana -= 50;
		}
	}

	public void tailWhip(Superhero hero, int damage) {
		if (mana >= 25) {
		System.out.println("The Monster spun its spiky tail and whipped the hero!");
		System.out.println("The Hero takes " + damage * 1.5 + " damage!");
		hero.health -= (1.5 * damage);
		mana -= 25;
		}
	}

	// Villain Dies
	public void die() {
		if (health <= 0) {
			dead = true;
			System.out.println("The Villain has died! Woohoo!");
		}
	}
}
