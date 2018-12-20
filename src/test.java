import java.io.IOException;
import java.util.ArrayList;
import Coords.GpsCoord;
import GUI.MyFrame;
import algorithm.ShortestPathAlgo;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;
import gameUtils.Paired;

public class test {

	public static void main(String[] args) throws IOException, InterruptedException {
		MyFrame f=new MyFrame();
		Game game=f.getGame();
		GpsCoord gps1=new GpsCoord(32.10530,35.20832,0);
		Pacman pack1=new Pacman(gps1, 0, 0, 1, 1);
		GpsCoord gps2=new GpsCoord(32.10504,35.20764,0);
		Fruit frui1=new Fruit(gps2, 0, 0, 800);
		GpsCoord gps3=new GpsCoord(32.10485,35.20835,0);
		Fruit fruit2=new Fruit(gps3, 0, 0, 800);
		//game.addFruit(frui1);
		game.addPacman(pack1);
		game.addFruit(fruit2);
		ArrayList<Paired>pairs=ShortestPathAlgo.findPaths(game);
		
		//f.showGame(game);
		f.start();
		//game.start();
	}
	
}
