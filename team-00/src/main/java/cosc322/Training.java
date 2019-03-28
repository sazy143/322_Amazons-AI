package cosc322;

public class Training {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerMCTS train = new PlayerMCTS();
		Node root = new Node(new Board().getState(),"B",null,null);
		int count = 0;
		while(count<100000) {   		//change 20000 to however many iterations you want
			count++;
			Board board = new Board();
			train.b = board;
			Node sim = train.simulate(root,root);
			
			train.backprop(sim);
			if(count%1000==0) {
			System.out.println("completed a simulation");
			System.out.println("plays:"+root.plays+" wins:"+root.wins);
			}
			if(root.plays%100000==0) {			//change the 20000 to match above (save at end of training)
				train.updatetofile(root);
			}
		}
	}

}
