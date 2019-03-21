package cosc322;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Competitive {

	String[][] state;
	String color;
	Node tree;
	Node current;
	
	Competitive(String color, Board b){
		this.color = color;
		this.state = b.getState();
		
	}
	
	public void setTree(String loadfile){
		try{
			InputStream file = new FileInputStream(loadfile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			tree = (Node)input.readObject();
			current = tree;
			System.out.println("Root node/tree loaded");
			input.close();
		}
		catch(Exception e){
			System.out.println("Your fucked good luck with all random moves");
			e.printStackTrace();
		}
	}
	
	public String chooseMove(){
		String move = null;
		boolean rand = false;
		Node max = null;
		double maxs = Integer.MIN_VALUE;
		ArrayList<Node> children = current.getChildren();
		if(children.size()==0) {
			current.createChildren();
			children = current.getChildren();
			rand = true;
		}
		if(children.size()==0) {
			//game is over
		}
		if(rand) {
			max = children.get((int)(Math.random()*children.size()));
		}else {
			for(Node child : children) {
				if(child.getScore()>maxs) {
					max = child;
				}
			}
		}
		move = max.move;
		
		return move;
	}
	
	
}
