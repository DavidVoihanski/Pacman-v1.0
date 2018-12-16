package algorithm;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import com.sun.xml.bind.v2.runtime.reflect.Lister.Pack;

import Coords.GpsCoord;
import Geom.Point3D;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Packman;
import gameUtils.Paired;
import gameUtils.Path;

public abstract class ShortestPathAlgo {

	public static void findPaths(Game game) throws InvalidPropertiesFormatException {
		ArrayList<Paired>pairs=findPairs(game);
		Iterator<Paired>pairIt=pairs.iterator();
		Paired pair;
		Fruit fruit;
		Packman pack;
		while(pairIt.hasNext()) {
			pair=pairIt.next();
			fruit=pair.getFruit();
			pack=pair.getPackman();
			GpsCoord saveStartingLocation=pack.getLocation();
			Point3D vector=pack.getLocation().vector3D(fruit.getGps().getInternalPoint());
			Point3D normelized=vector.normVec();
			normelized.multVec(pack.getSpeed());
			Path pathToAdd=new Path();
			while(fruit.getGps().distance3D(pack.getLocation())>pack.getRange()) {
				GpsCoord addToPath;
				if(fruit.getGps().distance3D(pack.getLocation())<pack.getSpeed()) 
					addToPath=fruit.getGps();
				else {
					Point3D temp=pack.getLocation().add(normelized);
					addToPath= new GpsCoord(temp);
					pathToAdd.addPointToPath(addToPath);
				}
				pack.setLocation(addToPath);
			}
			pack.setLocation(saveStartingLocation);//returns to the starting location
			pack.addPath(pathToAdd);
		}
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
		return bestPair;
	}
}
