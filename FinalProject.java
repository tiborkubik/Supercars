import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.Color;
import acm.program.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FinalProject extends GraphicsProgram {

	private RandomGenerator rgen = RandomGenerator.getInstance();
	private static final int TREE_SIZE = 50;
	private static final int FLOWER_SIZE = 50;
	private static final int GRASS_WIDTH = 86;
	private static final int LINE_WIDTH = 6;
	private static final int CAR_WIDTH = 50;
	private static final int CAR_HEIGHT = 60;
	private static final int ROAD_BOUNDARY_LEFT = 100;
	private static final int DELAY = 10;
	private final int ROAD_BOUNDARY_RIGHT = getWidth() - 100;
	private GImage car;
	private GImage enemyCar;
	private ArrayList<GImage> list = new ArrayList<GImage>();

	//
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			carMoveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			carMoveRight();
		}
	}

	// creates 1 single full line
	private void fullLine(int x1) {
		GLine line = new GLine(x1, 0, x1, getHeight());
		line.setColor(Color.WHITE);
		line.setLineWidth(LINE_WIDTH);
		add(line);
	}

	// players car
	private void playersCar() {
		car = new GImage("car.png");
		car.setSize(CAR_WIDTH, CAR_HEIGHT);
		add(car, 324, getHeight() - CAR_HEIGHT);
	}

	// 1 enemy car
	private void enemyCar() {
		int xPadding = rgen.nextInt(0, 9);
		int yPadding = rgen.nextInt(1, 10);
		int x = 100 + xPadding * 56; // 100, 156, 212, 268, 324
		int y = -CAR_HEIGHT - CAR_HEIGHT * yPadding;
		enemyCar = new GImage(carGenerator());
		enemyCar.setSize(CAR_WIDTH, CAR_HEIGHT);
		add(enemyCar, x, y);
		list.add(enemyCar);
	}

	// generator of random car type for incoming cars
	private String carGenerator() {
		int decision = rgen.nextInt(1, 6);
		String fileName = "";
		switch (decision) {
		case 1:
			fileName = "enemyCar1.png";
			break;
		case 2:
			fileName = "enemyCar2.png";
			break;
		case 3:
			fileName = "enemyCar3.png";
			break;
		case 4:
			fileName = "enemyCar4.png";
			break;
		case 5:
			fileName = "enemyCar5.png";
			break;
		case 6:
			fileName = "enemyCar6.png";
			break;
		default:
			break;
		}
		return fileName;
	}

	// move car left
	private void carMoveLeft() {
		double moveBound = car.getX() - 56;
		while (car.getX() != moveBound && car.getX() != ROAD_BOUNDARY_LEFT) {
			car.move(-2, 0);
		}
	}

	// move car right
	private void carMoveRight() {
		double moveBound = car.getX() + 56;
		double bound = getWidth() - 100 - CAR_WIDTH;
		while (car.getX() != moveBound && car.getX() != bound) {
			car.move(2, 0);
		}
	}

	// create 1 dash
	private void dash(int x, int y) {
		GRect dash = new GRect(LINE_WIDTH, 18);
		dash.setFilled(true);
		dash.setFillColor(Color.WHITE);
		dash.setLineWidth(0);
		add(dash, x, y);

	}

	// create 1 single dashed line
	private void dashedLine(int x) {
		for (int i = 0; i < getHeight(); i += 24) {
			dash(x, i);
		}
	}

	// creates 1 single tree
	private void tree(int x, int y) {
		GImage tree = new GImage("tree.png");
		tree.setSize(TREE_SIZE, TREE_SIZE);
		add(tree, x, y);
	}

	// creates 1 single flower
	private void flower(int x, int y) {
		GImage flower = new GImage("flower.png");
		flower.setSize(FLOWER_SIZE, FLOWER_SIZE);
		add(flower, x, y);
	}

	// creates grass
	private void grass(int x, int y) {
		GRect grass = new GRect(GRASS_WIDTH, getHeight());
		grass.setFilled(true);
		grass.setFillColor(Color.GREEN);
		grass.setLineWidth(0);
		add(grass, x, y);
	}

	// creates the road = asphalt and lines
	private void road() {
		// gray asphalt
		GRect road = new GRect(getWidth() - 2 * GRASS_WIDTH, getHeight());
		road.setFilled(true);
		road.setLineWidth(0);
		Color roadColor = new Color(220, 220, 220);
		road.setFillColor(roadColor);
		add(road, GRASS_WIDTH, 0);

		// lines next to grass
		fullLine(GRASS_WIDTH);
		fullLine(GRASS_WIDTH + 8);
		fullLine(getWidth() - GRASS_WIDTH);
		fullLine(getWidth() - GRASS_WIDTH - 8);

		// middle line
		fullLine(374);

		for (int x = 150; x <= getWidth() - 122; x += 56) {
			if (x != 374) {
				dashedLine(x);
			}
		}
	}

	private void backgroud() {
		// add grass on left and on right
		grass(0, 0);
		grass(getWidth() - GRASS_WIDTH, 0);

		// add allay of trees on both sides
		for (int y = 0; y < getHeight(); y += TREE_SIZE) {
			tree(0, y);
			flower(40, y + 25);
		}
		for (int y = 0; y < getHeight(); y += TREE_SIZE) {
			tree(getWidth() - TREE_SIZE, y);
			flower(getWidth() - 90, y + 25);
		}

		// adds road
		road();
	}

	// 15 cars are generated
	private void waveOfCars() {
		for (int atTime = 0; atTime <= 15; atTime++) {
			enemyCar();
		}
	}

	// input values: speed of moving and number of cars generated per wave. They
	// will be different among difficulties
	private void enemyCarsGenerating() {
		double speed = 3;
		GImage enemyCarInList;
		boolean leaveLoop = false;
		while (true) {
			waveOfCars();

			while (true) {
				// adding cars to list and also its removal if necessary
				for (int j = 0; j < list.size(); j++) {
					list.get(j).move(0, speed);
					enemyCarInList = list.get(j);
					if (enemyCarInList.getY() > getHeight()) {
						remove(enemyCarInList);
						list.remove(enemyCarInList);	
					}
					
					GObject collider = GettingObjectOfCollision();
					if (collider.equals(enemyCarInList)) {
						leaveLoop = true;
						break;
					}

					/*
					 * generating new wave of cars when the first generated car from previous wave
					 * is removed new wave is generated ALWAYS after the previous, enemy cars cannot
					 * be stuck together
					 */
					if (list.size() == 14) {
						waveOfCars();
					}
					if (leaveLoop == true) {
						break;
					}

				}
				speed += 0.0001;
				pause(DELAY);
			}
		}
	}

	public GObject GettingObjectOfCollision() {
		GObject object = getElementAt(car.getX(), car.getY());
		if (object != null) {
			return object;
		}
		object = getElementAt(car.getX() + CAR_WIDTH, car.getY());
		if (object != null) {
			return object;
		}
		object = getElementAt(car.getX(), car.getY() + CAR_HEIGHT);
		if (object != null) {
			return object;
		}
		object = getElementAt(car.getX() + CAR_WIDTH, car.getY() + CAR_HEIGHT);
		if (object != null) {
			return object;
		} 
		return null;
	}

	public void run() {
		backgroud();
		playersCar();
		addKeyListeners();
		enemyCarsGenerating();

	}

}