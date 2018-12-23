package GUI;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import javax.swing.JFileChooser;

import algorithm.ShortestPathAlgo;
import gameUtils.Game;
import gameUtils.Paired;

/**
 * this class was created to deal with "menu action" , i.e pressing the menu
 * buttons in the GUI window
 * 
 * @author Evgeny & David
 *
 */
class MenuAction implements ActionListener {
	private MyFrame guiInstance;

	/**
	 * basic constructor - only gets the GUI instance it was called from
	 * 
	 * @param instance
	 */
	public MenuAction(MyFrame instance) {
		this.guiInstance = instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add pacman")) {
			this.guiInstance.setIsAddingFruit(false);
			this.guiInstance.setIsAddingPac(true);
			// ******************************************//
		} else if (e.getActionCommand().equals("add fruit")) {
			this.guiInstance.setIsAddingPac(false);
			this.guiInstance.setIsAddingFruit(true);
			// ******************************************//
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
			// ******************************************//
		} else if (e.getActionCommand().equals("save the game as CSV file")) {
			this.guiInstance.setIsAddingFruit(false);
			this.guiInstance.setIsAddingPac(false);
			int retrival = guiInstance.fc.showSaveDialog(null);
			if (retrival == JFileChooser.APPROVE_OPTION) {
				guiInstance.saveGameAsCsv(guiInstance.fc.getSelectedFile().getPath());
			}
		}
		// ******************************************//
		else if (e.getActionCommand().equals("run movement simulation")) {
			this.guiInstance.setIsAddingFruit(false);
			this.guiInstance.setIsAddingPac(false);
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
		// ******************************************//
		else if (e.getActionCommand().equals("save as KML")) {
			this.guiInstance.setIsAddingFruit(false);
			this.guiInstance.setIsAddingPac(false);
			JFileChooser fc = guiInstance.fc;
			int returnValue = fc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				guiInstance.saveKml(selectedFile.getPath());
			}
		}
		// ******************************************//
		else if (e.getActionCommand().equals("clear all")) {
			this.guiInstance.setIsAddingFruit(false);
			this.guiInstance.setIsAddingPac(false);
			this.guiInstance.paint(this.guiInstance.getGraphics());
			this.guiInstance.newGame();
		}
	}
}
