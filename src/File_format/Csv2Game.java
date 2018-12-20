package File_format;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import Coords.GpsCoord;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Map;
import gameUtils.Pacman;

/**
 * this abstract class is used to convert CSV files to "games", meaning - this
 * class "reads" the configuration CSV files and "build" a game out of it
 * 
 * @author Evgeny & David
 *
 */
public abstract class Csv2Game {
	/**
	 * 
	 * @param csvInputPath   the path to the CSV file which we want to "turn" into a
	 *                       game
	 * @param certainGameMap a map which the game we be "played" on
	 * @return a game which is the interpretation of the CSV file
	 */
	public static Game convertCsv2Game(String csvInputPath, Map certainGameMap) {
		ArrayList<String[]> readedCsv = csvReader(csvInputPath);// read the file
		Game output = gameConverter(readedCsv, certainGameMap);// configure a map to be the game based on the CSV file
		return output;// return the game
	}

	// *****************private methods*****************

	// this method used to "turn" the read string CSV file to a game on a map
	private static Game gameConverter(ArrayList<String[]> readedCsvFile, Map gameMap) {
		Iterator<String[]> it = readedCsvFile.iterator();
		Game outputGame = new Game(new ArrayList<Pacman>(), new ArrayList<Fruit>());
		it.next();// skipping first line of CSV file
		while (it.hasNext()) {
			String[] currentLine = it.next();// iterating through all CSV lines
			if (currentLine[0].equals("P")) {// in case the line is "about" a pacman
				GpsCoord pacmansGPS = null;
				try {
					pacmansGPS = new GpsCoord(Double.parseDouble(currentLine[2]), Double.parseDouble(currentLine[3]),
							Double.parseDouble(currentLine[4]));// creating a Gps coord to "hold" its Gps coord values
				} catch (NumberFormatException | InvalidPropertiesFormatException e) {
					System.out.println("wrong GPS coord from CSV file");
					e.printStackTrace();
				}
				Pacman newP = new Pacman(pacmansGPS, (int) gameMap.gpsToPixel(pacmansGPS).x(),
						(int) gameMap.gpsToPixel(pacmansGPS).y(), (Double.parseDouble(currentLine[5])),
						(Double.parseDouble(currentLine[6])));//creating a new pacman
				outputGame.addPacman(newP);//adding it to the game
			} else {//in case the lin is "about" a fruit
				GpsCoord fruitsGPS = null;
				try {
					fruitsGPS = new GpsCoord(Double.parseDouble(currentLine[2]), Double.parseDouble(currentLine[3]),
							Double.parseDouble(currentLine[4]));// creating a Gps coord to "hold" its Gps coord values
				} catch (NumberFormatException | InvalidPropertiesFormatException e) {
					System.out.println("wrong GPS coord from CSV file");
					e.printStackTrace();
				}
				Fruit newF = new Fruit(fruitsGPS, (int) gameMap.gpsToPixel(fruitsGPS).x(),
						(int) gameMap.gpsToPixel(fruitsGPS).y(), Double.parseDouble(currentLine[5]));//creating a new fruit
				outputGame.addFruit(newF);//adding it to the game
			}
		}
		return outputGame;//returning the game ouput
	}

	// this method reads a CSV file from certain path
	private static ArrayList<String[]> csvReader(String filePath) {
		ArrayList<String[]> readedInfoOutPut = new ArrayList<>();// every line of the CSV file is a string array, so we
																	// have an ArrayList of String arrays to hold the
																	// whole file
		BufferedReader br = null;
		// setting the buffered reader
		try {
			br = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String line = "";
		String csvSplitBy = ",";
		// every CSV value is an array element, every array is a CSV line, and the whole
		// file is an arrayList
		try {
			while ((line = br.readLine()) != null) {
				readedInfoOutPut.add(line.split(csvSplitBy));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
		}
		return readedInfoOutPut;
	}

}
