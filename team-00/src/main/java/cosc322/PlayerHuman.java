package cosc322;

import java.util.Scanner;

public class PlayerHuman {
String color;
	public PlayerHuman(String color) {
		this.color=color;
	}
	
	public String move() {
		String m = null;
		Scanner in = new Scanner(System.in);
		System.out.println("What is your move format('qx-qy-nqx-nqy-ax-ay'?)");
		m = in.nextLine();
		m = this.color+"-"+m;
		in.close();
		return m;
	}
	
	public String getColor() {
		return this.color;
	}
	
	
	
}
