package algorithm;

import java.io.*;
import java.util.Iterator;


import File_format.Csv2Kml;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GisLayer;
import GIS.GisProject;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;


/**
 * This class has 2 purposes: 1: Recursively going through a folder and looking
 * for files ending with ".csv", returning an array list of GIS_layes made from
 * each file.
 * 
 * 2: Taking an array list of GIS_layes and converting them into a single KML
 * file
 * 
 * @author David&Evegeny
 *
 */
public abstract class MultiCSV extends Csv2Kml {
	/**
	 * Scans a directory for csv files and converts them to GIS_layers type object
	 * 
	 * @param directory the directory containing wanted csv files to convert
	 * @return returns an arraylist of layers, each layer is a csv folder
	 */
	public static GisProject readFolder(String inputDirectory) {
		GisProject layers = new GisProject();
		Filter filter = new Filter();
		File folder = new File(inputDirectory);
		File pathNames[] = folder.listFiles(filter);
		for (int i = 0; i < pathNames.length; i++) {
			if(pathNames[i].isDirectory()) {
				//takes the project returned from this call and adds it to 'layers'
				GisProject directoryWithinDirectory=readFolder(pathNames[i].getPath()); 
				layers.addAll(directoryWithinDirectory);
				pathNames[i]=null;	//this path should never be checked again
			}
		}
		int index = 0;
		int size = pathNames.length;
		scan(index, size, layers, pathNames);
		return layers;
	}

	/**
	 * Takes an array list of GIS_layers and converts it to a KML file
	 * 
	 * @param layers     Input array list of GIS_layers
	 * @param outputPath wanted path to save the KML file at
	 */
	public static void folder2Kml(GIS_project layers, String outputPath) {
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		Iterator<GIS_layer> it = layers.iterator();
		GIS_layer currentLayer;
		int counter = 0;
		// iterating all over the GIS layers and creating a folder of placeMarks of
		// every one of them
		while (it.hasNext()) {
			currentLayer = it.next();
			Folder folder = doc.createAndAddFolder().withName("GIS layer num:" + ++counter).withStyleUrl("green");
			gisLayer2KML(currentLayer, kml, doc, folder);
		}
		writeKml(kml, outputPath);
	}

	// recursively going through the files in a folder and turning them to
	// GIS_layers
	private static void scan(int index, int size, GisProject layers, File pathNames[]) {
		if(pathNames[index]!=null) {	
			GisLayer currLayer = (GisLayer) csv2Layer(pathNames[index].getPath());// converts to layer
			layers.add(currLayer);// adds to arraylist
		}
		index++;
		if (index >= size)
			return;
		scan(index, size, layers, pathNames);

	}

}
