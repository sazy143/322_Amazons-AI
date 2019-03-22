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
		
		Game game = new Game("test","test");
		Competitive player1 = new Competitive("W",game.board);
		player1.setTree();
		Competitive player2 = new Competitive("B",game.board);
		player2.setTree();
		while(true) {
			String turn = game.turn;
			String move = null;
			boolean valid = false;
			System.out.println("Its player "+turn+" turn!");
			System.out.println(player1.b.toString());
				if(turn.equals(player1.color)) {
					move = player1.chooseMove();
					System.out.println(move);
					player2.recieveMove(move);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					move = player2.chooseMove();
					
					System.out.println(move);
					
				player1.recieveMove(move);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			game.nextTurn();
			//System.out.println(game.board.toString());
		}

	}
}
