package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import Coords.GpsCoord;
/**
 * this class represents a GIS Project, a set of GIS layers
 * @author Evgeny&David
 *
 */
public class GisProject implements GIS_project {
	private ArrayList<GIS_layer>layers;
	private Meta_data data;
	/**
	 * Default constructor
	 */
	public GisProject() {
		layers=new ArrayList<GIS_layer>();
		data=new ProjectMetaData();
	}
	/**
	 * Adds a GIS_layer to this array list of GIS_layers
	 * @param e GIS_layer to be added
	 * @return returns true if added without removing element from e and false otherwise
	 */
	@Override
	public boolean add(GIS_layer e) {
		boolean flag=true;
		Iterator<GIS_layer>layerIt=layers.iterator();
		GIS_element currElement;
		GIS_layer currLayer;
		ArrayList<GIS_element> toBeRemoved=new ArrayList<GIS_element>();//to hold element needed to be removed from e
		while(layerIt.hasNext()) {	//goes through every element in every layer and checks if e contains it
			currLayer=layerIt.next();
			Iterator<GIS_element>elementIt=currLayer.iterator();
			while(elementIt.hasNext()) {
				currElement=elementIt.next();
				Iterator<GIS_element>elementIt2=e.iterator();
				GIS_element checkIfEqual;
				while(elementIt2.hasNext()) {
					checkIfEqual=elementIt2.next();
					try {
						if(isEqual((GisElement)currElement,(GisElement)checkIfEqual)) {//if e already has an element equal to an element in one of the layers
							toBeRemoved.add(checkIfEqual);	//remembers it needs to be removed
							flag=false;
						}
					} catch (InvalidPropertiesFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		Iterator<GIS_element>remover=toBeRemoved.iterator();
		while(remover.hasNext()) {	//removes duplicates
			e.remove(remover.next());
		}
		if(e.size()>0) {	//if after all duplicates were removed e still has elements in it
			layers.add(e);	//add it to the list
		}
		return flag;
	}
	/**
	 * Adds every GIS_layer to this array list
	 * @param c Collection of GIS_layers to be added
	 * @return Returns true if all of the layers were added successfully and false otherwise
	 */
	@Override
	public boolean addAll(Collection<? extends GIS_layer> c) {
		Iterator<? extends GIS_layer>it=c.iterator();
		GIS_layer currLayer;
		boolean flag=true;
		while(it.hasNext()) {
			currLayer=it.next();
			if(!add(currLayer))flag=false;
		}
		return flag;
	}
	@Override
	public void clear() {
		layers=new ArrayList<GIS_layer>();
	}

	@Override
	public boolean contains(Object o) {
		return this.layers.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.layers.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return this.layers.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		return this.layers.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return this.layers.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.layers.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.layers.retainAll(c);
	}

	@Override
	public int size() {
		return this.layers.size();
	}

	@Override
	public Object[] toArray() {
		return this.layers.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.layers.toArray(a);
	}

	@Override
	public Meta_data get_Meta_data() {
		return this.data;
	}

	//private supporting method
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
