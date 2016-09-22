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
import java.util.Random;


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



class GASolution {

    ArrayList<MyPolygon> shapes;

    // width and height are for the full resulting image
    int width, height,nPoints,nPoly;
    
    // Each GASolution has 10 MyPolygon objects,each myPolygon has 5 points
    public GASolution(int _width, int _height, int _nPoly, int _nPoints) {
	shapes = new ArrayList<MyPolygon>();
	width = _width;
	height = _height;
	nPoints=_nPoints;
	nPoly=_nPoly;
    }

    public GASolution(int _width, int _height, int _nPoly, int _nPoints, ArrayList<MyPolygon> _shapes) {
	shapes = new ArrayList<MyPolygon>();
	width = _width;
	height = _height;
	nPoly = _nPoly;
	nPoints = _nPoints;
	shapes = _shapes;
    }

    public GASolution deepCopy(){
	GASolution copy= new GASolution(width, height, nPoly, nPoints);
	for (MyPolygon poly : shapes){
	    Polygon oldPoly = poly.getPolygon();
	    Polygon newPoly = new Polygon(oldPoly.xpoints.clone(), oldPoly.ypoints.clone(), oldPoly.npoints);
	    Color oldColor = poly.getColor();
	    Color newColor = new Color(oldColor.getRGB());
	    MyPolygon newMyPoly = new MyPolygon(newPoly, newColor);
	    copy.addPolygon(newMyPoly);
	}
	return copy;
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
    
    
    public Polygon createPolygon(){
	Polygon onePolygon = new Polygon();
	    for(int p =0; p < nPoints; p++){
		int x = (int)(Math.random()*(width));
		int y = (int)(Math.random()*(height));
		onePolygon.addPoint(x, y);
	    }
	return onePolygon;
    }

    public Color createColor(){
	int r=ThreadLocalRandom.current().nextInt(0,256);
	int g=ThreadLocalRandom.current().nextInt(0,256);
	int b=ThreadLocalRandom.current().nextInt(0,256);
	Color oneColor=new Color(r,g,b);
	return oneColor;
    }

    public void createSolution(){
	for (int i = 0; i< nPoly; i++){
	    Polygon poly=createPolygon();
	    Color c=createColor();
	    addPolygon(new MyPolygon(poly, c));
	}
    }
}




public class GA extends JComponent{
	
    GACanvas canvas;
    int width, height;
    BufferedImage realPicture;
    ArrayList<GASolution> population;
    ArrayList<Double> fitnesses;
    Random r = new Random();

    // Adjust these parameters as necessary for your simulation
    double MUTATION_RATE = 0.001;
    double CROSSOVER_RATE = 0.6;
    int MAX_POLYGON_POINTS = 5;
    int MAX_POLYGONS = 10;
    int popSize=50;

    public GA(GACanvas _canvas, BufferedImage _realPicture) {
        canvas = _canvas;
        realPicture = _realPicture;
        width = realPicture.getWidth();
        height = realPicture.getHeight();
        population = new ArrayList<GASolution>();       	
    }

    public void createPopulation(){
	for(int g=0;g<popSize;g++){
	    GASolution pop=new GASolution (width,height,MAX_POLYGONS,MAX_POLYGON_POINTS);
	    pop.createSolution();
	    population.add(pop);
	}
    }

    
    public ArrayList<GASolution> newGeneration(){
  	ArrayList<GASolution> newGen = new ArrayList<GASolution>();
    	for(int i = 0; i<popSize; i++){
	    GASolution one = pickParent();
	    GASolution two = pickParent();
	    GASolution child; 
	    if(Math.random()<CROSSOVER_RATE) {
		child = crossover(one, two);
	    }
	    else {
		child = one.deepCopy();
	    }
	    child = mutate(child);
	    newGen.add(child);
    	}
    	return newGen;
    }
  
    public double colorDistance(Color one, Color two){
	double distR=Math.pow(one.getRed()-two.getRed(),2);
	double distG=Math.pow(one.getGreen()-two.getGreen(),2);
	double distB=Math.pow(one.getBlue()-two.getBlue(),2);
	double distance=Math.sqrt(distR+distG+distB);
	return distance;
    }

