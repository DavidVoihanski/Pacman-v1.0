package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage changingImage;
	private BufferedImage originalImage;
	private BufferedImage packman;
	private BufferedImage fruit;
//constructor
	ImagePanel(BufferedImage image) throws IOException {
		this.changingImage = image;
		this.originalImage= image;
		this.packman = ImageIO.read(new File("pacman.png"));
		packman = this.resizeIcon(30, 30,packman);
		this.fruit = ImageIO.read(new File("fruit.png"));
		fruit = this.resizeIcon(30, 30,fruit);
		
	}
//these two are for internal uses
	@Override
	public Dimension getPreferredSize() {
		if (super.isPreferredSizeSet()) {
			return super.getPreferredSize();
		}
		return new Dimension(changingImage.getWidth(), changingImage.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(changingImage, 0, 0, null);
	}
	public void drawingPackman(int x, int y,Graphics g) {
		g.drawImage(this.packman, x, y, null);
	}
	public void drawingFruit(int x, int y,Graphics g) {
		g.drawImage(this.fruit, x, y, null);
	}
//resizing with the window size
	public void resizeImage(int width, int height) {
		int imageWidth = this.originalImage.getWidth();
		int imageHeight = this.originalImage.getHeight();
		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		this.changingImage = bilinearScaleOp.filter(this.originalImage, new BufferedImage(width, height, this.originalImage.getType()));
	}
	private BufferedImage resizeIcon(int width, int height,BufferedImage icon) {
		int imageWidth = icon.getWidth();
		int imageHeight =icon.getHeight();
		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		return bilinearScaleOp.filter(icon, new BufferedImage(width, height, icon.getType()));
	}
}
