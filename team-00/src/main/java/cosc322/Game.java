package cosc322;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JFrame;

//move format
//W-10-10-5-5-7-7
//player-queenx-queeny-newqueenx-newqueeny-arrowx-arrowy

public class Game {
	Board board;
	JFrame guiFrame;
	String name;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game("test","test");
		game.board.getValidMoves("W");
//		String move = null;
//		boolean check;
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		while(true) {
//			check = false;
//			try{
//				  move = in.readLine();
//			  }catch(IOException e){
//				 System.out.println(e);
//			  }
//			if(move.equals("x")){
//				break;
//			}
//			game.makemove(move);
//			System.out.println(game.board.toString());
//		}
		
		
	}
	
	public Game(String name, String passwd){  
			
		this.name = name;		       	   
		setupGUI();       
	    //connectToServer(name, passwd);        
	    }
	
	public void makemove(String move) {
		boolean check;
		check = this.board.validateMove(move);
		if(check) {
			System.out.println("valid move made");
		}else {
			System.out.println("invalid move made");
		}
	}
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
