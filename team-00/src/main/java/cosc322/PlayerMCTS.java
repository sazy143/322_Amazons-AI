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



	Board b;
	PlayerMCTS(){
		//black always starts first
		
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
				
				Node child = current.randomChild(b,b.getValidMoves(current.color));
				if(child==null) {
					return current;
				}
				leaf = select(child);
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
			rand = leaf.randomChild(b,b.getValidMoves(leaf.color));
			
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
	
	public Node simulate(Node sim,Node top) {
		
		if(sim.parent!= null) 
			sim.parent.isLeaf = false;
		ArrayList<Node> children = sim.getChildren();
		double maxs = 0;
		Node max = null;
		double score = 1.2;
		if(top.plays>15000)
			score = 1.4;
		if(top.plays>40000)
			score = 2;
		if(top.plays>80000)
			score = 4;
		if(children!=null&&children.size()!=0) {
		for(Node child : children) {
			if(sim.color.equals(top.color)&&child.getScore()>score) {
				if(child.getScore()>maxs) {
				max = child;
				maxs = child.getScore();
				}

			}

		}
		}
		if(max!=null) {
			return simulate(max,top);
		}
		Node randChild = sim.randomChild(b,b.getValidMoves(sim.color));
		if(randChild == null) {
			//System.out.println("no children");
			return sim;
		}
		
	
			return simulate(randChild,top);
		
			
		
	}
	
	public void backprop(Node no) {
		
		// boolean to keep tracking of winning/losing nodes
		boolean winlose = true;
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
public void backprop2(Node no,Node top) {
		
		// boolean to keep tracking of winning/losing nodes
		boolean winlose = true;
		//check if has parent, then child=parent
		while(no!=null) {
			if(no==top.parent&&top.parent!=null)
				break;
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
}

	

}

