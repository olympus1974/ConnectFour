package gui;

import java.util.Optional;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Gameboard extends Application{
	public GridPane gameboard;
	public Scene scene;
	public GameHandler gameHandler;
	
	public static void main(String[] args){
		launch(args);
	}
	
	public boolean turn = true; //true = player1
						 		//false = player2	
	
	public Stage primaryStage;
	public Label playerTurn;
	public Alert endingAlert;
	public ButtonType quitButton;
	public ButtonType replayButton;
	
	@SuppressWarnings("all")
	@Override
	public void start(Stage stage) throws Exception{
		buildWindows();
		gameHandler = new GameHandler(gameboard);
		buildBoard();
		createEventHandlers();
		
		primaryStage = stage;
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Connect Four");
		primaryStage.setResizable(false);
		stage.show();
	}
	
	@SuppressWarnings("all")
	private void buildBoard() {
		for(int i  = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				ImageView temp = new ImageView("file:./images/emptyCell.png");
				temp.setId("empty");
				gameboard.add(temp,j,i);
			}
		}
		playerTurn.setText("Player1 is red and Player2 is yellow.");
		gameboard.add(playerTurn,0,7,7,1);
	}
	
	

	public void buildWindows() {
		gameboard = new GridPane();
		gameboard.setStyle("-fx-background-color: #a6a6a6;");
		scene = new Scene(gameboard, 525, 500);
		endingAlert = new Alert(AlertType.NONE);
		quitButton = new ButtonType("Quit");
		replayButton = new ButtonType("Replay");
		endingAlert.getButtonTypes().setAll(quitButton, replayButton);
		endingAlert.setTitle("Winner Alert!");
		
		playerTurn = new Label();
		playerTurn.setFont(new Font(30));
		playerTurn.setTextFill(Color.CADETBLUE);
	}


	@SuppressWarnings("all")
	public void createEventHandlers() {
		for (Node cell : gameboard.getChildren()){
			cell.setOnMouseClicked(e -> {
				System.out.print("X: " + e.getX() + "   ");
				System.out.print("Y: " + e.getY() + "   ");
				int rowClicked = gameboard.getRowIndex(cell);
				int colClicked = gameboard.getColumnIndex(cell);
				System.out.println("You clicked " + rowClicked + ", " + (colClicked));
					if(turn){ //player1 turn
						gameHandler.placeChip(colClicked,"player1.png");
						turn = false;
						if(gameHandler.checkSolution(1)){
							playerWon("Player 1");
						}
						
					}else{ //player2 turn
						gameHandler.placeChip(colClicked,"player2.png");
						turn = true;
						if(gameHandler.checkSolution(2)){
							playerWon("Player 2");
						}
						
					}
				});
		
			}
		}
	
	@SuppressWarnings("all")
	public void playerWon(String player){
		endingAlert.setTitle("Player has won!");
		endingAlert.setContentText(player + " has won!");
		
		Optional<ButtonType> result = endingAlert.showAndWait();
		if(result.get()==quitButton){
			endingAlert.close();
			primaryStage.close();
		}
		if(result.get()==replayButton){
			endingAlert.close();
			resetGame();
		}
	}
	public void resetGame(){
		turn = true;
		
		try{
			this.start(primaryStage);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
