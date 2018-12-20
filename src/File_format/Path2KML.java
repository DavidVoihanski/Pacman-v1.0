package File_format;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

import Coords.GpsCoord;
import GIS.Path;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class Path2KML {

	public static void path2KML(Path givenPath, Kml kml, String startingTime) {
		pathCreator(givenPath, kml, startingTime);
	}

	private static void pathCreator(Path givenPath, Document doc, String startingTime) {
		Iterator<GpsCoord> gpsIt = givenPath.getPoints().iterator();
		while (gpsIt.hasNext()) {
				GpsCoord current = gpsIt.next();
				Placemark placeMark = doc.createAndAddPlacemark();
				startingTime=getTimeWithDelta(startingTime,//{this_points_time-dlta}// );
				placeMark.createAndSetTimeSpan().withBegin(startingTime);
				placeMark.createAndSetPoint().addToCoordinates(current.getLat(), current.getLon());
			}
		}

	private String getTimeWithDelta(String Time, int deltaTime) {
		String temp[] = Time.split(":");
		String updatedTime = "" + (Integer.parseInt(temp[2]) + deltaTime);
		String output = "";
		if (Integer.parseInt(updatedTime) < 60) {
			output = temp[0] + ":" + temp[1] + ":" + updatedTime;
		} else {
			String newSec = "" + (Integer.parseInt(updatedTime) - 60);
			if ((Integer.parseInt(temp[1]) + ((int) Integer.parseInt(updatedTime) / 60)) < 60) {
				temp[1] = "" + ((Integer.parseInt(temp[1]) + ((int) Integer.parseInt(updatedTime) / 60)));
				output = temp[0] + ":" + temp[1] + ":" + newSec;
			} else {
				if ((Integer.parseInt(temp[0]) + 1) < 24) {
					String newHour = "" + (Integer.parseInt(temp[0]) + 1);
					temp[1] = "00";
					output = newHour + ":" + temp[1] + ":" + newSec;
				}
				else {
					String newHour = "00";
					temp[1] = "00";
					output = newHour + ":" + temp[1] + ":" + newSec;
				}
			}
		}
		return output;
	}
}
