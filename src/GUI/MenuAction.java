package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import javax.swing.JFileChooser;

import File_format.Game2Kml;
import algorithm.ShortestPathAlgo;
import gameUtils.Game;
import gameUtils.Paired;

class MenuAction implements ActionListener {
	private MyFrame guiInstance;

	public MenuAction(MyFrame instance) {
		this.guiInstance = instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add pacman")) {
			System.out.println("add packman");
			MyFrame.isFruitAdding = false;
			MyFrame.isPackmanAdding = true;
			////////////////
		} else if (e.getActionCommand().equals("add fruit")) {
			MyFrame.isPackmanAdding = false;
			MyFrame.isFruitAdding = true;
			///////////////
		} else if (e.getActionCommand().equals("load CSV file")) {
			JFileChooser fc = guiInstance.fc;
			int returnValue = fc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				try {
					guiInstance.loadCsvToGame(selectedFile.getPath());
				} catch (IOException e1) {
					System.out.println("wrong PATH");
					e1.printStackTrace();
				}
			}
			/////////////
		} else if (e.getActionCommand().equals("save the game as CSV file")) {
			int retrival = guiInstance.fc.showSaveDialog(null);
			if (retrival == JFileChooser.APPROVE_OPTION) {
				guiInstance.saveGameAsCsv(guiInstance.fc.getSelectedFile().getPath());
			}
		}
		/////////////
		else if (e.getActionCommand().equals("run movment simulation")) {
			Game game = guiInstance.getGame();
			try {
				ArrayList<Paired> pairs = ShortestPathAlgo.findPaths(game);
			} catch (InvalidPropertiesFormatException e1) {
				System.out.println("ERR=> ShortestPathAlgo");
				e1.printStackTrace();
			}
			try {
				guiInstance.start();
			} catch (InterruptedException e1) {
				System.out.println("ERR=> guiInstance.start()");
				e1.printStackTrace();
			}
		}
		/////////////
		else if (e.getActionCommand().equals("save as KML")) {
			System.out.println("saving as KML");
			Game game = guiInstance.getGame();
			ArrayList<Paired> pairs =null;
			try {
				pairs = ShortestPathAlgo.findPaths(game);
			} catch (InvalidPropertiesFormatException e1) {
				System.out.println("ERR=> ShortestPathAlgo");
				e1.printStackTrace();
			}
			Game2Kml.game2Kml(pairs);
		}
	}
}
