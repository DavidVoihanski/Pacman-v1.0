package GIS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GisProjectTest {

	@Test
	void testGisProject() {
		GisProject project = new GisProject();
		if(project.size()!=0||project.get_Meta_data()==null)fail("Constructor failed!");
	}

	@Test
	void testAdd() {
		String[] certainCsvLine1 = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		//create 2 elements with the same data and add each to a different layer
		GisElement testElement1 = new GisElement(certainCsvLine1);
		GisElement testElement2 = new GisElement(certainCsvLine1);
		GisLayer layer1=new GisLayer();
		layer1.add(testElement1);
		GisLayer layer2=new GisLayer();
		layer2.add(testElement2);
		GisProject project=new GisProject();
		//tries to add both layers, the first one should go in and return true
		//the second one should not go in and return false
		if(!project.add(layer1)||project.add(layer1))fail("Add funtion failed test 1!");
		String[] certainCsvLine2 = { "14:ae:db:58:73:75", "diff", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		GisElement testElement3 = new GisElement(certainCsvLine2);
		GisLayer layer3=new GisLayer();
		layer3.add(testElement3);
		//tries to add another layer with a different element, should return true
		if(!project.add(layer3))fail("Add function failed test 2!");
		
	}

	@Test
	void testAddAll() {
		GisProject project1=new GisProject();
		GisProject project2 =new GisProject();
		String[] certainCsvLine1 = { "14:ae:db:58:73:75", "love", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		String[] certainCsvLine2 = { "14:ae:db:58:73:75", "diff", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		String[] certainCsvLine3= { "14:ae:db:58:73:75", "diff2", "[WPA2-PSK-CCMP][ESS]", "01/12/2017 10:49", "1", "-78",
				"32.17225088", "34.81436692", "25.86914313", "4", "WIFI" };
		//create 3 elements all different
		GisElement testElement1 = new GisElement(certainCsvLine1);
		GisElement testElement2 = new GisElement(certainCsvLine2);
		GisElement testElement3 = new GisElement(certainCsvLine3);
		//create 1 element equal to testElement1
		GisElement testElement4 = new GisElement(certainCsvLine1);
		GisLayer layer1=new GisLayer();
		GisLayer layer2=new GisLayer();
		GisLayer layer3=new GisLayer();
		layer1.add(testElement1);
		layer2.add(testElement2);
		layer3.add(testElement3);
		layer3.add(testElement4);
		project1.add(layer1);
		project1.add(layer2);
		project2.add(layer3);
		//should return false because there are 2 equal elements in layer3 and layer1
		if(!project1.addAll(project2)) {
			//but the size should still go up by one because layer3 has 1 different element
			if(project1.size()!=3)fail("Wrong size");
			
		}
		else fail("Added layer containing the same element!");
	}

	@Test
	void testGet_Meta_data() {
		GisProject project=new GisProject();
		if(project.get_Meta_data()==null)fail("Get meta data failed!");
	}

}
