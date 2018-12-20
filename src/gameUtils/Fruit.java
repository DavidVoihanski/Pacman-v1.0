package gameUtils;

import java.util.InvalidPropertiesFormatException;

import Coords.GpsCoord;
/**
 * this class represents a fruit in the game 
 * @author Evgeny & David
 *
 */
public class Fruit {
	// gps info
	private GpsCoord gps;
	// pixel info
	private int x;
	private int y;
	private double points;

	/**
	 * basic fruit constructor
	 * 
	 * @param gps    the gps location of the fruit
	 * @param x      x value in pixels
	 * @param y      y value in pixels
	 * @param points the points value of this fruit
	 */
	public Fruit(GpsCoord gps, int x, int y, double points) {
		this.gps = gps;
		this.x = x;
		this.y = y;
		this.points = points;
	}

	/**
	 * copy constructor
	 * 
	 * @param current a fruit instance we would like to copy
	 */
	public Fruit(Fruit current) {
		try {
			this.gps = new GpsCoord(current.getLocation());
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = current.x;
		this.y = current.y;
		this.points = current.points;
	}

//getters
	
	
	/**
	 * 
	 * @return gps location
	 */
	public GpsCoord getLocation() {
		return gps;
	}
	/**
	 * 
	 * @return x pixel location
	 */
	public int getX() {
		return x;
	}	
	/**
	 * 
	 * @return y pixel location
	 */
	public int getY() {
		return y;
	}
	/**
	 * 
	 * @return points value
	 */
	public double getPoints() {
		return points;
	}
}
