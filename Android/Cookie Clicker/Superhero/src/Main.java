import java.util.Scanner;

public class Main {
	static Superhero hero = new Superhero(120, 100, 60);
	static Monster villain = new Monster(200, 200, 40);
	static Scanner fingerBuddy = new Scanner(System.in);

	public static void main(String[] args) {
		// ////////////Game Output

		while (!hero.dead && !villain.dead) { // If they're both alive
			// Meta Interface/Output
			System.out.println("      HP: " + hero.health + "     " + "MP: "
					+ hero.mana);
			System.out.println(" Monster HP: " + villain.health + " "
					+ " Monster MP: " + villain.mana);
			System.out.println();

			// Hero User Input Commands (Do Randomized for Monster! :O)
			// ////////// ***** PICK UP ITEMS INTO FULL TEXT BASED RPG
			System.out.println("1. Punch");
			System.out.println("2. Dodge MP: 15"); // MP Count and Cooldown
			System.out.println("3. Block");
			System.out.println("4. Counter MP: 20"); // Takes hit, hits twice,
														// MP
			System.out.println();

			// Hero's turn to attack

			// Pause for Input
			System.out.println( " What do you want to do? " );
			int action = fingerBuddy.nextInt();

			if (action == 1) {
				hero.punch(villain, hero.damage);
			} else if (action == 2) {
				hero.dodge(villain);
			} else if (action == 3) {
				hero.block();
			} else if (action == 4) {
				hero.counter(villain);
			}
			villain.die(); // If HP <= 0, villain dies

			System.out.println();

			// Villain's turn to attack
			if (!villain.dead && !hero.avoid) {
				villain.attack(hero, 10);
				hero.die(); // If HP <= 0, hero dies
			}

			hero.avoid = false;

			System.out.println();
			System.out.println();
		}
	}
}
