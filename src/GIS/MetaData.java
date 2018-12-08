package GIS;

import java.util.Arrays;
import java.util.Date;

import Coords.GpsCoord;
import Geom.Point3D;

/**
 * this class represents the meta data of an GIS element
 * 
 * @author Evgeny&David
 *
 */
public class MetaData implements Meta_data {
	private GpsCoord certainGpsCoord;// this elements GPS coord
	private String[] data;// the actual data of this element

	/**
	 * 
	 * @param certainGpsCoord
	 * @param dataInCertainPoint
	 */
	public MetaData(GpsCoord certainGpsCoord, String[] dataInCertainPoint) {
		this.certainGpsCoord = certainGpsCoord;
		this.data = dataInCertainPoint;
	}

	/**
	 * copy constructor which works with any Meta_data instance
	 * 
	 * @param toCopyFrom the instance of GIS_element we want to copy
	 */
	public MetaData(Meta_data toCopyFrom) {
		MetaData temp = (MetaData) toCopyFrom;
		this.certainGpsCoord = temp.certainGpsCoord;
		this.data = temp.data;
	}

	/**
	 * return the time contained in this data in a UTC method
	 */
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public long getUTC() {
		int[] dataInInteger = splitterForDateTime(this.data[3]);
		Date outPut = null;
		return outPut.UTC(dataInInteger[2] - 1900, dataInInteger[1] - 1, dataInInteger[1], dataInInteger[3],
				dataInInteger[4], 0);
	}

	/**
	 * 
	 */
	@Override
	public Point3D get_Orientation() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * return the data array of this meta data
	 */
	public String[] getDataArray() {
		return this.data;
	}

	/**
	 * basic toString method
	 */
	@Override
	public String toString() {
		return "GPS location: " + certainGpsCoord + ", meta data: " + Arrays.toString(data);
	}

	// private method
	private int[] splitterForDateTime(String timeData) {
		String splitBy = " ";
		String[] timeAndDate = timeData.split(splitBy);
		int[] outPut = new int[5];
		if (timeAndDate[0].contains("-"))
			splitBy = "-";
		else
			splitBy = "/";
		String[] date = timeAndDate[0].split(splitBy);
		splitBy = ":";
		String[] time = timeAndDate[1].split(splitBy);
		for (int i = 0; i < outPut.length; i++) {
			if (i < 3) {
				outPut[i] = Integer.parseInt(date[i]);
			} else {
				outPut[i] = Integer.parseInt(time[i - 3]);
			}
		}
		return outPut;
	}

}
