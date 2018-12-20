package GUI;


import Coords.GpsCoord;
import Geom.Point3D;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Map;
import gameUtils.Pacman;

public class GuiWorker implements Runnable{
	private Game thisGuisGame;
	private MyFrame frame;
	Map gameMap;
	ImagePanel imagePanel;
	public GuiWorker(Game thisGuisGame,MyFrame frame,ImagePanel imagePanel,Map gameMap) {
		this.thisGuisGame=thisGuisGame;
		this.frame=frame;
		this.imagePanel=imagePanel;
		this.gameMap=gameMap;
	}

	@Override
	public void run() {

		while (!this.thisGuisGame.getFruitCollection().isEmpty()) {
			thisGuisGame.move();

			frame.showGame(thisGuisGame);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.showGame(thisGuisGame);
		}

	}
	public void showGame(Game givenGame) {
		for (int i = 0; i < this.thisGuisGame.getPackCollection().size(); i++) {
			Pacman current = this.thisGuisGame.getPackCollection().get(i);
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y()-10),
					(int) (this.gameMap.gpsToPixel(current.getLocation()).x()) + 44, frame.getGraphics());
		}
		for (Fruit current : this.thisGuisGame.getFruitCollection()) {
			GpsCoord gpsOfFruit = current.getLocation();
			Point3D gps2pixel = this.gameMap.gpsToPixel(gpsOfFruit);
			this.imagePanel.drawingFruit((int) gps2pixel.y()-10, (int) gps2pixel.x() + 44, frame.getGraphics());
		}
	}
} 