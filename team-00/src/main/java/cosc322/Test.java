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
		Node tree = null;
		try{
			FileInputStream file = new FileInputStream("AmazonsMem.gz");
			InputStream buffer = new GZIPInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			tree = (Node)input.readObject();
			System.out.println("Root node/tree loaded");
			input.close();
		}
		catch(Exception e){
			System.out.println("Your fucked good luck with all random moves");
			e.printStackTrace();
		}

		ArrayList<Node> children = tree.getChildren();
		System.out.println(children.size());
		for(Node child :children) {
			System.out.println(child.getScore());
		}
	}

}
