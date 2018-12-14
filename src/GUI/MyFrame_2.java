package GUI;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Geom.Point3D;
import gameUtils.Map;
import gameUtils.MapFactory;

public class MyFrame_2 extends JFrame implements MouseListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	private Map gameMap;
	private BufferedImage gameChangingImage;
	private JLabel savedForImage;
	public static Point3D lastClicked;
	public static int counter;
	public static int heigth;
	public static int width;

	public MyFrame_2() throws IOException {
		initComponents();
		this.addMouseListener(this);
		this.addComponentListener(this);
	}

	private void initComponents() throws IOException {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameMap = MapFactory.defaultMapInit();
		this.gameChangingImage = this.gameMap.getImage();
		// 4. we'll create a JLabel to "put" the image into
		this.savedForImage = new JLabel();
		this.savedForImage.setIcon(new ImageIcon(this.gameMap.getImage()));
		// 5. creating the panel which will be the container for the image label
		JPanel jPanel= new JPanel();
		// adding the label to the panel
		jPanel.add(savedForImage);
		// now building the menu bar and all its features
		JMenuBar menuBar = new JMenuBar();
		// menu first - external "button:
		JMenu mainMenu = new JMenu("Menu");
		mainMenu.setMnemonic(KeyEvent.VK_R);
		// creating subMenus
		JMenu subMenuCustomGame = new JMenu("custom game");
		subMenuCustomGame.setMnemonic(KeyEvent.VK_R);
		JMenu subMenuDefaultGame = new JMenu("default game");
		subMenuDefaultGame.setMnemonic(KeyEvent.VK_R);
		// creating all menuItems for custom game
		JMenuItem chooseMap = new JMenuItem("choose a map");
		chooseMap.setMnemonic(KeyEvent.VK_R);
		subMenuCustomGame.add(chooseMap);
		JMenuItem setMapGpsCoords = new JMenuItem("set the gps coords of the map");
		setMapGpsCoords.setMnemonic(KeyEvent.VK_R);
		subMenuCustomGame.add(setMapGpsCoords);
		mainMenu.add(subMenuCustomGame);
		// creating all menuItems for default game
		JMenu existingGame = new JMenu("play existing game");
		existingGame.setMnemonic(KeyEvent.VK_R);
		JMenuItem loadCsv = new JMenuItem("load CSV file");
		loadCsv.setMnemonic(KeyEvent.VK_R);
		existingGame.add(loadCsv);
		subMenuDefaultGame.add(existingGame);
		JMenu newGame = new JMenu("build your own game");
		newGame.setMnemonic(KeyEvent.VK_R);
		JMenuItem packman = new JMenuItem("add packman");
		packman.setMnemonic(KeyEvent.VK_R);
		JMenuItem fruit = new JMenuItem("add fruit");
		fruit.setMnemonic(KeyEvent.VK_R);
		JMenuItem saveAsCsv = new JMenuItem("save the game as CSV file");
		saveAsCsv.setMnemonic(KeyEvent.VK_R);
		newGame.add(packman);
		newGame.add(fruit);
		newGame.add(saveAsCsv);
		subMenuDefaultGame.add(newGame);
		mainMenu.add(subMenuDefaultGame);
		// creating menu items
		JMenuItem saveAsKml = new JMenuItem("save as KML");
		saveAsKml.setMnemonic(KeyEvent.VK_R);
		JMenuItem play = new JMenuItem("run movment simulation");
		play.setMnemonic(KeyEvent.VK_R);
		// adding last menu items to the main one
		mainMenu.add(play);
		mainMenu.add(saveAsKml);
		// adding the main menu to the menu bar
		menuBar.add(mainMenu);
		this.setJMenuBar(menuBar);
		// setting the size of the frame
		this.setSize(new Dimension(gameChangingImage.getWidth() + 20, gameChangingImage.getHeight() + 85));
		// adding the panel and the menu to the frame
		this.add(jPanel);
		// setting the frame visible
		this.setVisible(true);

	}

	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		return bilinearScaleOp.filter(originalImage, new BufferedImage(width, height, originalImage.getType()));
	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		try {
			this.gameChangingImage = (resizeImage(this.gameMap.getImage(), this.getWidth()-20, this.getHeight()-85));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		width = this.gameChangingImage.getWidth();
		heigth =this.gameChangingImage.getHeight();
		System.out.println("h: "+heigth+" w: "+width);
		this.savedForImage.setIcon(new ImageIcon(this.gameChangingImage));

	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		lastClicked=new Point3D(arg0.getX(),arg0.getY(),0);
		System.out.println("mouse Clicked on the");
		System.out.println("Width:"+arg0.getX()+", Height: "+arg0.getY());
		try {
			System.out.println(gameMap.clickedToAddPoint());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

}
