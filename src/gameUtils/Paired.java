package gameUtils;

import GIS.Path;

/**
 * this class represents the output of the algorithm which "runs" the game, this
 * is a pacman robot paired to a fruit it's about to eat
 * 
 * @author Evgeny & David
 *
 */
public class Paired {
	private Fruit fruit;// the fruit eaten
	private Pacman packman;// the pacman which eats
	private Path pathBetweenPackAndFruit;// the path between those two
	private double travelTime;// the time it take the pacman to get to the radius of eating the fruit

	/**
	 * basic constructor
	 * 
	 * @param fruit      the fruit eaten
	 * @param packman    the pacman which eats the fruit
	 * @param travelTime the time it will take the pacman to get to the fruit
	 */
	public Paired(Fruit fruit, Pacman packman, double travelTime) {
		this.fruit = fruit;
		this.packman = packman;
		this.travelTime = travelTime;
	}

//***getters***

	/**
	 * 
	 * @return the fruit in this pair
	 */
	public Fruit getFruit() {
		return fruit;
	}

	/**
	 * the pacman in this pair
	 * 
	 * @return
	 */
	public Pacman getPackman() {
		return packman;
	}

	/**
	 * 
	 * @return the time it take the pacman to get to the fruit, based on pacmans
	 *         speed configuration
	 */
	public double getTravelTime() {
		return this.travelTime;
	}

	/**
	 * 
	 * @return the path between the pacman and the fruit
	 */
	public Path getPathBetweenPackAndFruit() {
		return pathBetweenPackAndFruit;
	}

//***setters***

	/**
	 * set the path between the fruit and the pacman
	 * 
	 * @param pathBetweenPackAndFruit the path between pacman robot and the fruit
	 *                                it's about to eat
	 */
	public void setPathBetweenPackAndFruit(Path pathBetweenPackAndFruit) {
		this.pathBetweenPackAndFruit = pathBetweenPackAndFruit;
	}

}
