import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
//this takes wayyyyyyyyyy to long to run

class iDFS extends SlidingPlayer {
    int depth=0;
    int maxDepth=3;
    ArrayList <SlidingMove> path;
    int move_number=-1;
    
    // The constructor gets the initial board
    public iDFS(SlidingBoard _sb) {
        super(_sb);
    }


    //find the next move given a board state
    public SlidingMove idfs(SlidingBoard board){
	Stack <SlidingMove> stack= new Stack<SlidingMove>();
	stack.addAll(board.getLegalMoves());
	ArrayList<SlidingMove> seen=new ArrayList<>();
	SlidingMove curr;
	System.out.println("so far so good");
	while(!stack.isEmpty()&&depth<=maxDepth){
	    System.out.println("in loop");
	    curr =stack.pop();
	    depth++;
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
	        int children=childBoard.getLegalMoves().size();
		depth=depth-children;
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

	return 	idfs(board); 
    }   
}

