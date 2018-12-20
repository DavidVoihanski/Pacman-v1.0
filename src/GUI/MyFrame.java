package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Coords.GpsCoord;
import File_format.Csv2Game;
import File_format.Game2CSV;
import File_format.Game2Kml;
import Geom.Point3D;
import algorithm.ShortestPathAlgo;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Map;
import gameUtils.MapFactory;
import gameUtils.Pacman;
import gameUtils.Paired;

public class MyFrame extends JFrame implements MouseListener, ComponentListener, MenuListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private Map gameMap;
	private BufferedImage gameChangingImage;
	public static Point3D lastClicked;
	public static int height;
	public static int width;
	private ImagePanel imagePanel;
	private JMenu mainMenu;
	private JMenu subMenuDefaultGame;
	private JMenu existingGame;
	private JMenuItem loadCsv;
	private JMenu newGame;
	private JMenuItem packman;
	private JMenuItem fruit;
	private JMenuItem saveAsCsv;
	private JMenuItem saveAsKml;
	private JMenuItem play;
	public static boolean isPackmanAdding;
	public static boolean isFruitAdding;
	private Game thisGuisGame;
	public final JFileChooser fc = new JFileChooser("C:" + File.separator + "Users" + File.separator + "evgen"
			+ File.separator + "eclipse-workspace" + File.separator + "Assignment3" + File.separator + "config");
	private JMenuItem clear;

	public MyFrame() throws IOException {
		initComponents();
		this.addMouseListener(this);
		this.addComponentListener(this);
		this.thisGuisGame = new Game(new ArrayList<Pacman>(), new ArrayList<Fruit>());
	}

