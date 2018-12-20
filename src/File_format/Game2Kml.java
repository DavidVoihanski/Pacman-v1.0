package File_format;

import java.awt.Color;
import java.awt.geom.PathIterator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.sound.midi.Synthesizer;
import javax.swing.JFileChooser;

import Coords.GpsCoord;
import GIS.Path;
import de.micromata.opengis.kml.v_2_2_0.BalloonStyle;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;
import gameUtils.Paired;

public class Game2Kml {
	public static void game2Kml(ArrayList<Paired> pairs,String path) {
		boolean isFirst = true;
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		String startingTime = getCurrentDateTime();
		ArrayList<Pacman> allPacmanColl = getPacmanList(pairs);
		Iterator<Paired> itPairs = pairs.iterator();
		Folder folder = doc.createAndAddFolder().withName("Fruits");
		String dynTimeForFruits = startingTime;
		int pervDeltaTime = 0;
		Fruit lastFruit = null;
		while (itPairs.hasNext()) {
			Paired currentPaired = itPairs.next();
			if (currentPaired.getPackman().getPaths().size() == 1) {
				Fruit current = currentPaired.getFruit();
				int deltaTime = (int) currentPaired.getTravelTime();
				Fruit2Kml(startingTime, deltaTime, current, folder);
				if (!(itPairs.hasNext())) {
					lastFruit = currentPaired.getFruit();
				}
			} else if (currentPaired.getPackman().getPaths().size() != 0) {
				if (isFirst) {
					Fruit current = currentPaired.getFruit();
					int dist = (int) (currentPaired.getPackman().getOriginalLocation().distance3D(current.getLocation())
							- currentPaired.getPackman().getRange());
					int deltaTime = (int) (dist / currentPaired.getPackman().getSpeed());
					pervDeltaTime = deltaTime;
					Fruit2Kml(startingTime, deltaTime, current, folder);
					isFirst = false;
				} else {
					Fruit current = currentPaired.getFruit();
					int deltaTime = (int) currentPaired.getTravelTime() + pervDeltaTime;
					pervDeltaTime = deltaTime;
					Fruit2Kml(startingTime, deltaTime, current, folder);
					dynTimeForFruits = getTimeWithDelta(dynTimeForFruits, deltaTime);
					if (!(itPairs.hasNext())) {
						lastFruit = currentPaired.getFruit();
					}
				}
			}
		}
		ArrayList<Path> allPaths = combineAllPaths(allPacmanColl);
		Iterator<Path> itPath = allPaths.iterator();
		Folder folderPac = doc.createAndAddFolder().withName("Pacman");
		while (itPath.hasNext()) {
			Path current = itPath.next();
			if (!(itPath.hasNext())) {
				current.addPointToPath(lastFruit.getLocation());
			}
			Path2KML.path2KML(current, folderPac, startingTime, current.getSpeed());
		}
		String filePath = path +".kml";
		writeKml(kml, filePath);
		System.out.println("done..");
	}
	// here i will use a method to return all paths, then iterate over them all with
	// iterator and send any of them here->

	// lets assume that we created all paths in this kml file
	// now creating the fruits
	// assuming i know which fruits which pacman ate, sending them here:

	private static void Fruit2Kml(String startingTime, int deltaTime, Fruit current, Folder folder) {
		String eatenTime = getTimeWithDelta(startingTime, deltaTime);
		System.out.println("eaten : " + eatenTime);
		Placemark placeMark = folder.createAndAddPlacemark().withName("Fruit");
		TimeSpan fruitsTimeSpan = placeMark.createAndSetTimeSpan();
		fruitsTimeSpan.setBegin(startingTime + "Z");
		fruitsTimeSpan.setEnd(eatenTime + "Z");
		GpsCoord fruitsGPS = current.getLocation();
		placeMark.createAndSetPoint().addToCoordinates(fruitsGPS.getLon(), fruitsGPS.getLat());
	}


