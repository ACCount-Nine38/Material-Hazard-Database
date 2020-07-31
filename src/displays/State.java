package displays;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import utils.CustomizationTool;

/*
 * The parent class of all states, top of the class hierarchy
 * contains default information to be used for other states and abstract methods
 */
public abstract class State extends JFrame implements ActionListener {

	// final variables
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	public static final int SHIFT_AMOUNT = 60;
	public static final int SHIFT_SPEED = 14;

	// a constructor that will be shared to all states
	public State() {

		// method call of all the abstract and initialization methods
		init();
		addJComponents();
		CustomizationTool.frameSetup(this);
		CustomizationTool.customCursor(this);

	}

	// method that adds the items relating to the menu bar
	public void addMenuBar() {

		// create a new JMenuBar item that stores different menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// create a new menu called control and add it to the menu bar
		JMenu controlMenu = new JMenu("File");
		menuBar.add(controlMenu);

		// creating the exit option under the control menu
		JMenuItem menuOption = new JMenuItem("Logout");

		JFrame currentFrame = this;
		// add an action listener for button actions when clicked
		menuOption.addActionListener(new ActionListener() {

			// method handles the current button's actions
			public void actionPerformed(ActionEvent e) {

				new LoginState();
				currentFrame.dispose();

			}

		});
		
		controlMenu.add(menuOption);
		
		// creating the exit option under the control menu
		JMenuItem databaseOption = new JMenuItem("Back to Database");

		// add an action listener for button actions when clicked
		databaseOption.addActionListener(new ActionListener() {

			// method handles the current button's actions
			public void actionPerformed(ActionEvent e) {

				new DatabaseState();
				currentFrame.dispose();

			}

		});
				
		new DatabaseState();
		this.dispose();

		controlMenu.add(databaseOption);
		
		// the help menu will include all the help related menu items
		JMenu helpMenu = new JMenu("Help");

		menuBar.add(helpMenu);

		// the description menu item will specify the screen descriptions and controls
		JMenuItem descriptionOption = new JMenuItem("Support");
		descriptionOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new HelpState();
				
			}

		});

		helpMenu.add(descriptionOption);
		
	}

	// method that initializes all the instances of a class, this replaces the
	// constructor
	public abstract void init();

	// abstract method that adds all the JComponents to a state
	public abstract void addJComponents();

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}