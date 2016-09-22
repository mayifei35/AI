import java.util.Random;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.PriorityQueue;

class AStarNode {
    public SlidingBoard board;
    public ArrayList<SlidingMove> path;
    int cost;
    public AStarNode(SlidingBoard b, ArrayList<SlidingMove> p,int _cost) {
	board = b;
	path = p;
	cost=_cost;
    }
}

class AStar extends SlidingPlayer{
    ArrayList<SlidingMove> path=new ArrayList<SlidingMove>();
    int move_number=-1;
    

    public AStar (SlidingBoard _sb){
	super (_sb);
	path=astar(_sb);
    }
    
    //calculates the distance from current to goal
    public int distTo(SlidingBoard b){
	int dist=0;
	string board=b.toString();
	//add up (current row-goal row)+(current column-goal column) for each number

	return dist;
    }
    
    //calculate distance from initial state to current
    public int distFrom(AStarNode star){
	return path.size();
    }
    public int cost(AStarNode star){
	return distFrom(star)+distTo(star.board);
    }

    public ArrayList<SLidingMove> astar(SlidingBoard board){
	//initialize empty priorityqueue as frontier
	PriorityQueue <astarNode> q=new PriorityQueue<>();
	//add root node to frontier
	astarNode root=new astarNode(board,path,0);
	//repeat while goal node not found
	while(!astarNode.board.isSolved()){
	    //current= get and remove lowest cost node in frontier
	    astarNode curr=root;
	    //if current==goal
	    if (currNode.board.isSolved()){
		return currNode.path;
	    }
	    else{
		ArrayList<SLidingMove> legalMoves=currNode.getLegalMoves();
		for(SlidingMove legal:legalMoves){
		    int cost=legal.cost();
		    //add child and cost to frontier
		}
	    }
	    curr=q.poll;
	}
	return curr.path;
    }
    // Perform a single move based on the current given board state
    public SlidingMove makeMove(SlidingBoard board) {
	move_number++;
	return path.get(move_number);
    } 
}
