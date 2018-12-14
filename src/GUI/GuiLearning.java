package GUI;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GuiLearning {

	public static void main(String[] args) {
		// 1. creating a String representation of the path to the image
		String imagePath = "Ariel1.png";
		BufferedImage myImage = null;
		// 2. "reading" the file into the object, since this step the object is
		// "ashkara" an image
		try {
			myImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 3. creating a Graphics object to handle the image
		Graphics2D g = (Graphics2D) myImage.getGraphics();
		// 4. we'll create a JLabel to "put" the image into
		JLabel picLabel = new JLabel(new ImageIcon(myImage));
		// 5. creating the panel which will be the container for the image label
		JPanel jPanel = new JPanel();
		// adding the label to the panel
		jPanel.add(picLabel);
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
		// creting a frame to contain all the.. well frame
		JFrame f = new JFrame();
		f.setJMenuBar(menuBar);
		// setting the size of the frame
		f.setSize(new Dimension(myImage.getWidth()+20, myImage.getHeight()+80));
		// adding the panel and the menu to the frame
		f.add(jPanel);
		// setting the frame visible
		f.setVisible(true);

	}

}
