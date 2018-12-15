package gameUtils;

import java.util.ArrayList;

import Geom.Point3D;

public class Path {
	private ArrayList<Point3D>points;
	
	public Path(ArrayList<Point3D>points) {
		this.points=points;
	}

	public ArrayList<Point3D> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point3D> points) {
		this.points = points;
	}
	
}
