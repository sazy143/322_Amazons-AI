package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JFrame;

import ygraphs.ai.smart_fox.games.GameClient;

//move format
//W-10-10-5-5-7-7 W or B is done for you by the game	
//currentqueenx-currentqueeny-newqueenx-newqueeny-arrowx-arrowy

//We need to find a way to end game if there are no moves

public class Game {
	Board board;
	JFrame guiFrame;
	String name;
	private GameClient gameClient;
	String turn = "W";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create our game
		Game game = new Game("test","test");
		//create our players
		PlayerHuman p1 = new PlayerHuman("B");
		PlayerHuman p2 = new PlayerHuman("W");
		//run the game
		while(true) {
			String turn = game.turn;
			String move = null;
			boolean valid = false;
			System.out.println("Its player "+turn+" turn!");
			while(!valid) {
				if(turn.equals(p1.getColor())) {
					move = p1.move();
					System.out.println(move);
				}
				else {
					move = p2.move();
					System.out.println(move);
				}
				valid = game.makemove(move);
			}
			game.nextTurn();
			//System.out.println(game.board.toString());
		}
		
		
		
	}
	
	public Game(String name, String passwd){  
		
		this.name = name;		       	   
		setupGUI();       
	    //connectToServer(name, passwd);        
	    }
	
	public void connectToServer(String name, String password) {
		
		gameClient = new GameClient(name, password);
	}
	
	public void onLogin() {

		//once logged in, the gameClient will have  the names of available game rooms  
		ArrayList<String> rooms = gameClient.getRoomList();
		this.gameClient.joinRoom(rooms.get(0));	 		
	    }
	
	//checks with the board whether you made a valid move 
	public boolean makemove(String move) {
		boolean check;
		check = this.board.validateMove(move);
		if(check) {
			System.out.println("valid move made");
		}else {
			System.out.println("invalid move made");
		}
		return check;
	}
	
	//switch turn
	public void nextTurn() {
		if(turn.equals("W")) {
			turn = "B";
		}else {
			turn = "W";
		}
	}
	//GUI Stuff
	public void setupGUI() {
		guiFrame = new JFrame();
		   
		guiFrame.setSize(800, 600);
		guiFrame.setTitle("Game of the Amazons (COSC 322, " + this.name +")");	
		
		guiFrame.setLocation(200, 200);
		guiFrame.setVisible(true);
	    guiFrame.repaint();		
		guiFrame.setLayout(null);
		
		Container contentPane = guiFrame.getContentPane();
		contentPane.setLayout(new  BorderLayout());
		 
		contentPane.add(Box.createVerticalGlue()); 
		
		board = new Board();
		contentPane.add(board,BorderLayout.CENTER);

	}

}
