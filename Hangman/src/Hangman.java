/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;


public class Hangman extends ConsoleProgram {
	
	private static final int MAX_GUESSES = 8;
	
	private HangmanCanvas canvas;
	private RandomGenerator rgen = RandomGenerator.getInstance();
    private HangmanLexicon lexicon = new HangmanLexicon();
    private String word;
	
	public int nGuesses;
	private String guessWord = "";
	private char guessChar;
	
	// Add HangmanCanvas to program window
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
    public void run() {
    	while (true) {
    		setup();
    		while (true) {
    			displayGuessWord();
    			displayNGuesses();
    			if (nGuesses == MAX_GUESSES) {
    				println("The word was: " + word);
    				println("You lose.");
    				break;
    			}
    			readGuess();
    			checkGuess(); // Checks guess, updates word and nGuesses
    			if (checkForWin()) {
    				println("You guessed the word: " + word);
    				println("You win!");
					break;
    			}
    		}
    		playAgain();
    	}
	}
    
    private void setup() {
    	nGuesses = 0;
    	guessWord = "";
    	chooseWord();
    	createGuessWord();
    	canvas.reset();
    	canvas.displayWord(guessWord);
		println("Welcome to Hangman!");
    }
    
    private void chooseWord() {
        int randomIndex = rgen.nextInt(0, lexicon.getWordCount());
        word = lexicon.getWord(randomIndex);
    }
    
    /** Creates initial word of "---" */
    private void createGuessWord() {
    	for (int i = 0; i < word.length(); i++) {
    		guessWord += "-";
    	}
    }

    private void displayGuessWord() {
    	println("The word now looks like this: " + guessWord);
    }
    
    
    private void displayNGuesses() {
    	if (nGuesses < MAX_GUESSES) {
    		if (nGuesses == MAX_GUESSES - 1) {
    			println("You have only one guess left.");
    		} else {
    			int remainingGuesses = MAX_GUESSES - nGuesses;
    			println("You have " + remainingGuesses + " guesses left.");
    		}
    	} else {
    		println("You're completely hung.");
    	}
    }
    
    
    /** Reads a guess from player and checks if it is a valid single char.
     *  Insures guessChar is upper case.
     *  Repeats until guess is valid. */
    private void readGuess() {
    	while (true) {
    		String str = readLine("Your guess: ");
    		if (str.length() > 1) {
    			println("Please enter only one character");
    		} else {
    			str = str.toUpperCase();
    			guessChar = str.charAt(0);
    			if (guessChar < 65 || guessChar > 90) {
    				println("Guess is invalid. Please enter a letter.");
    			} else break;
    		}
    	}
    }
    
    
    private void checkGuess() {
    	boolean isCorrect = false;
    	int i = 0;
    	while (i < word.length()) {
    		if (guessChar == word.charAt(i)) {
    			isCorrect = true;
    			break;
    		}
    		i++;
    	}
    	if (isCorrect) {
    		println("That guess is correct.");
    		updateGuessWord();
    		canvas.displayWord(guessWord);
    	} else {
    		println("There are no " + guessChar + "'s in the word.");
    		updateNGuesses();
    		canvas.noteIncorrectGuess(guessChar, nGuesses);
    	}
    }
    
    
    /** Called within checkGuess(). Updates guessWord 
     *  and calls updateNGuesses() if correct guess not repeated. */
    private void updateGuessWord() {
    	String str = "";
    	for (int i = 0; i < word.length(); i++) {
    		if (guessWord.charAt(i) == '-') {
    			if (guessChar == word.charAt(i)) {
    				str += word.charAt(i);
    			} else {
    				str += '-';
    			}
    		} else {
    			str += guessWord.charAt(i);
    		}
    	}
    	guessWord = str;
    }
    
    private void updateNGuesses() {
    	nGuesses++;
    }
    
    /** Compares guessWord to word. If same, player has won. */
    private boolean checkForWin() {
    	boolean hasWon = true;
    	for (int i = 0; i < word.length(); i++) {
    		if (guessWord.charAt(i) != word.charAt(i)) {
    			hasWon = false;
    			break;
    		}
    	}
    	return hasWon;
    }
    
    private void playAgain() {
    	String str = readLine("Play again? [Y/N]: ");
		if (str.length() > 1) {
			println("Invalid response.");
		} else {
			str = str.toUpperCase();
			char ch = str.charAt(0);
			switch (ch) {
				case 'Y': break;
				case 'N': exit();
				default: println("Invalid response.");
						playAgain();
						break;
			}
		}
	}

    
    
}
