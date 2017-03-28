import java.awt.Color;

import javax.swing.JPanel;

public class CellAdapter extends JPanel {

	private static final long serialVersionUID = -6099641386621438159L;

	public static int mineCnter = 0;


	//Checks the adjacent cells of a target cell to verify which of them are mines
	public static void MineCounter(int mouseDownX, int mouseDownY){
		mineCnter = 0;

		//Loop to iterate over the Mine Array
		for (int i = mouseDownX-1; i <= mouseDownX+1; i++){
			for (int j = mouseDownY-1; j <= mouseDownY+1;j++){
				if (i < 0 || i > MyPanel.TOTAL_COLUMNS-1 || j < 0 || j > MyPanel.TOTAL_ROWS-1 ){
					//Do nothing out of colorArray bounds
				}else if (MyPanel.Mines[i][j] == 1){ 
					//Mine found
					mineCnter++;
				}
			}
		}

		if (mineCnter == 0){
			for (int x = mouseDownX-1; x <= mouseDownX+1; x++){
				for (int j = mouseDownY-1; j <= mouseDownY+1;j++){
					if (x < 0 || x > MyPanel.TOTAL_COLUMNS-1 || j < 0 || j > MyPanel.TOTAL_ROWS-1 ){
						//Do nothing: Out of colorArray bounds
					}else if (MyPanel.Mines[x][j] == 1){
						//Return: Cell contains a mine
						return;
					}else if (MyPanel.Mines[x][j] == 0 && MyPanel.colorArray[x][j] != Color.LIGHT_GRAY){
						//Cell is not a mine and is not Uncovered
						//Call this function itself: Recursion
						MyPanel.colorArray[x][j] = Color.LIGHT_GRAY;
						MineCounter(x,j);
					}
				}
			}
		}
	}
	
	//Called when a Mine is found
	//Paints the remaining mines of Black
	//Also sets the flag of a mine found to true to terminate game Loop at Main
	public static void MineFound() {
		for (int i = 0; i < MyPanel.TOTAL_COLUMNS; i++){
			for (int j = 0; j < MyPanel.TOTAL_ROWS; j++){
				if (MyPanel.Mines[i][j] == 1){
					MyPanel.colorArray[i][j] = Color.BLACK;
				}
			}
		}
		Main.mineFound = true;
	}

	//Checks if all cells without mines are uncovered
	//If all are uncovered terminates game Loop at Main
	public static boolean allUncover(){
		//Assume that all cells without mines are uncovered
		boolean allCellsUncovered = true;
		for (int i = 0; i < MyPanel.TOTAL_COLUMNS; i++){
			for (int j = 0; j < MyPanel.TOTAL_ROWS;j++){
				if (MyPanel.colorArray[i][j] == Color.WHITE && MyPanel.Mines[i][j] == 0){
					//A cell without mines is still covered
					//Set the boolean as false
					allCellsUncovered = false;
				}
			}
		}
		return allCellsUncovered;		
	}
}