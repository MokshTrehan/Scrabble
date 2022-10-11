// // this file represents a game of Scrabble, working as a scoring and check file. Created by Moksh Trehan. 
import java.io.*;
import java.util.*;

public class Scrabble {
	// defines an object of type Tile, used to contain a set of tile objects, privately
	private Tile[] tiles;
	
	private HashMap<String, Integer> pullHashmap() {
		// creates a Hash map to use for scoring purposes. Private so only used in this file.
		HashMap<String, Integer> scoreSheet = new HashMap<String, Integer>();
		// sets the values for the Hash map, assigning scores to letters.
		scoreSheet.put("A",1);
		scoreSheet.put("E",1);
		scoreSheet.put("I",1);
		scoreSheet.put("O",1);
		scoreSheet.put("R",1);
		scoreSheet.put("S",1);
		scoreSheet.put("T",1);
		scoreSheet.put("U",1);
		scoreSheet.put("L",1);
		scoreSheet.put("N",1);
		
		scoreSheet.put("D",2);
		scoreSheet.put("G",2);
		
		scoreSheet.put("B",3);
		scoreSheet.put("C",3);
		scoreSheet.put("M",3);
		scoreSheet.put("P",3);
		
		scoreSheet.put("F",4);
		scoreSheet.put("H",4);
		scoreSheet.put("V",4);
		scoreSheet.put("W",4);
		scoreSheet.put("Y",4);
		
		scoreSheet.put("K",5);
		
		scoreSheet.put("J",8);
		scoreSheet.put("X",8);
		
		scoreSheet.put("Q",10);
		scoreSheet.put("Z",10);
		// returns the scoreSheet Hash map with all the scoring values.
		return scoreSheet;
	}
	
	
	public Scrabble() {
		Tile randTile = new Tile();
		Tile[] scrabbleTiles = new Tile[7];
		// initializes a new tile object of size 7
		for  (int i = 0; i < 7; i++) {
			// uses Tile.Java's pickup method to create a random set of characters for tile values.
			randTile.pickup();
			char randChar = randTile.getValue();
			// creates a tile object from the random value, and then adds it to the scrabbleTiles array.
			Tile t1 = new Tile(randChar);
			scrabbleTiles[i] = t1;
		}
		this.tiles = scrabbleTiles;
	}
	
	
	public Scrabble(Tile[] scrabbleArray){
		// sets the tiles object to an input of an array of type Tile[].
		this.tiles = scrabbleArray;
	}
	
	
	public String getLetters() {
		String letterString = "";
		Tile[] letterArray = this.tiles;
			for (int i = 0; i < 7; i++) {
				// forms a string from the objects.
				letterString += letterArray[i].getValue();
			}
		// returns the string
		return letterString;
		}
	
	
	public boolean canSpellCheck(String wordToSpell) {
		String scrabbleTiles = this.getLetters();
		// maps a new Hash map.
		Map<Character, Integer> spellMap = new HashMap<>();
        for (int i = 0; i < scrabbleTiles.length(); i++) {
        	/* 
        	 * creates keys from the scrabbleTiles characters and then check if the map contains it, mapping
        	* the count of the occurrence of the character as the value.
        	*/
            Character key = scrabbleTiles.charAt(i);
            int count = 0;
            if (spellMap.containsKey(key)) {
                count = spellMap.get(key);
            }    
            spellMap.put(key, ++count);
        }
        for (int i = 0; i < wordToSpell.length(); i++) {
        	/*
        	 * creates a key from the wordToSpell characters, then uses count to compare the keys,
        	 * by deducting count to see if the count is the same for both keys. if it is not, and the count
        	 * goes to 0 after operating, return false.
        	 */
            Character key = wordToSpell.charAt(i);
            if (spellMap.containsKey(key)) {
                int count = spellMap.get(key);
                if (count > 0) {
                    spellMap.put(key, --count);
                } else {
                    return false;
                }
            } else {
            	// return false if the Spell map does not contain the key.
                return false;
            }
        }
        return true;
    }
		
	
	
	public ArrayList<String> getWords() {
		// defines a new arrayList because we are working with n objects, so a normal array would be tedious.
		ArrayList<String> wordList = new ArrayList <String>();
		try {
			// opens the file to read.
			File wordFile  = new File("CollinsScrabbleWords2019.txt");
			try (Scanner reader = new Scanner(wordFile)) {
				// iterates line by line over the file.
				while(reader.hasNextLine()) {
					// sets the line to a string variable
					String word = reader.nextLine();
					// turns it into an array for sorting purposes
					char [ ] wordArray = word.toCharArray();
					Arrays.sort(wordArray);
					// runs the canSpellCheck function to test if word can be spelled. if yes, adds to word array
					if (this.canSpellCheck(word)){
						wordList.add(word);
					}
				}
			}
			
		} catch (IOException e) {
			// simply there to prevent issues when reading the file, or to catch them.
			e.printStackTrace();
		}
		return wordList;
	}
	
	
	public int[] getScores() {
		// creates arrays and pulls a Hash map from private function for scoring.
		ArrayList<Integer> scoreList = new ArrayList<Integer>();
		HashMap<String, Integer> scoreSheet = this.pullHashmap();
		ArrayList<String> wordList = this.getWords();
		for (int i = 0; i < wordList.size(); i++ ) {
			// iterates over wordList
			String wordToCheck = wordList.get(i);
			int wordScore = 0;
			for (int j = 0; j < wordToCheck.length(); j++) {
				// iterates over the characters of the word, uses them as keys to pull from the Hash map.
				String letter = String.valueOf(wordToCheck.charAt(j));
				// adds the return values of the Hash map.
	            wordScore += scoreSheet.get(letter);
	        }
			// adds the score for the word to the empty array
			scoreList.add(wordScore);
		}
		// maps an array to type int using the .stream module.
		int[] finalScoreList = scoreList.stream().mapToInt(i -> i).toArray();
		// sorts and returns the array.
		Arrays.sort(finalScoreList);
		return finalScoreList;
	}
	
	
	public Boolean equals(Scrabble other) {
		// maps both objects .getLetters to char Arrays to sort.
		char[] originalLetters = this.getLetters().toCharArray();
		Arrays.sort(originalLetters);
		char[] lettersToCompare = other.getLetters().toCharArray();
		Arrays.sort(lettersToCompare);
		// compares the array to see if they are equal, and returns true if so.
    	if (Arrays.equals(originalLetters, lettersToCompare)){
    		return true;
    	} else {
    		return false;}
	}
}
		





