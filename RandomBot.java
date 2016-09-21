import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

public class RandomBot extends OthelloPlayer {
    
    // makeMove gets a current OthelloBoard game state as input
    // and then returns an OthelloMove object
    
    // Your bot knows what color it is playing
    //    because it has a playerColor int field
    
    // Your bot can get an ArrayList of legal moves:
    //    ArrayList<OthelloMove> moves = board.legalMoves(playerColor);
    
    // The constructor for OthelloMove needs the row, col, and player color ints.
    // For example, play your token in row 1, col 2:
    //   OthelloMove m = new OthelloMove(1, 2, playerColor);
    
    // OthelloBoard objects have a public size field defining the
    // size of the game board:
    //   board.size
    
    // You can ask the OthelloBoard if a particular OthelloMove
    //  flanks in a certain direction.
    // For example:
    //  board.flanksLeft(m) will return true if you can capture pieces to the left of move, m
    
    // You can ask the board what the current score is.
    //  This is just the difference in checker counts
    //  return the point differential in black's favor
    //  +3 means black is up by 3
    //  -5 means white is up by 5
    // int score = board.getBoardScore();

    // OthelloBoard has a toString:
    //  System.out.println(board);
    
    // OthelloPlayer superclass has a method to get the color for your opponent:
    //  int opponentColor = getOpponentColor();
    
    
    public RandomBot(Integer _color) {
        super(_color);
    }

    class stackNode{
	ArrayList<OthelloMove> path=new ArrayList<OthelloMove>();
	int score;
	OthelloMove current;
	
	    public stackNode(ArrayList<OthelloMove> _path, int _score, OthelloMove _current) {
	    path=_path;
	    score=score+_score;
	    current=_current;
	}
	public int getScore(){
	    return this.score;
	}
    }






    public OthelloBoard simulate(OthelloMove move,OthelloBoard board){
	int[][] b=board.getBoard();
	int row=move.getRow();
	int col=move.getCol();
        b[row][col]=playerColor;
	board.setBoard(b);
	return board;

    }
    
    public OthelloMove makeMove(OthelloBoard board) {
        return dfs(board);
    }

    public int heuristic(OthelloMove move,OthelloBoard board){
	//get board state 
	board =simulate(move,board);
	//assuming using this move, what other movies are left?
	ArrayList<OthelloMove> nextMoves=board.legalMoves(playerColor);
	//evaluate based on  available moves after applying
	int nextMoveNum=nextMoves.size();
	//evaluate based on position
	int value=risk(move,board);
	//get how many stones you have after the move, less is better(use in early stages),need to know how many moves have done
	int score = 0-board.getBoardScore();
	int finalV=nextMoveNum+value+score;
	return finalV;
    }

    //evaluate how good the move is based on its position
    public int risk(OthelloMove move,OthelloBoard board){
	int value=0;
	int col=move.getCol();
	int row=move.getRow();
	//give corners 100,edges 80  baked in board size
	if (row==1||row==board.getSize()){
	    if(col==1 || col==board.getSize()){
		value=9;
	    }
	    else {
		value=7;
	    }
	}
	else if (col==1||col==board.getSize()){
	    if(row==1 || row==board.getSize()){
		value=9;
	    }
	    else {
		value=7;
	    }
	}
	else{
	    value=5;
	}
	return value;
    }


    public OthelloMove dfs(OthelloBoard board ){
	int depth=0;
	int limit=6;  
	//get all legal moves from start board 
	ArrayList<OthelloMove> legal=board.legalMoves(playerColor);
	ArrayList<OthelloMove> currPath; 
	Stack <stackNode> s=new Stack<stackNode>();
	OthelloMove curr;
	stackNode n;
	stackNode best;
	//push all legal moves into the stack
	for (int q=0;q<legal.size();q++){
	    currPath.add(legal.get(q));
	    n=new stackNode(currPath,heuristic(curr,board),current);
	    s.push(n);
	    currPath.remove(0);
	}
	//iterate through the tree and keep adding to the stack
	while (!s.isEmpty()&depth<=limit){
	    curr=s.pop().current();    
	    //System.out.println(element.data + "\t");
	    //do the move and get next legal moves

	    board=simulate(curr,board);
	    legal=board.legalMoves(playerColor);
	    //if its not a leaf, add its children to the  stack
	    if(!legal.isEmpty()){  
		for (int w=0;w<legal.size();w++){
		    currPath.add(legal.get(w));
		    n=new stackNode(currPath,heuristic(curr,board),curr);
		    s.push(n);
		}
		depth++;	      
	    }
	    if (best.getScore()<=curr.getScore()){
		best=curr;
	    }
	}  
	return best.path.get(0);
    }  
} 

