package GUI;

import gameUtils.Game;
import gameUtils.Map;

/**
 * this class is used to activate another thread for the showing of the movment
 * of the pacman robots on screen without smearing the screen
 * 
 * @author evgen
 *
 */
public class GuiWorker implements Runnable {
	private Game thisGuisGame;
	private MyFrame frame;
	Map gameMap;
	ImagePanel imagePanel;

	/**
	 * basic constructor
	 * 
	 * @param thisGuisGame the game instance which we are running
	 * @param frame        the GUI on which the game is shown
	 * @param imagePanel   the image from the map
	 * @param gameMap      the map of the game
	 */
	public GuiWorker(Game thisGuisGame, MyFrame frame, ImagePanel imagePanel, Map gameMap) {
		this.thisGuisGame = thisGuisGame;
		this.frame = frame;
		this.imagePanel = imagePanel;
		this.gameMap = gameMap;
	}

	@Override
	public void run() {

		while (!this.thisGuisGame.getFruitCollection().isEmpty()) {//while the game continues -> move the robots to eat the fruit
			thisGuisGame.move();
			frame.showGame();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.showGame();
		}

	}

}