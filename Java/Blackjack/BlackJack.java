public class BlackJack {
    public static int[] number; //Deck values
    public static char[] suit; //Deck suit

    public BlackJack() {
      //Initialize deck structure
	number = new int[];
	char = {'D', 'C', 'H', 'S'}; //Diamond, Club, Heart, Spade
	for(int i = 0; i < 13; i++)
	    number[i] = i;
    }
    
    public static void main(String[] args) {
	
    }
}