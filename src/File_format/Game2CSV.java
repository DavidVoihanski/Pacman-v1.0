package File_format;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;

public class Game2CSV {
	public static void game2CSV(String path, Game certainGame) {
		StringBuilder sb = writeCsvString(certainGame);
		FileWriter fw = null;
		try {
			fw = new FileWriter(path + ".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("problem with writing the CSV file");
			e.printStackTrace();
		}
		try {
			fw.write(sb.toString());
		} catch (IOException e) {
			System.out.println("problem with writing the CSV file");
			e.printStackTrace();
		}
		try {
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done");
	}

	private static StringBuilder writeCsvString(Game certainGame) {
		StringBuilder sb = new StringBuilder();
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
