package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Coords.GpsCoord;
import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.GisElement;
import GIS.GisLayer;
import GIS.MetaData;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

/**
 * this class is used to read CSV files from certain path on the computer, and
 * convert them to GIS layer which is finally converted to KML file
 * 
 * @author Evgeny&David
 *
 */
public abstract class Csv2Kml {
	/**
	 * converting an CSV file which found on the input path, and creating a KML file
	 * with all the information asked on the output path
	 * 
	 * @param csvInputPath        the place where the CSV file is found
	 * @param wantedKmlOutputPath the place where you wish to create the KML file
	 * @throws FileNotFoundException if the file isn't found
	 */
	public static void csv2kml(String csvInputPath, String wantedKmlOutputPath) throws FileNotFoundException {
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		GIS_layer layer = csv2Layer(csvInputPath);
		Folder folder = doc.createAndAddFolder().withName("Gis Layer");
		gisLayer2KML(layer, kml, doc, folder);// turning the GIS layer to KML
		writeKml(kml, wantedKmlOutputPath);
		System.out.println("done...");
	}

//protected methods
	/**
	 * Converts a csv file to a GIS_layer
	 * 
	 * @param csvInputPath file to convert
	 * @return returns a GIS_layer converted from given file
	 */
	protected static GIS_layer csv2Layer(String csvInputPath) {
		ArrayList<String[]> readCsv = csvReader(csvInputPath);// "reading" the csv file
		GIS_layer geoLayer = csv2GisLayer(readCsv);// turning it to GIS layer
		return geoLayer;
	}

	/**
	 * Writes a pre-made kml to a kml file
	 * 
	 * @param kml      Kml type param already containing placemarks
	 * @param filePath wanted save path
	 */
	protected static void writeKml(Kml kml, String filePath) {
		try {
			kml.marshal(new File(filePath));
		} catch (IOException e) {
			System.out.println("ERR in KML MARSHAL");
			return;
		}
	}

//this method converts a GIS layer to KML file
	protected static void gisLayer2KML(GIS_layer certainLayer, Kml kml, Document doc, Folder folder) {
		Iterator<GIS_element> it = certainLayer.iterator();
		StringBuilder descOfElement = new StringBuilder();
		// iterating over all of the layers(amount can be between 1 to inf) we got
		while (it.hasNext()) {
			descOfElement = new StringBuilder();
			GisElement current = (GisElement) it.next();// current element we "stand" on
			MetaData currentData = (MetaData) current.getData();// this elements metaData
			String[] wholeData = currentData.getDataArray();
			// creating a placemark within the folder (each layer is a folder)
			Placemark placeMark = folder.createAndAddPlacemark();
			placeMark.withName(wholeData[1]);// setting elements name
			// creating the description of the placemark using elements string metadata
			descOfElement.append("\n" + "BBSSID: " + wholeData[0] + "\n");
			descOfElement.append("Capabilities: " + wholeData[2] + "\n");
			descOfElement.append("Timestamp: " + currentData.getUTC() + "\n");
			descOfElement.append("Data taken in: " + wholeData[3] + "\n");
			placeMark.setDescription(descOfElement.toString());// setting the description to be shown in the KML file
			// creating setting the timestamp
			String timeStamp = wholeData[3];
			timeStamp = timeStamp.replace(' ', 'T');// converting the time stamp to right figure
			placeMark.createAndSetTimeStamp().withWhen(timeStamp);
			placeMark.createAndSetPoint().addToCoordinates(((GpsCoord) current.getGeom()).getLon(),
					((GpsCoord) current.getGeom()).getLat(), ((GpsCoord) current.getGeom()).getAlt());
		}
	}
	// private supporting methods:

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

//this method converts a CSV file which has been read to GIS layer
	private static GisLayer csv2GisLayer(ArrayList<String[]> csvReadedFile) {
		Iterator<String[]> it = csvReadedFile.iterator();
		// both first lines are irrelevant
		it.next();
		it.next();
		// creating the GIS layer and adding to it all the GIS elements
		GisLayer layerOutput = new GisLayer();
		while (it.hasNext()) {
			String[] currentLine = it.next();
			layerOutput.add(new GisElement(currentLine));
		}
		return layerOutput;
	}

}
