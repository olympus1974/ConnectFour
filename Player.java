package player;

import javafx.scene.shape.Circle;

public class Player {

	//chips
	public Circle[] chips;
	//name
	public String name;
	//score
	public int score;
	
	public Player(String name){
		this.name = name;
		chips = new Circle[21];
		score = 0;
	}
	
}
