package cosc322;

import java.util.Scanner;

public class PlayerHuman {
String color;
	public PlayerHuman(String color) {
		this.color=color;
	}
	
	public String move() {
		String m = "";
		Scanner in = new Scanner(System.in);
		System.out.println("What is your move format('qx-qy-nqx-nqy-ax-ay'?)");
		m = in.next();
		m = this.color+"-"+m;
		//in.close();
		return m;
	}
	
	public String getColor() {
		return this.color;
	}
	
	
	
}
