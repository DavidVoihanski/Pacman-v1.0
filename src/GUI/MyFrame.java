package GUI;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.swing.*;

import Geom.Point3D;
import gameUtils.Map;
import gameUtils.MapFactory;

public class MyFrame extends JFrame implements MouseListener, ComponentListener {

	private static final long serialVersionUID = 1L;
	private Map gameMap;
	private BufferedImage gameChangingImage;
	public static Point3D lastClicked;

	public MyFrame() throws IOException {
		initComponets();
		this.addMouseListener(this);
		this.addComponentListener(this);
	}

	public void paint(Graphics g) {
			g.drawImage(this.gameChangingImage,0, 0, this);
	}

	// *******mouse interface implementation*******//
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("mouse Clicked on the");
		System.out.println("Width:"+arg0.getX()+", Height: "+arg0.getY());
		lastClicked=new Point3D(arg0.getX(),arg0.getY(),0);
		try {
			System.out.println(gameMap.clickedToAddPoint());
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

//*******component interface implementation*******// 
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		try {
			this.gameChangingImage=(resizeImage(this.gameMap.getImage(), this.getWidth(), this.getHeight()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		repaint();
	}

	// getters//
	public Map getMapOfGame() {
		return gameMap;
	}

	// *******private methods*******//
	private void initComponets() throws IOException {
		gameMap = MapFactory.defaultMapInit();
		gameChangingImage =gameMap.getImage();
//		MenuBar menuBar = new MenuBar();
//		Menu menu = new Menu("Menu");
//		MenuItem item1 = new MenuItem("menu item 1");
//		MenuItem item2 = new MenuItem("menu item 2");
//		menuBar.add(menu);
//		menu.add(item1);
//		menu.add(item2);
//		this.setMenuBar(menuBar);
	}

	private BufferedImage resizeImage(BufferedImage image, int width, int height) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}

}
