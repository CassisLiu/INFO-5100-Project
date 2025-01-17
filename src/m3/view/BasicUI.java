package m3.view;

import java.awt.Container;

import javax.swing.JFrame;

public abstract class BasicUI extends JFrame{
	
	public BasicUI() {
		create();
		Container con = getContentPane();
		add(con);
		addListeners();
		makeItVisible();
	}

	public abstract void create();

	public abstract void add(Container con);

	public abstract void addListeners();

	public void makeItVisible() {
		setSize(250, 300);
		setVisible(true);
	}

}
