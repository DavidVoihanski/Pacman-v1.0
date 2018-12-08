package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import Coords.GpsCoord;

/**
 * this class represents an entire layer of GIS elements, i.e an entire CSV
 * document, please note, this is a SET of GIS elements
 * 
 * @author Evgeny&David
 *
 */
public class GisLayer implements GIS_layer {
	private ArrayList<GIS_element> set;// the data structure which hosts the GIS element set
	private LayerMetaData layerData;// instance of the class LayerMetaData - GIS layers data

	/**
	 * basic empty constructor
	 */
	public GisLayer() {
		this.set = new ArrayList<>();
		layerData = new LayerMetaData();
	}

	/**
	 * adding a GIS_element instance to the set, as this is a set, there are no
	 * duplications
	 */
	@Override
	public boolean add(GIS_element e) {
		GisElement temp = new GisElement(e);
		Iterator<GIS_element> it = set.iterator();
		while (it.hasNext()) {// checking whether there are a same element as the one we're trying to add
			GisElement current = (GisElement) it.next();
			try {
				if (isEqual(current, temp)) {// checking whether these both elements are the same, if they are, we'll
												// return false and stop
					return false;
				}
			} catch (InvalidPropertiesFormatException e1) {
				e1.printStackTrace();
				return false;
			}
		}
//if there are no similar elements as this - add it and return true
		set.add(temp);
		return true;
	}

	/**
	 * adding all the elements from a collection, based on the add method
	 */
	@Override
	public boolean addAll(Collection<? extends GIS_element> c) {
		boolean output = true;
		Iterator<? extends GIS_element> it = c.iterator();
		while (it.hasNext()) {// for all the elements of this collection, we'll try to add it with the add
								// basic method
			GIS_element current = it.next();
			if (!this.add((GisElement) current)) {// in case there is one or more elements that are already in the
														// set, we'll return false (but keep trying to add later
														// elements)
				output = false;
			}
		}
		return output;
	}

	/**
	 * basic clear method. which will clear the set
	 */
	@Override
	public void clear() {
		this.set.clear();
	}

	/**
	 * will return true if the element is found, will return false if it isn't
	 */
	@Override
	public boolean contains(Object o) {
		return this.set.contains(o);
	}

	/**
	 * will return true if the all elements from the collection is found, will
	 * return false if they dont
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return this.set.containsAll(c);
	}

	/**
	 * will return true if the set is empty, will return false if it isn't
	 */
	@Override
	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	/**
	 * returning an iterator
	 */
	@Override
	public Iterator<GIS_element> iterator() {
		return (Iterator<GIS_element>) this.set.iterator();
	}

	/**
	 * removing certain element from the set
	 */
	@Override
	public boolean remove(Object o) {
		return this.set.remove(o);
	}

	/**
	 * removing all the element from this collection from the set
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return this.set.removeAll(c);
	}

	/**
	 * returning the size of the set
	 */
	@Override
	public int size() {
		return this.set.size();
	}

	/**
	 * basic "to array" method, will return an array of this set
	 */
	@Override
	public Object[] toArray() {
		return this.set.toArray();
	}

	/**
	 * more generic to array method
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return this.set.toArray(a);
	}

	/**
	 * retains the elements from this collection which found in the set
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return this.set.retainAll(c);
	}

	/**
	 * return the layer meta data
	 */
	@Override
	public Meta_data get_Meta_data() {
		return (Meta_data) layerData;
	}

//private method
	private boolean isEqual(GisElement arg1, GisElement arg2) throws InvalidPropertiesFormatException {
		GpsCoord arg1Location = new GpsCoord(arg1.getGeom());
		GpsCoord arg2Location = new GpsCoord(arg2.getGeom());
		MetaData arg1Data = new MetaData(arg1.getData());
		MetaData arg2Data = new MetaData(arg2.getData());
		if (arg1Location.distance3D(arg2Location) == 0 && arg1Data.toString().equals(arg2Data.toString())) {
			return true;
		}
		return false;
	}
}
