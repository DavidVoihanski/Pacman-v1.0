package gameUtils;

import Coords.GpsCoord;

public class Fruit {
	//gps info
	private GpsCoord gps;
	//pixel info
	private int x;
	private int y;
	private double points;
	
	public Fruit(GpsCoord gps,int x,int y,double points) {
		this.gps=gps;
		this.x=x;
		this.y=y;
		this.points=points;
	}
	
	public GpsCoord getGps() {
		return gps;
	}

	public void setGps(GpsCoord gps) {
		this.gps = gps;
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
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	
}
