package GIS;

import java.util.Date;

import Geom.Point3D;

/**
 * this class represents the meta data of a GIS layer
 * 
 * @author Evgeny&David
 *
 */
public class LayerMetaData implements Meta_data {

	private Date dateOfCreation;// the only thing this layer contains is the date it was created in

	/**
	 * basic constructor for the class
	 */
	public LayerMetaData() {

		dateOfCreation = new Date();
	}

	/**
	 * return the date of creation of the layer in UTC method
	 */
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public long getUTC() {
		return this.dateOfCreation.UTC(this.dateOfCreation.getYear(), this.dateOfCreation.getMonth(),
				this.dateOfCreation.getDay(), this.dateOfCreation.getHours(), this.dateOfCreation.getMinutes(),
				this.dateOfCreation.getSeconds());
	}

	@Override
	public Point3D get_Orientation() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * basic toString method
	 */
	@Override
	public String toString() {
		return "This layer was created in: " + this.dateOfCreation.toString();
	}
}
