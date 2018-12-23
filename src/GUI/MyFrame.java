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
import gameUtils.Pacman;
import gameUtils.Paired;

/**
 * this class is the GUI window which the game is showed in
 * 
 * @author Evgeny & David
 *
 */
public class MyFrame extends JFrame implements MouseListener, ComponentListener, MenuListener, ActionListener {
	private static final long serialVersionUID = 1L;
	// game related data
	private Map gameMap;
	private Game thisGuisGame;
	// games map image
	private BufferedImage gameChangingImage;
	private ImagePanel imagePanel;
	// last pixel clicked
	private Point3D lastClicked;
	// boolean values used to indicate clicked buttons
	private boolean isPackmanAdding;
	private boolean isFruitAdding;
	// menu buttons and file chooser
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
	public JFileChooser fc;
	private JMenuItem clear;

	/**
	 * basic constructor
	 * 
	 * @throws IOException
	 */
	public MyFrame() throws IOException {
		initComponents();
		this.addMouseListener(this);
		this.addComponentListener(this);
		this.thisGuisGame = new Game(new ArrayList<Pacman>(), new ArrayList<Fruit>());
	}

	// ****public methods

	/**
	 * 
	 * @return the game this GUI represents as for right now
	 */
	public Game getGame() {
		return this.thisGuisGame;
	}

	/**
	 * shows all the fruits and pacman on the screen
	 */
	public void showGame() {
		showPacman();
		showFruit();
	}

	/**
	 * start moving the pacmans on the screen
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		new Thread(new GuiWorker(thisGuisGame, this, imagePanel, gameMap)).start();
	}

	/**
	 * restarts the game which connected to this GUI
	 */
	public void newGame() {
		this.thisGuisGame = new Game(new ArrayList<Pacman>(), new ArrayList<Fruit>());
	}

///////////////////////////////////////////////////
	// ONLY used for JUnit testing
	public void setLastClicked(Point3D p) {
		lastClicked = p;
	}
///////////////////////////////////////////////////

	/**
	 * 
	 * @return the height of GUI's window normalized only to the image
	 */
	public int getH() {
		return (this.getHeight() - 79);
	}

	/**
	 * 
	 * @return the width of GUI's window normalized only to the image
	 */
	public int getW() {
		return (this.getWidth() - 22);
	}

	/**
	 * 
	 * @return last point clicked on GUI's window as pixels
	 */
	public Point3D getlastClicked() {
		return this.lastClicked;
	}

	/**
	 * setting the boolean value of "whether you want that every click on the screen
	 * will add a pacman"
	 * 
	 * @param arg
	 */
	public void setIsAddingPac(boolean arg) {
		this.isPackmanAdding = arg;
	}

	/**
	 * setting the boolean value of "whether you want that every click on the screen
	 * will add a fruit"
	 * 
	 * @param arg
	 */
	public void setIsAddingFruit(boolean arg) {
		this.isFruitAdding = arg;
	}
	// *******CSV related*******

