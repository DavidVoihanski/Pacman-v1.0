package File_format;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import Coords.GpsCoord;
import GIS.Path;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import gameUtils.Fruit;
import gameUtils.Pacman;
import gameUtils.Paired;

/**
 * this class is an abstract class which used to convert a game to a KML file
 * which can be presented in google earth
 * 
 * @author Evgeny & David
 *
 */
public abstract class Game2Kml {
	/**
	 * this is the public "external - use" method which called with "solved" game
	 * and creates a KML file
	 * 
	 * @param pairs collection of all the pairs the algorithm returned
	 * @param path  the string path to the KML file
	 */
	public static void game2Kml(ArrayList<Paired> pairs, String path) {
		// creating and setting all the KML - jar library features
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		Folder folder = doc.createAndAddFolder().withName("Fruits");
		Folder folderPac = doc.createAndAddFolder().withName("Pacman");
		String startingTime = getCurrentDateTime();// setting the current time for the time stamp/span in the KML file
		ArrayList<Pacman> allPacmanColl = getPacmanList(pairs);// getting all the pacmans of the game
		Iterator<Paired> itPairs = pairs.iterator();
		Fruit lastFruit = fruitAdder(startingTime, itPairs, folder);// adding all the fruits to the KML file
		ArrayList<Path> allPaths = combineAllPaths(allPacmanColl);// combining the paths of the same pacman if there are
																	// some
		Iterator<Path> itPath = allPaths.iterator();
		pathAdder(itPath, folderPac, startingTime, lastFruit);// adding all the paths to the KML
		writeKml(path, kml);// creating the KML
	}

	// *****************private methods*****************

//###### path related ######:

//private method which uses the "pathCreator" method
	private static void path2KML(Path givenPath, Folder fold, String startingTime, double pacmansPace) {
		pathCreator(givenPath, fold, startingTime, pacmansPace);
	}

// the method which converts a path to a set of GPS coord that google earth can receive (i.e place mark)
	private static void pathCreator(Path givenPath, Folder fold, String startingTime, double pacmansPace) {
//going through all the GpsCoords in a given path
		for (int index = 0; index < givenPath.getPoints().size(); index++) {
			GpsCoord current = givenPath.getPoints().get(index);// building a Gps coord
			Placemark placeMark = fold.createAndAddPlacemark();// adding another placemark in the KML paths folder
			int deltaTime = (int) pacmansPace;
			placeMark.createAndSetTimeSpan().withBegin(startingTime + "Z");
			startingTime = getTimeWithDelta(startingTime, (int) deltaTime);// creating the time Span based on pacmans
																			// haste
			placeMark.createAndSetPoint().addToCoordinates(current.getLon(), current.getLat());// adding the gps coord
																								// to the place mark
		}
	}

	// adding a collection of paths to the KML file
	private static void pathAdder(Iterator<Path> itPath, Folder folderPac, String startingTime, Fruit lastFruit) {
		while (itPath.hasNext()) {// going through all the paths in a collection

			Path current = itPath.next();
			if (!(itPath.hasNext())) {// adding the fruit location as a last point - this way we'll know the fruit
										// been disappear in the KML
				current.addPointToPath(lastFruit.getLocation());
			}
			path2KML(current, folderPac, startingTime, current.getSpeed());// converting any of the paths to placemarks
		}

	}

//this method combines paths that belong to the same pacman robot, this way we can be positive that we wont get a splited paths by the same pacman in the KML
	private static ArrayList<Path> combineAllPaths(ArrayList<Pacman> input) {
		ArrayList<Path> allCombinedPaths = new ArrayList<Path>();// we'll added all the paths to here
		Iterator<Pacman> pacmanIt = input.iterator();
		Path combinedPaths = new Path();
		while (pacmanIt.hasNext()) {// iterating through all the pacmans we got
			combinedPaths = new Path();
			Pacman currentPac = pacmanIt.next();
			if (currentPac.getPaths().size() == 1) {// in case the pacman got only one path - just keep it
				combinedPaths = currentPac.getPaths().get(0);
			} else if (currentPac.getPaths().size() != 0) {// in case the pacman got more than one path - combine
				combinedPaths.setSpeed((int) currentPac.getSpeed());
				for (int index = 0; index < currentPac.getPaths().size() - 1; index++) {
					combinedPaths = Path.combinePath(combinedPaths, currentPac.getPaths().get(index));

				}
			}
			allCombinedPaths.add(combinedPaths);
		}
		return allCombinedPaths;

	}
	// ###### fruit related ######:

