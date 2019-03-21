package cosc322;

import java.io.Serializable;
import java.util.ArrayList;

//The tree needs nodes
public class Node implements Serializable{
	int wins = 0;
	int plays = 0;
	double econst = Math.sqrt(2);
	Node parent;
	String[][] state;
	String color;
	ArrayList<Node> children = new ArrayList<Node>();
	boolean isLeaf = true;
	String move;
	
	Node(Board b, String color, Node parent,String move){
		this.state = b.getState();
		this.color = color;
		this.parent = parent;
		this.move = move;
	}
	//If this node is a leaf this will generate all the children
	public ArrayList<Node> createChildren(){
		ArrayList<Node> children = this.children;
		Board b = new Board(state);
		String nc;
		if(color.equals("W"))
			nc = "B";
		else
			nc = "W";
		ArrayList<String> childrenMoves = b.getValidMoves(color);
		ArrayList<Board> boards = new ArrayList<Board>();
		for(int i =0;i<childrenMoves.size();i++) {
			Board cb = new Board(b.getState());
			cb.move(childrenMoves.get(i));
			boards.add(cb);
		}
		for(int i =0; i<boards.size();i++) {
			children.add(new Node(boards.get(i),nc,this,childrenMoves.get(i)));
		}
		
		return children;
	}
	public Node randomChild() {
		Board b = new Board(state);
		String nc;
		if(color.equals("W"))
			nc = "B";
		else
			nc = "W";
		ArrayList<String> childrenMoves = b.getValidMoves(nc);
		if(childrenMoves.isEmpty()) {
			return null;
		}
		String move  = childrenMoves.get((int)(Math.random()*childrenMoves.size()));
		b.move(move);
		return new Node(b,nc,this,move);
	}
	public void addChild(Node child) {
		this.children.add(child);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	public Node getParent() {
		return parent;
	}
	public double getScore() {
		if(wins==0&&plays==0) {
			return 0;
		}
		else {
			return (wins/plays)+econst*Math.sqrt(Math.log(plays)/plays);
		}
	}

}
