package cosc322;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.jdom.Parent;

public class PlayerMCTS{
//lots to do here lol



	Node root;
	Board b;
	PlayerMCTS(){
		//black always starts first
		try {
			InputStream file = new FileInputStream("AmazonsMem.gz");
			InputStream buffer = new GZIPInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			root = (Node)input.readObject();
			System.out.println("Root node/tree loaded");
			input.close();
		}
		catch(Exception e){
			System.out.println("No file found run as normal");
			//e.printStackTrace();
			root = new Node(new Board().getState(),"B",null,null);
		}
		//root = new Node(new Board().getState(),"B",null,null);
	}
	
	public Node select(Node current) {
		
		ArrayList<Node> children;
		Node leaf = null;
		Node max = null;
		double maxs = Integer.MIN_VALUE;
		double tooStrong = 1.8;
		if(current.isLeaf) {
			
			return current;
		}else {
			
			children = current.getChildren();
//			max = children.get((int) (Math.random()*children.size()));
			if(children!=null&&children.size()!=0) {
			for(Node child : children) {
				if(child.getScore()>tooStrong) {
					if(child.getScore()>maxs) {
					max = child;
					maxs = child.getScore();
					}
				}
			}
			}
			if(max == null) {
				Node child = current.randomChild(b);
				return child;
			}else {
				leaf = select(max);
			}
		}
			
		
		return leaf;
	}

	public Node expand(Node leaf) {
		//System.out.println(leaf.color);
		Node rand = null;
		if(leaf.isLeaf) {
			rand = leaf.randomChild(b);
			
		}
		
	
		//if children is null then it is a leaf node
		if(rand==null) {
				System.out.println("bottom of the tree");
				//This node is the bottom of the tree
				return leaf;
			
		}
		// otherwise explore random child
		else {
			//System.out.println("random child");
			return rand;		
		}
		
	}
	
	public Node simulate(Node sim,int count) {
		count = count +1;
		if(sim.parent!= null) 
			sim.parent.isLeaf = false;
		Node randChild = sim.randomChild(b);
		if(randChild == null) {
			System.out.println("no children");
			return sim;
		}
			//System.out.println("sim rand");
			if(count%100==0)
				System.out.println(count);
				//System.out.println(b.toString());
				//System.out.println(sim.move);
			return simulate(randChild,count);
		
			
		/* moved to backprop
		//current color doesn't match top of color, count as winner
		if(sim.color!=color2) {
			sim.wins=sim.wins+1;
		}
		//update plays
		sim.plays=sim.plays+1;
		*/
		
		//return bottom node
		//return sim;
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
		
	}
	
	public void updatetofile(Node name) {
		// update to file about every 20-30 times
		// if statement might be more useful to be better in a seperate section, so this method isn't being called everytime
		//if(name.plays%25==0) {
			// make objectoutputstream to write to file using try/catch
			// may be missing some catch statements
			try {
				FileOutputStream file = new FileOutputStream("AmazonsMem.gz");
				OutputStream buffer = new GZIPOutputStream (file);
				ObjectOutput output = new ObjectOutputStream(buffer);
				
				output.writeObject(name);
				output.close();
				System.out.println("saved to file");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		//}
	}
	

}

