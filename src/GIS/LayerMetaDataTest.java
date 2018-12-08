package GIS;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class LayerMetaDataTest {

	@Test
	void testLayerMetaData() {
		// creating a Layer metaData which holds the date in which was made
		LayerMetaData test = new LayerMetaData();
		Date testDate = new Date();// creating another date type instance to compare between
		if (!test.toString().equals("This layer was created in: " + testDate))
			fail("metadata-layer, wasnt created correctly");
	}

	@Test
	void testGetUTC() {
		LayerMetaData test = new LayerMetaData();
		// saving the current UTC time in this variable
		long testDate = System.currentTimeMillis();
		// comparing between these two UTC's, test=> from when test was created, and
		// testDate=> from couple of seconds after, this is the reason a diff is allowed
		if (testDate - test.getUTC() > 200000000)
			fail("metadata-layer, hasnt returned right UTC value");
	}

	@Test
	void testToString() {
		// tested above
	}

}
