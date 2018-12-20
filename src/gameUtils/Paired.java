package gameUtils;

import GIS.Path;

public class Paired {
	private Fruit fruit;
	private Pacman packman;
	private Path pathBetweenPackAndFruit;
	private double travelTime;
	
	public Paired(Fruit fruit ,Pacman packman,double travelTime) {
		this.fruit=fruit;
		this.packman=packman;
		this.travelTime=travelTime;
	}

	public Fruit getFruit() {
		return fruit;
	}
	public Pacman getPackman() {
		return packman;
	}
	public double getTravelTime() {
		return this.travelTime;
	}

	public Path getPathBetweenPackAndFruit() {
		return pathBetweenPackAndFruit;
	}

	public void setPathBetweenPackAndFruit(Path pathBetweenPackAndFruit) {
		this.pathBetweenPackAndFruit = pathBetweenPackAndFruit;
	}
	
}
