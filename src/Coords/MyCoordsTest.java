package Coords;

import static org.junit.jupiter.api.Assertions.*;

import java.util.InvalidPropertiesFormatException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Geom.Point3D;

class MyCoordsTest {
	private static MyCoords tester;

	/**
	 * initializing the MyCoord instance for all tests
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tester = new MyCoords();
	}

	/**
	 * testing the add method,this test is performed on GpsCoord instance as they
	 * contain a Point3D inside of them, so this way we'll test both classes in one
	 * test
	 */
	@Test
	void testAdd() {
		GpsCoord gpsCoord = null;
		GpsCoord expected = null;
		GpsCoord output = null;
		try {
			gpsCoord = new GpsCoord(32.103315, 35.209039, 670);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			fail("gps coord constructor failed");
		}
		Point3D meterVector = new Point3D(337.6989921, -359.2492069, -20);// based on excel file for these two points
		try {
			expected = new GpsCoord(32.106352, 35.205225, 650);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			fail("gps coord constructor failed");
		}
		try {
			output = new GpsCoord(gpsCoord.add(meterVector));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			fail("gps coord constructor failed");
		}
		// checking whether the output is "close enough" to the expected,
		// we allow 1% deviation
		if ((output.getLat() > expected.getLat() + (expected.getLat() * 0.01)
				|| (output.getLat() < expected.getLat() - (expected.getLat() * 0.01))
				|| (output.getLon() > expected.getLon() + (expected.getLon() * 0.01)
						|| (output.getLon() < expected.getLon() - (expected.getLon() * 0.01))
						|| ((output.getAlt() > expected.getAlt() + (expected.getAlt() * 0.01)
								|| (output.getAlt() < expected.getAlt() - (expected.getAlt() * 0.01))))))) {
			fail("add method failed #1");
		}
		try {
			gpsCoord = new GpsCoord(82.103315, 65.209039, 670);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			fail("gps coord constructor failed");
		}
		meterVector = new Point3D(337.6989921,-58.26557696, 70);// based on the excel file with these two points
		try {
			expected = new GpsCoord(82.106352, 65.205225, 740);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			fail("gps coord constructor failed");
		}
		try {
			output = new GpsCoord(gpsCoord.add(meterVector));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			fail("gps coord constructor failed");
		}
		// checking whether the output is "close enough" to the expected,
		// we allow 1% deviation
		if ((output.getLat() > expected.getLat() + (expected.getLat() * 0.01)
				|| (output.getLat() < expected.getLat() - (expected.getLat() * 0.01))
				|| (output.getLon() > expected.getLon() + (expected.getLon() * 0.01)
						|| (output.getLon() < expected.getLon() - (expected.getLon() * 0.01))
						|| ((output.getAlt() > expected.getAlt() + (expected.getAlt() * 0.01)
								|| (output.getAlt() < expected.getAlt() - (expected.getAlt() * 0.01))))))) {
			fail("add method failed #2");
		}
	}

