import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;

/*create a stackNode object that contains the path to it from the start and the board state
class node{
    public SlidingBoard board;
    public ArrayList<SlidingMove> path;
    //constructor
    public node(SlidingBoard b, ArrayList<SlidingMove> p){
	board=b;
	path=p;
    }
    }*/

class BFS extends SlidingPlayer {
    ArrayList <SlidingMove> childList=new ArrayList<SlidingMove>();
    //keeps track of all the previous board states
    HashSet<String> seen=new HashSet <String>();
    ArrayList <SlidingMove> path;
    ArrayList<SlidingMove> legal;
    int move_number=-1;
    
    // The constructor gets the initial board
    public BFS(SlidingBoard _sb) {
        super(_sb);
	//path=bfs(_sb);
    }


    //find the next move given a board state
    public ArrayList<SlidingMove> bfs(SlidingBoard board){
	//int count=0;
	//node currNode=new node(board,new ArrayList<SlidingMove>());
	//loop until solved
	while (!board.isSolved()){
	    //get all legal moves at current board state
	    legal=board.getLegalMoves();
	    //enque all legal moves         
	    childList.addAll(legal);   
	    //for each legal move,check to see if it's goal
	    for(SlidingMove move:childList){
		//copying the current board
		SlidingBoard childBoard=new SlidingBoard (currNode.board.size);
		//perform a move 
		childBoard.setBoard(board);
	     	childBoard.doMove(move);
		//if it is goal
		if (childBoard.isSolved){
		    return path.get(0);
		}
		//if the path has been seen before,print
		if(seen.contains(childBoard.toString())){
		    System.out.println("been there done that");
		}
		//if it's a new path and not goal, add its chidren to the queue and keep track of the path 
		else{
		   
		    //ArrayList<SlidingMove> childPath=(ArrayList<SlidingMove>)currNode.path.clone();
		    //childPath.add(move);
		    //get all legal moves and add to the queue
		    childList.addAll(childBoard.getLegalMoves());
		    //count++;
		    seen.add(childBoard);
		    childList.remove(0);
		}
	    }
	}
	return currNode.path;
    }



    // Perform a single move based on the current given board state
    public SlidingMove makeMove(SlidingBoard board) {
	//move_number++;
	//return path.get(move_number);
	ArrayList<SlidingMove> bestPath =bfs(board);
	return bestPath.get(0);
    }   
}

