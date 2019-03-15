package cosc322;

import java.util.ArrayList;

//The tree needs nodes
public class Node {
	int wins = 0;
	int plays = 0;
	double econst = Math.sqrt(2);
	Node parent;
	String[][] state;
	String color;
	ArrayList<Node> children;
	boolean isLeaf = true;
	
	Node(Board b, String color, Node parent){
		this.state = b.getState();
		this.color = color;
		
	}
	//If this node is a leaf this will generate all the children
	public void createChildren(){
		ArrayList<Node> children = new ArrayList<>();
		Board b = new Board(state);
		String nc;
		if(color.equals("W"))
			nc = "B";
		else
			nc = "W";
		ArrayList<String> childrenMoves = b.getValidMoves(nc);
		ArrayList<Board> boards = new ArrayList<Board>();
		for(int i =0;i<childrenMoves.size();i++) {
			Board cb = b;
			cb.move(childrenMoves.get(i));
			boards.add(cb);
		}
		for(int i =0; i<boards.size();i++) {
			children.add(new Node(boards.get(i),nc,this));
		}
		
		this.children = children;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	public Node getParent() {
		return parent;
	}
	public double getScore() {
		return (wins/plays)+econst*Math.sqrt(Math.log(plays)/plays);
	}

}
