package gameUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.imageio.ImageIO;

import Coords.GpsCoord;
import GUI.MyFrame;
import Geom.Point3D;

/**
 * this class represents a map on which the game is conducted
 * 
 * @author Evgeny & David
 *
 */
public class Map {
	private GpsCoord topLeftP;
	private GpsCoord bottomRightP;
	private GpsCoord bottomLeftP;
	private BufferedImage mapImage;
	private static final Point3D topLeftPixelCoord = new Point3D(0, 54, 0);// keeping the top left pixel which never
																			// changes
	private double ratio;
	private int originalHeigth;
	private int originalWidth;
	private double mapH;
	private double mapW;

	/**
	* basic default map constructor 
	*/
	public Map() {
		try {
			this.topLeftP = new GpsCoord(32.10574, 35.20228, 0);
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.bottomRightP = new GpsCoord(32.10192, 35.21231, 0);
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.bottomLeftP = new GpsCoord(32.10194, 35.20236, 0);
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			initDefMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// custom constructor//
	public Map(double lat1, double lon1, double lat2, double lon2, String imagePath) throws IOException {
		//TO BE MADE 
	}
	
	
	//************public methods************
	
	
	// getters//
	/**
	 * 
	 * @return the image of the map 
	 * @throws IOException
	 */
	public BufferedImage getImage() throws IOException {
		return mapImage;
	}

	// setters
	/**
	 * setting the image of the map
	 * @param inputImage the wanted image to set 
	 * @throws IOException
	 */
	public void setImage(BufferedImage inputImage) throws IOException {
		mapImage = inputImage;
	}

	/**
	 * adding a meter vector to the left upper pixel to determine the Gps Coord of the pixel
	 * @return
	 * @throws IOException
	 */
	public GpsCoord pixel2Gps() throws IOException {
		Point3D vectorMeter = calcDiffMeterVector();
		return (new GpsCoord(this.topLeftP.add(vectorMeter)));
	}

	/**
	 * 
	 * @param gps
	 * @return
	 */
	public Point3D gps2Pixel(GpsCoord gps) {
		Point3D temp = new Point3D(gps.getLat(), gps.getLon(), gps.getAlt());
		Point3D vector = topLeftP.vector3D(temp);
		double hRatio = calcDymHRatio();
		double wRatio = calcDymWRatio();
		double y = Math.ceil(vector.y() / wRatio);
		double x = Math.ceil(-vector.x() / hRatio);
		Point3D output = new Point3D(x, y, 0);
		return output;

	}
////private function section///

	private void calcRatio() {
		double height = this.mapImage.getHeight();
		double width = this.mapImage.getWidth();
		double diagonal = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
		double distance = this.topLeftP.distance2D(this.bottomRightP);
		this.ratio = (distance / diagonal);
	}

	private double calcDymHRatio() {
		double dynHeight = MyFrame.height;
		return this.mapH / dynHeight;
	}

	private double calcDymWRatio() {
		double dynWidth = MyFrame.width;
		return this.mapW / dynWidth;
	}

	private Point3D calcDynamicRatio() {
		double dynHeight = MyFrame.height;
		double dynWidth = MyFrame.width;
		return new Point3D(dynHeight / originalHeigth, dynWidth / originalWidth, 0);
	}

	private Point3D calcDiffMeterVector() throws IOException {
		Point3D dynamicRatio = calcDynamicRatio();
		Point3D clicked = MyFrame.lastClicked;
		Point3D vector = new Point3D((((-1) * (clicked.y() - topLeftPixelCoord.y())) * (ratio / dynamicRatio.x())),
				(clicked.x() * (ratio / dynamicRatio.y())), 0);
		return vector;
	}

	private void initDefMap() throws IOException {
		mapH = topLeftP.distance2D(bottomLeftP);
		mapW = bottomLeftP.distance2D(bottomRightP);
		this.mapImage = ImageIO.read(new File("Ariel1.png"));
		originalHeigth = this.mapImage.getHeight();
		originalWidth = this.mapImage.getWidth();
		calcRatio();
	}

}