	// this method gets all the pairs we got and extracts all the fruits out of it,
	// to write them in the KML
	private static Fruit fruitAdder(String startingTime, Iterator<Paired> itPairs, Folder folder) {
		String dynTimeForFruits = startingTime;
		boolean isFirst = true;
		int pervDeltaTime = 0;
		Fruit lastFruit = null;
		while (itPairs.hasNext()) {// iterating through all the paris we got
			Paired currentPaired = itPairs.next();
			if (currentPaired.getPackman().getPaths().size() == 1) {// in case there is only one pacman moving to the
																	// fruit, we wont need to calculate all the "time
																	// delta's" to it - because there is only one
				Fruit current = currentPaired.getFruit();
				int deltaTime = (int) currentPaired.getTravelTime();
				Fruit2Kml(startingTime, deltaTime, current, folder);
				if (!(itPairs.hasNext())) {
					lastFruit = currentPaired.getFruit();
				}
			} else if (currentPaired.getPackman().getPaths().size() != 0) {// in case there are more than one pacman, we
																			// have to keep track of all these "time
																			// delta's"
				if (isFirst) {// in case the fruit is the first one in the path
					Fruit current = currentPaired.getFruit();
					int dist = (int) (currentPaired.getPackman().getOriginalLocation().distance3D(current.getLocation())
							- currentPaired.getPackman().getRange());
					int deltaTime = (int) (dist / currentPaired.getPackman().getSpeed());
					pervDeltaTime = deltaTime;
					Fruit2Kml(startingTime, deltaTime, current, folder);
					isFirst = false;
				} else {// in case thefruit isn't the first, we have to sum all deltas to keep a right
						// time span
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
		return lastFruit;// returning the last fruit for the path adder method
	}

//simple private method to convert a single fruit to a KML placemark 
	private static void Fruit2Kml(String startingTime, int deltaTime, Fruit current, Folder folder) {
		String eatenTime = getTimeWithDelta(startingTime, deltaTime);// setting the time the fruit was eaten
		Placemark placeMark = folder.createAndAddPlacemark().withName("Fruit");// this way we'll easily see that this
																				// placemark is a fruit in google earth
		// creating and setting the time span
		TimeSpan fruitsTimeSpan = placeMark.createAndSetTimeSpan();
		fruitsTimeSpan.setBegin(startingTime + "Z");
		fruitsTimeSpan.setEnd(eatenTime + "Z");
		GpsCoord fruitsGPS = current.getLocation();
		placeMark.createAndSetPoint().addToCoordinates(fruitsGPS.getLon(), fruitsGPS.getLat());
	}
	// ###### pacman related ######:

	// this method is returning a pacman array list out of a "paired" collection
	private static ArrayList<Pacman> getPacmanList(ArrayList<Paired> pairs) {
		ArrayList<Pacman> pacmans = new ArrayList<Pacman>();
		Iterator<Paired> it = pairs.iterator();
		while (it.hasNext()) {
			Pacman current = it.next().getPackman();
			pacmans.add(current);
		}
		return pacmans;
	}

	// ###### KML related ######:

	// method to write KML using JAK library
	private static void writeKml(String filePath, Kml kml) {
		try {
			kml.marshal(new File(filePath+".kml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERR in KML MARSHAL");
			return;
		}
	}

	// ###### date&time related ######:

	// this method return the time and sate right now presented to fit the KML
	// standard
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

	// method adding an integer time delta to a string time represented in the
	// google KML standard
	private static String getTimeWithDelta(String Time, int deltaTime) {
		String temp[] = Time.split("T");// splitting the T
		String temp_time[] = temp[1].split(":");// splitting the ":", ==>>> temp_time[2]-> seconds,
												// temp_time[1]->minutes,temp_time[0]->hours
		String updatedTime = "" + (Integer.parseInt(temp_time[2]) + deltaTime);// getting the sum of the time delta and
																				// the seconds
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
					// adding "00" in place we need
					temp_time[1] = "00";
					output = newHour + ":" + temp_time[1] + ":" + hour;
				} else {
					String newHour = "00";
					temp_time[1] = "00";
					output = newHour + ":" + temp_time[1] + ":" + hour;
				}
			}
		}
		output = checkOutput(output);// checking whether there are zero in every place there should be
		return (temp[0] + "T" + output);
	}

	// method which checks whether every part of the string got zeros in the right
	// place, to avoid situations like "3:30:25" for example, which should be
	// "03:30:25" in order to fit in the KML
	private static String checkOutput(String input) {
		String splited[] = input.split(":");
		String output = "";
		for (int index = 0; index < splited.length; index++) {// for all the parts of the split string
			if (Integer.parseInt(splited[index]) < 10) {// in case on of the parts is less than 10, we need to add the
														// zero
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
