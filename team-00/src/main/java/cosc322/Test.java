package cosc322;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class Test {

	public static void main(String[] args) {
		

		Competitive player1 = new Competitive("W",new Board());
		Competitive player2 = new Competitive("B",new Board());

		Game game = new Game("team13","test",player1);
		Game game2 = new Game("team132","test",player2);
		
//		//create our players
//		PlayerMCTS train = new PlayerMCTS();
//		Node root = train.root;
//		int count = 0;
//		while(count<20000) {   		//change 20000 to however many iterations you want
//			count++;
//			Board board = new Board();
//			train.b = board;
//			Node sel = train.select(root);
//			
//			Node exp = train.expand(sel);
//			
//			Node sim = train.simulate(exp);
//			
//			train.backprop(sim);
//			System.out.println("completed a simulation");
//			System.out.println("plays:"+train.root.plays+" wins:"+train.root.wins);
//			if(train.root.plays%20000==0) {			//change the 20000 to match above (save at end of training)
//				train.updatetofile(train.root);
//			}
//		}
		
	}
}
