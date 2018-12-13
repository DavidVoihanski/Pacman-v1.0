package gameUtils;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.imageio.ImageIO;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import Coords.GpsCoord;
import GUI.MyFrame;
import Geom.Point3D;

public class Map {
	private GpsCoord topLeftP;
	private GpsCoord bottomRightP;
	private BufferedImage mapImage;
	private double pixelMeterRatio;
	private static final Point3D topLeftPixelCoord=new Point3D(11,75,0);

	//constructors://
	//def constructor 
	public Map() throws IOException {
		this.topLeftP = new GpsCoord(32.10574, 35.20228, 0);
		this.bottomRightP = new GpsCoord(32.10192, 35.21231, 0);
		mapImage = ImageIO.read(new File("Ariel1.png"));
		calcRatio();
	}

	// custom constructor//
	public Map(double lat1, double lon1, double lat2, double lon2, String imagePath) throws IOException {
		this.topLeftP = new GpsCoord(lat1, lon1, 0);
		this.bottomRightP = new GpsCoord(lat2, lon2, 0);
		this.mapImage = ImageIO.read(new File(imagePath));
	}

	//getters//
	public BufferedImage getImage() throws IOException {
		return mapImage;
	}

	// setters
	public void setImage(BufferedImage inputImage) throws IOException {
		mapImage = inputImage;
	}
	public GpsCoord clickedToAddPoint() throws InvalidPropertiesFormatException {
		GpsCoord wantedPoint=new GpsCoord(topLeftP.add(calcDiffMeterVector()));
		return wantedPoint;
	}
	////private function section///
	private void calcRatio() {
		int height=this.mapImage.getHeight();
		int width=this.mapImage.getWidth();
		double diagonal=Math.sqrt(Math.pow(height, 2)+Math.pow(width, 2));
		double distance=this.topLeftP.distance2D(this.bottomRightP);
		pixelMeterRatio=distance/diagonal;
	}
	private Point3D calcDiffMeterVector() {
		Point3D pointClicked=MyFrame.lastClicked;
		double diffX=pointClicked.x()-topLeftPixelCoord.x();
		double diffY=topLeftPixelCoord.y()-pointClicked.y();
		Point3D meterVector=new Point3D(diffY*pixelMeterRatio,diffX*pixelMeterRatio,0);
		return meterVector;

	}
	//private String splitStringForPath (String inputString) {
	//	String withOutSlash [] = inputString.split("/");
	//}
}
