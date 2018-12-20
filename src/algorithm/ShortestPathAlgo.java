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

/**
 * this class represents the algorithm which is responsible for pacman moving
 * 
 * @author Evgeny & David
 *
 */

public abstract class ShortestPathAlgo {

<<<<<<< HEAD
	// this collection represents all the fruits on the screen (i.e in the game)
	private static ArrayList<Fruit> saveFruits = new ArrayList<Fruit>();

	/**
	 * this method dose the actual "work" which is expected from the algorithm,
	 * finding all the paths from the pacman "robots" to the fruits
	 * 
	 * @param game the game on which all the events are happening
	 * @return ArrayList of Paired instances which represents a pacman "robot"
	 *         paired with a fruit
	 * @throws InvalidPropertiesFormatException we create GpsCoords, so in a case
	 *                                          one of the coords dosent represent
	 *                                          real coord values
	 */
=======
	private static ArrayList<Fruit>saveFruits=new ArrayList<Fruit>();

>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
	public static ArrayList<Paired> findPaths(Game game) throws InvalidPropertiesFormatException {
		ArrayList<Paired> pairs = findPairs(game);// using the private method "find pairs" to find all the pairs between
													// pacman "robots" and fruits
		Iterator<Paired> pairIt = pairs.iterator();
		// initializing variabels we'll use in the loop
		Paired pair;
		Fruit fruit;
		Pacman pack;
		// looping through all the pairs
		while (pairIt.hasNext()) {
			pair = pairIt.next();
			fruit = pair.getFruit();
			pack = pair.getPackman();
			// indicating that the pacman starts moving
			pack.setMoving(true);
<<<<<<< HEAD
			// now we'll create a meter vector which represents the meter diff between the
			// pacman and the fruit, normalize it and multiply it by the speed of the pacman
			Point3D vector = pack.getLocation().vector3D(fruit.getLocation().getInternalPoint());
			Point3D normalized = vector.normVec();
			normalized.multVec(pack.getSpeed());
			// now we'll use the private method createPath to create each path for this
			// pacman robot
			createAndAddPath(fruit, pack, pair, normalized);
=======
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
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
		}
		pairIt = pairs.iterator();
		// returning all pacmans to their original location
		while (pairIt.hasNext()) {
			Paired currPair = pairIt.next();
			Pacman currPac = currPair.getPackman();
			currPac.setLocation(currPac.getOriginalLocation());
		}
		game.setFruitCollection(saveFruits);
		return pairs;
	}

	// *****************private methods*****************

	// this method is creating paths between all fruits and pacmans robots
	private static void createAndAddPath(Fruit fruit, Pacman pack, Paired pair, Point3D normalized) {
		Path pathToAdd = new Path();// this is the about to be path - we'll fill it with the coords
		// as long as there is still 3D distance between the fruit and the pacman - keep
		// adding Gps coords to the path
		while (fruit.getLocation().distance3D(pack.getLocation()) > pack.getRange()) {
			GpsCoord addToPath = null;
			if (fruit.getLocation().distance3D(pack.getLocation()) < pack.getSpeed())
				addToPath = fruit.getLocation();
			else {
				Point3D temp = pack.getLocation().add(normalized);
				try {
					addToPath = new GpsCoord(temp);
				} catch (InvalidPropertiesFormatException e) {
					System.out.println("ERR => GpsCoord creating in Algorithm");
					e.printStackTrace();
				}
				// adding the actual Gps coord to the path
				pathToAdd.addPointToPath(addToPath);
			}
			// "moving" the pacman
			pack.setLocation(addToPath);
		}
		// setting some data for later use
		pathToAdd.setFruitAtTheEnd(fruit);
		pair.setPathBetweenPackAndFruit(pathToAdd);
		pack.addPath(pathToAdd);
	}

	// this method is "paring" all the fruits and the pacmans by the closest one
	// time - wise
	private static ArrayList<Paired> findPairs(Game game) {
		ArrayList<Paired> pairs = new ArrayList<Paired>();
		Iterator<Fruit> fruitIt = game.getFruitCollection().iterator();
		while (fruitIt.hasNext()) {
			pairs.add(findSmallestDistance(game));
			fruitIt = game.getFruitCollection().iterator();
		}
		return pairs;
	}