	/**
	 * testing the distance3d method, notice that 3d distance is calculated
	 * similarly to the 2d distance except the altitude diff, so this test is
	 * similar to the 2d, with the altitude diff combined
	 */
	@Test
	void testDistance3d() {
		double dev; // allowed deviation will be 1% of the result we're supposed to get
		double expected;
		double diff3D;
		// test close points
		GpsCoord gps1 = null;
		try {
			gps1 = new GpsCoord(32.063607, 34.835309, 670);
		} catch (InvalidPropertiesFormatException e1) {
			fail("gps coord constructor failed");
		}
		GpsCoord gps2 = null;
		try {
			gps2 = new GpsCoord(32.062896, 34.840948, 7000);
		} catch (InvalidPropertiesFormatException e1) {
			fail("gps coord constructor failed");
		}
		double dist = gps1.distance3D(gps2); // should be 538+- according to google earth
		// adding the alt diff to the 2d expected distance
		diff3D = Math.pow(gps2.getAlt() - gps1.getAlt(), 2);
		expected = 538.0;// expected 2d distance based on "google earth"
		expected = Math.pow(expected, 2);
		expected += diff3D;
		expected = Math.sqrt(expected);
		dev = expected * 0.01;
		// checking that the distance is indeed "close enough" as the deviation we allow
		// is 1%
		if (dist > dev + expected || dist < expected - dev)
			fail("Distance function failed#1");
		// test far points
		try {
			gps1 = new GpsCoord(32.063607, 34.835309, 670);
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		try {
			gps2 = new GpsCoord(31.990175, 34.800077, -345);
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		dist = gps1.distance3D(gps2);
		// adding the alt diff to the 2d expected distance
		diff3D = Math.pow(gps2.getAlt() - gps1.getAlt(), 2);
		expected = 8796.0;// expected 2d distance based on "google earth"
		expected = Math.pow(expected, 2);
		expected += diff3D;
		expected = Math.sqrt(expected);
		dev = expected * 0.01;
		// checking that the distance is indeed "close enough" as the deviation we allow
		// is 1%
		if (dist > dev + expected || dist < expected - dev)
			fail("Distance function failed#2");
	}

	/**
	 * testing distance2d method, this test is performed on GpsCoord instance as
	 * they contain a Point3D inside of them, so this way we'll test both classes in
	 * one test
	 */
	@Test
	void testDistance2d() {
		double dev; // allowed deviation will be 1% of the result we're supposed to get
		double expected;
		// test close points
		GpsCoord gps1 = null;
		try {
			gps1 = new GpsCoord(32.063607, 34.835309, 670);
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		GpsCoord gps2 = null;
		try {
			gps2 = new GpsCoord(32.062896, 34.840948, 7000);
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		double dist = gps1.distance2D(gps2); // should be 538+- according to google earth
		expected = 538.0;// expected 2d distance based on "google earth"
		dev = expected * 0.01;
		if (dist > dev + expected || dist < expected - dev)
			fail("Distance function failed");
		// test far points
		try {
			gps1 = new GpsCoord(32.063607, 34.835309, 670);
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		try {
			gps2 = new GpsCoord(31.990175, 34.800077, -345);
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		dist = gps1.distance2D(gps2);
		expected = 8796.0;// expected 2d distance based on "google earth"
		dev = expected * 0.01;
		if (dist > dev + expected || dist < expected - dev)
			fail("Distance function failed");
	}

	/**
	 * testing the vector3d method, this test is performed on GpsCoord instance as
	 * they contain a Point3D inside of them, so this way we'll test both classes in
	 * one test
	 */
	@Test
	void testVector3D() {
		GpsCoord gps1 = null;
		try {
			gps1 = new GpsCoord(32.103315, 35.209039, 670);// first GPS coord from excel file
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		GpsCoord gps2 = null;
		try {
			gps2 = new GpsCoord(32.106352, 35.205225, 650);// second GPS coord from the excel file
		} catch (InvalidPropertiesFormatException e) {
			fail("gps coord constructor failed");
		}
		Point3D expectedVecotr = new Point3D(337.6989921, -359.2492069, -20);// expected vector from excel file
		Point3D output = gps1.vector3D(gps2.getInternalPoint());
		// checking whether the output vector is "close enough" to the expected vector,
		// we allow 1% deviation
		if ((((output.x() > (expectedVecotr.x() + (expectedVecotr.x() * 0.01)))
				|| (output.x() < (expectedVecotr.x() - (expectedVecotr.x() * 0.01))))
				|| (output.y() > (expectedVecotr.y() - (expectedVecotr.y() * 0.01))
						|| output.y() < expectedVecotr.y() + (expectedVecotr.y() * 0.01)
						|| (output.z() > (expectedVecotr.z() - (expectedVecotr.z() * 0.01))
								|| (output.z() < (expectedVecotr.z() + (expectedVecotr.z() * 0.01))))))) {
			fail("vector3D test failed");
		}
	}

	/**
	 * testing azimuth_elevation_dist method, this test is performed on GpsCoord
	 * instance as they contain a Point3D inside of them, so this way we'll test
	 * both classes in one test
	 * 
	 * @throws InvalidPropertiesFormatException
	 */
	@Test
	void testAzimuth_elevation_dist() throws InvalidPropertiesFormatException {
		// distance was already tested before so we'll skip that
		//
		GpsCoord center = new GpsCoord(32.098729, 35.207258, 670);// the centre point
		// testing elevation
		// 3 points distant 500+-eps meter away from centre
		// the elevation difference should be 24.70243023, allowed deviation will be 0.1
		// because the distances are exactly 500M
		// because the distance and the elevation is the same for all points it should
		// give the same
		// result +-eps in arr[1] representing the elevation difference in degrees
		double eps = 0.1;
		double expected = 24.70243023;// tan^-1(230/500)
		GpsCoord gps1 = new GpsCoord(32.102285, 35.210515, 900);
		GpsCoord gps2 = new GpsCoord(32.098921, 35.201961, 900);
		GpsCoord gps3 = new GpsCoord(32.094311, 35.206260, 900);
		double arr1[] = center.azimuth_elevation_dist(gps1);
		double arr2[] = center.azimuth_elevation_dist(gps2);
		double arr3[] = center.azimuth_elevation_dist(gps3);
		if (arr1[1] > expected + eps || arr1[1] < expected - eps)
			fail("Wrong value for elevation difference");
		if (arr2[1] > expected + eps || arr2[1] < expected - eps)
			fail("Wrong value for elevation difference");
		if (arr3[1] > expected + eps || arr3[1] < expected - eps)
			fail("Wrong value for elevation difference");

		// now testing azimuth
		gps1 = new GpsCoord(10, 50, 900);
		gps2 = new GpsCoord(10.1, 50, 900);
		// should be zero since longitude didn't change, we only moved to the north
		arr1 = gps1.azimuth_elevation_dist(gps2);
		if (arr1[0] != 0)
			fail("Wrong value for azimuth");
		gps1 = new GpsCoord(10.1, 50, 900);
		gps2 = new GpsCoord(10, 50, 900);
		// should be 180 since we moved only to the south
		arr1 = gps1.azimuth_elevation_dist(gps2);
		if (arr1[0] != 180)
			fail("Wrong value for azimuth");
		gps1 = new GpsCoord(10, 50, 900);
		gps2 = new GpsCoord(10, 50.000000001, 900);
		// should be 90 because we only moves to the east
		arr1 = gps1.azimuth_elevation_dist(gps2);
		if (arr1[0] != 90)
			fail("Wrong value for azimuth");
		gps1 = new GpsCoord(10, 50.000000001, 900);
		gps2 = new GpsCoord(10, 50, 900);
		// should be 270 since we only moved to the west
		arr1 = gps1.azimuth_elevation_dist(gps2);
		if (arr1[0] != 270)
			fail("Wrong value for azimuth");
	}

	/**
	 * testing isValidGpsPoint, this test is made on Point3D instance because there
	 * is no way that a GpsCoord will be not valid as the constructor validates it
	 */
	@Test
	void testIsValid_GPS_Point() {
		assertTrue(tester.isValid_GPS_Point(new Point3D(35, -87, 30)));
		assertFalse(tester.isValid_GPS_Point(new Point3D(-200, -87, 30)));
		assertFalse(tester.isValid_GPS_Point(new Point3D(-20, -999, 0)));
		assertFalse(tester.isValid_GPS_Point(new Point3D(-200, -87, 30000000)));

	}

}
