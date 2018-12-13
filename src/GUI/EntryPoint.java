package GUI;
import java.io.IOException;

import javax.swing.JFrame;


public class EntryPoint 
{
	public static void main(String[] args) throws IOException
	{
		MyFrame gameWindow = new MyFrame();
		gameWindow.setVisible(true);
		gameWindow.setResizable(true);;
		gameWindow.setSize(gameWindow.getMapOfGame().getImage().getWidth(),gameWindow.getMapOfGame().getImage().getHeight());
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
