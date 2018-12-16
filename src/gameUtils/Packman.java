package gameUtils;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import Coords.GpsCoord;

public class Packman {
	//gps info
	private GpsCoord location;
	//pixel info
	private int x;
	private int y;
	private double speed;
	private double range;
	private boolean isMoving=false;
	private GpsCoord targetLocation;
	private ArrayList<Path>paths;//paths this packman has to complete
	private double timeToTravel;//The time it will take for this packman to complete 'paths'
	
	public Packman(Packman pack) {
		this.location=pack.getLocation();
		this.x=pack.getX();
		this.y=pack.getY();
		this.range=pack.getRange();
		this.isMoving=pack.isMoving;
		this.targetLocation=pack.getLocation();
		this.paths=pack.getPaths();
		this.timeToTravel=pack.getTimeToTravel();
	}
	public Packman(GpsCoord gps,int x,int y,double speed,double range) {
		this.location=gps;
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.range=range;
		this.paths=new ArrayList<Path>();
	}
	//getters and setters **************
	public double getTimeToTravel() {
		return this.timeToTravel;
	}
	public ArrayList<Path> getPaths(){
		return this.paths;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}

	public GpsCoord getLocation() {
		return location;
	}

	public void setLocation(GpsCoord location) {
		this.location = location;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public GpsCoord getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(GpsCoord targetLocation) {
		this.targetLocation = targetLocation;
	}
	/**
	 * Adds a path to this packman, also updates its timeToTravel and targetLocation
	 * @param path The path to be added
	 */
	public void addPath(Path path) {
		this.paths.add(path);
		ArrayList<GpsCoord>points=path.getPoints();
		double timeToAdd=path.getPathLenght()/this.speed;
		timeToTravel=timeToTravel+timeToAdd;
		GpsCoord last;
		try {
			last = new GpsCoord(points.get(points.size()-1));
			this.targetLocation=last;
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
