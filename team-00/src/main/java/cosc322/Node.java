package cosc322;

import java.io.Serializable;
import java.util.ArrayList;

//The tree needs nodes
public class Node implements Serializable{
	double wins = 0;
	double plays = 0;
	Node parent;
	String[][] state;
	String color;
	ArrayList<Node> children = new ArrayList<Node>();
	boolean isLeaf = true;
	String move;
	
	Node(String[][] state, String color, Node parent,String move){
		this.state = state;
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
			children.add(new Node(boards.get(i).getState(),nc,this,childrenMoves.get(i)));
		}
		
		return children;
	}
	public Node randomChild(Board b , ArrayList<String> moves) {
		String nc;
		if(color.equals("W"))
			nc = "B";
		else
			nc = "W";
		ArrayList<String> childrenMoves = moves;
		
		if(childrenMoves.isEmpty()) {
			return null;
		}
		
		String move  = childrenMoves.get((int)(Math.random()*childrenMoves.size()));
		if(children!=null&&children.size()>0) {
		for(int i =0;i<children.size();i++) {
			if(children.get(i).move.equals(move)) {
				b.move(children.get(i).move);
				//System.out.println("same child");
				return children.get(i);
			}
		}
		}
		b.move(move);
		Node child = new Node(b.getState(),nc,this,move);
		children.add(child);
		return child;
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
//			int pp = 0;
//			if(parent!=null)
//				pp = parent.plays;
			if(wins==0) {
				return 0;
			}
			double score = ((double)wins/plays)*Math.log(wins)*1.2;
			
			return score;
		}
	}

}
