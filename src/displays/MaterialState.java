package displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import objects.Materials;
import utils.CustomizationTool;
import utils.SortAToZ;
import utils.SortByRecentlyAdded;
import utils.SortZToA;

/*
 * Author: Alan Sun
 * 
 * the material state enables the user to select materials to be analyzed
 * can be saved and loaded from different projects
 * users are able to add, select, or remove materials for future use
 */
public class MaterialState extends State {

	// JComponents
	private JPanel panel, materialCartPanel, materialListPanel;
	private JScrollPane materialCart, materialList;
	private JTextField searchBar, addOrRemoveAmount, nameField, typeField, brandField, storageField, hyperToPurchaseField, 
		hyperToMSDField, hazardousRatingField; 
	private JTextArea environmentalField, firstAidField, dangersField;
	private JLabel materialImage, addOrRemoveLabel, filterLabel,
		nameLabel, typeLabel, brandLabel, storageLabel, hyperToPurchaseLabel, hyperToMSDLabel, environmentalLabel,
		firstAidLabel, dangersLabel, cartIcon, hazardousRating, cartLabel;
	private JButton backToMaterialSelection, addNewMaterial, addMaterial, removeMaterial, 
		search, sortAZ, sortZA, sortRecentlyAdded, proceedToReport, info, newIcon, save, delete, clear,
		toPurchase, toMSD;
	
	// ArrayLists that are used for storing materials and material buttons
	public static ArrayList<Materials> materialLibrary; // material cart
	private ArrayList<Materials> materialStorage; // container for all existing materials
	private ArrayList<Materials> materialContainer; // container for all filtered materials
	private ArrayList<JButton> materialButtonContainer;
	private ArrayList<JButton> materialButtonCart;
	// lists that categorizes which direction a JComponent moves during the sliding animation
	private ArrayList<JComponent> shiftUp;
	private ArrayList<JComponent> shiftDown;
	private ArrayList<JComponent> shiftLeft;
	private ArrayList<JComponent> shiftRight;

	// the current material being selected
	private Materials selectedMaterial;
	
	// timer components for a sliding animation of edition or making an material
	private Timer componentForwardTimer, componentBackwardTimer;
	
	// variable to keep track of the amount to be moved during the sliding animation 
	private int moveAmount;
	
	// variable for keeping track of recent materials being added
	private int inputOrder;

	// the init method overrides from the State class, it acts as the constructor of this class
	@Override
	public void init() {

		// Initializing all the variables below
		materialContainer = new ArrayList<Materials>();
		materialButtonContainer = new ArrayList<JButton>();
		materialButtonCart = new ArrayList<JButton>();
		materialLibrary = new ArrayList<Materials>();
		materialStorage = new ArrayList<Materials>();
		shiftUp = new ArrayList<JComponent>();
		shiftDown = new ArrayList<JComponent>();
		shiftLeft = new ArrayList<JComponent>();
		shiftRight = new ArrayList<JComponent>();

		selectedMaterial = null;
		
		componentForwardTimer = new Timer(1, this);
		componentBackwardTimer = new Timer(1, this);
		
		moveAmount = 0;
		inputOrder = 0;
		
	}

	// this method overrides from the State class, it adds all the JComponents needed for this screen
	
	// the addJComponents method overrides from the State class and displays all JCompoents on screen
	@Override
	public void addJComponents() {

		// a panel is used to hold all the JComponents on top
		panel = new JPanel(null);
		// set the background color and position of the panel
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);

		// add the JComponents for the material screen
		addMaterialScreenComponents();
		
		// add the JComponents for edition/add a new material
		addInfoComponents();

		// add the panel to the frame
		add(panel);

