package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import Coords.GpsCoord;
import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.GisElement;
import GIS.GisLayer;
import GIS.GisProject;

//by testing this class we test the KML2CSV class too, as all of these methods use CSV2KML's methods
class MultiCSVTest {
	// this is the paths we about to use in the tests
	private static final String inputPath = "C:" + File.separator + "Users" + File.separator + "evgen" + File.separator
			+ "eclipse-workspace" + File.separator + "Assignment2" + File.separator + "csvFilesTest";
	final String outputPathKML = "C:" + File.separator + "Users" + File.separator + "evgen" + File.separator
			+ "eclipse-workspace" + File.separator + "Assignment2" + File.separator + "kmlFilesOutPut";

	@Test
	void testReadFolder() {
		// creating a project from the tester CSV files
		GisProject testProject = MultiCSV.readFolder(inputPath);
		Iterator<GIS_layer> it = testProject.iterator();
		int counter;
		assertTrue(testProject.size() == 2);// asserting that the method did read 2 files
		while (it.hasNext()) {
			GisLayer current = (GisLayer) it.next();
			if (current.size() != 159 && current.size() != 185) {// asserting we got the right amount of elements in
																	// each read file
				fail("not enough csv lines scanned");
			}
			// we planted a unique GPS coord in the 122th line of both CSV files, now we're
			// asserting that this GPS coord was read correctly
			counter = 0;
			Iterator<GIS_element> itLayer = current.iterator();
			while (itLayer.hasNext()) {
				counter++;
				GIS_element currentElement = (GisElement) itLayer.next();
				if (counter == 120) {
					GpsCoord testGPS = (GpsCoord) currentElement.getGeom();
					System.out.println(testGPS);
					if (testGPS.getLat() != 32.17112265 && testGPS.getLon() != 34.8133483
							&& testGPS.getAlt() != 30.37432785) {
						fail("wrong values in certain csv element");
					}
				}
			}
		}
	}

	@Test
	void testFolder2Kml() {
		// creating a GIS project as we tested earlier
		GisProject testProject = MultiCSV.readFolder(inputPath);
		// converting the project to a file in the output path
		MultiCSV.folder2Kml(testProject, outputPathKML + File.separator + "kmlTESTER.kml");
		// reading the file, this way we know it is indeed was created
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(outputPathKML + File.separator + "kmlTESTER.kml");
			br = new BufferedReader(fr);
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
			}
		} catch (IOException e) {
			// if there was an ERROR in reading the file => the file wasn't created
			// correctly
			fail("the file wasnt created - folder 2 kml");
		}
	}
}
