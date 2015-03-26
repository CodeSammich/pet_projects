//Samuel Zhang, Susan Wu, Felipe Mansilla, Amanda Chiu

import java.util.*;

public class Main {
	public static Scanner fingerBuddy = new Scanner(System.in);
  public static Random r = new Random();

  public static boolean fightGoon = true;
  public static boolean fightBoss = false;
  public static boolean encounter = true; //For approach messages
  public static boolean win = false; 
  public static boolean first = true; //To guarantee at least one goon fight
  public static boolean ninja = false; //To see what character the user chose
  public static boolean archer = false;

  public static int defaultHealth = 120;
  public static int defaultMana = 100;
  public static int defaultCritChance = 25;
  public static int action = 0;

  public static String cont = "Begin";
  public static String name = "Bob";
  public static String s = "";

  public static void ninjaCommands() {
    System.out.println( "1: Ninja Chop!" );
    System.out.println( "2: Sneak Attack! MP: 10" );
    System.out.println( "3: Dodge! MP: 10" );
    System.out.println( "4: Block! MP : 10");
  }

  public static void archerCommands() {
    System.out.println( "1: Shoot!" );
    System.out.println( "2: Poison! MP: 10" );
    System.out.println( "3: Dodge! MP: 10");
    System.out.println( "4: Block! MP: 10");
  }

