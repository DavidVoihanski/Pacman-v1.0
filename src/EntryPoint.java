import java.io.File;

import GIS.GisProject;
import algorithm.MultiCSV;

/**
 * this is the entry point to the project, you can use all the methods from here
 * @author Evgeny&David
 */
public class EntryPoint {
	public static void main(String[] args) {
//the path to the folder in which we save the KML output
		final String pathKML = "C:" + File.separator + "Users" + File.separator + "evgen" + File.separator
				+ "eclipse-workspace" + File.separator + "OopAssignment2-4" + File.separator + "kmlFilesOutPut"
				+ File.separator;
		// the path to the folder in which we save our CSV files
		final String inputCsvFolderPath = "C:" + File.separator + "Users" + File.separator + "evgen" + File.separator
				+ "eclipse-workspace" + File.separator + "OopAssignment2-4" + File.separator + "csvFilesTest";
		// reading the files:
		GisProject contents = MultiCSV.readFolder(inputCsvFolderPath);
		// writing a KML output file to the output folder with chosen file name
		String chooseKmlFileName = "kmlTEST";
		MultiCSV.folder2Kml(contents, pathKML + File.separator + chooseKmlFileName + ".kml");
	}

}
