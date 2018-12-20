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
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class Path2KML {

	public static void path2KML(Path givenPath, Folder fold, String startingTime, double pacmansPace) {
		pathCreator(givenPath, fold, startingTime, pacmansPace);
	}

	private static void pathCreator(Path givenPath,Folder fold, String startingTime, double pacmansPace) {
		System.out.println(givenPath.getPoints().size() - 1);
		for (int index = 0; index < givenPath.getPoints().size() - 1; index++) {
			GpsCoord current = givenPath.getPoints().get(index);
			Placemark placeMark = fold.createAndAddPlacemark();
			int deltaTime = (int) pacmansPace;
			startingTime = getTimeWithDelta(startingTime, (int) deltaTime);
			placeMark.createAndSetTimeSpan().withBegin(startingTime + "Z");
			placeMark.createAndSetPoint().addToCoordinates(current.getLon(), current.getLat());
		}
	}

	private static String getTimeWithDelta(String Time, int deltaTime) {
		String temp[] = Time.split("T");
		String temp_time[] = temp[1].split(":");
		String updatedTime = "" + (Integer.parseInt(temp_time[2]) + deltaTime);
		String output = "";
		if (Integer.parseInt(updatedTime) < 60) {
			output = temp_time[0] + ":" + temp_time[1] + ":" + updatedTime;
		} else {
			String newSec = "" + (Integer.parseInt(updatedTime) - 60);
			if ((Integer.parseInt(temp_time[1]) + ((int) Integer.parseInt(updatedTime) / 60)) < 60) {
				temp_time[1] = "" + ((Integer.parseInt(temp_time[1]) + ((int) Integer.parseInt(updatedTime) / 60)));
				output = temp_time[0] + ":" + temp_time[1] + ":" + newSec;
			} else {
				if ((Integer.parseInt(temp_time[0]) + 1) < 24) {
					String newHour = "" + (Integer.parseInt(temp_time[0]) + 1);
					temp_time[1] = "00";
					output = newHour + ":" + temp_time[1] + ":" + newSec;
				} else {
					String newHour = "00";
					temp_time[1] = "00";
					output = newHour + ":" + temp_time[1] + ":" + newSec;
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
			if(Integer.parseInt(splited[index])<10) {
				if(index==0) {
					output+="0"+splited[index];
				}
				else {
					output+=":0"+splited[index];
				}
			}
			else {
				if(index==0) {
					output+=splited[index];
				}
				else {
					output+=":"+splited[index];
				}
			}
		}
		return output; 
	}
}
