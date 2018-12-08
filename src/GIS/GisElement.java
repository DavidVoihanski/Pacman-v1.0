package GIS;

import java.util.InvalidPropertiesFormatException;
import Coords.GpsCoord;
import Geom.Geom_element;

/**
 * this class is a implementation of the GIS_element interface
 * 
 * @author Evgeny&David
 *
 */
public class GisElement implements GIS_element {
	private MetaData elementsData;// the meta data this element contains
	private GpsCoord location;// the GPS coord of this element

	/**
	 * basic constructor which input is a line from the CSV file, the constructor
	 * breaks apart the line and matching it to the wanted data in any google KML
	 * place mark
	 * 
	 * @param certainCsvLine String input which represents a certain line in the CSV
	 *                       file, this line turn to a GIS element which will be
	 *                       converted to a KML place mark
	 */
	public GisElement(String[] certainCsvLine) {
		this.location = this.getLocation(certainCsvLine);
		this.elementsData = this.getData(certainCsvLine);
	}
/**
 * copy constructor which works with any GIS_element instance
 * @param toCopyFrom the instance of GIS_element we want to copy
 */
	public GisElement(GIS_element toCopyFrom) {
		this.elementsData = (MetaData) toCopyFrom.getData();
		this.location = (GpsCoord) toCopyFrom.getGeom();
	}
/**
 * returning the GPS coord of this element as a Geom_element
 */
	@Override
	public Geom_element getGeom() {
		return (Geom_element) this.location;
	}
/**
 * returning the meta data of this element 
 */
	@Override
	public Meta_data getData() {
		return (Meta_data) this.elementsData;
	}

	// private methods :
	private GpsCoord getLocation(String[] certainCsvLine) {
		GpsCoord outputLocation = null;
		try {
			outputLocation = new GpsCoord(Double.parseDouble(certainCsvLine[6]), Double.parseDouble(certainCsvLine[7]),
					Double.parseDouble(certainCsvLine[8]));
		} catch (NumberFormatException | InvalidPropertiesFormatException e) {
			e.printStackTrace();
			return null;
		}
		return outputLocation;
	}

	private String[] pullData(String[] certainCsvLine) {
		String[] outPut = { certainCsvLine[0], certainCsvLine[1], certainCsvLine[2], certainCsvLine[3],
				certainCsvLine[4], certainCsvLine[5], certainCsvLine[9], certainCsvLine[10] };
		return outPut;
	}

	private MetaData getData(String[] certainCsvLine) {
		MetaData outputData = new MetaData(this.getLocation(certainCsvLine), this.pullData(certainCsvLine));
		return (outputData);
	}

}
