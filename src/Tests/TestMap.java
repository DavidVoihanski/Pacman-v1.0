package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.junit.jupiter.api.Test;

import Coords.GpsCoord;
import GUI.MyFrame;
import Geom.Point3D;
import gameUtils.Map;

class TestMap {
	
	//test conversion from pixel to gps
	@Test
	void testPixelToGps() {
		Map map=new Map();
		try {
			MyFrame frame=new MyFrame();
			Point3D p=new Point3D(853.0,179.0,0.0);
			frame.setLastClicked(p);
		} catch (IOException e1) {
			fail("Failed to create gui");
			e1.printStackTrace();
		}
		try {
			GpsCoord result=map.pixel2Gps();
			GpsCoord expected=new GpsCoord(32.10494, 35.20831 ,0);//32.1047812,35.2082646
			if(result.distance3D(expected)>30) {
				fail("result too far away");
			}
		} catch (IOException e) {
			fail("failed to calculate gps coord");
			e.printStackTrace();
		}
		
	}
	//test conversion from gps to pixel
	@Test
	void testGpsToPixel() throws InvalidPropertiesFormatException {
		Map map=new Map();
		try {
			MyFrame frame=new MyFrame();
			GpsCoord toConvert=new GpsCoord(32.1047812,35.2082646, 0);//32.1047812,35.2082646
			Point3D result=map.gps2Pixel(toConvert);
			if(Math.abs(result.x()-179)>30) 
				fail("Distance to big");
			if(Math.abs(result.y()-853)>30) 
				fail("Distance to big");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
