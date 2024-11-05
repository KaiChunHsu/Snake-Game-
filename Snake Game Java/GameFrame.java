import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame(){
		/* GamePanel panel = new GamePanel();
		this.add(panel);  below has the similar value*/
		
		// Add a new GamePanel to the frame
		this.add(new GamePanel());
		this.setTitle("Snake"); // Set the title of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
		//this.setResizable(false); //Optional
		this.pack(); // Adjust the frame to fit the preferred size of the panel
		this.setVisible(true); // Make the frame visible
		this.setLocationRelativeTo(null); // Center the frame on the screen
		
		
	}
}
