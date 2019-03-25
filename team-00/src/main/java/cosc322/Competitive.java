package cosc322;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class Competitive {

	Board b;
	String color;
	Node tree;
	PlayerMCTS mcts = new PlayerMCTS();
	Node current = new Node(new Board().getState(),"B",null,null);
	String opColor;
	DecimalFormat format = new DecimalFormat("##.##");
	
	Competitive(String color, Board b){
		this.color = color;
		this.b = b;
		if(color.equals("W")) {
			opColor = "B";
		}
		else {
			opColor = "W";
		}
		tree = mcts.root;
		current = tree;
		
	}
	public void setColor(String color) {
		this.color=color;
		if(color.equals("W")) {
			this.opColor = "B";
		}else {
			this.opColor = "W";
		}
	}
	
	//no longer needed with MCTS player
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
	public void searchFromCurrent() {
		mcts.b = this.b.shallowCopy();
		//Node sel = mcts.select(current);
		//Node exp = mcts.expand(sel);
		Node sim = mcts.simulate(current);
		mcts.backprop2(sim,current);
	}
	
	public String chooseMove(){
		//System.out.println(current.color);
		System.out.println("This node has played "+current.plays+" simulations and won "+current.wins+" times");
		if(!current.color.equals(color)) {
			return "out of turn play";
		}
		String move = null;
		boolean rand = false;
		Node max = null;
		Node smax = null;
		double maxs = Integer.MIN_VALUE;
		ArrayList<Node> children = current.getChildren();
		ArrayList<String> validMoves = b.getValidMoves(color);
		ArrayList<Node> validChildren = new ArrayList<Node>();
		for(String vmove : validMoves) {
			
			for(Node cc : children) {
				
				if(vmove.equals(cc.move)) {
					validChildren.add(cc);
					
					break;
				}
			}
			
		}
		System.out.println(children.size()+" children "+validChildren.size()+" valid children");
		
		Node random = null;
		if(validChildren.size()==0||validChildren==null) {
			max = current.randomChild(b);
			if(random == null) {
				return "no more moves found";
			}

		}else {
			max = validChildren.get(0);
			//smax = children.get(0);
			maxs = validChildren.get(0).getScore();
			for(Node child : validChildren) {
				double score = child.getScore();
				if(score>maxs) {
					smax = max;
					maxs = score;
					max = child;
					
				}
			}
		}
		//NEED A SECOND CHECKER CAUSE OF WEIRD GLITCH
		current.parent = null;
		move = max.move;
		System.out.println("The strongest child had a "+format.format(((double)max.wins)/max.plays)+"% win rate from "+max.plays+" simulations, score:"+format.format(max.getScore()));
		b.move(move);
		current = max;
		return move;
	}
	public int[] playerMove(){		
		String move = this.chooseMove();
		String[] parsed = move.split("-");
		int[] together = new int[6];
		int[] qf = new int[2];
		qf[0] = Integer.parseInt(parsed[1]);
		qf[1] = Integer.parseInt(parsed[2]);

		int[] qn = new int[2];
		qn[0] = Integer.parseInt(parsed[3]);
		qn[1] = Integer.parseInt(parsed[4]);

		int[] ar = new int[2];
		ar[0] = Integer.parseInt(parsed[5]);
		ar[1] = Integer.parseInt(parsed[6]);
		together[0] = qf[0]; 
		together[1] = qf[1]; 
		together[2] = qn[0]; 
		together[3] = qn[1]; 
		together[4] = ar[0]; 
		together[5] = ar[1]; 
		
		return together;
		//To send a move message, call this method with the required data  
		//this.gameClient.sendMoveMessage(qf, qn, ar);
	}
	public void recieveMove(String move) {
		//System.out.println(current.color);
		String[] split = move.split("-");
		String color = split[0];
		String oc;
		if(color.equals("W"))
			oc = "B";
		else 
			oc = "W";
		
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