		// other methods being called
		loadMaterials();
		loadCart();
		addMenuBar();

	}

	// method that adds all the JComponents for the material screen
	
	// method that adds all the JComponents to the material selection screen
	public void addMaterialScreenComponents() {
		
		// this panel is made for the material cart, and new materials will be added on the X-axis
		materialCartPanel = new JPanel();
		materialCartPanel.setLayout(new BoxLayout(materialCartPanel, BoxLayout.X_AXIS));
		materialCartPanel.setBounds(0, 0, WIDTH, HEIGHT);
		
		// a scroll pane is added to the material cart panel so that all materials will be seen, if there are too many
		materialCart = new JScrollPane(materialCartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		materialCart.setBounds(190, 30, 930, 95);
		// this component will shift up during the sliding animation
		shiftUp.add(materialCart);
		panel.add(materialCart);
		
		// clear button is added to clear the material cart with just one click
		// resize the icon to 10 pixels smaller than the actual size to create a nice smooth white button boarder
		clear = new JButton(new ImageIcon(new ImageIcon("images/clear.png").getImage().getScaledInstance(85, 85, 0)));
		// enable button action for this component
		clear.addActionListener(this);
		clear.setBounds(1135, 30, 95, 95);
		shiftUp.add(clear);
		panel.add(clear);

		// the material list contains all the materials that can be added to the cart, it will be scrolled in the y-axis
		materialListPanel = new JPanel();
		materialListPanel.setLayout(new BoxLayout(materialListPanel, BoxLayout.Y_AXIS));
		materialListPanel.setBounds(0, 0, WIDTH, HEIGHT);
		
		// a scroll pane is added to material list so that all the materials can be seen
		materialList = new JScrollPane(materialListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		materialList.setBounds(40, 225, 400, 400);
		shiftLeft.add(materialList);
		panel.add(materialList);
		
		// label to indicate the material cart 
		cartLabel = new JLabel("Material Cart:");
		// set the font of this component to Gill Sans MT Condensed size 20 and bold
		cartLabel.setFont(new Font("Gill Sans MT Condensed", Font.BOLD, 20));
		// customize the text color
		cartLabel.setForeground(new Color(68, 225, 212));
		cartLabel.setBounds(40, 40, 200, 20);
		shiftUp.add(cartLabel);
		panel.add(cartLabel);
		
		// an icon to indicate the material cart
		cartIcon = new JLabel(new ImageIcon(new ImageIcon("images/library.png").getImage().getScaledInstance(50, 50, 0)));
		cartIcon.setBounds(80, 70, 50, 50);
		shiftUp.add(cartIcon);
		panel.add(cartIcon);
		
		// text box that enables the user to search for a material
		searchBar = new JTextField();
		searchBar.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		searchBar.setBounds(40, 150, 340, 50);
		shiftLeft.add(searchBar);
		panel.add(searchBar);

		// button that opens the information to a material
		info = new JButton(new ImageIcon(new ImageIcon("images/info.png").getImage().getScaledInstance(50, 47, 0)));
		info.addActionListener(this);
		info.setBounds(825, 145, 60, 60);
		panel.add(info);
		
		// button that allows the user to edit the image for the selected material
		newIcon = new JButton(new ImageIcon(new ImageIcon("images/edit pencil.png").getImage().getScaledInstance(50, 47, 0)));
		newIcon.addActionListener(this);
		newIcon.setBounds(825 - 7*SHIFT_AMOUNT, 145, 60, 60);
		newIcon.setVisible(false);
		panel.add(newIcon);

		// image that displays the current material
		materialImage = new JLabel();
		// set the background to white and turn the default opaque layer to true
		materialImage.setBackground(Color.white);
		materialImage.setOpaque(true);
		materialImage.setBounds(480, 150, 400, 400);
		panel.add(materialImage);
		
		// button that enables the user to search for a specific material upon click
		search = new JButton(new ImageIcon(new ImageIcon("images/search.png").getImage().getScaledInstance(50, 47, 0)));
		search.addActionListener(this);
		search.setBounds(385, 145, 60, 60);
		shiftLeft.add(search);
		panel.add(search);

		// label that indicates the add amount text field
		addOrRemoveLabel = new JLabel("Add Amount(g or ml):");
		addOrRemoveLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 26));
		// center the text on the label
		addOrRemoveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addOrRemoveLabel.setForeground(new Color(68, 225, 212));
		addOrRemoveLabel.setBounds(930, 155, 300, 50);
		shiftRight.add(addOrRemoveLabel);
		panel.add(addOrRemoveLabel);

		// button that adds a certain amount of materials to the cart
		addMaterial = new JButton(
				new ImageIcon(new ImageIcon("images/save amount.png").getImage().getScaledInstance(125, 125, 0)));
		addMaterial.addActionListener(this);
		addMaterial.setBounds(920, 300, 140, 140);
		shiftRight.add(addMaterial);
		panel.add(addMaterial);

		// button that removes the selected material from cart
		removeMaterial = new JButton(
				new ImageIcon(new ImageIcon("images/removeLibrary.png").getImage().getScaledInstance(125, 125, 0)));
		removeMaterial.addActionListener(this);
		removeMaterial.setBounds(1100, 300, 140, 140);
		shiftRight.add(removeMaterial);
		panel.add(removeMaterial);

		// button that customizes how much to add a material to the cart
		addOrRemoveAmount = new JTextField();
		addOrRemoveAmount.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		addOrRemoveAmount.setBounds(920, 225, 320, 50);
		shiftRight.add(addOrRemoveAmount);
		panel.add(addOrRemoveAmount);

		// button that enables the user to add a material that he/she researched
		addNewMaterial = new JButton(
				new ImageIcon(new ImageIcon("images/addNewMaterial.png").getImage().getScaledInstance(387, 60, 0)));
		addNewMaterial.setBounds(40, 650, 400, 75);
		addNewMaterial.addActionListener(this);
		shiftLeft.add(addNewMaterial);
		panel.add(addNewMaterial);

		// button that sorts all the materials by alphabetical order
		sortAZ = new JButton(
				new ImageIcon(new ImageIcon("images/sortAZ.png").getImage().getScaledInstance(105, 105, 0)));
		sortAZ.setBounds(480, 610, 120, 120);
		sortAZ.addActionListener(this);
		shiftDown.add(sortAZ);
		panel.add(sortAZ);

		// button that sorts all the materials by reverse alphabetical order
		sortZA = new JButton(
				new ImageIcon(new ImageIcon("images/sortZA.png").getImage().getScaledInstance(105, 105, 0)));
		sortZA.setBounds(620, 610, 120, 120);
		sortZA.addActionListener(this);
		shiftDown.add(sortZA);
		panel.add(sortZA);

		// button that sorts all the materials by when they are added
		sortRecentlyAdded = new JButton(
				new ImageIcon(new ImageIcon("images/sortRecentlyAdded.png").getImage().getScaledInstance(105, 105, 0)));
		sortRecentlyAdded.setBounds(760, 610, 120, 120);
		sortRecentlyAdded.addActionListener(this);
		shiftDown.add(sortRecentlyAdded);
		panel.add(sortRecentlyAdded);

		// label that indicates the filters
		filterLabel = new JLabel("Filters:");
		filterLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		filterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filterLabel.setForeground(new Color(68, 225, 212));
		filterLabel.setBounds(480, 565, 400, 50);
		shiftDown.add(filterLabel);
		panel.add(filterLabel);

		// button that enables the user to jump to the next screen
		proceedToReport = new JButton(
				new ImageIcon(new ImageIcon("images/proceed.png").getImage().getScaledInstance(307, 62, 0)));
		proceedToReport.setBounds(920, 650, 320, 75);
		proceedToReport.addActionListener(this);
		shiftDown.add(proceedToReport);
		panel.add(proceedToReport);
		
		// button that enables the user to open the purchase link
		toPurchase = new JButton(
				new ImageIcon(new ImageIcon("images/purchase material.png").getImage().getScaledInstance(307, 62, 0)));
		toPurchase.setBounds(920, 460, 320, 75);
		toPurchase.addActionListener(this);
		shiftDown.add(toPurchase);
		panel.add(toPurchase);
		
		// button that enables the user to open the MSD link
		toMSD = new JButton(
				new ImageIcon(new ImageIcon("images/msds link.png").getImage().getScaledInstance(307, 62, 0)));
		toMSD.setBounds(920, 555, 320, 75);
		toMSD.addActionListener(this);
		shiftDown.add(toMSD);
		panel.add(toMSD);
		
	}
	
	// method that adds all the JComponents from in the info/new material category
	
	// method that adds all the JComponents to the edit/new materials section
	private void addInfoComponents() {
		
		// label that indicates the name of the material
		nameLabel = new JLabel("Name: ");
		nameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		nameLabel.setForeground(new Color(68, 225, 212));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// placed out of the screen at first so that it can be shifted in by animation
		nameLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 50, 350, 50);
		shiftLeft.add(nameLabel);
		panel.add(nameLabel);
		
		// text field that enables the user to customize the name of the material
		nameField = new JTextField();
		nameField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		nameField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 100, 350, 50);
		shiftLeft.add(nameField);
		panel.add(nameField);
		
		// label that indicates the type of the material
		typeLabel = new JLabel("Material Type: ");
		typeLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		typeLabel.setForeground(new Color(68, 225, 212));
		typeLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 150, 350, 50);
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(typeLabel);
		panel.add(typeLabel);
		
		// text field that enables the user to customize the type of the material
		typeField = new JTextField();
		typeField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		typeField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 200, 350, 50);
		shiftLeft.add(typeField);
		panel.add(typeField);
		
		// label that indicates the brand of the material
		brandLabel = new JLabel("Brand: ");
		brandLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		brandLabel.setForeground(new Color(68, 225, 212));
		brandLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 250, 350, 50);
		brandLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(brandLabel);
		panel.add(brandLabel);
		
		// text field that enables the user to customize the brand of the material
		brandField = new JTextField();
		brandField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		brandField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 350, 50);
		shiftLeft.add(brandField);
		panel.add(brandField);
		
		// label that indicates the storage condition of the material
		storageLabel = new JLabel("Storage Condition: ");
		storageLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		storageLabel.setForeground(new Color(68, 225, 212));
		storageLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 350, 350, 50);
		storageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(storageLabel);
		panel.add(storageLabel);
		
		// text field that enables the user to customize the storage condition of the material
		storageField = new JTextField();
		storageField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		storageField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 400, 350, 50);
		shiftLeft.add(storageField);
		panel.add(storageField);
		
		// label that indicates the hyperlink of the material
		hyperToPurchaseLabel = new JLabel("Purchase Link: ");
		hyperToPurchaseLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToPurchaseLabel.setForeground(new Color(68, 225, 212));
		hyperToPurchaseLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 450, 350, 50);
		hyperToPurchaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(hyperToPurchaseLabel);
		panel.add(hyperToPurchaseLabel);
		
		// text field that enables the user to customize the hyperlink of the material
		hyperToPurchaseField = new JTextField();
		hyperToPurchaseField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToPurchaseField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 350, 50);
		shiftLeft.add(hyperToPurchaseField);
		panel.add(hyperToPurchaseField);
		
		// label that indicates the MSD link of the material
		hyperToMSDLabel = new JLabel("MSD Link: ");
		hyperToMSDLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToMSDLabel.setForeground(new Color(68, 225, 212));
		hyperToMSDLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 550, 350, 50);
		hyperToMSDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(hyperToMSDLabel);
		panel.add(hyperToMSDLabel);
		
		// text field that enables the user to customize the MSD link of the material
		hyperToMSDField = new JTextField();
		hyperToMSDField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToMSDField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 600, 350, 50);
		shiftLeft.add(hyperToMSDField);
		panel.add(hyperToMSDField);
		
		// label that indicates the hazardous rating of the material
		hazardousRating = new JLabel("Environmental Hazard Rating (0-10):");
		hazardousRating.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hazardousRating.setForeground(new Color(68, 225, 212));
		hazardousRating.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 675, 500, 50);
		hazardousRating.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(hazardousRating);
		panel.add(hazardousRating);
		
		// text field that enables the user to customize the hazardous rating of the material
		hazardousRatingField = new JTextField();
		hazardousRatingField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hazardousRatingField.setBounds(500 + 500 + SHIFT_AMOUNT*SHIFT_SPEED, 675, 50, 50);
		hazardousRatingField.addKeyListener(new KeyAdapter() {
			
			// method that restricts the excess characters being entered to the text field 
		    public void keyTyped(KeyEvent event) { 
		    	
		    	// limit text field to 2 characters
		        if (hazardousRatingField.getText().length() >= 2) 
		        	// remove excess characters
		        	event.consume(); 
		        
		    }  
		    
		});
		shiftLeft.add(hazardousRatingField);
		panel.add(hazardousRatingField);
		
		// label that indicates the environmental concerns of the material
		environmentalLabel = new JLabel("Environmental Concerns");
		environmentalLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		environmentalLabel.setForeground(new Color(68, 225, 212));
		environmentalLabel.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 50, 350, 50);
		environmentalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(environmentalLabel);
		panel.add(environmentalLabel);
		
		// text area that enables the user to customize the environmental concerns of the material
		environmentalField = new JTextArea();
		environmentalField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		environmentalField.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 100, 350, 150);
		// only allow text to display within the boarders of the JTextArea
		environmentalField.setLineWrap(true);
		environmentalField.setWrapStyleWord(true);
		shiftLeft.add(environmentalField);
		JScrollPane environmentalFieldScroll = new JScrollPane(environmentalField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		environmentalFieldScroll.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 100, 350, 150);
		shiftLeft.add(environmentalFieldScroll);
		panel.add(environmentalFieldScroll); 

		// label that indicates the first aid informations of the material
		firstAidLabel = new JLabel("Stability And React First Aid Measures");
		firstAidLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 18));
		firstAidLabel.setForeground(new Color(68, 225, 212));
		firstAidLabel.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 250, 350, 50);
		firstAidLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(firstAidLabel);
		panel.add(firstAidLabel);
		
		// text area that enables the user to customize the first aid informations of the material
		firstAidField = new JTextArea();
		firstAidField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		firstAidField.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 350, 150);
		firstAidField.setLineWrap(true);
		firstAidField.setWrapStyleWord(true);
		shiftLeft.add(firstAidField);
		JScrollPane firstAidFieldScroll = new JScrollPane(firstAidField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		firstAidFieldScroll.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 350, 150);
		shiftLeft.add(firstAidFieldScroll);
		panel.add(firstAidFieldScroll); 
		
		// label that indicates the dangers of the material
		dangersLabel = new JLabel("Dangers And Health Impacts");
		dangersLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 24));
		dangersLabel.setForeground(new Color(68, 225, 212));
		dangersLabel.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 450, 350, 50);
		dangersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(dangersLabel);
		panel.add(dangersLabel);
		
		// text area that enables the user to customize the dangers of the material
		dangersField = new JTextArea();
		dangersField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		dangersField.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 350, 150);
		dangersField.setLineWrap(true);
		dangersField.setWrapStyleWord(true);
		shiftLeft.add(dangersField);
		JScrollPane dangersFieldScroll = new JScrollPane(dangersField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dangersFieldScroll.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 350, 150);
		shiftLeft.add(dangersFieldScroll);
		panel.add(dangersFieldScroll); 
		
		// button that allows the user to save changed or new informations for a material
		save = new JButton(
				new ImageIcon(new ImageIcon("images/material save.png").getImage().getScaledInstance(170, 65, 0)));
		save.addActionListener(this);
		save.setBounds(60, 590 + SHIFT_AMOUNT*SHIFT_SPEED/2, 180, 75);
		shiftUp.add(save);
		panel.add(save);
		
		// button that deletes a material
		delete = new JButton(
				new ImageIcon(new ImageIcon("images/material_delete.png").getImage().getScaledInstance(170, 65, 0)));
		delete.addActionListener(this);
		delete.setBounds(280, 590 + SHIFT_AMOUNT*SHIFT_SPEED/2, 180, 75);
		shiftUp.add(delete);
		panel.add(delete);
		
		// button that leads back to materail selection
		backToMaterialSelection = new JButton(
				new ImageIcon(new ImageIcon("images/back to materials.png").getImage().getScaledInstance(390, 62, 0)));
		backToMaterialSelection.setBounds(60, 40 - SHIFT_AMOUNT*SHIFT_SPEED/2, 400, 75);
		backToMaterialSelection.addActionListener(this);
		shiftDown.add(backToMaterialSelection);
		panel.add(backToMaterialSelection);
		
	}

	// method that loads all the materials stored from the materials folder
	
	// method that loads all the materials stored in the materials folder
	private void loadMaterials() {

		// loads all the files and puts their name in a list
		File materialFolder = new File("materials");
		
		// loop through every single file to get the material informations
		for (int i = 0; i < materialFolder.listFiles().length; i++) {

			// opening the material file by getting the absolute path to the material file
			File material = new File(materialFolder.listFiles()[i].getAbsolutePath());

			// an array that contains all 14 attributes of a material
			String[] information = new String[14];

			// scanner object is declared to read the file
			Scanner reader = null;

			// try and catch to determine if the file exist
			try {

				// declare the scanner to read the designated material file
				reader = new Scanner(material);

				// index variable that keeps track of the current material information being read
				int index = 0;

				// loop through all 14 lines of each material and read its information
				while (reader.hasNext()) {

					information[index] = reader.nextLine();
					index++;
					
				}

				// a material object is then declared using the properties read
				Materials newMaterial = new Materials(information[0], information[1], information[2], information[3],
						information[4], information[5], new JTextArea(information[6]), new JTextArea(information[7]), 
						new JTextArea(information[8]), information[9],
						Integer.parseInt(information[10]), material.getAbsolutePath(), Integer.parseInt(information[11]),
						Integer.parseInt(information[12]), information[13]);
				
				// the next input order value for the materials will be determined by the largest input order from file +1
				if(Integer.parseInt(information[10]) > inputOrder) {
					
					inputOrder = Integer.parseInt(information[10])+1;
					
				}
				
				// add the material to the material container and storage
				materialContainer.add(newMaterial);
				materialStorage.add(newMaterial);
				

			} catch (FileNotFoundException error) {

				// print the stack trace to display errors
				error.printStackTrace();

			}

		}
		
		// method that adds every material as a button
		for(Materials material: materialContainer)
			
			addMaterialToScrollPanel(material);

		// screen has been updated, repaint to display updates
		repaint();

	}

	
	// method that loads the all the materials stored previously in the cart, if any
	private void loadCart() {
		
		// loop through every material in the storage
		for(Materials material: materialStorage) {
			
			// loads each material from the cart from the selected project in database
			if(DatabaseState.currentProject.getMaterials().containsKey(material.getName())) {
				
				// set the amount of the material by loading it from the database
				material.setAmount(DatabaseState.currentProject.getMaterials().get(material.getName()));
				
				// add it to the cart
				materialLibrary.add(material);
				
				// create a button for the scroll bar for that material
				JButton selectedMaterialButton = new JButton(
						new ImageIcon(material.getIcon().getImage().getScaledInstance(70, 70, 0)));
				selectedMaterialButton.setBounds(0, 0, 75, 75);
				selectedMaterialButton.setMaximumSize(selectedMaterialButton.getSize());
				selectedMaterialButton.setMinimumSize(selectedMaterialButton.getSize());
				selectedMaterialButton.setPreferredSize(selectedMaterialButton.getSize());
				selectedMaterialButton.addActionListener(this);

				// add it to the panel and button list
				materialButtonCart.add(selectedMaterialButton);
				materialCartPanel.add(selectedMaterialButton);

			}
			
		}
		
		// update all the changes on screen
		materialCartPanel.revalidate();
		materialCartPanel.repaint();
		
	}

	// shortcut method that adds a material 
	private void addMaterialToScrollPanel(Materials material) {

		JButton materialButton = new JButton(material.getName());
		materialButton.setBounds(0, 0, 380, 100);
		// set max, min and preferred size is required when adding to a scroll pane to preserve layout
		materialButton.setMaximumSize(materialButton.getSize());
		materialButton.setMinimumSize(materialButton.getSize());
		materialButton.setPreferredSize(materialButton.getSize());
		materialButton.setIcon(
				new ImageIcon(new ImageIcon("images/buttonBackground.png").getImage().getScaledInstance(380, 100, 0)));
		materialButton.setHorizontalTextPosition(SwingConstants.CENTER);
		materialButton.setForeground(Color.white);
		materialButton.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		materialButton.setOpaque(true);
		materialButton.setBorderPainted(false);
		materialButton.addActionListener(this);
		// add the button to the button list and the panel
		materialButtonContainer.add(materialButton);
		materialListPanel.add(materialButton);

	}
	

	// method that searches a material that contains the text entered on the search field
	private void search() {
		
		// makes sure that there are text on the text field to search
		if (searchBar.getText().length() > 0) {

			// temporary array to store the filtered materials
			ArrayList<Materials> searchedMaterialContainer = new ArrayList<Materials>();

			// remove all the buttons from the material list panel and clear materialButtonContainer
			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			// search through the storage to see if any materials contains the text on the search field
			for (Materials material : materialStorage) {

				if (material.getName().toLowerCase().contains(searchBar.getText().toLowerCase())) {

					searchedMaterialContainer.add(material);

				}

			}
			
			// update the material container to the filtered one
			materialContainer = searchedMaterialContainer;
			
			// add the filters buttons back to the material panel
			for(Materials material: materialContainer) {
				
				addMaterialToScrollPanel(material);
				
			}
			
			// update the screen to display changes
			materialList.revalidate();
			materialList.repaint();

		} else {

			// remove all the current materials in the material panel
			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}
			
			materialButtonContainer.clear();
			
			// if the user clears the search, then display all the materials
			materialContainer = materialStorage;
			
			// add the filters buttons back to the material panel
			for(Materials material: materialContainer) {
				
				addMaterialToScrollPanel(material);
				
			}
			
			// update the screen to display changes
			materialList.revalidate();
			materialList.repaint();

		}

	}
	
	// method that loads all the informations for the selected material
	private void updateAllFields() {
		
		// sets the informations to each field a material
		nameField.setText(selectedMaterial.getName());
		typeField.setText(selectedMaterial.getType());
		brandField.setText(selectedMaterial.getBrand());
		storageField.setText(selectedMaterial.getStorageCondition());
		hyperToPurchaseField.setText(selectedMaterial.getHyperlinkToPurchase());
		hyperToMSDField.setText(selectedMaterial.getHyperlinkToMSD());
		environmentalField.setText(selectedMaterial.getEnvironmentalConcerns().getText());
		firstAidField.setText(selectedMaterial.getStabilityAndReactFirstAidMeasures().getText());
		dangersField.setText(selectedMaterial.getDangers().getText());
		hazardousRatingField.setText(selectedMaterial.getDangerRating() + "");
		
	}
	
	// method that detects actions towards a JComponent
	@Override
	public void actionPerformed(ActionEvent event) {

		// checks if any of the buttons in the material selection list is being clicked
		for (int index = 0; index < materialButtonContainer.size(); index++) {

			if (event.getSource() == materialButtonContainer.get(index)) {

				// update the selected material to be the one user clicked on
				selectedMaterial = materialContainer.get(index);
				
				// update the amount of this material being added to cart
				addOrRemoveAmount.setText(selectedMaterial.getAmount() + "");
				
				// update the material icon to the selected material
				materialImage.setIcon(
						new ImageIcon(materialContainer.get(index).getIcon().getImage().getScaledInstance(400, 400, 0)));
				
				// update all the other informations for the selected material
				updateAllFields();
				
				// repaint the screen to display updates
				repaint();

			}

		}

		// checks if any buttons in the material cart is clicked
		for (int index = 0; index < materialLibrary.size(); index++) {

			if (event.getSource() == materialButtonCart.get(index)) {

				// update the selected material to be the one clicked in the cart
				selectedMaterial = materialLibrary.get(index);
				addOrRemoveAmount.setText(selectedMaterial.getAmount() + "");
				materialImage.setIcon(
						new ImageIcon(materialLibrary.get(index).getIcon().getImage().getScaledInstance(400, 400, 0)));

				updateAllFields();
				
				repaint();

			}

		}
		
		// if the forward timer is active, the display the shift animation for all components 
		if (event.getSource() == componentForwardTimer) {
			
			for(JComponent component: shiftUp) {
				
				component.setBounds(component.getX(), component.getY() - SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftDown) {
				
				component.setBounds(component.getX(), component.getY() + SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftLeft) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftRight) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			// the material image will shift differently than the other components
			materialImage.setBounds(materialImage.getX() - 7, materialImage.getY(), materialImage.getWidth(), materialImage.getHeight());
			
			repaint();
			
			// every time when the components move, the move amount timer will subtract by one
			moveAmount--;
			
			// when move amount is zero, the shifting stops and animation is over
			if(moveAmount == 0) {
				
				componentForwardTimer.stop();
				
				// set the visibility of icon editing to true
				newIcon.setVisible(true);
				
			}
			
		} 
		
		// backward timer reverses the animation to go back to the mains creen
		else if (event.getSource() == componentBackwardTimer) {
			
			for(JComponent component: shiftUp) {
				
				component.setBounds(component.getX(), component.getY() + SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftDown) {
				
				component.setBounds(component.getX(), component.getY() - SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftLeft) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftRight) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			materialImage.setBounds(materialImage.getX() + 7, materialImage.getY(), materialImage.getWidth(), materialImage.getHeight());
			
			repaint();
			
			moveAmount--;
			
			if(moveAmount == 0) {
				
				componentBackwardTimer.stop();
				
				// set the information pop up button to true and image editing to false
				info.setVisible(true);
				newIcon.setVisible(false);
				
			}
			
		} 
		
		// button action for when user adds a material
		else if (event.getSource() == addMaterial) {
			
			// user can only add a material if the selected material exist
			if (selectedMaterial != null) {
				
				// boolean condition to make sure that all inputs are integers
				boolean allInteger = true;

				// loop through every single character of the text field to check if all the values inputed are intergers
				for(int index = 0; index < addOrRemoveAmount.getText().length(); index++) {
					
					if((int)addOrRemoveAmount.getText().charAt(index) < 48 || (int)addOrRemoveAmount.getText().charAt(index) > 57) {
						allInteger = false;
					}
					
				}
				
				// if the input conains letters other than integers, display invalid amount message
				if(!allInteger) {

					JOptionPane.showMessageDialog(null,
							"Please enter a valid positive integer!\n\n"
									+ "click 'ok' to continue...",
							"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
					
					return;
					
				}

				// if input is 0 or blank, then display invalid amount message
				if(addOrRemoveAmount.getText().equals("0") || addOrRemoveAmount.getText().length() == 0) {
					
					JOptionPane.showMessageDialog(null,
							"Please enter a valid positive integer!\n\n"
									+ "click 'ok' to continue...",
							"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
					
					return;
					
				}
				
				// if all conditions passed, set the amount of that material to the amount in the text field
				selectedMaterial.setAmount(Integer.parseInt(addOrRemoveAmount.getText()));
				
				// checks to see if the cart already contains the selected material
				if(!materialLibrary.contains(selectedMaterial)) {
					
					// if the cart doesn't contain the selected material then add it to cart
					materialLibrary.add(selectedMaterial);
					
					// creating a new JButton to be added to the cart panel and button list
					JButton selectedMaterialButton = new JButton(
							new ImageIcon(selectedMaterial.getIcon().getImage().getScaledInstance(70, 70, 0)));
					selectedMaterialButton.setBounds(0, 0, 75, 75);
					selectedMaterialButton.setMaximumSize(selectedMaterialButton.getSize());
					selectedMaterialButton.setMinimumSize(selectedMaterialButton.getSize());
					selectedMaterialButton.setPreferredSize(selectedMaterialButton.getSize());
					selectedMaterialButton.addActionListener(this);

					materialButtonCart.add(selectedMaterialButton);
					materialCartPanel.add(selectedMaterialButton);

					// update the changes to display on screen
					materialCartPanel.revalidate();
					materialCartPanel.repaint();
					
				}

			} 
			
			// display message dialogue for invalid input
			else {
				
				JOptionPane.showMessageDialog(null,
						"Please select a material to add!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
			}

		}  
		
		// button action for when the remove material button is being pressed
		else if (event.getSource() == removeMaterial) {
			
			// loop through the cart to see if it contains the selected material to be removed
			for(int i = 0; i < materialLibrary.size(); i++) {
				
				if(materialLibrary.get(i) == selectedMaterial) {
					
					// set the added amount to 0
					selectedMaterial.setAmount(0);
					addOrRemoveAmount.setText(selectedMaterial.getAmount() + "");
					
					// remove material as a button and material
					materialLibrary.remove(i);
					materialButtonCart.remove(i);
					materialCartPanel.remove(i);

					// update the screen to display changes
					materialCartPanel.revalidate();
					materialCartPanel.repaint();
					
				}
				
			}
			
		} 
		
		// button action for when the sort by alphabetical A to Z is pressed
		else if (event.getSource() == sortAZ) {

			// remove all the buttons in the material selection panel first
			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			// sort the material list in alphabetical A to Z order using the customized comparator class
			Collections.sort(materialContainer, new SortAToZ());

			// re-add the materials to the panel in alphabetical A to Z order
			for(Materials material: materialContainer)
				
				addMaterialToScrollPanel(material);

			// update the changes to display on screen
			materialListPanel.revalidate();
			materialListPanel.repaint();

		} 
		
		// button action for when the sort by alphabetical Z to A is pressed
		else if (event.getSource() == sortZA) {

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			// sort the material list in alphabetical Z to A order using the customized comparator class
			Collections.sort(materialContainer, new SortZToA());

			for(Materials material: materialContainer)
				
				addMaterialToScrollPanel(material);

			materialListPanel.revalidate();
			materialListPanel.repaint();

		} 
		
		// button action for when the sort by recently added
		else if (event.getSource() == sortRecentlyAdded) {

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			// sort the material list in recently added order using the customized comparator class
			Collections.sort(materialContainer, new SortByRecentlyAdded());

			for(Materials material: materialContainer)
				
				addMaterialToScrollPanel(material);

			materialListPanel.revalidate();
			materialListPanel.repaint();

		} 
		
		// button action for the search button
		else if (event.getSource() == search) {

			// the search function handles the searching process
			search();

		} 
		
		// button actions for clearing the cart
		else if (event.getSource() == clear) {

			materialCartPanel.removeAll();
			materialButtonCart.clear();
			
			// update every material in the cart to set its amount added to 0
			for(Materials material: materialLibrary) {
				
				material.setAmount(0);
				
			}
			
			materialLibrary.clear();
			repaint();
			
		} 
		
		// button action for the material information button
		else if (event.getSource() == info) {

			// set the icon editing button visibility to false
			newIcon.setVisible(false);
			
			// if selected material is not defined, then display message for invalid selection
			if(selectedMaterial == null) {
				
				// display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Please select a material first!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			// start the shifting timer and start the animation
			componentForwardTimer.start();
			moveAmount = SHIFT_AMOUNT;
			info.setVisible(false);
			
		} 
		
		// button action for the material icon editing button
		else if (event.getSource() == newIcon) {

			// attach a file chooser to the button with file filters
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("jpg files", 
					"jpg", "png");
			chooser.setFileFilter(fileFilter);
			
			// disable select options for other file types besides jpg
			chooser.setAcceptAllFileFilterUsed(false);
			
			// open the file chooser and store the file type as an integer
			int selectedFile = chooser.showOpenDialog(this);

			// check if file is approved by the filter
			if (selectedFile == JFileChooser.APPROVE_OPTION) {

				File inputFile = chooser.getSelectedFile();

				// try and catch to see if file being read in exist
				try {

					Image loadedImage = ImageIO.read(inputFile);
					
					// the input image is a resized version of the loaded image to fit the button size
					materialImage.setIcon(
							new ImageIcon(loadedImage.getScaledInstance(400, 400, 0)));
					
					selectedMaterial.setIconPath(inputFile.getAbsolutePath());
					// repaint the panel in case image change didn't update
					materialImage.repaint();

				} catch (IOException error) {

					System.out.println("input file does not exsist");

				}

			} 

		} 
		
		// button action for adding a new material
		else if (event.getSource() == addNewMaterial) {

			// set the icon to null to enable the user to import a new icon
			materialImage.setIcon(null);
			
			// start the sliding animation timer 
			componentForwardTimer.start();
			moveAmount = SHIFT_AMOUNT;
			info.setVisible(false);
			
			// make all the text blank for the user to enter new informations
			nameField.setText("");
			typeField.setText("");
			brandField.setText("");
			storageField.setText("");
			hyperToPurchaseField.setText("");
			hyperToMSDField.setText("");
			environmentalField.setText("");
			firstAidField.setText("");
			dangersField.setText("");
			hazardousRatingField.setText("");
			
			// turn the selected material to a brand new material
			selectedMaterial = new Materials("", "", "", "", "", "", 
					environmentalField, firstAidField, dangersField, "images/tempImage.jpg", inputOrder, null, 0, 0, "");

		} 
		
		// button action for going back to material selection main page
		else if (event.getSource() == backToMaterialSelection) {

			// makes sure that the material name is not empty
			if(nameField.getText().length() == 0) {
				
				selectedMaterial = null;
				
			}
			
			// set the icon editing button visibility to false
			newIcon.setVisible(false);
			
			// start the reverse shift animation timer
			componentBackwardTimer.start();
			moveAmount = SHIFT_AMOUNT;

		} 
		
		// button action to open the purchase link of the selected material
		else if (event.getSource() == toPurchase) {

			CustomizationTool.openWebPage(hyperToPurchaseField.getText());

		} 
		
		// button action to open the MSD link of the selected material
		else if (event.getSource() == toMSD) {

			CustomizationTool.openWebPage(hyperToMSDField.getText());

		} 
		
		// button action to save the material after changing informations
		else if(event.getSource() == save) {
			
			// checks if the name being entered and the hazardous rating field are not empty
			if(nameField.getText().length() == 0 || hazardousRatingField.getText().length() == 0) {
				
				// display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Please validate your input\n"
						+ "The name field and hazardous rating cannot be empty!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			// makes sure that every character of the hazardous ratings field is an integer
			for(char character: hazardousRatingField.getText().toCharArray()) {
				
				if((int)character < 48 || (int)character > 57) {
					
					// display message dialogue for invalid input
					JOptionPane.showMessageDialog(null,
							"Environmental Hazardous Rating can only be an integer between 0 - 10\n"
							+ "Revalidate your input!\n\n"
									+ "click 'ok' to continue...",
							"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
					
					return;
					
				}
				
			}
			
			// makes sure that the hazardous ratings field is an integer between one and ten
			if(Integer.parseInt(hazardousRatingField.getText()) > 10) {
				
				// display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Environmental Hazardous Rating can only be an integer between 0 - 10\n"
						+ "Revalidate your input!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			newIcon.setVisible(false);
			
			// opens the file for the selected material 
			File material;
			
			if(selectedMaterial.getPath() == null) {
				material = new File("materials/" + nameField.getText());
			} else {
				material = new File(selectedMaterial.getPath());
			}
			
			// try and catch to see if the file exists
			try {
				
				// update all the material informations
				selectedMaterial.setName(nameField.getText());
				selectedMaterial.setType(typeField.getText());
				selectedMaterial.setBrand(brandField.getText());
				selectedMaterial.setStorageCondition(storageField.getText());
				selectedMaterial.setHyperlinkToPurchase(hyperToPurchaseField.getText());
				selectedMaterial.setHyperlinkToMSD(hyperToMSDField.getText());
				selectedMaterial.setEnvironmentalConcerns(environmentalField);
				selectedMaterial.setStabilityAndReactFirstAidMeasures(firstAidField);
				selectedMaterial.setDangers(dangersField);
				selectedMaterial.setDangerRating(Integer.parseInt(hazardousRatingField.getText()));
				
				// update all the material informations to the file
				PrintWriter pr = new PrintWriter(material);
				pr.println(selectedMaterial.getName());
				pr.println(selectedMaterial.getType());
				pr.println(selectedMaterial.getBrand());
				pr.println(selectedMaterial.getStorageCondition());
				pr.println(selectedMaterial.getHyperlinkToPurchase());
				pr.println(selectedMaterial.getHyperlinkToMSD());
				pr.println(selectedMaterial.getEnvironmentalConcerns().getText());
				pr.println(selectedMaterial.getStabilityAndReactFirstAidMeasures().getText());
				pr.println(selectedMaterial.getDangers().getText());
				pr.println(selectedMaterial.getIconPath());
				pr.println(selectedMaterial.getAddOrder());
				pr.println(selectedMaterial.getDangerRating());
				pr.println(0);
				pr.println(selectedMaterial.getState());
				
				pr.close();
				
			} catch (FileNotFoundException error) {
				
				// print the stack trace to display invalid file warning
				error.printStackTrace();
				
			}
			
			// updates all the containers that contains the selected material
			materialLibrary.clear();
			materialListPanel.removeAll();
			materialButtonContainer.clear();
			materialContainer.clear();
			materialStorage.clear();
			
			loadMaterials();
			
			materialList.revalidate();
			materialList.repaint();
			
			// start the reverse sliding animation to go back to the mains screen
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			
		} 
		
		// button action for deleting the selected material
		else if(event.getSource() == delete) {
			
			// remove all the informations associated to the selected material
			newIcon.setVisible(false);
			
			File material = new File(selectedMaterial.getPath());
			material.delete();
			
			materialListPanel.removeAll();
			materialButtonContainer.clear();
			materialContainer.clear();
			materialStorage.clear();
			
			loadMaterials();
			
			materialList.revalidate();
			materialList.repaint();
			
			materialImage.setIcon(null);
			materialImage.repaint();
			selectedMaterial = null;
			
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			
		} 
		
		// button action to go to the next state
		else if(event.getSource() == proceedToReport) {
			
			// open the next state and close the current state
			new ProjectDetailsState();
			this.dispose();
			
		}
		
	} 

}