	/**
	 * used to handle the "load CSV" request, load a CSV file as a game
	 * configuration file
	 * 
	 * @param path the path in which the CSV file you want to load is found
	 * @throws IOException
	 */
	public void loadCsvToGame(String path) throws IOException {
		// in case there are objects on the "field" -> delete them before setting the
		// new
		// game
		if (!this.thisGuisGame.getFruitCollection().isEmpty() || !this.thisGuisGame.getPackCollection().isEmpty()) {
			newGame();
			paint(getGraphics());
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// set the new game from the CSV file
		this.thisGuisGame = Csv2Game.convertCsv2Game(path, this.gameMap);
		// show the new game
		this.showGame();
	}

	/**
	 * save this created game as a CSV file in a certain path
	 * 
	 * @param path the path you wish to save the file as CSV in
	 */
	public void saveGameAsCsv(String path) {
		Game2CSV.game2CSV(path, this.thisGuisGame);
		System.out.println("done");
	}

	/**
	 * used to handle the "save as KML" request, saves this certain file as a KML
	 * file which can be interpreted by google earth
	 * 
	 * @param path the path you wish to save the file as KML in
	 */
	public void saveKml(String path) {
		ArrayList<Paired> pairs = null;
		try {
			pairs = ShortestPathAlgo.findPaths(this.thisGuisGame);// as we want the full movement of the game we'll
																	// first "solve" it with the algorithm
		} catch (InvalidPropertiesFormatException e1) {
			System.out.println("ERR=> ShortestPathAlgo");
			e1.printStackTrace();
		}
		Game2Kml.game2Kml(pairs, path);

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

//this method is used for changing the image with every change of window size 
	@Override
	public void componentResized(ComponentEvent arg0) {
		// resize the actual image
		this.imagePanel.resizeImage(this.getW(), this.getH());
		// make the thread "go to sleep" to avoid smearing the screen
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// show the game again after every resize
		showGame();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

//*******mouse listener******* 

	// this is the method which enables us to put pacmans and fruits on the screen
	// by clicking the mouse
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// every click on pixels on the screen is saved in a variable to let
		// other classes
		// approach it
		this.lastClicked = new Point3D(arg0.getX(), arg0.getY());
		// in case the "add a pacman" button was clicked
		if (isPackmanAdding) {
			this.imagePanel.drawingPackman(arg0.getX() - 10, arg0.getY() - 10, getGraphics());// draw the pacman on the
																								// screen
			Pacman current = null;
			// create the new pacman
			try {
				current = new Pacman(new GpsCoord(this.gameMap.pixel2Gps()), arg0.getX() - 10, arg0.getY() - 10, 1, 1);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// add it to the game
			this.thisGuisGame.addPacman(current);
			// in case the "add a fruit" button was clicked
		} else if (isFruitAdding) {
			this.imagePanel.drawingFruit(arg0.getX() - 10, arg0.getY() - 10, getGraphics());// draw the fruit on the
			// screen
			Fruit current = null;
			// create the new fruit
			try {
				current = new Fruit(new GpsCoord(this.gameMap.pixel2Gps()), arg0.getX() - 10, arg0.getY() - 10, 1);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thisGuisGame.addFruit(current);
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

// *******action listener*******
	@Override
	public void actionPerformed(ActionEvent e) {
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

	}

	// *******private methods*******

//used to initialize GUIs data members (buttons etc..)
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
		this.fc = new JFileChooser();
		fc.setCurrentDirectory(new File("config"));
		menuBar.add(mainMenu);
		this.setJMenuBar(menuBar);
		// initializing all data members
		this.gameMap = new Map();
		this.gameMap.setGui(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameChangingImage = this.gameMap.getImage();
		this.imagePanel = new ImagePanel(this.gameChangingImage);
		this.getContentPane().add(imagePanel);
		this.isFruitAdding = false;
		this.isPackmanAdding = false;
		// setting the GUI and showing it
		this.pack();
		this.setVisible(true);
		isPackmanAdding = false;
		isFruitAdding = false;
	}

//used to "print" pacmans on the screen
	private void showPacman() {
		for (int i = 0; i < this.thisGuisGame.getPackCollection().size(); i++) {
			Pacman current = this.thisGuisGame.getPackCollection().get(i);
			this.imagePanel.drawingPackman((int) (this.gameMap.gps2Pixel(current.getLocation()).y() - 10),
					(int) (this.gameMap.gps2Pixel(current.getLocation()).x()) + 44, getGraphics());
		}
	}

//used to "print" fruit on the screen
	private void showFruit() {
		for (Fruit current : this.thisGuisGame.getFruitCollection()) {
			GpsCoord gpsOfFruit = current.getLocation();
			Point3D gps2pixel = this.gameMap.gps2Pixel(gpsOfFruit);
			this.imagePanel.drawingFruit((int) gps2pixel.y() - 10, (int) gps2pixel.x() + 44, getGraphics());
		}
	}

}