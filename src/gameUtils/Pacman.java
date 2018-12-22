package gameUtils;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import Coords.GpsCoord;
import GIS.Path;
import Geom.Point3D;

/**
 * this class represents a pacman robot which is able to move in order to eat
 * fruits in the most efficient way
 * 
 * @author Evgeny & David
 *
 */

public class Pacman {
	// gps location info
	private GpsCoord location;
	// pixel location info
	private int x;
	private int y;
	// helpful data
	private double speed;
	private double range;
	private boolean isMoving;
	private GpsCoord currTargetLocation;// targets i.e fruits location
	private GpsCoord endTargetLocation;// "last" targets location
	private GpsCoord originalLocation;// original robots location
	private GpsCoord nextStep;
	private int indexForMove;
	private Path currPath;
	private double score;
	private ArrayList<Fruit> eaten;// fruits the robot ate
	private Fruit lastEaten;// last fruit the robot ate
	private int indexForPaths;
	private ArrayList<Path> paths;// paths this packman has to complete
	private double timeToTravel;// The time it will take for this packman to complete 'paths'

	/**
	 * basic constructor for pacman instance
	 * 
	 * @param gps   GPS location on the map
	 * @param x     x pixel location on the map
	 * @param y     y pixel location on the map
	 * @param speed the speed in which the robot moves - > meters / second
	 * @param range robots eating radius
	 */
	public Pacman(GpsCoord gps, int x, int y, double speed, double range) {
		this.location = gps;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.range = range;
		this.paths = new ArrayList<Path>();
		this.originalLocation = gps;
		this.isMoving = false;
		this.indexForMove = 1;
		this.score = 0;
		this.eaten = new ArrayList<Fruit>();
		this.indexForPaths = 1;
		this.timeToTravel = 0;
	}

	/**
	 * copy constructor
	 * 
	 * @param pack values you wish to copy
	 */
	public Pacman(Pacman pack) {
		this.location = pack.getLocation();
		this.x = pack.getX();
		this.y = pack.getY();
		this.range = pack.getRange();
		this.isMoving = pack.isMoving;
		this.endTargetLocation = pack.getLocation();
		this.paths = pack.getPaths();
		this.timeToTravel = pack.getTimeToTravel();
		this.speed = pack.getSpeed();
		this.originalLocation = pack.originalLocation;
		this.score = pack.score;
		this.indexForMove = pack.indexForMove;
		this.eaten = pack.eaten;
		this.indexForPaths = pack.indexForPaths;
	}