    public double calcFitness(GASolution solution){
	double total=0;
	int numPoints=50;
	BufferedImage solutionImg=solution.getImage();
	for (int v=0;v<numPoints;v++){
	    int x=(int)Math.random()*width;
	    int y=(int)Math.random()*height;
	    Color real=new Color(realPicture.getRGB(x,y));
	    Color simulated=new Color(solutionImg.getRGB(x,y));
	    total+=colorDistance(real,simulated);
	}
	return total;
    }

    public ArrayList<Double> getPopFitnesses(){
    	for (GASolution sol:population){
	    fitnesses.add(calcFitness(sol));
    	}
    	return fitnesses;
    }
    
    
    public int getFittest(){
	double fittest=0;
	int count;
	for (count=0;count<fitnesses.size();count++){
	    if (fitnesses.get(count)>fittest){
		fittest=fitnesses.get(count);
	    }
	}
	return count;
    }
    public double averageFitness(){
	double total=0;
	for ( double fit:fitnesses){
	    total+=fit;
	}
	return total/fitnesses.size();
    }

	
    public GASolution  pickParent(){
	double total=0;
	for (double fit:fitnesses){
	    total+=fit;
	}
	double arrow=Math.random()*total;
	int i=-1;
	while (arrow>1){
	    i++;
	    arrow-=fitnesses.get(i);
	}
	return population.get(i);
    }

    public GASolution crossover(GASolution one,GASolution two){
	ArrayList <MyPolygon> childP=new ArrayList <MyPolygon>();
	Polygon parentOnePoly,parentTwoPoly,childPoly,childPoly2;
	Color childColor;
	for (int k=0;k<MAX_POLYGONS;k=k+2){
	    parentOnePoly=one.getShapes().get(k).getPolygon();
	    parentTwoPoly=one.getShapes().get(k+1).getPolygon();    
	    childPoly=new Polygon(parentOnePoly.xpoints.clone(), parentTwoPoly.ypoints.clone(), parentOnePoly.npoints);
	    childPoly2=new Polygon(parentTwoPoly.xpoints.clone(), parentOnePoly.ypoints.clone(), parentTwoPoly.npoints);
	    Color c1=one.getShapes().get(k).getColor();
	    Color c2=two.getShapes().get(k+1).getColor();
	    if (Math.random()<0.5){
		childP.add(new MyPolygon(childPoly,c1));
		childP.add(new MyPolygon(childPoly2,c2));
	    }
	    else{
		childP.add(new MyPolygon(childPoly,c2));
		childP.add(new MyPolygon(childPoly2,c1));
	    }
	}

	return new GASolution(one.width,one.height,one.nPoly,one.nPoints,childP);
        
    }

    public GASolution mutate(GASolution sol){
    	ArrayList<MyPolygon> newShapes = new ArrayList<MyPolygon>();
    	ArrayList<MyPolygon> oldShapes = sol.getShapes();

    	for (MyPolygon p : oldShapes){
	    Polygon oldPoly = p.getPolygon();
	    int[] xVals = oldPoly.xpoints.clone();
	    int[] yVals = oldPoly.ypoints.clone();
	    for(int i = 0; i < oldPoly.npoints; i++){
		if(Math.random()<MUTATION_RATE){
		    xVals[i] = (int)(xVals[i]+r.nextGaussian()*(width/10));
		    yVals[i] = (int)(yVals[i]+r.nextGaussian()*(height/10));
		}
	    }
	    Polygon newPoly = new Polygon(xVals, yVals, oldPoly.npoints);

	    Color newColor = new Color(p.getColor().getRGB());
	    if(Math.random()<MUTATION_RATE){
		float r = (float)Math.random();
		float g = (float)Math.random();
		float b = (float)Math.random();
		newColor = new Color(r, g, b);
	    }
	    newShapes.add(new MyPolygon(newPoly, newColor));
    	}

    	return new GASolution(sol.width, sol.height, sol.nPoly, sol.nPoints, newShapes);
    }
    

    //needs to figure out how to run simulation several times
    public void runSimulation(int epochs) {
        createPopulation();
    	for(int i = 0; i < epochs; i++){
	    population=newGeneration();
	    if (i % 100==0){
		System.out.println("Epochs = " + i + "; Average fitness = " + averageFitness());
	    }
	    canvas.setImage(population.get(getFittest()));
	    canvas.repaint();
	}
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
        pt.runSimulation(5000);
    }
}




