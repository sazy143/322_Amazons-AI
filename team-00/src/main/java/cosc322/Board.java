package cosc322;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cosc322.Amazons.BoardGameModel;

public class Board extends JPanel{
	int size = 10;
	int GUISize = 600;
	int[] Queensx = {0,0,3,3,6,6,9,9};
	int[] Queensy = {3,6,0,9,0,9,3,6};
	String[][] matrix = new String[size][size];
	
	public Board() {
		
		for(int i = 0; i<size; i++) {
			for(int j = 0; j<size; j++) {
					matrix[i][j] = ".";
			}
		}
		matrix[0][3] = "W";
		matrix[0][6] = "W";
		matrix[3][0] = "W";
		matrix[3][9] = "W";
		
		matrix[6][0] = "B";
		matrix[6][9] = "B";
		matrix[9][3] = "B";
		matrix[9][6] = "B";
	}
	public ArrayList<String> getValidMoves(String player){
		//player boolean is for whose turn it is true=white, false=black
		ArrayList<String> moves = new ArrayList<String>();
		ArrayList<String> qMoves = new ArrayList<String>();
		
		for(int i = 0; i<size; i++) {
			for(int j = 0; j<size; j++	) {
				if(matrix[i][j].equalsIgnoreCase(player)) {
					qMoves.addAll(queenMoves(i, j, player));
				}
			}
		}
		for(String move : qMoves) {
			moves.addAll(arrows(move));
			
		}
		System.out.println("White has "+moves.size()+" moves");
		return moves;
	}
	
	private ArrayList<String> arrows(String qMove){
		ArrayList<String> aMoves = new ArrayList<String>();
		String str = qMove.toString();
		String[] parsed = str.split("-");
		int qx = Integer.parseInt(parsed[3]);
		int qy = Integer.parseInt(parsed[4]);
		String[] directions = {"N","NE","E","SE","S","SW","W","NW"};
		for(String dir : directions) {
			boolean obstruction = false;
			int len = dir.length();
			char first = dir.charAt(0);
			char second = 'z';
			if(len==2) {	
				second = dir.charAt(1);
			}
			int nx = qx;
			int ny = qy;
			while(!obstruction) {
				
				if(first == 'N')
					nx +=1;
				if(first == 'S')
					nx -=1;
				if(first == 'E') 
					ny +=1;
				if(first == 'W')
					ny -=1;
				if(second!='z') {
					if(second =='E')
						ny +=1;
					if(second =='W')
						ny +=1;
				}
				if(nx>=size || ny>=size ||nx<0 || ny<0)
					obstruction = true;
				else if(matrix[nx][ny].equals(".")) {
					
					aMoves.add(new String(qMove+(nx+"-"+ny)));
				}
				else {
					obstruction = true;
				}
				
				
			}
		}
		
		return aMoves;
	}
	
	private ArrayList<String> queenMoves(int x, int y, String player) {
		ArrayList<String> moves = new ArrayList<String>();
		String[] directions = {"N","NE","E","SE","S","SW","W","NW"};
		for(String dir : directions) {
			boolean obstruction = false;
			int len = dir.length();
			char first = dir.charAt(0);
			char second = 'z';
			if(len==2) {	
				second = dir.charAt(1);
			}
			int nx = x;
			int ny = y;
			while(!obstruction) {
				
				if(first == 'N')
					nx +=1;
				if(first == 'S')
					nx -=1;
				if(first == 'E') 
					ny +=1;
				if(first == 'W')
					ny -=1;
				if(second!='z') {
					if(second =='E')
						ny +=1;
					if(second =='W')
						ny +=1;
				}
				if(nx>=size || ny>=size ||nx<0 || ny<0)
					obstruction = true;
				else if(matrix[nx][ny].equals(".")) {
					
					moves.add(new String(player+"-"+(x+1)+"-"+(y+1)+"-"+(nx+1)+"-"+(ny+1)+"-"));
				}
				else {
					obstruction = true;
				}
				
				
			}
		}
		return moves;
	}
	
	public boolean validateMove(String move) {
		boolean valid = false;
		String turn = move.substring(0, 1);
		ArrayList<String> validmoves = getValidMoves(turn);
//		for(int i =0; i<validmoves.size();i++) {
//			//if(move.equals(validmoves.get(i))) {
//				valid = true;
//				move(move);
//				break;
//			}
		move(move);
		
		
		return true;
	}
	private void move(String move) {
		String[] split = move.split("-");
		String turn = split[0];
		int pqy = Integer.parseInt(split[1])-1;
		int pqx = Integer.parseInt(split[2])-1;
		int qy = Integer.parseInt(split[3])-1;
		int qx = Integer.parseInt(split[4])-1;
		int ay = Integer.parseInt(split[5])-1;
		int ax = Integer.parseInt(split[6])-1;
		matrix[pqx][pqy] = ".";
		if(turn.equalsIgnoreCase("W")) 
			matrix[qx][qy] = "W";
		else
			matrix[qx][qy] = "B";
		matrix[ax][ay] = "x";
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		int offset = 25;
		int posize = 50;
		BufferedImage vine = null;
		BufferedImage queenW = null;
		BufferedImage queenB = null;
		BufferedImage arrow = null;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		try {
	    	vine = ImageIO.read(new File("vine.png"));
	    	queenW = ImageIO.read(new File("queenW.png"));
	    	queenB = ImageIO.read(new File("queenB.png"));
	    	arrow = ImageIO.read(new File("arrow.png"));
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		g.setColor(Color.BLACK);
		g.drawRect(offset-1, offset-1, 10*posize+2, 10*posize+2);
		for(int i = 0; i<size; i++) {
			g.drawString(Integer.toString(i+1), i*posize+offset+(posize/2)-5, 20);
			g.drawString(Integer.toString(i+1), 2, i*posize+offset+(posize/2)+5);
		}
		for(int r = 0; r<size; r++) {
			for(int c = 0; c<size; c++) {
				String value = matrix[r][c];
				if((r+c)%2==0) {
					g.setColor(Color.decode("#efafa2"));
					g.fillRect(c*posize+offset, r*posize+offset, posize+1, posize+1);
				}else {
					g.setColor(Color.decode("#ffdfd8"));
					g.fillRect(c*posize+offset, r*posize+offset, posize+1, posize+1);
				}
				if(value.equalsIgnoreCase("W")) {
					g.drawImage(queenW, c*posize+offset, r*posize+offset, posize, posize, null);
				}
				if(value.equalsIgnoreCase("B")) {
					g.drawImage(queenB, c*posize+offset, r*posize+offset, posize, posize, null);
				}
				if(value.equalsIgnoreCase("X")) {
					g.drawImage(arrow, c*posize+offset, r*posize+offset, posize, posize, null);
				}
				
			}
			g.drawImage(vine, 450, 0,500,500, null);
		}
	    
	}
	public Dimension getPreferredSize() {
	    return new Dimension(500,500);
     }
	
	public String toString() {
		String b = "";
		for(int i = 0; i<size; i++) {
			for(int j = 0; j<size; j++) {
				b += matrix[i][j] +" ";
			}
			b+="\n";
		}
		return b;
	}
}
