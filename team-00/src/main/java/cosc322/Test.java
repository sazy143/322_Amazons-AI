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
		String player2 = "B";
		while(true) {
			String turn = game.turn;
			String move = null;
			boolean valid = false;
			System.out.println("Its player "+turn+" turn!");
			System.out.println(player1.b.toString());
				if(turn.equals(player1.color)) {
					move = player1.chooseMove();
					System.out.println(move);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					ArrayList<String> moves = game.board.getValidMoves(player2);
					if(moves==null||moves.size()==0) {
						System.out.println("player2 lost");
						break;
					}
					move = moves.get((int)(Math.random()*moves.size()));
					
					System.out.println("Random: "+move);
					
					player1.recieveMove(move);
				try {
					Thread.sleep(100);
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
