package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import org.junit.jupiter.api.Test;

import Coords.GpsCoord;
import algorithm.ShortestPathAlgo;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;
import gameUtils.Paired;

class TestAlgo {

	@Test
	void test() throws InvalidPropertiesFormatException {
		//placed 3 pacmen and 3 fruit in front of them 
		//
		//pac1                     fruit1
		//
		//pac2                     fruit2
		//
		//pac3                     fruit3
		
		//pac21 should move to fruit1, 2 to 2 and 3 to 3
		GpsCoord forPac1=new GpsCoord(32.10528,35.20414,0);
		GpsCoord forPac2=new GpsCoord(32.1049,35.20417,0);
		GpsCoord forPac3=new GpsCoord(32.10413,35.20420,0);
		GpsCoord forFruit1=new GpsCoord(32.10535,35.20590,0);
		GpsCoord forFruit2=new GpsCoord(32.10468,35.20593,0);
		GpsCoord forFruit3=new GpsCoord(32.10399,35.20598,0);
		Pacman pac1=new Pacman(forPac1, 0, 0, 1, 1);
		Pacman pac2=new Pacman(forPac2, 0, 0, 1, 1);
		Pacman pac3=new Pacman(forPac3, 0, 0, 1, 1);
		Fruit fruit1=new Fruit(forFruit1, 0, 0, 600);
		Fruit fruit2=new Fruit(forFruit2, 0, 0, 600);
		Fruit fruit3=new Fruit(forFruit3, 0, 0, 600);
		ArrayList<Pacman>packCollection=new ArrayList<>();
		ArrayList<Fruit>fruitCollection=new ArrayList<>();
		packCollection.add(pac1);
		packCollection.add(pac2);
		packCollection.add(pac3);
		fruitCollection.add(fruit1);
		fruitCollection.add(fruit2);
		fruitCollection.add(fruit3);
		Game game=new Game(packCollection, fruitCollection);
		ArrayList<Paired>paired;
		paired=ShortestPathAlgo.findPaths(game);
		
		if(pac1.getPaths().isEmpty()||pac2.getPaths().isEmpty()||pac3.getPaths().isEmpty()) {
			fail("Didn't move everypacman");
		}
		double distance=pac1.getPaths().get(0).getPathLenght();
		if(distance-1>pac1.getPaths().get(0).getPoints().size()) {
			fail("Not enough points in path for pac1");
		}
		distance=pac2.getPaths().get(0).getPathLenght();
		if(distance-1>pac2.getPaths().get(0).getPoints().size()) {
			fail("Not enough points in path for pac2");
		}
		distance=pac3.getPaths().get(0).getPathLenght();
		if(distance-1>pac3.getPaths().get(0).getPoints().size()) {
			fail("Not enough points in path for pac3");
		}
		ArrayList<GpsCoord>points=pac1.getPaths().get(0).getPoints();
		if(points.get(points.size()-1).distance3D(fruit1.getLocation())>1) {
			fail("pac1 didn't reach fruit!");
		}
		points=pac2.getPaths().get(0).getPoints();
		if(points.get(points.size()-1).distance3D(fruit2.getLocation())>1) {
			fail("pac2 didn't reach fruit!");
		}
		points=pac3.getPaths().get(0).getPoints();
		if(points.get(points.size()-1).distance3D(fruit3.getLocation())>1) {
			fail("pac3 didn't reach fruit!");
		}
	}

}
