import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
//this takes wayyyyyyyyyy to long to run

class DFS extends SlidingPlayer {

    ArrayList <SlidingMove> path;
    int move_number=-1;
    
    // The constructor gets the initial board
    public DFS(SlidingBoard _sb) {
        super(_sb);
    }


    //find the next move given a board state
    public SlidingMove dfs(SlidingBoard board){
	Stack <SlidingMove> stack= new Stack<SlidingMove>();
	stack.addAll(board.getLegalMoves());
	ArrayList<SlidingMove> seen=new ArrayList<>();
	SlidingMove curr;
	System.out.println("so far so good");
	while(!stack.isEmpty()){
	    System.out.println("in loop");
	    curr =stack.pop();
	    SlidingBoard childBoard=new SlidingBoard (board.size);
	    System.out.println("perform a move");
	    childBoard.setBoard(board);
	    childBoard.doMove(curr);
	    System.out.println("check goal");
	    //if it is goal
	    if (childBoard.isSolved()){
		System.out.println("found it");
		return curr;
	    }
	    else {
		stack.addAll(childBoard.getLegalMoves());
		//path.add(curr);
	    }
	    seen.add(curr);
	}
	System.out.println("failed");
        return null;
    }


    // Perform a single move based on the current given board state
    public SlidingMove makeMove(SlidingBoard board) {
	//move_number++;
	//return path.get(move_number);

	return 	dfs(board); 
    }   
}

