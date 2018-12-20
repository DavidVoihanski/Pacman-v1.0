package gameUtils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class represents a game i.e a collection of pacmans and fruits
 * 
 * @author Evgeny & David
 *
 */
public class Game {
	private ArrayList<Pacman> packCollection;
	private ArrayList<Fruit> fruitCollection;

	/**
	 * basic constructor for game
	 * 
	 * @param packCollection  pacman collection
	 * @param fruitCollection fruit collection
	 */
	public Game(ArrayList<Pacman> packCollection, ArrayList<Fruit> fruitCollection) {
		this.packCollection = packCollection;
		this.fruitCollection = fruitCollection;
	}

	/**
	 * moving the pacmans of the game and deleting the eaten fruits
	 */
	public void move() {
		if (packCollection.isEmpty()) {// if there are no pacmans to move
			System.out.println("No pacmans to move!");
			return;
		}
		Pacman currPac;
		Iterator<Pacman> pacIt = packCollection.iterator();
		while (pacIt.hasNext()) {
			currPac = pacIt.next();
			if (currPac.isMoving()) {
				if (!currPac.move()) {// if the pacman isnt moving any more
					fruitCollection.remove(currPac.getLastEaten());
				}
			}
		}
	}

	/**
	 * adding a pacman to the collection
	 * 
	 * @param p the pacman you wish to add
	 */
	public void addPacman(Pacman p) {
		this.packCollection.add(p);
	}

	/**
	 * adding a fruit to the collection
	 * 
	 * @param f the fruit you wish to add
	 */
	public void addFruit(Fruit f) {
		this.fruitCollection.add(f);
	}

	// getters

	/**
	 * 
	 * @return pacmans collection
	 */
	public ArrayList<Pacman> getPackCollection() {
		return packCollection;
	}

	/**
	 * 
	 * @return fruit collection
	 */
	public ArrayList<Fruit> getFruitCollection() {
		return fruitCollection;
	}
	//setters
	
	/**
	 * setting the fruit collection
	 * @param fruitCollection fruit collection you wish to set
	 */
	public void setFruitCollection(ArrayList<Fruit> fruitCollection) {
		this.fruitCollection = fruitCollection;
	}
	/**
	 * setting the pacman collection
	 * @param packCollection pacman collection you wish to set
	 */
	public void setPackCollection(ArrayList<Pacman> packCollection) {
		this.packCollection = packCollection;
	}

}