	/**
	 * Adds a path to this pacman robot and updates its timeToTravel and Last target
	 * location
	 * 
	 * @param path The path needs to be added
	 */
	public void addPath(Path givenPath) {
		this.paths.add(givenPath);// adding the path to the path collection
		ArrayList<GpsCoord> givenGPSPoints = givenPath.getPoints();// extracting the path GPS coord collection
		GpsCoord last;
		if (!givenGPSPoints.isEmpty()) {// if the path isn't empty
			try {// update the last target of the robot
				last = new GpsCoord(givenGPSPoints.get(givenGPSPoints.size() - 1));
				this.setEndTargetLocation(last);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.paths.size() == 1) {// in case this robots paths collection contain only 1 path, i.e the added path
										// is its only
			if (!givenGPSPoints.isEmpty())// and given paths do contain GPS coords (just a double check to the fact that
											// the only path is the added one)
				this.nextStep = givenPath.getPoints().get(0);// set the "next" step to be added paths first GPS coord
			else// in case the input-given path is empty
				this.nextStep = this.location;// "next step" is the location of the robot
			currPath = givenPath;
		}
	}

	/**
	 * this method "moves" the pacman robot based on the path it got
	 * 
	 * @return boolean value to indicate whether the pacman got more GPS coord on
	 *         its path
	 */
	public boolean move() {
		this.location = this.nextStep;// updating the location i.e move
		if (this.indexForMove < this.currPath.getPoints().size()) {// in case the pacman has more GPS coord to move to
																	// next -> prepare it for the movement
			this.nextStep = currPath.getPoints().get(this.indexForMove);
			this.indexForMove++;
			return true;
		} else {// in case the pacman has no more GPS coords in it's path -> update the score,
				// add the eaten fruit to the list and update "last eaten fruit"
			this.indexForMove = 0;
			this.score = this.score + this.currPath.getFruitAtTheEnd().getPoints();
			this.eaten.add(this.currPath.getFruitAtTheEnd());
			this.lastEaten = this.currPath.getFruitAtTheEnd();
			if (indexForPaths < this.paths.size()) {// in case there are more paths for this pacman
				this.currPath = paths.get(this.indexForPaths);// set the path as the current one
				this.indexForPaths++;// update the index
			} else {
				this.isMoving = false;
			}
			return false;
		}
	}

	// ************** getters **************

	/**
	 * 
	 * @return time it will take to travel to the next fruit
	 */
	public double getTimeToTravel() {
		return this.timeToTravel;
	}

	/**
	 * 
	 * @return collection of paths this pacman robot holds
	 */
	public ArrayList<Path> getPaths() {
		return this.paths;
	}

	/**
	 * 
	 * @return x pixel location value
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return y pixel location value
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return the speed this pacman robot moves with
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * 
	 * @return eating radius
	 */
	public double getRange() {
		return range;
	}

	/**
	 * 
	 * @return current GPS location of this pacman robot
	 */
	public GpsCoord getLocation() {
		return location;
	}

	/**
	 * 
	 * @return boolean value to indicate whether this pacman robot is moving
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * 
	 * @return the end fruit target GPS location of the pacman robot
	 */
	public GpsCoord getEndTargetLocation() {
		return endTargetLocation;
	}

	/**
	 * 
	 * @return current fruit target GPS location of the pacman robot
	 */
	public GpsCoord getCurrTargetLocation() {
		return currTargetLocation;
	}

	/**
	 * 
	 * @return original location in which the robot started
	 */
	public GpsCoord getOriginalLocation() {
		return originalLocation;
	}

	/**
	 * 
	 * @return the last fruit the robot ate
	 */
	public Fruit getLastEaten() {
		return lastEaten;
	}

	// ************** setters **************

	/**
	 * set x pixel location of the pacman robot
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * set y pixel location of the pacman robot
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * set the speed of the pacman robot
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * set eating radius of the pacman robot
	 * 
	 * @param range
	 */
	public void setRange(double range) {
		this.range = range;
	}

	/**
	 * set GPS location of the pacman robot
	 * 
	 * @param location
	 */
	public void setLocation(GpsCoord location) {
		this.location = location;
	}

	/**
	 * set whether the pacman moves
	 * 
	 * @param isMoving
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	/**
	 * set pacmans ending target location, if pacmans got only 1 path - > this GPS
	 * will be set to the next fruit, if not -> this GPS will be set to the last
	 * fruit of the path
	 * 
	 * @param targetLocation
	 */
	public void setEndTargetLocation(GpsCoord targetLocation) {
		if (this.paths.size() == 1)
			currTargetLocation = targetLocation;
		this.endTargetLocation = targetLocation;
	}

	/**
	 * set pacmans current target location
	 * 
	 * @param currTargetLocation
	 */
	public void setCurrTargetLocation(GpsCoord currTargetLocation) {
		this.currTargetLocation = currTargetLocation;
	}

	/**
	 * set pacmans time to travel to the next fruit target
	 * 
	 * @param timeToTravel
	 */
	public void setTimeToTravel(double timeToTravel) {
		this.timeToTravel = timeToTravel;
	}

	/**
	 * set pacmans last eaten fruit
	 * 
	 * @param lastEaten
	 */
	public void setLastEaten(Fruit lastEaten) {
		this.lastEaten = lastEaten;
	}

}