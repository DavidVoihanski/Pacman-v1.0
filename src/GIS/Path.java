package GIS;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.GpsCoord;
import gameUtils.Fruit;

/**
 * this class represents a path of Gps Coords, which the pacman "crossed"
 * 
 * @author Evgeny & David
 *
 */
public class Path {
	private ArrayList<GpsCoord> points;
	private Fruit fruitAtTheEnd;
	private double pathLenght;
	private int speedGetEaten;

	/**
	 * empty constructor
	 */
	public Path() {
		points = new ArrayList<GpsCoord>();
		pathLenght = 0;
		speedGetEaten = 1;
	}

	/**
	 * basic constructor with GpsCoords collection input
	 * 
	 * @param points GpsCoords collection input
	 */
	public Path(ArrayList<GpsCoord> points) {
		this.points = points;
	}

	// static method which used to combine paths (used in Game2KML class)
	/**
	 * combining two paths as one
	 * 
	 * @param arg0 first path to combine
	 * @param arg1 second path to combine
	 * @return first path is both paths are the same one, combined path if not
	 */
	public static Path combinePath(Path arg0, Path arg1) {
		if (!arg0.equals(arg1)) {// checking whether these two aren't the same one
			Iterator<GpsCoord> it = arg1.points.iterator();
			while (it.hasNext()) {
				GpsCoord current = it.next();
				arg0.addPointToPath(current);
			}
			return arg0;// in case the aren't -> combine and return the path
		}
		// if they are -> return the first argument without changing
		return arg0;
	}

	/**
	 * adding single point to the path
	 * 
	 * @param gps the GpsCoord you wish to add
	 */
	public void addPointToPath(GpsCoord gps) {
		points.add(gps);
	}

	// getters
	/**
	 * 
	 * @return the GpsCoord collection
	 */
	public ArrayList<GpsCoord> getPoints() {
		return points;
	}

	/**
	 * 
	 * @return paths length in double
	 */
	public double getPathLenght() {
		pathLenght = this.points.get(0).distance3D(this.points.get(this.points.size() - 1));
		return pathLenght;
	}

	/**
	 * 
	 * @return the fruit which is in the end of a path
	 */
	public Fruit getFruitAtTheEnd() {
		return fruitAtTheEnd;
	}

	/**
	 * 
	 * 
	 * @return speed in which this path is "built" (depends on the pacman)
	 */
	public int getSpeed() {
		return (this.speedGetEaten);
	}

	// setters

	/**
	 * set the GpsCoord collection 
	 * @param points the GpsCoord collection you wish to set
	 */
	public void setPoints(ArrayList<GpsCoord> points) {
		this.points = points;
	}

	/**
	 * set the fruit at the end of the path 
	 * @param fruitAtTheEnd the fruit you wish to set 
	 */
	public void setFruitAtTheEnd(Fruit fruitAtTheEnd) {
		this.fruitAtTheEnd = fruitAtTheEnd;
	}

	/**
	 * set the speed on which the path is built 
	 * @param speed the speed you wish to set
	 */
	public void setSpeed(int speed) {
		this.speedGetEaten = speed;
	}

}
