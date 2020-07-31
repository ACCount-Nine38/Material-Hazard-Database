package displays;

import javax.swing.*;
import objects.Materials;
import objects.Projects;
import displays.ProjectDetailsState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportScreen extends State {
	
	private JPanel panel, reportPanel, impactPanel;
	
	private JScrollPane report;

	private JLabel materialImageTitle, materialNameTitle, materialAmountTitle, materialColourTitle;

	private JButton wasteReduction, exit, save, back;
	private JLabel title, projectImpact, projectRating, rating;
	private JTextArea impactDescription;

	private JButton[] materialImages;
	private JLabel[] materialNames;
	private JLabel[] materialAmounts;
	private JLabel[] materialColours;
	
	private String settingString;
	public String projectColour;
	public int totalRating;
	public int totalToxic;
	public int totalNegative;
	public int totalDamage;
	
	//private String testImpact;
	//private ArrayList<Materials> testObject;


	public ReportScreen() {
		
	}

	@Override
	public void init() {
		/*
		MaterialState.materialLibrary = new ArrayList<Materials>();
=======

		testObject = new ArrayList<Materials>();
>>>>>>> refs/remotes/origin/master
		testString = new String ("images/groceryBags.png");
		testString2 = new String ("images/cigarettes.png");
		testImpact = new String ("This is a test to see if this message will fit within the boundaries of the designated JLabel, maybe it will maybe it won't but I just need this to be long so I'm just typing random thigns right now happy new year");
		
		MaterialState.materialLibrary.add(new Materials (("Grocery Bag"), testString, testString, testString, testString, testString, null, null, null, testString, getDefaultCloseOperation(), testString, 3, getDefaultCloseOperation(), testString));
		MaterialState.materialLibrary.add(new Materials (("Cigarettes"), testString, testString, testString, testString, testString, null, null, null, testString2, getDefaultCloseOperation(), testString2, 5, getDefaultCloseOperation(), testString));
		MaterialState.materialLibrary.add(new Materials (("Cigarettes"), testString, testString, testString, testString, testString, null, null, null, testString2, getDefaultCloseOperation(), testString2, 1, getDefaultCloseOperation(), testString));
		MaterialState.materialLibrary.add(new Materials (("Cigarettes"), testString, testString, testString, testString, testString, null, null, null, testString2, getDefaultCloseOperation(), testString2, 2, getDefaultCloseOperation(), testString));
		
<<<<<<< HEAD
		materialImages = new JLabel[MaterialState.materialLibrary.size()];
		materialNames = new JLabel[MaterialState.materialLibrary.size()];
		materialAmounts = new JLabel[MaterialState.materialLibrary.size()];
		materialColours = new JLabel[MaterialState.materialLibrary.size()];
		*/
		materialImages = new JButton[MaterialState.materialLibrary.size()];
		materialNames = new JLabel[MaterialState.materialLibrary.size()];
		materialAmounts = new JLabel[MaterialState.materialLibrary.size()];
		materialColours = new JLabel[MaterialState.materialLibrary.size()];

		totalRating = 0;
		totalToxic = 0;
		totalNegative = 0;
		totalDamage = 0;

	}
	

	@Override
	public void addJComponents() {
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		
		//Determine how many columns are needed
		reportPanel = new JPanel();
		reportPanel.setBounds(0, 0, WIDTH, HEIGHT);
		reportPanel.setLayout(new GridLayout(4, MaterialState.materialLibrary.size() + 1, 0, 0));
		panel.add(reportPanel);
		
		materialImageTitle = new JLabel();
		materialImageTitle.setText("Material Image");
		materialImageTitle.setOpaque(true);
		materialImageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialImageTitle.setBackground(Color.WHITE);
		materialImageTitle.setBounds(10, 0, 219, 50);
		reportPanel.add(materialImageTitle);
		
		//Change each image into the designated item 
		for (int totalImage = 0; totalImage < MaterialState.materialLibrary.size(); totalImage++){
			materialImages[totalImage] = new JButton();
			materialImages[totalImage].setBounds(11, 60 + totalImage * 165, 219, 157);
			materialImages[totalImage].setHorizontalAlignment(SwingConstants.CENTER);
			reportPanel.add(materialImages[totalImage]);
			settingString = MaterialState.materialLibrary.get(totalImage).getIconPath();
			materialImages[totalImage].setIcon(new ImageIcon (new ImageIcon (settingString).getImage().getScaledInstance(219, 157, 0)));
			materialImages[totalImage].setOpaque(true);
			materialImages[totalImage].setBackground(Color.white);
		}

		materialNameTitle = new JLabel();
		materialNameTitle.setText("Material Name");
		materialNameTitle.setOpaque(true);
		materialNameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialNameTitle.setBackground(Color.WHITE);
		materialNameTitle.setBounds(240, 0, 159, 50);
		reportPanel.add(materialNameTitle);
		
		//Creating the name labels for each material
		materialNames = new JLabel[MaterialState.materialLibrary.size()];
		for (int totalNames = 0; totalNames < MaterialState.materialLibrary.size(); totalNames++){
			settingString = MaterialState.materialLibrary.get(totalNames).getName();
			materialNames[totalNames] = new JLabel(settingString);
			materialNames[totalNames].setBounds(240, 60 + totalNames * 165, 159, 157);
			materialNames[totalNames].setFont(new Font(null, Font.ITALIC, 20));
			materialNames[totalNames].setHorizontalAlignment(SwingConstants.CENTER);
			reportPanel.add(materialNames[totalNames]);
			materialNames[totalNames].setOpaque(true);
			materialNames[totalNames].setBackground(Color.white);
			}
		
		materialAmountTitle = new JLabel();
		materialAmountTitle.setText("Material Amount");
		materialAmountTitle.setOpaque(true);
		materialAmountTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialAmountTitle.setBackground(Color.WHITE);
		materialAmountTitle.setBounds(408, 0, 124, 50);
		reportPanel.add(materialAmountTitle);
		
		//Creating the amount labels for each material
		materialAmounts = new JLabel[MaterialState.materialLibrary.size()];
		for (int totalAmount = 0; totalAmount < MaterialState.materialLibrary.size(); totalAmount++){
			settingString = Integer.toString(MaterialState.materialLibrary.get(totalAmount).getAmount());
			materialAmounts[totalAmount] = new JLabel(settingString);
			materialAmounts[totalAmount].setBounds(408, 60 + totalAmount * 165, 124, 157);
			materialAmounts[totalAmount].setFont(new Font(null, Font.ITALIC, 30));
			materialAmounts[totalAmount].setHorizontalAlignment(SwingConstants.CENTER);
			reportPanel.add(materialAmounts[totalAmount]);
			materialAmounts[totalAmount].setOpaque(true);
			materialAmounts[totalAmount].setBackground(Color.white);
			}
		
		materialColourTitle = new JLabel();
		materialColourTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialColourTitle.setText("Material Colour");
		materialColourTitle.setOpaque(true);
		materialColourTitle.setBackground(Color.WHITE);
		materialColourTitle.setBounds(543, 0, 256, 50);
		reportPanel.add(materialColourTitle);
		
		//Adding the colours for each material they chose
		materialColours = new JLabel[MaterialState.materialLibrary.size()];
		for (int totalColours = 0; totalColours < MaterialState.materialLibrary.size(); totalColours++){
			materialColours[totalColours] = new JLabel();
			settingString = Integer.toString(MaterialState.materialLibrary.get(totalColours).getDangerRating());
			if (Integer.valueOf(settingString) == 1)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour1.png"));
			else if (Integer.valueOf(settingString) == 2)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour2.png"));
			else if (Integer.valueOf(settingString) == 3)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour3.png"));
			else if (Integer.valueOf(settingString) == 4)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour4.png"));
			else if (Integer.valueOf(settingString) == 5)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour5.png"));
			
			materialColours[totalColours].setBounds(543, 60 + totalColours * 165, 256, 157);
			materialColours[totalColours].setFont(new Font(null, Font.ITALIC, 30));
			materialColours[totalColours].setHorizontalAlignment(SwingConstants.CENTER);

			reportPanel.add(materialColours[totalColours]);
			materialColours[totalColours].setBackground(Color.white);
		}
		
		//Adding the scroll bar
		report = new JScrollPane(reportPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		report.setBounds(27, 58, 824, 530);
		panel.add(report);
		
		wasteReduction = new JButton(new ImageIcon(new ImageIcon("images/possibleAlternatives.png").getImage().getScaledInstance(238, 30, 0)));
		wasteReduction.addActionListener(this);
		wasteReduction.setBounds(943, 710, 251, 42);
		panel.add(wasteReduction);
		
		back = new JButton(new ImageIcon (new ImageIcon ("images/back.png").getImage().getScaledInstance(260, 23, 0)));
		back.setBounds(23, 11, 275, 35);
		back.addActionListener(this);
		panel.add(back);
		
		exit = new JButton(new ImageIcon(new ImageIcon("images/exitProject.png").getImage().getScaledInstance(310, 90, 0)));
		exit.addActionListener(this);
		exit.setBounds(76, 599, 323, 102);
		panel.add(exit);
	
		getContentPane().add(panel);
		
		save = new JButton(new ImageIcon(new ImageIcon("images/saveProject.png").getImage().getScaledInstance(310, 90, 0)));
		save.setBounds(466, 599, 323, 102);
		save.addActionListener(this);
		panel.add(save);
		
		title = new JLabel("Project Report");
		title.setFont(new Font("times new roman", Font.ITALIC, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(68, 225, 0));
		title.setBounds(344, 22, 174, 25);
		panel.add(title);
		
		impactPanel = new JPanel();
		impactPanel.setBounds(886, 58, 365, 530);
		panel.add(impactPanel);

		impactDescription = new JTextArea();
		impactDescription.setText(ProjectDetailsState.textbox.getText());
		impactDescription.setLineWrap(true);
		impactDescription.setWrapStyleWord(true);
		impactDescription.setBounds(10, 10, 345, 530);
		impactPanel.add(impactDescription); 
		
		projectImpact = new JLabel("Project Impact");
		projectImpact.setFont(new Font("times new roman", Font.ITALIC, 20));
		projectImpact.setForeground(new Color(68, 225, 212));
		projectImpact.setBounds(999, 21, 151, 27);
		panel.add(projectImpact);
		
		projectRating = new JLabel("Project Colour:");
		projectRating.setForeground(new Color(68, 225, 212));
		projectRating.setFont(new Font("times new roman", Font.ITALIC, 20));
		projectRating.setBounds(935, 625, 151, 36);
		panel.add(projectRating);
		
		
		for (int i = 0; i < MaterialState.materialLibrary.size(); i++){
			totalRating = totalRating + Integer.valueOf(MaterialState.materialLibrary.get(i).getDangerRating());
		rating = new JLabel();
		rating.setHorizontalAlignment(SwingConstants.CENTER);
		rating.setForeground(new Color(68, 225, 212));
		rating.setFont(new Font("times new roman", Font.ITALIC, 50));
		
		//Determining the overall project colour
		if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 1 || Math.round(totalRating / MaterialState.materialLibrary.size()) == 2)
			projectColour = ("images/colour1.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 3 || Math.round(totalRating / MaterialState.materialLibrary.size()) == 4)
			projectColour = ("images/colour2.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 5 || Math.round(totalRating / MaterialState.materialLibrary.size()) == 6)
			projectColour = ("images/colour3.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 7 || Math.round(totalRating / MaterialState.materialLibrary.size()) == 8)
			projectColour = ("images/colour4.png");
		else
			projectColour = ("images/colour5.png");
		revalidate();
		rating.setIcon(new ImageIcon (projectColour));
		rating.setBounds(1072, 615, 122, 62);
		rating.setOpaque(true);
		panel.add(rating);
		}
		
		addMenuBar();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {

		if(event.getSource() == exit) {
			//Closes the screen and opens DatabaseState
			new DatabaseState();
			this.dispose();
		}
		
		if(event.getSource() == back) {
			//Goes back to Project Details screen
			new ProjectDetailsState();
			this.dispose();
		}
		
		if(event.getSource() == wasteReduction) {
			// Determines how many impactful items are in the cart
			for (int i = 0; i < MaterialState.materialLibrary.size(); i++){
				if ((MaterialState.materialLibrary.get(i).getEnvironmentalConcerns().getText().indexOf("toxic") == 1))
					totalToxic = totalToxic + 1;

				if ((MaterialState.materialLibrary.get(i).getDangerRating() >= 3))
					totalNegative = totalNegative + 1;

				
				if ((MaterialState.materialLibrary.get(i).getEnvironmentalConcerns().getText().indexOf("damage") == 1))
					totalDamage = totalDamage + 1;

		}
			//Opens message screen
			new PossibleAlternatives();
			this.dispose();
			
		}
		
		if(event.getSource() == save) {
			
			updateInfo();
			
			JOptionPane.showMessageDialog(null,
					"PROJECT SAVED! \n\n"
							+ "click 'ok' to continue...",
					"SUCCESS", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	private void updateInfo() {
		
		try {

			PrintWriter pr = new PrintWriter("libraries/" + LoginState.finalUsername);

			for(Projects project: DatabaseState.projectLibrary) {
				
				pr.println("X");
				pr.println(project.getName());
				pr.println(ProjectDetailsState.textbox.getText());
				
				if(project.getName().equals(DatabaseState.currentProject.getName())) {
					
					for(Materials material: MaterialState.materialLibrary) {    
					      
						pr.println("---");
						pr.println(material.getName());
						pr.println(material.getAmount());
						
					}
					
				} else {
					for(HashMap.Entry<String,Integer> material : project.getMaterials().entrySet()) {    
					      
						pr.println("---");
						pr.println(material.getKey());
						pr.println(material.getValue());
						
					}
				}

				pr.println("END");
				
			}

			pr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
