import acm.graphics.*;
import acm.program.GraphicsProgram;
import java.awt.Color;
import acm.program.*;
import com.sun.glass.events.KeyEvent;
import com.sun.glass.events.MouseEvent;
import java.awt.event.*;

public class ShortFilm extends GraphicsProgram {

	private static final int TREE_SIZE = 50;
	private static final int FLOWER_SIZE = 50;
	private static final int GRASS_WIDTH = 86;
	private static final int LINE_WIDTH = 6;
	private static final int CAR_WIDTH = 50;
	private static final int CAR_HEIGHT = 80;
	private GImage car;
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	//creates 1 single full line
	private void fullLine(int x1) {
		GLine line = new GLine(x1, 0, x1, getHeight());
		line.setColor(Color.WHITE);
		line.setLineWidth(LINE_WIDTH);
		add(line);
	}
	
	//players car
	private void playersCar() {
		car = new GImage("car.png");
		car.setSize(CAR_WIDTH, CAR_HEIGHT);
		add(car, 322, getHeight() - CAR_HEIGHT);
	}
	
	//move car
	private void carMoveLeft() {
		while(car.getX() != 266) {
			car.move(-1, 0);
			pause(10);
		}
	}
	
	//create 1 dash
	private void dash(int x, int y) {
		GRect dash = new GRect(LINE_WIDTH, 18);
		dash.setFilled(true);
		dash.setFillColor(Color.WHITE);
		dash.setLineWidth(0);
		add(dash, x, y);
		
	}
	
	//create 1 single dashed line
	private void dashedLine(int x) {
		for(int i = 0; i < getHeight(); i += 24) {
			dash(x, i);
		}
	}

	//creates 1 single tree
	private void tree(int x, int y) {
		GImage tree = new GImage("tree.png");
		tree.setSize(TREE_SIZE, TREE_SIZE);
		add(tree, x, y);
	}
	
	//creates 1 single flower
	private void flower(int x, int y) {
		GImage flower = new GImage("flower.png");
		flower.setSize(FLOWER_SIZE, FLOWER_SIZE);
		add(flower, x, y);
	}
	
	//creates grass 
	private void grass(int x, int y) {
		GRect grass = new GRect(GRASS_WIDTH,getHeight());
		grass.setFilled(true);
		grass.setFillColor(Color.GREEN);
		grass.setLineWidth(0);
		add(grass, x, y);
	}
	
	//creates the road = asphalt and lines
	private void road() {
		//gray asphalt
		GRect road = new GRect(getWidth() - 2*GRASS_WIDTH, getHeight());
		road.setFilled(true);
		road.setLineWidth(0);
		Color roadColor = new Color(220, 220, 220);
		road.setFillColor(roadColor);
		add(road, GRASS_WIDTH, 0);
		
		//lines next to grass
		fullLine(GRASS_WIDTH);
		fullLine(GRASS_WIDTH + 8);
		fullLine(getWidth() - GRASS_WIDTH);
		fullLine(getWidth() - GRASS_WIDTH - 8);
		
		//middle line
		fullLine(getWidth()/ 2 - LINE_WIDTH/ 2);
		
		for(int x = 148; x <= getWidth() - 122; x += 56) {
			if(x != 372) {
				dashedLine(x);
			}
		}
	}
	
	private void backgroud() {
		//add grass on left and on right
		grass(0, 0);
		grass(getWidth() - GRASS_WIDTH, 0);
		
		//add allay of trees on both sides
		for(int y = 0; y < getHeight(); y += TREE_SIZE) {
			tree(0, y);
			flower(40, y + 25);
		}
		for(int y = 0; y < getHeight(); y += TREE_SIZE) {
			tree(getWidth() - TREE_SIZE, y);
			flower(getWidth() - 90, y + 25);
		}
		
		//adds road
		road();
	}
	
	public void run() {
		backgroud();
		playersCar();
		addMouseListeners();
	}

}