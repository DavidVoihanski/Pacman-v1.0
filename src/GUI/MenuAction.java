package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import File_format.Game2CSV;

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
	}
}