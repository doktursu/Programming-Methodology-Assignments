/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acm.graphics.*;

import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	
	/** Diameter of the ball in pixels */
	private static final int BALL_DIAM = 2 * BALL_RADIUS;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
	/** Offset of the first column of bricks from the side */
	private static final int BRICK_X_OFFSET = (WIDTH - ((NBRICKS_PER_ROW * BRICK_WIDTH) + ((NBRICKS_PER_ROW - 1) * BRICK_SEP))) / 2;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	/** Animation delay or pause */
	private static final int DELAY = 5;

	public void run() {
		setup();
		addMouseListeners();
		turns = 0;
		while (turns < NTURNS) {
			drawBall();
			initVelocity();
			displayGameStatus();
			while (true) {
				moveBall();
				if (collidedWithBottom()) break;
				checkForCollision();
				pause(DELAY);
			}
		}
		println("Game Over");
	}
	
	private void setup() {
		drawBoard();
		drawBricks();
		drawPaddle();
	}
	
	private void drawBoard() {
		GRect board = new GRect(0, 0, WIDTH, HEIGHT);
		add(board);
	}
	
	private void drawBricks() {
		
		for (int j = 0; j < NBRICK_ROWS; j++) {
			for (int i = 0; i < NBRICKS_PER_ROW; i++) {
				GRect brick = new GRect(BRICK_X_OFFSET + (i* (BRICK_WIDTH + BRICK_SEP)), BRICK_Y_OFFSET + (j * (BRICK_HEIGHT + BRICK_SEP)), BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				
				Color color;
				switch (j) {
					case 0: case 1:
						color = Color.RED;
						break;
					case 2: case 3:
						color = Color.ORANGE;
						break;
					case 4: case 5:
						color = Color.YELLOW;
						break;
					case 6: case 7:
						color = Color.GREEN;
						break;
					case 8: case 9:
						color = Color.CYAN;
						break;
					default:
						color = Color.MAGENTA;
						break;
				}
				
				brick.setColor(color);
				add(brick);
			}
		}
		
	}
	
	private void drawPaddle() {
		
		paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		
	}
	
	private void drawBall() {
		
		ball = new GOval((WIDTH - BALL_DIAM) / 2, (HEIGHT - BALL_DIAM) / 2, BALL_DIAM, BALL_DIAM);
		ball.setFilled(true);
		add(ball);
		
	}
	
	private void initVelocity() {
		
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		
		vy = 3.0;
	}
	
	private void displayGameStatus() {
		
		int remainingTurns = NTURNS - turns;
		gameLabel = new GLabel("You have " + remainingTurns + " / " + NTURNS + " turns remaining. Click to continue");
		gameLabel.setFont("Helvetica-bold-16");
		gameLabel.setColor(Color.MAGENTA);
		double x = (WIDTH - gameLabel.getWidth()) / 2;
		double y = 0.4 * HEIGHT;
		add(gameLabel, x, y);
		waitForClick();
		remove(gameLabel);
		
	}
	
	private void moveBall() {
		
		ball.move(vx, vy);
		
		/** Determine if collision with boundaries, update velocities */
		
		/** Collision with top */
		if (ball.getY() <= 0) {
			vy = -vy;
		}
		
		/** Collision with left */
		if (ball.getX() <= 0) {
			vx = -vx;
		}
		
		/** Collision with right */
		if (ball.getX() + BALL_DIAM >= WIDTH) {
			vx = -vx;
		}
	}
	
	private boolean collidedWithBottom() {
		
		/** Collision with bottom */
		if (ball.getY() + BALL_DIAM >= HEIGHT) {
			incrementTurns();
			remove(ball);
			return true;
		}
		
		return false;
		
	}
	
	private void incrementTurns() {
		turns++;
	}
	
	private void checkForCollision() {
			
		GObject collider = getCollidingObject(ball.getX(), ball.getY());
		if (collider != null) {
			if (collider == paddle) {
				vy = -vy;
			} else {
				vy = -vy;
				remove(collider);
			}
		}
	}
	
	private GObject getCollidingObject(double x, double y) {
		
		GObject gobj = getElementAt(x, y);
		if (getElementAt(x, y) != null) {
			gobj = getElementAt(x, y);
		} else if (getElementAt(x + BALL_DIAM, y) != null) {
			gobj = getElementAt(x + BALL_DIAM, y);
		} else if (getElementAt(x + BALL_DIAM, y + BALL_DIAM) != null) {
			gobj = getElementAt(x + BALL_DIAM, y + BALL_DIAM);
		} else if (getElementAt(x, y + BALL_DIAM) != null) {
			gobj = getElementAt(x, y + BALL_DIAM);
		}
		return gobj;
	}
	
	/** Moves paddle with mouse, keeping within the game board */
	public void mouseMoved(MouseEvent e) {
		
		paddleX = e.getX() - (0.50 * PADDLE_WIDTH);
		
		if (paddleX <= 0) {
			paddleX = 0;
		}
		
		if (paddleX + PADDLE_WIDTH >= WIDTH) {
			paddleX = WIDTH - PADDLE_WIDTH;
		}
		
		paddle.setLocation(paddleX, HEIGHT - PADDLE_Y_OFFSET);
		
	}
	
	private int turns;
	private GLabel gameLabel;
	private GRect paddle;
	private double paddleX;
	private GOval ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
