package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JFrame;

public class HumanVsAI {
	static JFrame guiFrame;

	//not done 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String turn = "B";
		Board board = new Board();
		Competitive player1 = new Competitive("B",new Board());
		PlayerHuman player2 = new PlayerHuman("W");
		setupGUI(board);
		
		while(true) {
			if(turn.equals("B")) {
				long timeout = System.currentTimeMillis();
				player1.current.checkChildren(board.getValidMoves(turn));
				int time = 2000;
				if(player1.current.color.equals("B"))
					time = 8000;
				
				while(System.currentTimeMillis()-timeout<time) {
					player1.searchFromCurrent();
					if(player1.current.plays>100000)
						break;
				}
					
				String move = player1.chooseMove();
				if(move==null||move.length()==0) {
					
					System.out.println("Game Over! No more moves :(");
				}else {
				board.move(move);
				board.repaint();
				turn = "W";
			}
			}else {
				String move = player2.move();
				board.move(move);
				board.repaint();
				turn = "B";
			}
		}

	}
	public static void setupGUI(Board b) {
		guiFrame = new JFrame();
		   
		guiFrame.setSize(800, 600);
		guiFrame.setTitle("Game of the Amazons (COSC 322, human Vs AI)");	
		
		guiFrame.setLocation(200, 200);
		guiFrame.setVisible(true);
	    guiFrame.repaint();		
		guiFrame.setLayout(null);
		
		Container contentPane = guiFrame.getContentPane();
		contentPane.setLayout(new  BorderLayout());
		 
		contentPane.add(Box.createVerticalGlue()); 
		
		contentPane.add(b,BorderLayout.CENTER);
		
	}

}
