package gameUtils;

import java.util.ArrayList;

public class Game {
	private ArrayList<Packman>packCollection;
	private ArrayList<Fruit>fruitCollection;
	
	public Game(ArrayList<Packman>packCollection,ArrayList<Fruit>fruitCollection) {
		this.packCollection=packCollection;
		this.fruitCollection=fruitCollection;
	}

	public ArrayList<Packman> getPackCollection() {
		return packCollection;
	}

	public void setPackCollection(ArrayList<Packman> packCollection) {
		this.packCollection = packCollection;
	}

	public ArrayList<Fruit> getFruitCollection() {
		return fruitCollection;
	}

	public void setFruitCollection(ArrayList<Fruit> fruitCollection) {
		this.fruitCollection = fruitCollection;
	}
	
}
