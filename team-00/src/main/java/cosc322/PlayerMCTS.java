package cosc322;

import java.util.ArrayList;
import java.util.Timer;

import org.jdom.Parent;

public class PlayerMCTS {
//lots to do here lol



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
		}
		
	}
	
	public Node simulate(Node sim) {

		//check if this sim has wins return sims

		//check if this sim has wins return sim

		if(sim.wins>0) {
			return sim;
		}
		//look again for a winning move
		else {
			return simulate(expand(sim.parent));
		}

	}
	
	public double backprop(Node no) {
		//update amount of plays of game
		no.plays=no.plays+1;
		//return score from leaf node;
		return no.getScore();
	}
	
	//if not root, parents update first
	public void updaterecursive() {
		
	}
	
	public void recursive() {
		
	}
	
	public void getvalue() {
		
	}

}