	private static String getCurrentDateTime() {
		Calendar now = Calendar.getInstance();
		String year = "" + now.get(Calendar.YEAR);
		String month = "" + (now.get(Calendar.MONTH) + 1); // Note: zero based!
		String day = "" + now.get(Calendar.DAY_OF_MONTH);
		String hour = "" + now.get(Calendar.HOUR_OF_DAY);
		String minute = "" + now.get(Calendar.MINUTE);
		String second = "" + now.get(Calendar.SECOND);
		String returnedTime = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
		return returnedTime;
	}

	private static void writeKml(Kml kml, String filePath) {
		try {
			kml.marshal(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERR in KML MARSHAL");
			return;
		}
	}

	private static ArrayList<Pacman> getPacmanList(ArrayList<Paired> pairs) {
		ArrayList<Pacman> pacmans = new ArrayList<Pacman>();
		Iterator<Paired> it = pairs.iterator();
		while (it.hasNext()) {
			Pacman current = it.next().getPackman();
			pacmans.add(current);
		}
		return pacmans;
	}

	private static ArrayList<Path> combineAllPaths(ArrayList<Pacman> input) {
		ArrayList<Path> allCombinedPaths = new ArrayList<Path>();
		Iterator<Pacman> pacmanIt = input.iterator();
		Path combinedPaths = new Path();
		while (pacmanIt.hasNext()) {
			combinedPaths = new Path();
			Pacman currentPac = pacmanIt.next();
			if (currentPac.getPaths().size() == 1) {
				combinedPaths = currentPac.getPaths().get(0);
			} else if (currentPac.getPaths().size() != 1) {
				combinedPaths.setSpeed((int) currentPac.getSpeed());
				for (int index = 0; index < currentPac.getPaths().size() - 1; index++) {
					combinedPaths = Path.combinePath(combinedPaths, currentPac.getPaths().get(index));

				}
			}
			allCombinedPaths.add(combinedPaths);
		}
		return allCombinedPaths;

	}

	private static String getTimeWithDelta(String Time, int deltaTime) {
		String temp[] = Time.split("T");
		String temp_time[] = temp[1].split(":");
		String updatedTime = "" + (Integer.parseInt(temp_time[2]) + deltaTime);
		String output = "";
		if (Integer.parseInt(updatedTime) < 60) {// in case updated time is below a minute
			output = temp_time[0] + ":" + temp_time[1] + ":" + updatedTime;
		} else {
			String min = "" + (Integer.parseInt(updatedTime) / 60);// if it is above minute, we'll divide it by 60 (to
																	// normalize it to minutes)
			if ((Integer.parseInt(temp_time[1]) + (int) Integer.parseInt(min)) < 60) {// in case the sum of the "already
																						// there" minutes with the "new"
																						// minutes is below a hour
				temp_time[1] = "" + ((Integer.parseInt(temp_time[1]) + ((int) Integer.parseInt(updatedTime) / 60)));
				output = temp_time[0] + ":" + temp_time[1] + ":" + min;
			} else {
				String hour = "" + (Integer.parseInt(updatedTime) / 60);// if it is above minute, we'll divide it by 60
																		// (to normalize it to hours)
				if ((Integer.parseInt(temp_time[0]) + 1) < 24) {
					String newHour = "" + (Integer.parseInt(temp_time[0]) + 1);
					temp_time[1] = "00";
					output = newHour + ":" + temp_time[1] + ":" + hour;
				} else {
					String newHour = "00";
					temp_time[1] = "00";
					output = newHour + ":" + temp_time[1] + ":" + hour;
				}
			}
		}
		output = checkOutput(output);
		return (temp[0] + "T" + output);
	}

	private static String checkOutput(String input) {
		String splited[] = input.split(":");
		String output = "";
		for (int index = 0; index < splited.length; index++) {
			if (Integer.parseInt(splited[index]) < 10) {
				if (index == 0) {
					output += "0" + splited[index];
				} else {
					output += ":0" + splited[index];
				}
			} else {
				if (index == 0) {
					output += splited[index];
				} else {
					output += ":" + splited[index];
				}
			}
		}
		return output;
	}
}
