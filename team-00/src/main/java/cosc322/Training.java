package cosc322;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

public class Training {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerMCTS train = new PlayerMCTS();
		Node root = new Node("B",null,null);
		try{
			InputStream file = new FileInputStream("AmazonsMem.gz");
			InputStream buffer = new GZIPInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			root = (Node)input.readObject();
			
			System.out.println("Root node/tree loaded");
			input.close();
		}
		catch(Exception e){
			System.out.println("good luck with all random moves");
			
			e.printStackTrace();
		}
		int count = 0;
		while(count<40000) {   		//change 20000 to however many iterations you want
			count++;
			Board board = new Board();
			train.b = board;
			Node sim = train.simulate(root,root);
			
			train.backprop(sim);
			if(count%1000==0) {
			System.out.println("completed a simulation");
			System.out.println("plays:"+root.plays+" wins:"+root.wins);
			}
			if(root.plays%40000==0) {			//change the 20000 to match above (save at end of training)
				train.updatetofile(root);
			}
		}
	}

}
