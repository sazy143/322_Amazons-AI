package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JFrame;

import cosc322.Amazons.MyTimer;
import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
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




starts game with "game-start" message

send Move message(int[] queenPosCurrent, int[] queenPosNew, int{} arrowpos)

join room (String name)

public boolean handleGamemessage(String messagetype, Map<String,Object>,msgDetails)
note: above method also ued in amazons.java


************************************************/
import ygraphs.ai.smart_fox.games.GamePlayer;
public class Game extends GamePlayer{
	Board board;
	JFrame guiFrame;
	public GameClient gameClient;
	String turn = "B";
	String userName;
	Competitive player;
	
	public Game(String name, String passwd,Competitive player){  
		
		this.userName = name;	
		this.player= player;
		setupGUI();       
	    connectToServer(name, passwd);        
	    }
	
	public void connectToServer(String name, String password) {
		
		gameClient = new GameClient(name, password,this);
	}
	
	public void playerMove(){	
		long timeout = System.currentTimeMillis();
		if(player.color.equals("W")) {
			while(System.currentTimeMillis()-timeout<2000) {
				player.searchFromCurrent();
			}
		}else {
			while(System.currentTimeMillis()-timeout<10000) {
				player.searchFromCurrent();
			}
		}
			
		String move = player.chooseMove();
		board.move(move);
		String[] parsed = move.split("-");
		int[] qf = new int[2];
		qf[0] = Integer.parseInt(parsed[1]);
		qf[1] = Integer.parseInt(parsed[2]);

		int[] qn = new int[2];
		qn[0] = Integer.parseInt(parsed[3]);
		qn[1] = Integer.parseInt(parsed[4]);

		int[] ar = new int[2];
		ar[0] = Integer.parseInt(parsed[5]);
		ar[1] = Integer.parseInt(parsed[6]);

		
		
		System.out.println(move);
		//System.out.println(board.toString());
		//System.out.println(board.toString());
		//To send a move message, call this method with the required data  
		//this.gameClient.sendMoveMessage(qf, qn, ar);
		nextTurn();
		gameClient.sendMoveMessage(qf, qn, ar);
	}
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails){
		
		if(messageType.equals(GameMessage.GAME_ACTION_START)){	
			System.out.println("start game msg");
		    if(((String) msgDetails.get("player-black")).equals(this.userName)){
			System.out.println("Game State: " +  msgDetails.get("player-black"));
			player.setColor("B");
			playerMove();
		    }else {
		    	player.setColor("W");
		    }
		}
		else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
		    handleOpponentMove(msgDetails);
		}
		
		return true;
	    }
	
	public void handleOpponentMove(Map<String, Object> msgDetails){
//		System.out.println("OpponentMove(): " + msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
		ArrayList<Integer> qcurr = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
		ArrayList<Integer> qnew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
		ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		System.out.println("QCurr: " + qcurr);
		System.out.println("QNew: " + qnew);
		System.out.println("Arrow: " + arrow);	
		String move = turn+"-"+qcurr.get(0)+"-"+qcurr.get(1)+"-"+qnew.get(0)+"-"+qnew.get(1)+"-"+arrow.get(0)+"-"+arrow.get(1);
		board.move(move);
		player.recieveMove(move);
		
		try {
			Thread.sleep(100);
		}catch(Exception e) {
			e.printStackTrace();
		}
		nextTurn();
		playerMove();
	    }
	public void onLogin() {

		//once logged in, the gameClient will have  the names of available game rooms  
		ArrayList<String> rooms = this.gameClient.getRoomList();
		for(String room : rooms) {
			System.out.println(room);
		}
		this.gameClient.joinRoom(rooms.get(2));
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
		guiFrame.setTitle("Game of the Amazons (COSC 322, " + this.userName +")");	
		
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

	@Override
	public String userName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	class MyTimer extends TimerTask{
		GameClient gameClient = null;
		int[] qf;
		int[] qn;
		int[] ar;
		
		public MyTimer(GameClient gameClient, int[] qf, int[] qn, int[] ar){	
		    this.gameClient = gameClient;
		    this.qf = qf;
		    this.qn = qn;
		    this.ar = ar;
		}
			
		/**
		 * send the move 
		 */
		public void run() {
			gameClient.sendMoveMessage(qf, qn, ar);
		}
	    }
}

