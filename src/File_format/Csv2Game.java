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

public class Csv2Game {
	// public method which we'll use "externally"
	public static Game convertCsv2Game(String csvInputPath, Map certainGameMap) {
		ArrayList<String[]> readedCsv = csvReader(csvInputPath);
		Game output = gameConverter(readedCsv, certainGameMap);
		return output;

	}

	// private supporting methods:
	private static Game gameConverter(ArrayList<String[]> readedCsvFile, Map gameMap) {
		Iterator<String[]> it = readedCsvFile.iterator();
		Game outputGame = new Game(new <Pacman>ArrayList(), new <Fruit>ArrayList());
		it.next();
		while (it.hasNext()) {
			String[] currentLine = it.next();
			if (currentLine[0].equals("P")) {
				GpsCoord pacmansGPS = null;
				try {
					pacmansGPS = new GpsCoord(Double.parseDouble(currentLine[2]), Double.parseDouble(currentLine[3]),
							Double.parseDouble(currentLine[4]));
				} catch (NumberFormatException | InvalidPropertiesFormatException e) {
					System.out.println("wrong GPS coord from CSV file");
					e.printStackTrace();
				}
				Pacman newP = new Pacman(pacmansGPS, (int) gameMap.gpsToPixel(pacmansGPS).x(),
						(int) gameMap.gpsToPixel(pacmansGPS).y(), (Double.parseDouble(currentLine[5])),
						(Double.parseDouble(currentLine[6])));
				outputGame.addPacman(newP);
			} else {
				GpsCoord fruitsGPS = null;
				try {
					fruitsGPS = new GpsCoord(Double.parseDouble(currentLine[2]), Double.parseDouble(currentLine[3]),
							Double.parseDouble(currentLine[4]));
				} catch (NumberFormatException | InvalidPropertiesFormatException e) {
					System.out.println("wrong GPS coord from CSV file");
					e.printStackTrace();
				}
				Fruit newF = new Fruit(fruitsGPS, (int) gameMap.gpsToPixel(fruitsGPS).x(),
						(int) gameMap.gpsToPixel(fruitsGPS).y(), Double.parseDouble(currentLine[5]));
				outputGame.addFruit(newF);
			}
		}
		return outputGame;
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
