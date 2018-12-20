package algorithm;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;


import Coords.GpsCoord;
import Geom.Point3D;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;
import gameUtils.Paired;
import GIS.Path;

public abstract class ShortestPathAlgo {

	private static ArrayList<Fruit>saveFruits=new ArrayList<Fruit>();

	public static ArrayList<Paired> findPaths(Game game) throws InvalidPropertiesFormatException {
		ArrayList<Paired>pairs=findPairs(game);
		Iterator<Paired>pairIt=pairs.iterator();
		Paired pair;
		Fruit fruit;
		Pacman pack;
		while(pairIt.hasNext()) {
			pair=pairIt.next();
			fruit=pair.getFruit();
			pack=pair.getPackman();
			pack.setMoving(true);
			Point3D vector=pack.getLocation().vector3D(fruit.getLocation().getInternalPoint());
			Point3D normelized=vector.normVec();
			normelized.multVec(pack.getSpeed());
			Path pathToAdd=new Path();
			while(fruit.getLocation().distance3D(pack.getLocation())>pack.getRange()) {
				GpsCoord addToPath;
				if(fruit.getLocation().distance3D(pack.getLocation())<pack.getSpeed()) 
					addToPath=fruit.getLocation();
				else {
					Point3D temp=pack.getLocation().add(normelized);
					addToPath= new GpsCoord(temp);
					pathToAdd.addPointToPath(addToPath);
				}
				pack.setLocation(addToPath);
			}
			pathToAdd.setFruitAtTheEnd(fruit);
			pair.setPathBetweenPackAndFruit(pathToAdd);
			pack.addPath(pathToAdd);
		}
		pairIt=pairs.iterator();
		//returns all pacmans to their original location
		while(pairIt.hasNext()) {
			Paired currPair=pairIt.next();
			Pacman currPac=currPair.getPackman();
			currPac.setLocation(currPac.getOriginalLocation());
		}
		game.setFruitCollection(saveFruits);
		return pairs;
	}

	//private methods
	private static ArrayList<Paired> findPairs(Game game) {
		ArrayList<Paired>pairs=new ArrayList<Paired>();
		Iterator<Fruit>fruitIt=game.getFruitCollection().iterator();
		while(fruitIt.hasNext()) {
			pairs.add(findSmallestDistance(game));
			fruitIt=game.getFruitCollection().iterator();
		}
		return pairs;
	}
	//finds closest packman to fruit
	private static Paired FindClosestPackToFruit(Fruit currFruit,Game game) {
		double smallestTime;
		Iterator<Pacman>packmanIt=game.getPackCollection().iterator();
		if(!packmanIt.hasNext())return null;
		Pacman closest=packmanIt.next();
		if(!closest.isMoving()) 
			smallestTime=currFruit.getLocation().distance3D(closest.getLocation())/closest.getSpeed();
		else {
			smallestTime=currFruit.getLocation().distance3D(closest.getEndTargetLocation())/closest.getSpeed();
			smallestTime=smallestTime+closest.getTimeToTravel();
		}
		while(packmanIt.hasNext()) {
			Pacman tempPack=packmanIt.next();
			double tempTime;
			if(!tempPack.isMoving())
				tempTime=currFruit.getLocation().distance3D(tempPack.getLocation())/tempPack.getSpeed();
			else {
				tempTime=currFruit.getLocation().distance3D(tempPack.getEndTargetLocation())/tempPack.getSpeed();
				tempTime=tempTime+tempPack.getTimeToTravel();
			}
			if(tempTime<smallestTime) {
				smallestTime=tempTime;
				closest=tempPack;
			}
		}
		Paired pairFound=new Paired(currFruit, closest, smallestTime);

		return pairFound;
	}

	//finds the fruit with the smallest distance from a packman overall
	private static Paired findSmallestDistance(Game game) {
		Iterator<Fruit>fruitIt=game.getFruitCollection().iterator();
		if(!fruitIt.hasNext())return null;
		Fruit bestFruit=fruitIt.next();
		Paired bestPair=FindClosestPackToFruit(bestFruit,game);
		double smallestTravelTime=bestPair.getTravelTime();
		while(fruitIt.hasNext()) {
			Fruit tempFruit=fruitIt.next();
			Paired tempPair=FindClosestPackToFruit(tempFruit,game);
			double tempTravelTime=tempPair.getTravelTime();
			if(tempTravelTime<smallestTravelTime) {
				bestPair=tempPair;
				smallestTravelTime=tempTravelTime;
				bestFruit=tempFruit;
			}
		}
		game.getFruitCollection().remove(bestFruit);
		if(bestPair.getPackman().getEndTargetLocation()==null) {
			bestPair.getPackman().setEndTargetLocation(bestPair.getPackman().getLocation());
		}
		double timeToAdd=bestFruit.getLocation().distance3D(bestPair.getPackman().getEndTargetLocation())/bestPair.getPackman().getSpeed();
		
		bestPair.getPackman().setTimeToTravel(bestPair.getPackman().getTimeToTravel()+timeToAdd);
		bestPair.getPackman().setEndTargetLocation(bestFruit.getLocation());
		bestPair.getPackman().setMoving(true);
		saveFruits.add(bestFruit);
		//bestPair.getPackman().setMoving(true);
		return bestPair;
	}
}