	// this private method finds the closest packman to fruit time speaking not
	// distance
	private static Paired FindClosestPackToFruit(Fruit currFruit, Game game) {
		double smallestTime;
<<<<<<< HEAD
		Iterator<Pacman> packmanIt = game.getPackCollection().iterator();
		// in case the game has no pacmans
		if (!packmanIt.hasNext())
			return null;
		// now starting to look for the closest pacman
		Pacman closest = packmanIt.next();
		// Addressing to the fact that the pacman dose \ doesn't move
		if (!closest.isMoving())
			smallestTime = currFruit.getLocation().distance3D(closest.getLocation()) / closest.getSpeed();
		else {
			smallestTime = currFruit.getLocation().distance3D(closest.getEndTargetLocation()) / closest.getSpeed();
			smallestTime = smallestTime + closest.getTimeToTravel();
		}
		// looping through all the pacmans from the game to determine which is the
		// closest one
		while (packmanIt.hasNext()) {
			Pacman tempPack = packmanIt.next();
			double tempTime;
			if (!tempPack.isMoving())
				tempTime = currFruit.getLocation().distance3D(tempPack.getLocation()) / tempPack.getSpeed();
			else {
				tempTime = currFruit.getLocation().distance3D(tempPack.getEndTargetLocation()) / tempPack.getSpeed();
				tempTime = tempTime + tempPack.getTimeToTravel();
			}
			if (tempTime < smallestTime) {
				smallestTime = tempTime;
				closest = tempPack;
			}
		}
		// as we found we'll pair these two together and retrun it
		Paired pairFound = new Paired(currFruit, closest, smallestTime);
=======
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

>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
		return pairFound;
	}

	// finds the fruit with the smallest distance from a pacman overall
	private static Paired findSmallestDistance(Game game) {
		Iterator<Fruit> fruitIt = game.getFruitCollection().iterator();
		// in case there are no fruits in the game right now
		if (!fruitIt.hasNext())
			return null;
		Fruit bestFruit = fruitIt.next();
		// finding the "best" pair time - wise, using the "FindClosestPackToFruit"
		// method
		Paired bestPair = FindClosestPackToFruit(bestFruit, game);
		// setting the "best" time
		// now checking whether it is the best time or can we find better, if we can -
		// we'll swap the time with the better one
		double smallestTravelTime = bestPair.getTravelTime();
		while (fruitIt.hasNext()) {
			Fruit tempFruit = fruitIt.next();
			Paired tempPair = FindClosestPackToFruit(tempFruit, game);
			double tempTravelTime = tempPair.getTravelTime();
			if (tempTravelTime < smallestTravelTime) {
				bestPair = tempPair;
				smallestTravelTime = tempTravelTime;
				bestFruit = tempFruit;
			}
		}
		// now removing the fruit we "ate"
		game.getFruitCollection().remove(bestFruit);
<<<<<<< HEAD
		// in case there are no more fruits to eat
		if (bestPair.getPackman().getEndTargetLocation() == null) {
			bestPair.getPackman().setEndTargetLocation(bestPair.getPackman().getLocation());
		}
		// setting the "time delta"
		double timeToAdd = bestFruit.getLocation().distance3D(bestPair.getPackman().getEndTargetLocation())
				/ bestPair.getPackman().getSpeed();
		// setting the time travel, end target location and the moving of the pacman 
		bestPair.getPackman().setTimeToTravel(bestPair.getPackman().getTimeToTravel() + timeToAdd);
=======
		if(bestPair.getPackman().getEndTargetLocation()==null) {
			bestPair.getPackman().setEndTargetLocation(bestPair.getPackman().getLocation());
		}
		double timeToAdd=bestFruit.getLocation().distance3D(bestPair.getPackman().getEndTargetLocation())/bestPair.getPackman().getSpeed();
		
		bestPair.getPackman().setTimeToTravel(bestPair.getPackman().getTimeToTravel()+timeToAdd);
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
		bestPair.getPackman().setEndTargetLocation(bestFruit.getLocation());
		bestPair.getPackman().setMoving(true);
		saveFruits.add(bestFruit);
		return bestPair;
	}
}