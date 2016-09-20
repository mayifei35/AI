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

    public AStar (SlidingBoard _sb){
	super (_sb);
	path=astar(_sb);
    }
    
    //calculates the distance from current to goal
    public int distTo(SlidingBoard b){
	int dist=0;



	return dist;
    }
    
    //calculate distance from initial state to current
    public int distFrom(AStarNode star){
	int dis=0;


	return dis;
    }

    public ArrayList<SLidingMove> astar(SlidingBoard board){
	//initialize empty priorityqueue as frontier
	PriorityQueue <astarNode> q=new PriorityQueue<>();
	//add root node to frontier
	astarNode root=new astarNode(board,path,0);
	//repeat while goal node not found
	while(!astarNode.board.isSolved()){
	    //current= get and remove lowest cost node in frontier
	    astarNode curr=q.poll();
	    //if current==goal
if curr
//return goal node path
//else
//children =get current's list of children
//for each child

//calculate cost(child)
//add child and cost to frontier
}
