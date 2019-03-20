package cosc322;

import java.util.ArrayList;
import java.util.Timer;

import org.jdom.Parent;

public class PlayerMCTS {
//lots to do here lol
//test
	Node root;
	PlayerMCTS(){
		root = new Node(new Board(),"W",null);
		
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

	public Node expand(Node leaf) {
		leaf.createChildren();
		boolean gameEnd = false;
		ArrayList<Node> children = leaf.getChildren();
		if(children.size()==0) {
			// figure out how to end game properly
			gameEnd = true;
			return leaf;
		}
		else {
			
			return children.get((int)Math.random()*children.size());
			/*
			int size = children.size();
			double maxnode=0;
			for(int i=0;i<children.size();i++) {
				if(maxnode<children.get(i).getScore()) {
					maxnode=children.get(i).getScore();
				}
			}
			*/		
		}
		
	}
	
	public void simulate() {
		
	}
	
	public void backprop() {
		
	}
	
}

