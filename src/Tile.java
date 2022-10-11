// this file represents a single tile in a game of Scrabble. Created by Moksh Trehan. 
import java.util.Random;
public class Tile {
	// defines a private value of type char.
	private char value;
	
	public Tile() {
		// sets the value to an empty character.
		this.value = '\0';
	}
	
	
	public Tile(char letter) {
		// sets the value to the input character.
		this.value = letter;
	}
	
	
	public void pickup() {
		// using the java.util.random(), pulls a random letter from the alphabet.
		Random letter = new Random();
		int character = letter.nextInt(26) + (byte)'a';
		// sets the random letter to this.value
		this.value = (char)character;
	}
		
	
	public char getValue() {
		// simply returns the stored value.
		return this.value;
	}	
}
