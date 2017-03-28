import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Main {
	
	public static boolean mineFound = false; //Used to know when to terminate gamme Loop
	
	public static void main(String[] args) throws InterruptedException {
		JFrame myFrame = new JFrame("Mine Sweeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(500, 200);
		myFrame.setSize(330, 330);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);
		
		myFrame.setVisible(true);
		myFrame.setResizable(false);
		
		while(!mineFound && !CellAdapter.allUncover()){
			//Loop until a mine is Found		
			//Or player Wins
			Thread.sleep(1);
		}
		
		//Verifies which case terminated the program
		if(mineFound){
			JOptionPane.showMessageDialog(null , "Mine Exploded!");	
		} else{
			JOptionPane.showMessageDialog(null , "Congratulations!\nAll cells wihtout mines uncovered.");	
		}
		
		System.exit(0);
	}
}