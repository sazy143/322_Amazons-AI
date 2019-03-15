package cosc322;

import java.util.ArrayList;

public class PlayerMCTS {
//lots to do here lol
	String color;
	Node root;
	PlayerMCTS(String color){
		this.color = color;
		root = new Node(new Board(),color,null);
		
	}
	
	public Node select(Node current) {
		ArrayList<Node> children;
		Node leaf = null;
		Node max = null;
		double maxs = Integer.MIN_VALUE;
		if(current.isLeaf) {
			return current;
		}
		while(!current.isLeaf||current.getChildren().size()!=0) {
			children = current.getChildren();
			for(Node child : children) {
				if(child.getScore()>maxs) {
					max = child;
					maxs = child.getScore();
				}
			}
			leaf = select(max);
		}
		return leaf;
	}
	
	public void expand(Node leaf) {
		leaf.createChildren();
		boolean gameEnd = false;
		ArrayList<Node> children = leaf.getChildren();
		if(children.size()==0) {
			gameEnd = true;
		}
		
		
	}
	
	public void simulate() {
		
	}
	
	public void backprop() {
		
	}
	
}

