package gui;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
@SuppressWarnings("all")
public class GameHandler {
	
	public GridPane gameboard;
	public int[][] solutionArray= new int[6][7];
				
	public GameHandler(GridPane gameboard){
		this.gameboard = gameboard;
	}
	
	public boolean placeChip(int col, String filename){
		ImageView tempChip = new ImageView("file:./images/" + filename);
		tempChip.setId(filename);
		Node deadCell = lastEmptyCell(col);
		if(deadCell == null){
			return false;
		}
		int row = gameboard.getRowIndex(deadCell);	
		gameboard.getChildren().remove(deadCell);
		gameboard.add(tempChip, col, row);
		if(filename.equals("player1.png")){
			solutionArray[row][col] = 1;
		}else if(filename.equals("player2.png")){
			solutionArray[row][col] = 2;
		}
		return true;
	}
	
	//checks for four in a row (not diagonally)
	public boolean checkSolution(int player){
		int count = 0;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				if(solutionArray[i][j] == player){
					count = count + 1;
				}else{
					count = 0;
				}
				if(count == 4){	
					return true;
				}
			}
			count = 0;
		}
		count = 0;
		//column check
		for(int i = 0; i < 7;i++){
			for(int j = 0; j < 6; j++){
				if(solutionArray[j][i] == player){
					count = count + 1;
				}else{
					count = 0;
				}
				if(count == 4){
					return true;
				}
			}
		}
		// ascendingDiagonalCheck 
	    for (int i=3; i<7; i++){
	        for (int j=0; j<3; j++){
	            if (this.solutionArray[i][j] == player && this.solutionArray[i-1][j+1] == player && this.solutionArray[i-2][j+2] == player && this.solutionArray[i-3][j+3] == player){
	                return true;
	            }
	        }
	    }
	    // descendingDiagonalCheck
	    for (int i=3; i<7; i++){
	        for (int j=3; j<6; j++){
	            if (this.solutionArray[i][j] == player && this.solutionArray[i-1][j-1] == player && this.solutionArray[i-2][j-2] == player && this.solutionArray[i-3][j-3] == player){
	                return true;
	            }
	        }
	    }
		return false;
	}
	
	private Node lastEmptyCell(int col) {
		Node node = null;
		for(Node cell : gameboard.getChildren()){
			if(gameboard.getColumnIndex(cell) == col){
				if(cell.getId()!="empty"){
					return node;
				}else{
					node = cell;
				}
			}
		}
		return node;
	}
}
