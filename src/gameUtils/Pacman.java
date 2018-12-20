package gameUtils;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import Coords.GpsCoord;
import GIS.Path;
import Geom.Point3D;

public class Pacman {
	//gps info
	private GpsCoord location;
	//pixel info
	private int x;
	private int y;
	private double speed;
	private double range;
	private boolean isMoving=false;
	private GpsCoord currTargetLocation;
	private GpsCoord endTargetLocation;
	private GpsCoord originalLocation;
	private GpsCoord nextStep;
	private int indexForMove=1;
	private Path currPath;
	private double score=0;
	private ArrayList<Fruit>eaten=new ArrayList<Fruit>();
	private Fruit lastEaten;
	private int indexForPaths=1;

	private ArrayList<Path>paths;//paths this packman has to complete
	private double timeToTravel=0;//The time it will take for this packman to complete 'paths'

	public Pacman(Pacman pack) {
		this.location=pack.getLocation();
		this.x=pack.getX();
		this.y=pack.getY();
		this.range=pack.getRange();
		this.isMoving=pack.isMoving;
		this.endTargetLocation=pack.getLocation();
		this.paths=pack.getPaths();
		this.timeToTravel=pack.getTimeToTravel();
	}
	public Pacman(GpsCoord gps,int x,int y,double speed,double range) {
		this.location=gps;
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.range=range;
		this.paths=new ArrayList<Path>();
		this.originalLocation=gps;
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

	public GpsCoord getEndTargetLocation() {
		return endTargetLocation;
	}

	public void setEndTargetLocation(GpsCoord targetLocation) {
		if(this.paths.size()==1)currTargetLocation=targetLocation;
		this.endTargetLocation = targetLocation;
	}

	public GpsCoord getCurrTargetLocation() {
		return currTargetLocation;
	}
	public void setCurrTargetLocation(GpsCoord currTargetLocation) {
		this.currTargetLocation = currTargetLocation;
	}

	public GpsCoord getOriginalLocation() {
		return originalLocation;
	}
	public void setOriginalLocation(GpsCoord originalLocation) {
		this.originalLocation = originalLocation;
	}

	public void setTimeToTravel(double timeToTravel) {
		this.timeToTravel = timeToTravel;
	}
	/**
	 * Adds a path to this packman, also updates its timeToTravel and targetLocation
	 * @param path The path to be added
	 */
	public void addPath(Path path) {
		this.paths.add(path);
		ArrayList<GpsCoord>points=path.getPoints();
		GpsCoord last;
		if(!points.isEmpty()) {
			try {
				last = new GpsCoord(points.get(points.size()-1));
				this.setEndTargetLocation(last);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(this.paths.size()==1) {
			if(!points.isEmpty())
				this.nextStep=path.getPoints().get(0);
			else this.nextStep=this.location;
			currPath=path;
		}
	}

	public Fruit getLastEaten() {
		return lastEaten;
	}
	public void setLastEaten(Fruit lastEaten) {
		this.lastEaten = lastEaten;
	}
	public boolean move() {
		this.location=this.nextStep;
		if(this.indexForMove<this.currPath.getPoints().size()) {
			this.nextStep=currPath.getPoints().get(this.indexForMove);
			this.indexForMove++;
			return true;
		}
		else {
			this.indexForMove=0;
			this.score=this.score+this.currPath.getFruitAtTheEnd().getPoints();
			this.eaten.add(this.currPath.getFruitAtTheEnd());
			this.lastEaten=this.currPath.getFruitAtTheEnd();
			if(indexForPaths<this.paths.size()) {
				this.currPath=paths.get(this.indexForPaths);
				this.indexForPaths++;
			}
			else {
				this.isMoving=false;
			}
			return false;
		}
	}
}