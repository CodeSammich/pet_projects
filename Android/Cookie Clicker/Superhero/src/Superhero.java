import java.util.Random;

public class Superhero {
	public int health;
	public int mana;
	public int damage;
	public int critChance; // Percent Chance
	public boolean dead; // Is it dead?
	public boolean critical;
	public String hitAction; // Different Attacks Output

	public int dodgeChance; // Dodge Chance Percent (Decreases after each
							// successful dodge)
	public boolean avoid; // Does the hero avoid/block the attack?

	public int blockChance; // Block Percent

	public int counterChance; // Counter Percent

	public boolean cannotUseAttack; //If MP is not enough
	
	public Random r = new Random();

	public Superhero(int health, int mana, int critChance) {
		this.health = health;
		this.mana = mana;
		this.damage = 10;
		this.critChance = critChance;
		dead = false;
		critical = false;

		hitAction = "blunted";
		// Avoid
		dodgeChance = 75;
		avoid = false;

		// Block
		blockChance = 50;

		// Counter
		counterChance = 50;
		
		//MP Count
		cannotUseAttack = false;
	}

	public void punch(Monster villain, int damage) {
		// Critical Chance
		if (100 - critChance < r.nextInt(100)) {
			System.out.println("The Hero hit the monster for a critical hit!");
			damage *= 2;
			critical = true;
		}

		// Attack
		villain.health -= damage;

		// Attack Message (Monster Health)
		System.out.println("The Hero " + hitAction + " the monster for "
				+ damage + " damage!");
		// System.out.println("Boom! The Monster has " + villain.health
		// + " health!");

		// Reset to Original Damage
		if (critical) {
			damage /= 2;
			critical = false;
		}
	}

	public void dodge(Monster villain) {
		if (mana >= 15) {
			if (100 - dodgeChance < r.nextInt(100)) {
				avoid = true;
				System.out.println("The Monster tried to hit the hero!");
				System.out.println("The hero avoided the monster's attack!");
				System.out.println("The Monster hurt itself in anger!");
				villain.health -= 20;
			} else {
				System.out.println("The hero could not avoid the attack!");
			}

			mana -= 15;
		}
		else {
			cannotUseAttack = true;
		}
	}

	public void block() {
		if (100 - blockChance < r.nextInt(100)) {
			avoid = true;
			System.out.println("The Hero successfully blocked the attack!");
		} else {
			System.out.println("The Hero could not block the attack!");
		}
	}

	public void counter(Monster villain) {
		if (mana >= 20) {
			if (100 - counterChance < r.nextInt(100)) {
				System.out
						.println("The Hero successfully countered the attack!");
				hitAction = "countered";
				punch(villain, 2 * damage);
			} else {
				System.out.println("The Hero failed to counter the attack!");
				punch(villain, damage);
			}

			hitAction = "punched";
			mana -= 20;
		}
		else {
			cannotUseAttack = true;
		}
	}

	// Hero Dies
	public void die() {
		if (health <= 0) {
			dead = true;
			System.out
					.println("The Hero has died. And the world fades to black...");
		}
	}
}
