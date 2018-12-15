package algorithm;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import Geom.Point3D;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Packman;
import gameUtils.Paired;

public class ShortestPathAlgo {
	private Game game;
	public ShortestPathAlgo(Game game) {
		this.game=game;
	}
	public ArrayList<Paired> calcPaths() {
		ArrayList<Paired>pairs=new ArrayList<Paired>();
		Iterator<Fruit>fruitIt=game.getFruitCollection().iterator();
		while(fruitIt.hasNext()) {
			pairs.add(findSmallestDistance());
			fruitIt=game.getFruitCollection().iterator();
		}
		return pairs;
	}

	//private methods
	//finds closest packman to fruit
	private Paired FindClosestPackToFruit(Fruit currFruit) {
		double smallestTime;
		Iterator<Packman>packmanIt=game.getPackCollection().iterator();
		if(!packmanIt.hasNext())return null;
		Packman closest=packmanIt.next();
		if(!closest.isMoving()) 
			smallestTime=currFruit.getGps().distance3D(closest.getLocation())/closest.getSpeed();
		else 
			smallestTime=currFruit.getGps().distance3D(closest.getTargetLocation())/closest.getSpeed();
		while(packmanIt.hasNext()) {
			Packman tempPack=packmanIt.next();
			double tempTime;
			if(!tempPack.isMoving())
				tempTime=currFruit.getGps().distance3D(tempPack.getLocation())/tempPack.getSpeed();
			else
				tempTime=currFruit.getGps().distance3D(tempPack.getTargetLocation())/tempPack.getSpeed();
			if(tempTime<smallestTime) {
				smallestTime=tempTime;
				closest=tempPack;
			}
		}
		Paired pairFound=new Paired(currFruit, closest, smallestTime);
		return pairFound;
	}
	//finds the fruit with the smallest distance from a packman overall
	private Paired findSmallestDistance() {
		Iterator<Fruit>fruitIt=game.getFruitCollection().iterator();
		if(!fruitIt.hasNext())return null;
		Fruit bestFruit=fruitIt.next();
		Paired bestPair=FindClosestPackToFruit(bestFruit);
		double smallestTravelTime=bestPair.getTravelTime();
		while(fruitIt.hasNext()) {
			Fruit tempFruit=fruitIt.next();
			Paired tempPair=FindClosestPackToFruit(tempFruit);
			double tempTravelTime=tempPair.getTravelTime();
			if(tempTravelTime<smallestTravelTime) {
				bestPair=tempPair;
				smallestTravelTime=tempTravelTime;
				bestFruit=tempFruit;
			}
		}
		game.getFruitCollection().remove(bestFruit);
		return bestPair;
	}
}
