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
		// TO BE MADE
	}

	// ************public methods************

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
	 * 
	 * @param inputImage the wanted image to set
	 * @throws IOException
	 */
	public void setImage(BufferedImage inputImage) throws IOException {
		mapImage = inputImage;
	}

	/**
	 * returning a Gps Coord based on a given pixel on given map
	 * 
	 * @return
	 * @throws IOException
	 */
	public GpsCoord pixel2Gps() throws IOException {
		Point3D vectorMeter = calcDiffMeterVector();// calculating the difference meter vector and adding it to the to
													// left pixel after we normalize it by the ratio
		return (new GpsCoord(this.topLeftP.add(vectorMeter)));
	}

	/**
	 * converting gps coord to pixels in a given map (i.e map image)
	 * 
	 * @param gps
	 * @return
	 */
	public Point3D gps2Pixel(GpsCoord gps) {
		Point3D temp = new Point3D(gps.getLat(), gps.getLon(), gps.getAlt());
		Point3D vector = topLeftP.vector3D(temp); // creating a meter vector between the top left Gps coord to the
													// chosen point)
		double hRatio = calcDymHRatio();// calculating height ratio of the resolution as for right now
		double wRatio = calcDymWRatio();// calculating width ratio of the resolution as for right now
		// taking the integer part of the division as we talk about pixels
		double y = Math.ceil(vector.y() / wRatio);
		double x = Math.ceil(-vector.x() / hRatio);
		Point3D output = new Point3D(x, y, 0);// creating a new point and returning it - these are the pixels
		return output;

	}
////private function section///

	// this method calculates the ratio of the screen right now based on the
	// original image resolution
	private void calcRatio() {
		// getting both height and width of the map right now
		double height = this.mapImage.getHeight();
		double width = this.mapImage.getWidth();
		// calculating the diagonal of the screen based on Pitgors theorem
		double diagonal = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
		// calculating the distance between the top left and bottom right corners
		double distance = this.topLeftP.distance2D(this.bottomRightP);
		// returning the ratio as a division between the diagonal and the distance
		this.ratio = (distance / diagonal);
	}

	// calculating changing - dynamic height ratio
	private double calcDymHRatio() {
		double dynHeight = MyFrame.height;
		return this.mapH / dynHeight;// the division between maps height and the current guis height
	}

	// calculating changing - dynamic width ratio
	private double calcDymWRatio() {
		double dynWidth = MyFrame.width;
		return this.mapW / dynWidth;// the division between maps width and the current guis width
	}

	// calculating both width and height ratio and returning them both as a 3D point
	private Point3D calcDynamicRatio() {
		double dynHeight = MyFrame.height;
		double dynWidth = MyFrame.width;
		return new Point3D(dynHeight / originalHeigth, dynWidth / originalWidth, 0);
	}

	// calculating the meter difference vector between the last clicked pixel and
	// the top left corner, used to determine pixels GPS coord
	private Point3D calcDiffMeterVector() throws IOException {
		Point3D dynamicRatio = calcDynamicRatio();// calculating the new - updated ratio based on the size of the screen
													// right now
		Point3D clicked = MyFrame.lastClicked;// saving the last clicked pixel as a 3D point
		Point3D vector = new Point3D((((-1) * (clicked.y() - topLeftPixelCoord.y())) * (ratio / dynamicRatio.x())),
				(clicked.x() * (ratio / dynamicRatio.y())), 0);// calculating the actual vector and returning it
		return vector;
	}
	
	//initializing the map used to make constructor less "ugly"
	private void initDefMap() throws IOException {
		mapH = topLeftP.distance2D(bottomLeftP);
		mapW = bottomLeftP.distance2D(bottomRightP);
		this.mapImage = ImageIO.read(new File("gameUtils" + File.separator + "Ariel1.png"));
		originalHeigth = this.mapImage.getHeight();
		originalWidth = this.mapImage.getWidth();
		calcRatio();
	}

}