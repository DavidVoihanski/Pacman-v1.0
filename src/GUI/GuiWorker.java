package GUI;

<<<<<<< HEAD
=======
import java.io.IOException;
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438

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
<<<<<<< HEAD

=======
		
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
		while (!this.thisGuisGame.getFruitCollection().isEmpty()) {
			thisGuisGame.move();

			frame.showGame(thisGuisGame);
			try {
<<<<<<< HEAD
				Thread.sleep(10);
=======
				Thread.sleep(30);
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.repaint();
			try {
<<<<<<< HEAD
				Thread.sleep(15);
=======
				Thread.sleep(30);
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.showGame(thisGuisGame);
		}
<<<<<<< HEAD

=======
		
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
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
<<<<<<< HEAD
}
=======
}
>>>>>>> de227fdb7519915e3b8f9734fdedbb63545b9438
