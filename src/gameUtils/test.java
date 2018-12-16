package gameUtils;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import Coords.GpsCoord;
import Geom.Point3D;
import algorithm.ShortestPathAlgo;

public class test {

	public static void main(String[] args) {
		ArrayList<Packman>packCollection=new ArrayList<Packman>();
		ArrayList<Fruit>fruitCollection=new ArrayList<Fruit>();
		try {
			//			GpsCoord gps1 = new GpsCoord(32.10538982541669, 35.20835699527703, 0.0);
			//			Packman pack1=new Packman(gps1, 0, 0, 1, 1);
			//			GpsCoord gps2 = new GpsCoord(32.10213326610136, 35.2026284892298, 0.0);
			//			Packman pack2=new Packman(gps2, 0, 0, 1, 1);
			//			
			//			GpsCoord gps3 = new GpsCoord(32.105494877650685, 35.20816821677538, 0.0);
			//			Fruit fruit1 =new Fruit(gps3, 0, 0, 1);
			//			GpsCoord gps4 = new GpsCoord(32.105235749025425, 35.208625642363, 0.0);
			//			Fruit fruit2 =new Fruit(gps4, 0, 0, 1);
			//			GpsCoord gps5 = new GpsCoord(32.1055439020679, 35.2089741588261, 0.0);
			//			Fruit fruit3=new Fruit(gps5, 0, 0, 1);
			//			GpsCoord gps6 = new GpsCoord(32.10501163837494, 35.20832795240206, 0.0);
			//			Fruit fruit4 =new Fruit(gps6, 0, 0, 1);
			//			GpsCoord gps7 = new GpsCoord(32.10564895447915, 35.2083497345573, 0.0);
			//			Fruit fruit5 =new Fruit(gps7, 0, 0, 1);
			//			GpsCoord gps8 = new GpsCoord(32.10524275249212, 35.20887976880296, 0.0);
			//			Fruit fruit6 =new Fruit(gps8, 0, 0, 1);
			//			packCollection.add(pack1);
			//			packCollection.add(pack2);
			//			fruitCollection.add(fruit1);
			//			fruitCollection.add(fruit2);
			//			fruitCollection.add(fruit3);
			//			fruitCollection.add(fruit4);
			//			fruitCollection.add(fruit5);
			//			fruitCollection.add(fruit6);
			//			Game game=new Game(packCollection, fruitCollection);
			//			ShortestPathAlgo algo=new ShortestPathAlgo(game);
			//			ArrayList<Paired>temp=algo.calcPaths();
			//			Iterator<Paired>it=temp.iterator();
			GpsCoord gps_ = new GpsCoord(32.10495, 35.20831, 0.0);
			Fruit fruit_ =new Fruit(gps_, 0, 0, 1);
			GpsCoord gps_1 = new GpsCoord(32.10548, 35.20781, 0.0);
			Packman pack_ =new Packman(gps_1, 0, 0, 5, 1);
			packCollection.add(pack_);
			fruitCollection.add(fruit_);
			Game game=new Game(packCollection, fruitCollection);
			ShortestPathAlgo.findPaths(game);
			System.out.println("");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
