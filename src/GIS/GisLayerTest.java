package GIS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GisLayerTest {
	
	@Test
	void testGisLayer() {
		GisLayer layer=new GisLayer();
		if(layer.size()!=0||layer.get_Meta_data()==null)fail("Constructor failed!");
	}
	
	@Test
	void testAdd() {
		String[] certainCsvLine1 = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		//creates 2 elements from the same line
		GisElement testElement1 = new GisElement(certainCsvLine1);
		GisElement testElement2 = new GisElement(certainCsvLine1);
		GisLayer layer=new GisLayer();
		//checks if it adds 2 equal elements
		if(!layer.add(testElement1)||layer.add(testElement2))fail("Add function failed");
		String[] certainCsvLine2 = { "14:ae:db:58:73:75", "test", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement3= new GisElement(certainCsvLine2);
		//checks if it adds a different element
		if(!layer.add(testElement3))fail("Add function failed");
	}

	@Test
	void testAddAll() {
		//create a layer with 2 different elements
		String[] certainCsvLine1 = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement1 = new GisElement(certainCsvLine1);
		String[] certainCsvLine2 = { "14:ae:db:58:73:75", "test", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement2= new GisElement(certainCsvLine2);
		GisLayer layer1=new GisLayer();
		layer1.add(testElement1);
		layer1.add(testElement2);
		//create a layer with 1 element different from the 2 above
		String[] certainCsvLine3= { "14:ae:db:58:73:75", "test2", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement3= new GisElement(certainCsvLine3);
		GisLayer layer2=new GisLayer();
		layer2.add(testElement3);
		if(!layer2.addAll(layer1))fail("Add all failed!");//because all 3 elements are different add all should return true
		if(layer2.addAll(layer1))fail("Added duplicates!");//tries to add the same elements again,should return false
	}

	@Test
	void testGet_Meta_data() {
		GisLayer layer=new GisLayer();
		Meta_data data;
		data=layer.get_Meta_data();
		if(data==null)fail("Get meta data failed!");
	}

}
