package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JFrame;

import ygraphs.ai.smart_fox.games.GameClient;
/******************************************
move format
W-10-10-5-5-7-7 W or B is done for you by the game	
currentqueenx-currentqueeny-newqueenx-newqueeny-arrowx-arrowy

This Game class holds the main method, which creates the initial board, gui, and connects us to the client
Moves will come in and out of this class aswell

The board class is the board it contains the information for where pieces are located, and what moves are valid from that point
It can create a board from a state, and can generate a state from a board. It can make moves, validate moves, and generate viable moves
It also has a paint component to be drawn

The Node class are the nodes in our MCT, and contain all the data that each node needs. A node has a unique state, a parent node, and 
potentially children, it also contains color, so we know if the move was made by either W player or B player.
The node is able to generate children from its state, and is initially a leaf until it is explored. It also has a method 
to calculate its score which is needed for the selection phase.

The playerMCTS class is for implementing all the methods needed for MCTS. When initially called it sets the root node to the base board
state (should not be new if we are loading from memory). For now it has the four base methods for MCTS.
Select - To find a leaf node from root node(current state)
Expand - Expand the leaf node from select, and select one of its children
Simulation - Complete a playout from node returned by expand (win/lose/tie)
Backpropagation - Use the result of the playout and update information at each of the nodes till the root node

The player Human was for acceptance testing of viable moves, and if you want to just play no AI needed

TODO:
Game class needs the ability to send and receive moves
Game class needs a way of getting our chosen move

Board and Node class may need additional helper methods in the future, but for now are good

PlayerMCTS class select needs to alternate between getting min and max based on current color, and the next child nodes color (min/max)
PlayerMCTS class expand needs to end game if no children 
PlayerMCTS class expand needs to choose a child 
PlayerMCTS class implement simulation
PlayerMCTS class implement backpropagation

There is more todo but cannot think of right now, and we will figure it out regardless, but these are the first steps above

************************************************/
public class Game {
	Board board;
	JFrame guiFrame;
	String name;
	private GameClient gameClient;
	String turn = "B";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create our game
		Game game = new Game("test","test");
		//create our players
		PlayerMCTS train = new PlayerMCTS();
		Node root = train.root;
		int count = 0;
		while(count<10000) {
			count++;
			Board board = new Board();
			train.b = board;
			Node sel = train.select(root);
			
			Node exp = train.expand(sel);
			
			Node sim = train.simulate(exp,0);
			
			train.backprop(sim);
			System.out.println("completed a simulation");
			System.out.println("plays:"+train.root.plays+" wins:"+train.root.wins);
			if(train.root.plays%10000==0) {
				train.updatetofile(train.root);
			}
			
			
			
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