//*******component listener*******
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
		height = this.getHeight() - 79;
		width = this.getWidth() - 22;
		this.imagePanel.resizeImage(width, height);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < this.thisGuisGame.getPackCollection().size(); i++) {
			Pacman current = this.thisGuisGame.getPackCollection().get(i);
<<<<<<< HEAD
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y() - 10),
=======
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y()-10),
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
					(int) (this.gameMap.gpsToPixel(current.getLocation()).x()) + 44, getGraphics());
		}
		for (Fruit current : this.thisGuisGame.getFruitCollection()) {
			GpsCoord gpsOfFruit = current.getLocation();
			Point3D gps2pixel = this.gameMap.gpsToPixel(gpsOfFruit);
<<<<<<< HEAD
			this.imagePanel.drawingFruit((int) gps2pixel.y() - 10, (int) gps2pixel.x() + 44, getGraphics());
=======
			this.imagePanel.drawingFruit((int) gps2pixel.y()-10, (int) gps2pixel.x() + 44, getGraphics());
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

//*******mouse listener******* 
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (isPackmanAdding) {
			lastClicked = new Point3D(arg0.getX(), arg0.getY(), 0);
			this.imagePanel.drawingPackman(arg0.getX() - 10, arg0.getY() - 10, getGraphics());
			Pacman current = null;
			try {
				current = new Pacman(new GpsCoord(this.gameMap.clickedToAddPoint()), arg0.getX() - 10, arg0.getY() - 10,
						1, 1);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thisGuisGame.addPacman(current);
		} else if (isFruitAdding) {
			lastClicked = new Point3D(arg0.getX(), arg0.getY(), 0);
			this.imagePanel.drawingFruit(arg0.getX() - 10, arg0.getY() - 10, getGraphics());
			Fruit current = null;
			try {
				current = new Fruit(new GpsCoord(this.gameMap.clickedToAddPoint()), arg0.getX() - 10, arg0.getY() - 10,
						1);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thisGuisGame.addFruit(current);
		}
		width = this.getWidth() - 22;
		height = this.getHeight() - 79;
		lastClicked = new Point3D(arg0.getX(), arg0.getY(), 0);
		try {
			System.out.println("***" + this.gameMap.clickedToAddPoint());
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

//*******private methods*******
	private void initComponents() throws IOException {
		// now building the menu bar and all its features
		JMenuBar menuBar = new JMenuBar();
		// menu first - external "button:
		this.mainMenu = new JMenu("Menu");
		mainMenu.setMnemonic(KeyEvent.VK_R);
		mainMenu.addMenuListener(this);
		// creating subMenus
		this.subMenuDefaultGame = new JMenu("default game");
		subMenuDefaultGame.setMnemonic(KeyEvent.VK_R);
		subMenuDefaultGame.addMenuListener(this);
		// creating all menuItems for default game
		this.existingGame = new JMenu("play existing game");
		existingGame.setMnemonic(KeyEvent.VK_R);
		existingGame.addMenuListener(this);
		this.loadCsv = new JMenuItem("load CSV file");
		loadCsv.setMnemonic(KeyEvent.VK_R);
		loadCsv.addActionListener(new MenuAction(this));
		existingGame.add(loadCsv);
		subMenuDefaultGame.add(existingGame);
		this.newGame = new JMenu("build your own game");
		newGame.setMnemonic(KeyEvent.VK_R);
		this.packman = new JMenuItem("add pacman");
		packman.setMnemonic(KeyEvent.VK_R);
		packman.addActionListener(new MenuAction(this));
		this.fruit = new JMenuItem("add fruit");
		fruit.setMnemonic(KeyEvent.VK_R);
		fruit.addActionListener(new MenuAction(this));
		this.saveAsCsv = new JMenuItem("save the game as CSV file");
		saveAsCsv.setMnemonic(KeyEvent.VK_R);
		saveAsCsv.addActionListener(new MenuAction(this));
		newGame.add(packman);
		newGame.add(fruit);
		newGame.add(saveAsCsv);
		subMenuDefaultGame.add(newGame);
		mainMenu.add(subMenuDefaultGame);
		// creating menu items
		this.saveAsKml = new JMenuItem("save as KML");
		saveAsKml.setMnemonic(KeyEvent.VK_R);
		saveAsKml.addActionListener(new MenuAction(this));
		this.play = new JMenuItem("run movment simulation");
		play.setMnemonic(KeyEvent.VK_R);
		play.addActionListener(new MenuAction(this));
		this.clear = new JMenuItem("clear all");
		clear.setMnemonic(KeyEvent.VK_R);
		clear.addActionListener(new MenuAction(this));
		// adding last menu items to the main one
		mainMenu.add(play);
		mainMenu.add(saveAsKml);
		mainMenu.add(clear);
		// adding the main menu to the menu bar
		menuBar.add(mainMenu);
		this.setJMenuBar(menuBar);
		this.gameMap = MapFactory.defaultMapInit();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameChangingImage = this.gameMap.getImage();
		this.imagePanel = new ImagePanel(this.gameChangingImage);
		this.getContentPane().add(imagePanel);
		this.pack();
		this.setVisible(true);
		height = this.getHeight() - 22;
		width = this.getWidth() - 79;
		isPackmanAdding = false;
		isFruitAdding = false;
	}

// *******action listener*******
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.equals(this.packman)) {
			System.out.println("packmans");
		}
	}

//*******menu listener*******
	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuSelected(MenuEvent e) {
		if (e.getSource().equals(this.subMenuDefaultGame)) {
			System.out.println("menu clicked");
		}
	}

	public Game getGame() {
		return this.thisGuisGame;
	}

	public void showGame(Game givenGame) {
		for (int i = 0; i < this.thisGuisGame.getPackCollection().size(); i++) {
			Pacman current = this.thisGuisGame.getPackCollection().get(i);
<<<<<<< HEAD
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y() - 10),
=======
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y()-10),
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
					(int) (this.gameMap.gpsToPixel(current.getLocation()).x()) + 44, getGraphics());
		}
		for (Fruit current : this.thisGuisGame.getFruitCollection()) {
			GpsCoord gpsOfFruit = current.getLocation();
			Point3D gps2pixel = this.gameMap.gpsToPixel(gpsOfFruit);
<<<<<<< HEAD
			this.imagePanel.drawingFruit((int) gps2pixel.y() - 10, (int) gps2pixel.x() + 44, getGraphics());
=======
			this.imagePanel.drawingFruit((int) gps2pixel.y()-10, (int) gps2pixel.x() + 44, getGraphics());
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
		}
	}

	public void start() throws InterruptedException {
<<<<<<< HEAD
		Game backUp = this.thisGuisGame.cloneGame();
		new Thread(new GuiWorker(thisGuisGame, this, imagePanel, gameMap)).start();
=======
//		while (!this.thisGuisGame.getFruitCollection().isEmpty()) {
//			this.thisGuisGame.move();
//			repaint();
//			Thread.sleep(50);
//			this.showGame(thisGuisGame);
//			Thread.sleep(40);
//		}
		new Thread(new GuiWorker(thisGuisGame,this,imagePanel,gameMap)).start();
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
	}

	// *******CSV converter*******
	public void loadCsvToGame(String path) throws IOException {
		if (!this.thisGuisGame.getFruitCollection().isEmpty() || !this.thisGuisGame.getPackCollection().isEmpty()) {
			paint(getGraphics());
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.thisGuisGame = Csv2Game.convertCsv2Game(path, this.gameMap);
		Iterator<?> it = thisGuisGame.getFruitCollection().iterator();
		int i = 0;
		while (it.hasNext()) {
			i++;
			System.out.println("fruit NUM " + i);
			it.next();
		}
		it = thisGuisGame.getPackCollection().iterator();
		i = 0;
		while (it.hasNext()) {
			i++;
			System.out.println("pac NUM " + i);
			it.next();
		}
		this.showGame(this.thisGuisGame);
		System.out.println("Shown");
	}

	public void saveGameAsCsv(String path) {
		Game2CSV.game2CSV(path, this.thisGuisGame);
		System.out.println("done");
	}

	public void saveKml(String path) {
		ArrayList<Paired> pairs = null;
		try {
			pairs = ShortestPathAlgo.findPaths(this.thisGuisGame);
		} catch (InvalidPropertiesFormatException e1) {
			System.out.println("ERR=> ShortestPathAlgo");
			e1.printStackTrace();
		}
		Game2Kml.game2Kml(pairs, path);

	}

	public void newGame() {
		this.thisGuisGame = new Game(new ArrayList<Pacman>(), new ArrayList<Fruit>());
	}

}