	public static void main(String[] args) {
    Superhero hero = new Superhero(defaultHealth, defaultMana, defaultCritChance);
    Goon villain = new Goon();

   // Choose Character
    System.out.println("You have been given the opportunity of a lifetime!");
    System.out.println("Who do you wish to be on this adventure?");
    System.out.println();
    System.out.println("Ninja - He attacks foes acutely from the shadows.." );
    System.out.println("Archer - He snipes targets from a distance, even with prolonged damage.");
    System.out.println();
     
    System.out.println( "Please pick a class: " );
    while (!(s.equals("Ninja") || (s.equals("Archer")))) {
      s = fingerBuddy.next(); 

      if (s.equals("Ninja")) {
        hero = new Ninja(defaultHealth, defaultMana, defaultCritChance);
        ninja = true;
      }
      else if (s.equals("Archer")) {
        hero = new Archer(defaultHealth, defaultMana, defaultCritChance);
        archer = true;
      }
      else {
        System.out.println("Please enter either Ninja or Archer:");
      }
    }

    System.out.println( "Please pick a name: ");
    name = fingerBuddy.next();

    //Game Begins 
   
    while (!win) {
      //If the hero dies, then game ends.
      if (hero.dead) {
        System.out.println("Game Over");
        break;
      }

      //Beginning of Game/Continuing after Battle/End of Game if User Chooses
      if (cont.equals("Begin")) {
        System.out.println();
        System.out.println( name + " embarks on his glorious quest!" );
      }
      else if (cont.equals("Yes")) {
        System.out.println(name + " continue on your glorious quest. Onward!");
      }
      else if (cont.equals("No")) {
        System.out.println(" After a long day of journeying and adventure, ");
        System.out.println(" you finally return home and marry the girl of ");
        System.out.println(" your dreams, having become the hero of your village.");
        System.out.println();
        System.out.println("                              THE END.           ");
        
        break;
      }
      
      //Chance to spawn Boss
      if (!first) {
        if (100 - r.nextInt(100) > 30) {
          fightGoon = true;
        }
        else {
          fightGoon = false;
          fightBoss = true;
        }
      }
      //Actual Spawning of Enemies
      if (!first) {
        if (fightGoon) {
          villain = new Goon();
        }
        else if (fightBoss) {
          villain = new Boss(200, 200, 20);
        }
      }

      //Allows users to leave whenever they wish
      cont = "";

      //Resets Poison Effect
      hero.poison = false;

      //Battle Scene! =================================================================================
      while (!hero.dead && !villain.dead && !villain.flee) { // If they're both alive
        
        //Monster Approaches Text and Healing of Hero
        if (fightGoon && encounter) { //Will only print out encounter statement once
          villain.approach();
          hero.health += 30;
          System.out.println(name + " drank a potion and restored 30 health!");
          System.out.println();

          encounter = false;
        }
        else if (fightBoss && encounter) {
          villain.approach();
          hero.health = 2*defaultHealth;
          hero.mana = 2*defaultMana;
          System.out.println(name + " felt an inner strength as he looked into the dragon's eyes.");
          System.out.println(name + " felt his energy revitalized!");
          System.out.println("The dragon attacks!");

          encounter = false;
        }
        
        //Poison Turn
        if (hero.poison) {
            int poisonDamage = r.nextInt(5) + 1;
            villain.health -= poisonDamage;

            System.out.println("The enemy suffered " + poisonDamage + " poison damage!");
            System.out.println();
          }
        
			// Meta Interface/Output
        System.out.println("      HP: " + hero.health + "     " + "MP: " + hero.mana);
        System.out.println(" Monster HP: " + villain.health + " " + " Monster MP: " + villain.mana);
        System.out.println();
        
        // Hero User Input Commands (Do Randomized for Monster! :O)
        if (ninja) {
          ninjaCommands();
        }
        else if(archer) {
          archerCommands();
        }
        
        System.out.println();
        System.out.println("What do you wish to do? (Enter the corresponding number)");

        action = 0;

        //Reset Actions
        while (action != 1 && action != 2 && action != 3 && action != 4) {
          action = fingerBuddy.nextInt();

          if (action != 1 && action != 2 && action != 3 && action != 4) {
            System.out.println("Please input a number between 1 and 4!");
          }
        }
        
        System.out.println();
        
        //Character Actions        
        
        //Ninja
        if (ninja) {
          if (action == 1) {
            hero.chop(villain);
          }
          else if (action == 2) {
            hero.sneak(villain);
          }
          else if (action == 3) {
            hero.dodge(villain);
          }
          else if (action == 4) {
            hero.block();
          }
        }
        //Archer
        else if (archer) {
          if (action == 1) {
            hero.shoot(villain, hero.damage);
          }
          else if (action == 2) {
            hero.poison(villain, hero.damage);
          }
          else if (action == 3) {
            hero.dodge(villain);
          }
          else if (action == 4) {
            hero.block();
          }
        }

        //If Dragon Dies (To end game)
        if (villain.health <= 0) {
          villain.die();
        }
        
        if (villain.dead && fightBoss) {          
          win = true;
          break;
        }
        
        //If a Goon Dies (To continue game)
        if (villain.health <= 0 || villain.flee) {
          first = false;
          encounter = true;
          
          while(!(cont.equals("Yes") || cont.equals("No"))) { 
            System.out.println("Would you like to continue? Please enter Yes or No");
            cont = fingerBuddy.next();
            
            if (!(cont.equals("Yes") || cont.equals("No"))) {
              System.out.println("Please enter Yes or No");
            }
          }
          break;
        }
        
        //Monster Attacks
        //Goons
        if (fightGoon) {
          if (villain.level < 3 && r.nextInt(100) < 30) {
            villain.flee();
          }
          if (!villain.flee) {
            villain.attack(hero, villain.damage);
          }
          if (!hero.poison) {
            System.out.println();
            System.out.println();
          }
        }
        else if (fightBoss) {
          villain.attack(hero, villain.damage);
          if (!hero.poison) {
            System.out.println();          
            System.out.println();
          }
        }
        
        //If Hero Dies..
        if (hero.health <= 0) {
          hero.die();
          break;
        }        
      }
    }
    
    //If you win!
    if (win) {
      
      System.out.println( "After a long and arduous fight, the great dragon shakes ");
      System.out.println( "and, finally, falls. You return home with the dragon's ");
      System.out.println( "great talon and is crowned king among your villagers." );
      System.out.println();
      System.out.println( "                                THE END.             " );
      System.out.println();
      System.out.println( "And of course, you marry the pretty girl. " );
      
    }
  }
}
