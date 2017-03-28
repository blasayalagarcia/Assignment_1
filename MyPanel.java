import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;	

	public static final int TOTAL_COLUMNS = 9;
	public static final int TOTAL_ROWS = 9;  
	public static int Mines[][] = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public static Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;

	public boolean mineFound = false;
	public boolean firstPlay; //Used to know when is the first play

	public MyPanel() {   //This is the constructor...this code runs first to initialize

		firstPlay = true; //Initialize the fistPlay

		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}

		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}

		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}

		//Determines which cells will have the mines
		Random rn = new Random();
		for(int i = 0; i < TOTAL_COLUMNS; i++){
			for (int j = 0; j < TOTAL_COLUMNS; j++){
				if (rn.nextBoolean()){
					int TF = rn.nextInt(1 + 1);
					Mines[i][j] = TF;
				}

			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 9x9 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
			}
		}
		//Draw numbers from the beginning of the program
		int l;
		int m;
		int mineCounter;
		for (int x = 0; x < TOTAL_COLUMNS; x++){
			for (int y = 0; y < TOTAL_ROWS; y++){
				mineCounter = 0;
				if (Mines[x][y] != 1){ //Cell is not a mine
					for (int i = x-1; i <= x+1; i++){
						for (int j = y-1; j <= y+1; j++){
							if ( i < 0 || i > TOTAL_COLUMNS-1 || j < 0 || j >TOTAL_ROWS-1){
								//Do nothing: out of colorArray bounds
							} else if (Mines[i][j] == 1){
								mineCounter++;
							}
						}
					}
					
					//Determinate the coordinates of the numbers
					l = x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12;
					m =  y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 20;
					g.setColor(Color.WHITE);
					//Switch Case to know which number print depending
					//on the number of mines are adjacent to the target cell
					switch (mineCounter) {
					case 1:
						g.drawString("1", l, m);
						break;
					case 2:
						g.drawString("2", l, m);
						break;
					case 3:
						g.drawString("3", l, m);
						break;
					case 4:
						g.drawString("4",l, m);
						break;
					case 5:
						g.drawString("5",l, m);
						break;
					case 6:
						g.drawString("6", l, m);
						break;
					case 7:
						g.drawString("7", l, m);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}

	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
}