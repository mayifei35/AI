import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import java.util.ArrayList;
import java.lang.InterruptedException;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

// Each MyPolygon has a color and a Polygon object
class MyPolygon {

    Polygon polygon;
    Color color;

    public MyPolygon(Polygon _p, Color _c) {
	polygon = _p;
	color = _c;
    }

    public Color getColor() {
	return color;
    }

    public Polygon getPolygon() {
	return polygon;
    }
}


// Each GASolution has 10 MyPolygon objects
class GASolution {

    ArrayList<MyPolygon> shapes;

    // width and height are for the full resulting image
    int width, height;

    public GASolution(int _width, int _height) {
	shapes = new ArrayList<MyPolygon>();
	width = _width;
	height = _height;
    }

    public void addPolygon(MyPolygon p) {
	shapes.add(p);
    }	

    public ArrayList<MyPolygon> getShapes() {
	return shapes;
    }

    public int size() {
	return shapes.size();
    }

    // Create a BufferedImage of this solution
    // Use this to compare an evolved solution with 
    // a BufferedImage of the target image
    //
    // This is almost surely NOT the fastest way to do this...
    public BufferedImage getImage() {
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	for (MyPolygon p : shapes) {
	    Graphics g2 = image.getGraphics();			
	    g2.setColor(p.getColor());
	    Polygon poly = p.getPolygon();
	    if (poly.npoints > 0) {
		g2.fillPolygon(poly);
	    }
	}
	return image;
    }

    public String toString() {
	return "" + shapes;
    }
}


// A Canvas to draw the highest ranked solution each epoch
class GACanvas extends JComponent{

    int width, height;
    GASolution solution;

    public GACanvas(int WINDOW_WIDTH, int WINDOW_HEIGHT) {
    	width = WINDOW_WIDTH;
    	height = WINDOW_HEIGHT;
    }
 
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setImage(GASolution sol) {
	solution = sol;
    }

    public void paintComponent(Graphics g) {
	BufferedImage image = solution.getImage();
	g.drawImage(image, 0, 0, null);
    }
}


public class GA extends JComponent{
	
    GACanvas canvas;
    int width, height;
    BufferedImage realPicture;
    ArrayList<GASolution> population;

    // Adjust these parameters as necessary for your simulation
    double MUTATION_RATE = 0.01;
    double CROSSOVER_RATE = 0.6;
    int MAX_POLYGON_POINTS = 5;
    int MAX_POLYGONS = 10;

    public GA(GACanvas _canvas, BufferedImage _realPicture) {
        canvas = _canvas;
        realPicture = _realPicture;
        width = realPicture.getWidth();
        height = realPicture.getHeight();
        population = createPopulation(50);


        // You'll need to define the following functions
	// Make 50 new, random chromosomes
       	
    }

    public Polygon createPolygon(){
	int npoints=5;
	int [] xpoints;
	int [] ypoints;
	for (int l=0;l<npoints;l++){
	    xpoints[l]=ThreadLocalRandom.current().nextInt(0,201);
	    ypoints[l]=ThreadLocalRandom.current().nextInt(0,201);
	}
	Polygon onePolygon=new Polygon(xpoints,ypoints,npoints);
	return onePolygon;
    }

    public Color createColor(){
	int r=ThreadLocalRandom.current().nextInt(0,256);
	int g=ThreadLocalRandom.current().nextInt(0,256);
	int b=ThreadLocalRandom.current().nextInt(0,256);
	color oneColor=new Color(r,g,b);
	return oneColor;
    }
    
    
    public GASolution createSolution(){
	Polygon p;
	MyPolygon mp;
	Color cl; 
	ArrayList<MyPolygon> allTen=new ArrayList<>();
	//each solution has 10 MyPolygons
	for (int h=0;h<10;h++){
	    cl=createColor();
	    p=createPolygon();
	    mp= new MyPolygon(p,cl);
	    allTen.add(mp);
	}
	GASolution newSolution=new GASolution(200,200);
	newSolution.shapes=allTen;
	return newSolution;
    }

    //each individual is a GASolution
    public ArrayList<GASolution> createPopulation(int popSize) {
	ArrayList<GASolution> population=new ArrayList<GASolution>();
	for (int i=0;i<popSize;i++){
	    population.add(createSolution());
	}
	return population;
    }

    //needs to figure out how to run simulation several times
    public double runSimulation() {
	ArrayList<GASolution> population=createPopulation(50);
	ArrayList<GASolution> parents=pickParent(population);
	GASolution child=crossover(parents.get(0),parents.get(1));
	int fitness=totalFitness(child);
	return fitness;
    }
  
    public double  totalFitness( ArrayList<GASolution> population){
	double fitness=0;
	for(each solution : population){
	    fitness=fitness+solution.parentFitness();
	}
	return fitness;
    }
    
    public double  parentFitness(GASolution parent){
	double total=12.0;
	double fitness=0.0;
        for (int k=0;k<200;k+=30){
	    for (int p=0;p<200;p+=30){
		if(parent.getImage().getRGB(p,k)==realPicture.getRGB(p,k)){
		    fitness++;
		}
	    }
	}
	return fitness/total;
    }

    //needs work
    public ArrayList<GASolution>  pickParent(ArrayList<GASolution> _parents){
	ArrayList<GASolution> parents=new ArrayList<GASolution>();
	for( each parent : _parents){
	    parentFitness(parent);
	    //get the hightest two
	    parents.add(parent);
	}   
	return parents;
    }

    public GASolution crossover(GASolution one,GASolution two){
	ArrayList <MyPolygon> childP=new ArrayList <MyPolygon>();
	GASolution childSolution=new GASolution(200,200);
	childSolution.shapes=childP;
	for (int k=0;k<10;k=k+2){
	    childP.add(one.shapes.get(k));
	    childP.add(two.shapes.get(k+1));
	}
	childSolution=mutate(childSolution);
	return childSolution;
    }

    //brighter color and one more point for the polygon
    public GASolution mutate(GASolution child){
	//generate a random number between 0 and 1,rate
	if (Math.random()<0.001){
	    ArrayList<MyPolygon> cmPolygons=child. getShapes();
	    for (each cmp: cmPolygons){
		Color c=cmp.getColor;
		Polygon p=cmp.getPolygon();
		c.brighter();
		int x=ThreadLocalRandom.current().nextInt(0,200);
		int y=ThreadLocalRandom.current().nextInt(0,200);
		p.addPoint(x,y);
	    }

	}
	return child;
    }
    
 

    public static void main(String[] args) throws IOException {

        String realPictureFilename = "test.jpg";

        BufferedImage realPicture = ImageIO.read(new File(realPictureFilename));

        JFrame frame = new JFrame();
        frame.setSize(realPicture.getWidth(), realPicture.getHeight());
        frame.setTitle("GA Simulation of Art");
	
        GACanvas theCanvas = new GACanvas(realPicture.getWidth(), realPicture.getHeight());
        frame.add(theCanvas);
        frame.setVisible(true);

        GA pt = new GA(theCanvas, realPicture);
	double fit=pt.runSimulation();
	System.out.println(fit);
    }
}




