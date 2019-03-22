package cosc322;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class Competitive {

	Board b;
	String color;
	Node tree;
	Node current;
	String opColor;
	
	Competitive(String color, Board b){
		this.color = color;
		this.b = b;
		if(color.equals("W")) {
			opColor = "B";
		}
		else {
			opColor = "W";
		}
		
	}
	
	public void setTree(){
		try{
			InputStream file = new FileInputStream("AmazonsMem.gz");
			InputStream buffer = new GZIPInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			tree = (Node)input.readObject();
			current = tree;
			System.out.println("Root node/tree loaded");
			input.close();
		}
		catch(Exception e){
			System.out.println("Your fucked good luck with all random moves");
			e.printStackTrace();
		}
	}
	
	public String chooseMove(){
		System.out.println(current.color);
		if(!current.color.equals(color)) {
			return "out of turn play";
		}
		String move = null;
		boolean rand = false;
		Node max = null;
		double maxs = Integer.MIN_VALUE;
		ArrayList<Node> children = current.getChildren();
		Node random = null;
		if(children.size()==0) {
			random = current.randomChild(b);
			if(random == null) {
				return "no more moves found";
			}
			System.out.println("no children random move");
			current = random;
			return random.move;
		}else {
			for(Node child : children) {
				if(child.getScore()>maxs) {
					max = child;
				}
			}
		}
		move = max.move;
		b.move(move);
		current = max;
		return move;
	}
	public void recieveMove(String move) {
		System.out.println(current.color);
		String[] split = move.split("-");
		String color = split[0];
		String oc;
		if(color.equals("W"))
			oc = "B";
		else 
			oc = "W";
//		int pqx = Integer.parseInt(split[1])-1;
//		int pqy = Integer.parseInt(split[2])-1;
//		int qx = Integer.parseInt(split[3])-1;
//		int qy = Integer.parseInt(split[4])-1;
//		int ax = Integer.parseInt(split[5])-1;
//		int ay = Integer.parseInt(split[6])-1;
		
		boolean valid = false;
		
		if(!opColor.equals(current.color)) {
			System.out.println("out of turn playing anyways");
		}
		ArrayList<Node> children = current.getChildren();
		if(children!=null&&children.size()>0) {
			for(Node child : children) {
				if(move.equals(child.move)) {
					valid = true;
					b.move(move);
					current = child;
					System.out.println("we know this move");
					break;
				}
			}
		}
		if(valid==false){
			System.out.println("unknown move");
			b.move(move);
			Node child = new Node(b.getState(),oc,current,move);
			current.addChild(child);
			current = child;
			
		}
		
	}
	
	
}
