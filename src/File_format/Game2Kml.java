package File_format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Pacman;
import gameUtils.Paired;

public class Game2Kml {
	public static void game2Kml (//an object yet built//ArrayList<Solutions> solutionOfGame) {
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		String startingTime = getCurrentDateTime();
		// here i will use a method to return all paths, then iterate over them all with
		// iterator and send any of them here->
		Path2KML.path2KML(givenPath, doc, startingTime);
       //lets assume that we created all paths in this kml file
		//now creating the fruits
		//assuming i know which fruits which pacman ate, sending them here:
		
	}

	private static void createFruitKml(ArrayList<Fruit> flist, Document doc, Pacman whoAte, String whenCreated) {
		Iterator<Fruit> fruitIt = flist.iterator();
		while (fruitIt.hasNext()) {
			Fruit current = fruitIt.next();
			Placemark placeMark = doc.createAndAddPlacemark();
			double dist = whoAte.getLocation().distance3D(current.getLocation()) - (whoAte.getRange());
			double endingDeltaTimeSeconds = (double) dist / whoAte.getSpeed();
			String temp[] = whenCreated.split(":");
			double endingTime;
			if (endingDeltaTimeSeconds < 60) {
				endingTime = endingDeltaTimeSeconds + Double.parseDouble(temp[2]);
				if (endingTime > 60) {
					temp[2] = "00";
					temp[1] = "" + ((endingTime - 60) + Double.parseDouble(temp[1]));
				}
			} else if (endingDeltaTimeSeconds < 3600) {
				endingDeltaTimeSeconds = endingDeltaTimeSeconds / 60;
				endingTime = endingDeltaTimeSeconds + Double.parseDouble(temp[1]);
				if (endingTime > 60) {
					temp[1] = "00";
					temp[0] = "" + ((endingTime - 60) + Double.parseDouble(temp[0]));
				}
			} else {
				endingDeltaTimeSeconds = endingDeltaTimeSeconds / (60 * 60);
				endingTime = endingDeltaTimeSeconds + Double.parseDouble(temp[0]);
				if (endingTime > 23) {
					temp[0] = "" + ((endingTime - 23) + Double.parseDouble(temp[0]));
				}
			}
			String endTimeString = temp[0] + ":" + temp[1] + ":" + temp[2];
			placeMark.createAndSetTimeSpan().setBegin(whenCreated);
			placeMark.createAndSetTimeSpan().setEnd(endTimeString);
			placeMark.createAndSetPoint().addToCoordinates(current.getLocation().getLat(),
					current.getLocation().getLon());
		}
	}

	private static String getCurrentDateTime() {
		Calendar now = Calendar.getInstance();
		String year = "" + now.get(Calendar.YEAR);
		String month = "" + now.get(Calendar.MONTH) + 1; // Note: zero based!
		String day = "" + now.get(Calendar.DAY_OF_MONTH);
		String hour = "" + now.get(Calendar.HOUR_OF_DAY);
		String minute = "" + now.get(Calendar.MINUTE);
		String second = "" + now.get(Calendar.SECOND);
		String returnedTime = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
		return returnedTime;
	}
}
