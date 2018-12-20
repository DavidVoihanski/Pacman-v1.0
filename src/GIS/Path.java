package GIS;

import java.util.ArrayList;

import Coords.GpsCoord;
import gameUtils.Fruit;

public class Path {
	private ArrayList<GpsCoord>points;
	private Fruit fruitAtTheEnd;
	private double pathLenght;
	
	public Path() {
		points=new ArrayList<GpsCoord>();
		pathLenght=0;
	}
	
	public Path(ArrayList<GpsCoord>points) {
		this.points=points;
	}

	public ArrayList<GpsCoord> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<GpsCoord> points) {
		this.points = points;
	}
	public double getPathLenght() {
		pathLenght=this.points.get(0).distance3D(this.points.get(this.points.size()-1));
		return pathLenght;
	}
	public void addPointToPath(GpsCoord gps) {
		points.add(gps);
	}

	public Fruit getFruitAtTheEnd() {
		return fruitAtTheEnd;
	}

	public void setFruitAtTheEnd(Fruit fruitAtTheEnd) {
		this.fruitAtTheEnd = fruitAtTheEnd;
	}
	
}

