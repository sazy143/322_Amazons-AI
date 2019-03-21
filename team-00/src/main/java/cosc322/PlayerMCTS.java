package cosc322;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import org.jdom.Parent;

public class PlayerMCTS{
//lots to do here lol



	Node root;
	PlayerMCTS(){
		//black always starts first
		root = new Node(new Board(),"B",null,null);
	}
	
	public Node select(Node current) {
		ArrayList<Node> children;
		Node leaf = null;
		Node max = null;
		double maxs = Integer.MIN_VALUE;
		if(current.isLeaf) {
			System.out.println("test");
			return current;
		}else {
			System.out.println("test2");
			children = current.getChildren();
//			max = children.get((int) (Math.random()*children.size()));
			for(Node child : children) {
				if(child.getScore()>maxs) {
					max = child;
					maxs = child.getScore();
				}
			}
			select(max);
		}
			
		
		return leaf;
	}

	public Node expand(Node leaf) {
		leaf.createChildren();
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
		
		sim.parent.isLeaf = false;
		sim.createChildren();
		ArrayList<Node> children = sim.getChildren();
		if(children.size()==0) {
			return sim;
		}else {
			Node rand = children.get((int) (Math.random()*children.size()));
			simulate(rand);
		}
			
		/* moved to backprop
		//current color doesn't match top of color, count as winner
		if(sim.color!=color2) {
			sim.wins=sim.wins+1;
		}
		//update plays
		sim.plays=sim.plays+1;
		*/
		
		//return bottom node
		return sim;
	}
	
	public void backprop(Node no) {
		
		// boolean to keep tracking of winning/losing nodes
		boolean winlose = false;
		//check if has parent, then child=parent
		while(no!=null) {
			
			//update plays
			no.plays=no.plays+1;
			
			//bottom node is a loser
			if(winlose==false) {
			winlose=true;
			
			}
			// tops above bottom and alternating are winners
			else {
			winlose=false;	
			//update wins
			no.wins=no.wins+1;
			}
			
			//move up each node while updating
			no=no.parent;
		}
		System.out.println("backprop end");
		
	}
	
	public void updatetofile(Node name) {
		// update to file about every 20-30 times
		// if statement might be more useful to be better in a seperate section, so this method isn't being called everytime
		//if(name.plays%25==0) {
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
			catch(Exception e){
				e.printStackTrace();
			}
		//}
	}
	

}

