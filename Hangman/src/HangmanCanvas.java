/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
 

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
		private static final int SCAFFOLD_HEIGHT = 360;
		private static final int BEAM_LENGTH = 144;
		private static final int ROPE_LENGTH = 18;
		private static final int HEAD_RADIUS = 36;
		private static final int BODY_LENGTH = 144;
		private static final int ARM_OFFSET_FROM_HEAD = 28;
		private static final int UPPER_ARM_LENGTH = 72;
		private static final int LOWER_ARM_LENGTH = 44;
		private static final int HIP_WIDTH = 36;
		private static final int LEG_LENGTH = 108;
		private static final int FOOT_LENGTH = 28;
		
		private static double cx = 200;
		private static double cy = 200;
		
		private static final String FONT = "SERIF-PLAIN-26";
	
	/** HangmanCanvas constructor. You can do initialization (if needed) here. */
	public HangmanCanvas() {
	}
	
	private void addScaffold() {
		double x = cx;
		double y = cy - (BODY_LENGTH / 2) - (HEAD_RADIUS * 2);
		GLine rope = new GLine(x, y, x, y - ROPE_LENGTH);
		add(rope);
		GLine beam = new GLine(x, y - ROPE_LENGTH, x - BEAM_LENGTH, y - ROPE_LENGTH);
		add(beam);
		GLine scaffold = new GLine(x - BEAM_LENGTH, y - ROPE_LENGTH, x - BEAM_LENGTH, y - ROPE_LENGTH + SCAFFOLD_HEIGHT);
		add(scaffold);
	}
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		removeAll();
		addScaffold();
		incorrectLetters = "";
	}
	
/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		displayWord.setLabel(word);
		double w = displayWord.getWidth();
		displayWord.setLocation(cx- (w/2), cy * 2.2);
		displayWord.setFont(FONT);
		add(displayWord);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter, int nGuesses) {
		addNextBodyPart(nGuesses);
		addLetterToList(letter);
	}
	
	private void addNextBodyPart(int nGuesses) {
		switch (nGuesses) {
			case 0:	break;
			case 1:	addHead(cx, cy - (BODY_LENGTH / 2));
					break;
			case 2: addBody(cx, cy - (BODY_LENGTH / 2));
					break;
			case 3: addLeftArm(cx, cy - (BODY_LENGTH / 2));
					break;
			case 4: addRightArm(cx, cy - (BODY_LENGTH / 2));
					break;
			case 5: addLeftLeg(cx, cy + (BODY_LENGTH / 2));
					break;
			case 6: addRightLeg(cx, cy + (BODY_LENGTH / 2));
					break;
			case 7: addLeftFoot(cx, cy + (BODY_LENGTH / 2));
					break;
			case 8: addRightFoot(cx, cy + (BODY_LENGTH / 2));
					break;
		}
	}
	
	private void addHead(double cx, double cy) {
		double x = cx - HEAD_RADIUS;
		double y = cy - (HEAD_RADIUS * 2);
		GOval head = new GOval(x, y, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		add(head);
	}
	
	private void addBody(double cx, double cy) {
		double x = cx;
		double y = cy;
		GLine body = new GLine(x, y, x, y + BODY_LENGTH);
		add(body);
	}
	
	private void addLeftArm(double cx, double cy) {
		addArm(cx, cy, -1);
	}
	
	private void addRightArm(double cx, double cy) {
		addArm(cx, cy, 1);
	}
	
	private void addArm(double cx, double cy, double LR) {
		double x = cx;
		double y = cy + ARM_OFFSET_FROM_HEAD;
		GLine upperArm = new GLine(x, y, x + (LR * UPPER_ARM_LENGTH), y);
		add(upperArm);
		GLine lowerArm = new GLine(x + (LR * UPPER_ARM_LENGTH), y, x + (LR * UPPER_ARM_LENGTH), y + LOWER_ARM_LENGTH);
		add(lowerArm);
	}

	private void addLeftLeg(double cx, double cy) {
		addLeg(cx, cy, -1);
	}
	
	private void addRightLeg(double cx, double cy) {
		addLeg(cx, cy, 1);
	}
	
	private void addLeg(double cx, double cy, double LR) {
		double x = cx;
		double y = cy;
		GLine hip = new GLine(x, y, x + (LR * HIP_WIDTH), y);
		add(hip);
		GLine leg = new GLine(x + (LR * HIP_WIDTH), y, x + (LR * HIP_WIDTH), y + LEG_LENGTH);
		add(leg);
	}
	
	private void addLeftFoot(double cx, double cy) {
		addFoot(cx, cy, -1);
	}
	
	private void addRightFoot(double cx, double cy) {
		addFoot(cx, cy, 1);
	}
	
	private void addFoot(double cx, double cy, double LR) {
		double x = cx + (LR * HIP_WIDTH);
		double y = cy + LEG_LENGTH;
		GLine foot = new GLine(x, y, x + (LR * FOOT_LENGTH), y);
		add(foot);
	}
	
	private void addLetterToList(char letter) {
		incorrectLetters += letter;
		incorrectList.setLabel(incorrectLetters);
		double w = displayWord.getWidth();
		incorrectList.setLocation(cx- (w/2), cy * 2.3);
		add(incorrectList);
	}
	
	private GLabel displayWord = new GLabel("");
	private String incorrectLetters = "";
	private GLabel incorrectList = new GLabel("");

}
