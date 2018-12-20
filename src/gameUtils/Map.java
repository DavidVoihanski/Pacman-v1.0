package gameUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.GpsCoord;
import GUI.MyFrame;
import Geom.Point3D;

public class Map {
	private GpsCoord topLeftP;
	private GpsCoord bottomRightP;
	private GpsCoord bottomLeftP;
	private BufferedImage mapImage;
	private static final Point3D topLeftPixelCoord = new Point3D(0, 54, 0);
	private double ratio;
	private int originalHeigth;
	private int originalWidth;
	private double mapH;
	private double mapW;

	// constructors://
	// def constructor
	public Map() throws IOException {
		this.topLeftP = new GpsCoord(32.10574, 35.20228, 0);
		this.bottomRightP = new GpsCoord(32.10192, 35.21231, 0);
		this.bottomLeftP = new GpsCoord(32.10194, 35.20236, 0);
		initDefMap();
	}

	// custom constructor//
	public Map(double lat1, double lon1, double lat2, double lon2, String imagePath) throws IOException {
		this.topLeftP = new GpsCoord(lat1, lon1, 0);
		this.bottomRightP = new GpsCoord(lat2, lon2, 0);
		this.mapImage = ImageIO.read(new File(imagePath));
		calcRatio();
	}

	// getters//
	public BufferedImage getImage() throws IOException {
		return mapImage;
	}

	// setters
	public void setImage(BufferedImage inputImage) throws IOException {
		mapImage = inputImage;
	}

 //public methods
	public GpsCoord clickedToAddPoint() throws IOException {
		Point3D vectorMeter = calcDiffMeterVector();
		return (new GpsCoord(this.topLeftP.add(vectorMeter)));
	}

	//// private function section///

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
		Point3D vector = new Point3D((((-1)*(clicked.y() - topLeftPixelCoord.y())) * (ratio / dynamicRatio.x())),
				(clicked.x() * (ratio / dynamicRatio.y())), 0);
		return vector;
	}

	public Point3D gpsToPixel(GpsCoord gps) {
		Point3D temp = new Point3D(gps.getLat(), gps.getLon(), gps.getAlt());
		Point3D vector = topLeftP.vector3D(temp);
		double hRatio = calcDymHRatio();
		double wRatio = calcDymWRatio();
		double y = Math.ceil(vector.y() / wRatio);
		double x = Math.ceil(-vector.x() / hRatio);
		Point3D output = new Point3D(x, y, 0);
		return output;

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