import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	// screen dimensions and game settings
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; //size of each unit in the game
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE; //total number of units
	static final int DELAY = 100; //delay for the game timer/change the snake speed
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6; //initial length of the snake
	int appleEaten;
	int applex; // X coordinate of the apple 
	int appley; // Y coordinate of the apple
	char direction = 'R'; // Initial direction of the snake (Right)
	boolean running = false;
	Timer timer; // Timer to control game speed
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Set the panel size
		this.setBackground(Color.black); // Set the background color of the panel
		this.setFocusable(true); // Make the panel focusable to receive key events
		this.addKeyListener(new MyKeyAdapter()); // Add key listener for controls
		startGame();
		
		
	}
	public void startGame() {
		newApple(); //Generate a new apple
		running = true; // Set the game state to running
		timer = new Timer(DELAY, this); // Create a timer that calls this class on each tick
		timer.start(); // Start the timer
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g); // Call the draw method to render the game
	}
	
	public void draw(Graphics g) {
		// Drawing the grid
		g.setColor(Color.white); // Set the grid color to white
		
		if(running) {
			for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; ++i) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); //Vertical lines
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // Horizontal lines
			}
			g.setColor(Color.red); // Set color for apple 
			g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE); //Draw the apple
			
			for(int i = 0; i < bodyParts; ++i) {
				if(i == 0) {
					g.setColor(Color.green); // Color for the head of the snake
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); // Draw the snake head
				}
				
				else {
					
					g.setColor(new Color(45,180,0));
					// optional to draw the random color for snake body
					//g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			//Score optional to put the score when it is running
			/*g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + appleEaten)) / 2, g.getFont().getSize());
			*/
		}
		else {
			gameOver(g); // Call game over method if not running
		}
		
	}
	
	public void newApple() {
		applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; // Random x position
		appley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; // Random y position
	}
	
	
	public void move() {
		for(int i = bodyParts; i > 0; --i) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		
		// Update the head position based on the direction
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
			
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
			
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
			
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if((x[0] == applex) && (y[0] == appley)){ // Check if head coordinates match apple coordinates
			bodyParts++;
			appleEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		//check if head collides with bodys
		for(int i = bodyParts; i > 0; i --) {
			if((x[0]) == x[i] && (y[0] == y[i])) {
				running = false;
			}
		}
		//checks if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		
		//checks if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		//checks if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		
		//checks if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		//Score 
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + appleEaten)) / 2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
			
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
