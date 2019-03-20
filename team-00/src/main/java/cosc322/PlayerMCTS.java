package cosc322;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
		//leaf.createChildren();
		//boolean for now to end game: fix
		boolean gameEnd = false;
		//store children of node in arraylist
		ArrayList<Node> children = leaf.getChildren();
		//if no children, end game
		if(children.size()==0) {
			// figure out how to end game properly
			gameEnd = true;
			//return itself if empty
			return leaf;
		}
		// otherwise explore random child
		else {
			return children.get((int) (Math.random()*children.size()));		
		}
		
	}
	
	public Node simulate(Node sim) {
		//store start of current node color
		String color2=sim.color;
		
		//while it's child is not the same as itself, since in previous method we return itself if no children
		while(sim!=expand(sim)) {
			//node = child and iterate through tree
			sim=expand(sim);
			
		}
		
		//current color doesn't match top of color, count as winner
		if(sim.color!=color2) {
			sim.wins=sim.wins+1;
		}
		
		//update plays
		sim.plays=sim.plays+1;
		
		//return bottom node
		return sim;
	}
	
	public  Node backprop(Node no) {
		//check if has parent, then child=parent
		while(no.parent!=null) {
			no=no.parent;
		}
		// return top node
		return no;
	}
	
	public void updatetofile(Node name) {
		// update to file about every 20-30 times
		// if statement might be more useful to be better in a seperate section, so this method isn't being called everytime
		if(name.plays%25==0) {
			// make objectoutputstream to write to file using try/catch
			// may be missing some catch statements
			try {
				OutputStream file = new FileOutputStream("AmazonsMem.Ser");
				OutputStream buffer = new BufferedOutputStream (file);
				ObjectOutput output = new ObjectOutputStream(buffer);
				try {
					output.writeObject(name);
				}
				finally {
					output.close();
				}
			}
			catch(IOException e){
				System.out.println("IOException error");
			}
		}
	}
	

}

