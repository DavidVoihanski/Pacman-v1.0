package gameUtils;

import java.util.ArrayList;
import java.util.Iterator;


public class Game {
	private ArrayList<Pacman> packCollection;
	private ArrayList<Fruit> fruitCollection;

	public Game(ArrayList<Pacman> packCollection, ArrayList<Fruit> fruitCollection) {
		this.packCollection = packCollection;
		this.fruitCollection = fruitCollection;
	}

	public ArrayList<Pacman> getPackCollection() {
		return packCollection;
	}

	public void setPackCollection(ArrayList<Pacman> packCollection) {
		this.packCollection = packCollection;
	}

	public ArrayList<Fruit> getFruitCollection() {
		return fruitCollection;
	}

	public void setFruitCollection(ArrayList<Fruit> fruitCollection) {
		this.fruitCollection = fruitCollection;
	}

	public void addPacman(Pacman p) {
		this.packCollection.add(p);
	}

	public void addFruit(Fruit f) {
		this.fruitCollection.add(f);
	}

	public void move() {
		if (packCollection.isEmpty()) {
			System.out.println("No pacmans to move!");
			return;
		}
		Pacman currPac;
		Iterator<Pacman> pacIt = packCollection.iterator();
		while (pacIt.hasNext()) {
			currPac = pacIt.next();
			if (currPac.isMoving()) {
				if (!currPac.move()) {
					fruitCollection.remove(currPac.getLastEaten());
				}
			}
		}
	}

	public Game cloneGame () {
		Game output = new Game( new ArrayList<Pacman>() ,  new ArrayList<Fruit>());
		Iterator<Pacman> itP = this.getPackCollection().iterator();
		while(itP.hasNext()) {
			Pacman current = itP.next();
			Pacman newP = new Pacman(current);
			output.addPacman(newP);
		}
		Iterator<Fruit> itF = this.getFruitCollection().iterator();
		while(itF.hasNext()) {
			Fruit current = itF.next();
			Fruit newF = new Fruit(current);
			output.addFruit(newF);
		}
		return output;
	}
}
