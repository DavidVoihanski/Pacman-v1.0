package GIS;

import static org.junit.jupiter.api.Assertions.*;

import java.util.InvalidPropertiesFormatException;

import org.junit.jupiter.api.Test;

import Coords.GpsCoord;

class MetaDataTest {
//testing the string array constructor
	@Test
	void testMetaDataGpsCoordStringArray() throws InvalidPropertiesFormatException {
		String[] metaData = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78", "4",
				"WIFI" };
		GpsCoord testGps = new GpsCoord(25.86914313, 34.81436692, 32.17225088);
		MetaData testData = new MetaData(testGps, metaData);
		// after creating the meta data instance we check its value by comparing the
		// toString output to the expected output
		if (!testData.toString().equals(
				"GPS location: 25.86914313, 34.81436692, 32.17225088, meta data: [14:ae:db:58:73:75, love, [WPA2-PSK-CCMP][ESS], 01/12/2017 10:49, 1, -78, 4, WIFI]"))
			fail("metaData constructor isnt working correctly");
	}
//testing the get UTC method
	@Test
	void testGetUTC() throws InvalidPropertiesFormatException {
		//creating a meta data instance
		String[] metaData = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78", "4",
				"WIFI" };
		MetaData testData = new MetaData(new GpsCoord(0, 0, 0), metaData);
		//comparing the output to expected output
		if (testData.getUTC() != 1513075740000L)
			fail("getUTC method wont return a corrrect value");
	}

	@Test
	void testToString() {
		// tested above
	}

}
