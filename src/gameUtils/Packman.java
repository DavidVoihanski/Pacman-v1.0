package gameUtils;

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
	//getters and setters 
	public Packman(GpsCoord gps,int x,int y,double speed,double range) {
		this.location=gps;
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.range=range;
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
	
}
