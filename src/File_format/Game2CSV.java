package File_format;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;

/**
 * this abstract class is used to save games as CSV files
 * 
 * @author Evgeny & David
 *
 */
public abstract class Game2CSV {
	/**
	 * 
	 * @param path        string path to the file you wish to be created
	 * @param certainGame the game you wish to "write" as a CSV file
	 */
	public static void game2CSV(String path, Game certainGame) {
		StringBuilder sb = writeCsvString(certainGame);// creating a string with all the needed data
		FileWriter fw = null;
		try {
			fw = new FileWriter(path + ".csv");//creating the file 
		} catch (IOException e) {
			System.out.println("problem with writing the CSV file");
			e.printStackTrace();
		}
		try {
			fw.write(sb.toString());//writing the previously created string into it
		} catch (IOException e) {
			System.out.println("problem with writing the CSV file");
			e.printStackTrace();
		}
		//"cleaning" the stream
		try {
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}

//private method used to write a game as a configuration CSV file
	private static StringBuilder writeCsvString(Game certainGame) {
		StringBuilder sb = new StringBuilder();
		// first line settings
		sb.append("Type");
		sb.append(',');
		sb.append("id");
		sb.append(',');
		sb.append("Lat");
		sb.append(',');
		sb.append("Lon");
		sb.append(',');
		sb.append("Alt");
		sb.append(',');
		sb.append("Speed/Weight");
		sb.append(',');
		sb.append("Radius");
		sb.append('\n');
//adding all pacman robots
		Iterator<Pacman> PacmanIt = certainGame.getPackCollection().iterator();
		while (PacmanIt.hasNext()) {
			Pacman current = PacmanIt.next();
			sb.append("P");
			sb.append(',');
			sb.append("0");
			sb.append(',');
			sb.append("" + current.getLocation().getLat());
			sb.append(',');
			sb.append("" + current.getLocation().getLon());
			sb.append(',');
			sb.append("" + current.getLocation().getAlt());
			sb.append(',');
			sb.append("" + current.getSpeed());
			sb.append(',');
			sb.append("" + current.getRange());
			sb.append('\n');
		}
//adding all fruits
		Iterator<Fruit> FruitIt = certainGame.getFruitCollection().iterator();
		while (FruitIt.hasNext()) {
			Fruit current = FruitIt.next();
			sb.append("F");
			sb.append(',');
			sb.append("0");
			sb.append(',');
			sb.append("" + current.getLocation().getLat());
			sb.append(',');
			sb.append("" + current.getLocation().getLon());
			sb.append(',');
			sb.append("" + current.getLocation().getAlt());
			sb.append(',');
			sb.append("" + current.getPoints());
			sb.append(',');
			sb.append("");
			sb.append('\n');
		}
		return sb;
	}
}
