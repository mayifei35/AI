import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;

class StackNode {
    public SlidingBoard board;
    public ArrayList<SlidingMove> path;
    public StackNode(SlidingBoard b, ArrayList<SlidingMove> p) {
	board = b;
	path = p;
    }
}
class iDFS extends SlidingPlayer {
    ArrayList<SlidingMove> path;
    int move_number = -1;

    public iDFS(SlidingBoard _sb) {
	super(_sb);
	path = idfs(_sb);
    }
    public ArrayList<SlidingMove> idfs(SlidingBoard board) {
	int depth=0;
	int maxDepth=2;
	HashSet<String> seen = new HashSet<String>();
	LinkedList<StackNode> stack = new LinkedList<StackNode>();
	StackNode currNode = new StackNode(board, new ArrayList<SlidingMove>());
	while (!currNode.board.isSolved()) {
	    if (depth<=maxDepth){
		ArrayList<SlidingMove> legal = currNode.board.getLegalMoves();
		for (SlidingMove move : legal) {
		    SlidingBoard childBoard = new SlidingBoard(currNode.board.size);
		    childBoard.setBoard(currNode.board);
		    childBoard.doMove(move);
		    if (!seen.contains(childBoard.toString())) {
			seen.add(childBoard.toString());
			ArrayList<SlidingMove> childPath = (ArrayList<SlidingMove>)currNode.path.clone();
			childPath.add(move);
			stack.add(new StackNode(childBoard, childPath));
		    }
		}
		currNode = stack.pop();
		depth++;
	    }
	    else{
		maxDepth++;
	    }
	}
	return currNode.path;
    } 
    
    public SlidingMove makeMove(SlidingBoard board) {
	move_number++;
	return path.get(move_number);
    }
}
