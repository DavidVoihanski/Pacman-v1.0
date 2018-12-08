package GIS;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class ProjectMetaDataTest {

	@Test
	void testProjectMetaData() {
		// creating a project metaData which holds the date in which was made
				ProjectMetaData test = new ProjectMetaData();
				Date testDate = new Date();// creating another date type instance to compare between
				if (!test.toString().equals("This project was created in: " + testDate))
					fail("metadata-layer, wasnt created correctly");
	}

	@Test
	void testGetUTC() {
		ProjectMetaData test = new ProjectMetaData();
		// saving the current UTC time in this variable
		long testDate = System.currentTimeMillis();
		// comparing between these two UTC's, test=> from when test was created, and
		// testDate=> from couple of seconds after, this is the reason a diff is allowed
		if (testDate - test.getUTC() > 200000000)
			fail("metadata-layer, hasnt returned right UTC value");
	}

	@Test
	void testToString() {
		//tested above
	}

}
