package GIS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Coords.GpsCoord;

class GisElementTest {
	@Test
	void testGisElementStringArray() {
		// creating a string array which represents a CSV line
		String[] certainCsvLine = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
//using the string array constructor to create the GIS element
		GisElement testElement = new GisElement(certainCsvLine);
//testing whether the constructor works by comparing GPS coords which the element got to the expected one		
		GpsCoord testGPS = (GpsCoord) testElement.getGeom();
		if (testGPS.getLat() != 25.86914313 && testGPS.getLon() != 34.81436692 && testGPS.getAlt() != 32.17225088) {
			fail("wrong GPS values in GIS element - constructor");
		}
	}

	@Test
	void testGisElementGIS_element() {
		//creating an element that will be pointed on by the GIS_element reference
		String[] certainCsvLine = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement = new GisElement(certainCsvLine);
		GIS_element toCopyFrom = testElement;
		//using copy constructor from GIS_element type to create the GisElement 
		GisElement testCopy = new GisElement(toCopyFrom);
		//testing whether the constructor works by comparing GPS coords which the element got to the expected one
		GpsCoord testGPS = (GpsCoord) testCopy.getGeom();
		if (testGPS.getLat() != 25.86914313 && testGPS.getLon() != 34.81436692 && testGPS.getAlt() != 32.17225088) {
			fail("wrong GPS values in GIS element - constructor");
		}
	}

	@Test
	void testGetGeom() {
		//was already tested above
	}

	@Test
	void testGetData() {
		// creating a string array which represents a CSV line
		String[] certainCsvLine = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement = new GisElement(certainCsvLine);
		//testing whether the getData method works by comparing MetaData which the element got to the expected one
		MetaData testData = (MetaData) testElement.getData();
		System.out.println(testData);
		if (!testData.toString().equals(
				"GPS location: 32.17225088, 34.81436692, 25.86914313, meta data: [14:ae:db:58:73:75, love, [WPA2-PSK-CCMP][ESS], 01/12/2017 10:49, 1, -78, 4, WIFI]")) {
			fail("wrong metaData values in GIS element - getMetaData");
		}
	}

}
