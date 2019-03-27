package cosc322;

public class Training {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerMCTS train = new PlayerMCTS();
		Node root = train.root;
		int count = 0;
		while(count<20000) {   		//change 20000 to however many iterations you want
			count++;
			Board board = new Board();
			train.b = board;
			Node sim = train.simulate(root,root);
			
			train.backprop(sim);
			System.out.println("completed a simulation");
			System.out.println("plays:"+train.root.plays+" wins:"+train.root.wins);
			if(train.root.plays%20000==0) {			//change the 20000 to match above (save at end of training)
				train.updatetofile(train.root);
			}
		}
	}

}
