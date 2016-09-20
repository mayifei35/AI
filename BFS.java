import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;

class queueNode {
    public SlidingBoard board;
    public ArrayList<SlidingMove> path;
    public queueNode(SlidingBoard b, ArrayList<SlidingMove> p) {
	board = b;
	path = p;
    }
}


class BFS extends SlidingPlayer {

    ArrayList <SlidingMove> path;
    int move_number=-1;
    
    // The constructor gets the initial board
    public BFS(SlidingBoard _sb) {
        super(_sb);
	path = bfs(_sb);
    }
    public ArrayList<SlidingMove> bfs(SlidingBoard board) {
	HashSet<String> seen = new HashSet<String>();
	LinkedList<queueNode> queue = new LinkedList<queueNode>();
	queueNode curr = new queueNode(board, new ArrayList<SlidingMove>());
	while (!curr.board.isSolved()) {
	    ArrayList<SlidingMove> legal = curr.board.getLegalMoves();
	    for (SlidingMove move : legal) {
		SlidingBoard childBoard = new SlidingBoard(curr.board.size);
		childBoard.setBoard(curr.board);
		childBoard.doMove(move);
		if (!seen.contains(childBoard.toString())) {
		    seen.add(childBoard.toString());
		    ArrayList<SlidingMove> childPath = (ArrayList<SlidingMove>)curr.path.clone();
		    childPath.add(move);
		    queue.add(new queueNode(childBoard, childPath));
		}
	    }
	    curr = queue.remove();
	}
	return curr.path;
    }

    // Perform a single move based on the current given board state
    public SlidingMove makeMove(SlidingBoard board) {
	move_number++;
	return path.get(move_number);

    }   
}

