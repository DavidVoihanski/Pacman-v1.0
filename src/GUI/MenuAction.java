package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("add packman")) {
			System.out.println("add packman");
			MyFrame.isFruitAdding=false;
			MyFrame.isPackmanAdding = true;
		}
		else if(e.getActionCommand().equals("add fruit")) {
			MyFrame.isPackmanAdding = false;
			MyFrame.isFruitAdding = true;
		}
	}
}