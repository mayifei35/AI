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
class DFS extends SlidingPlayer {
    ArrayList<SlidingMove> path;
    int move_number = -1;

    public DFS(SlidingBoard _sb) {
	super(_sb);
	path = dfs(_sb);
    }
    public ArrayList<SlidingMove> dfs(SlidingBoard board) {
	HashSet<String> seen = new HashSet<String>();
	LinkedList<StackNode> stack = new LinkedList<StackNode>();
	StackNode currNode = new StackNode(board, new ArrayList<SlidingMove>());
	while (!currNode.board.isSolved()) {
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
	}
	return currNode.path;
    } 
    
    public SlidingMove makeMove(SlidingBoard board) {
	move_number++;
	return path.get(move_number);
    }
}
