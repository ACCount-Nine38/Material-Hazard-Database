package displays;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;

import objects.Projects;

public class DatabaseState extends State {

	// Final and global variables
	private static final int SHIFT_AMOUNT = 60;
	private static final int SHIFT_SPEED = 14;
	
	public static Projects currentProject;

	// JComponents
	private JPanel panel, projectCartPanel;
	private JScrollPane projectCart;
	private JTextField nameField;
	private JLabel titleLabel, intro_project, intro_material, userLabel, projectImage, nameLabel,newNameLabel;
	private JButton logout, addNewProject, removeProject, openProject, save, cancel,
			addMaterial, selectedProjectButton;

	// Arrays/Lists
	public static ArrayList<Projects> projectLibrary;
	private ArrayList<JButton> projectButtonContainer;
	private ArrayList<JButton> projectButtonCart;
	private ArrayList<JComponent> shiftUp;
	private ArrayList<JComponent> shiftDown;
	private ArrayList<JComponent> shiftLeft;
	private ArrayList<JComponent> shiftRight;

	// Other fields and variables
	// private Users selectedUser;
	private Projects selectedProject;
	private JButton selectedButton;
	private Timer componentForwardTimer, componentBackwardTimer;
	private int moveAmount, inputOrder;
	private String inputImagePath;

	private File userLibrary;

	@Override
	public void init() {

		projectLibrary = new ArrayList<Projects>();
		projectButtonContainer = new ArrayList<JButton>();
		projectButtonCart = new ArrayList<JButton>();
		shiftUp = new ArrayList<JComponent>();
		shiftDown = new ArrayList<JComponent>();
		shiftLeft = new ArrayList<JComponent>();
		shiftRight = new ArrayList<JComponent>();

		selectedProject = null;

		componentForwardTimer = new Timer(1, this);
		componentBackwardTimer = new Timer(1, this);

		moveAmount = 0;
		inputOrder = 0;

	}

	@Override
	public void addJComponents() {

		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);

		addMainScreenComponents();

		addInfoComponents();

		add(panel);

		titleLabel = new JLabel("Project  Library");
		titleLabel.setBounds(0, 70, 1280, 80);
		titleLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 60));
		titleLabel.setForeground(new Color(68, 225, 212));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(titleLabel);

		userLabel = new JLabel("User :  " + LoginState.finalUsername);
		userLabel.setBounds(50, 685, 1250, 50);
		userLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 45));
		userLabel.setForeground(new Color(68, 225, 212));
		panel.add(userLabel);

		// loadProjects();

	}

	public void addMainScreenComponents() {

		intro_project = new JLabel("Projects:");
		intro_project.setBounds(140, 140, 1000, 80);
		intro_project.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 50));
		intro_project.setForeground(new Color(68, 225, 212));
		shiftUp.add(intro_project);
		panel.add(intro_project);

		projectCartPanel = new JPanel();
		projectCartPanel.setLayout(new BoxLayout(projectCartPanel, BoxLayout.X_AXIS));
		projectCartPanel.setBounds(0, 0, WIDTH, HEIGHT);

		projectCart = new JScrollPane(projectCartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		projectCart.setBounds(140, 220, 1000, 200);
		shiftUp.add(projectCart);
		panel.add(projectCart);

		addNewProject = new JButton(
				new ImageIcon(new ImageIcon("images/newProject.png").getImage().getScaledInstance(140, 140, 0)));
		addNewProject.setBounds(990, 500, 150, 150);
		addNewProject.addActionListener(this);
		shiftRight.add(addNewProject);
		panel.add(addNewProject);

		openProject = new JButton(
				new ImageIcon(new ImageIcon("images/openProject.png").getImage().getScaledInstance(140, 140, 0)));
		openProject.setBounds(590, 500, 150, 150);
		openProject.addActionListener(this);
		shiftDown.add(openProject);
		panel.add(openProject);
		
		removeProject = new JButton(
				new ImageIcon(new ImageIcon("images/delete project.png").getImage().getScaledInstance(140, 140, 0)));
		removeProject.setBounds(790, 500, 150, 150);
		removeProject.addActionListener(this);
		shiftDown.add(removeProject);
		panel.add(removeProject);

		// ================================================================================================================
		projectImage = new JLabel(
				new ImageIcon(new ImageIcon("images/newProject.png").getImage().getScaledInstance(250, 250, 0)));
		projectImage.setOpaque(true);
		projectImage.setBounds(-140 -  SHIFT_AMOUNT * SHIFT_SPEED / 2, 250 , 270, 270);
		shiftRight.add(projectImage);
		panel.add(projectImage);

		nameLabel = new JLabel("Selected Project: ");
		nameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		nameLabel.setForeground(new Color(68, 225, 212));
		nameLabel.setBounds(150, 515, 350, 50);
		shiftDown.add(nameLabel);
		panel.add(nameLabel);

		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		nameField.setBounds(150, 575, 350, 45);
		panel.add(nameField);

	}

	private void addInfoComponents() {
		
		newNameLabel = new JLabel("Project Name:");
		newNameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newNameLabel.setForeground(new Color(68, 225, 212));
		newNameLabel.setBounds(665, 260 -  SHIFT_AMOUNT * SHIFT_SPEED / 2, 350, 50);
		shiftDown.add(newNameLabel);
		panel.add(newNameLabel);

		save = new JButton(
				new ImageIcon(new ImageIcon("images/checkmark.png").getImage().getScaledInstance(65, 65, 0)));
		save.addActionListener(this);
		save.setBounds(680, 440 + SHIFT_AMOUNT * SHIFT_SPEED / 2, 75, 75);
		shiftUp.add(save);
		panel.add(save);

		cancel = new JButton(new ImageIcon(new ImageIcon("images/clear.png").getImage().getScaledInstance(65, 65, 0)));
		cancel.addActionListener(this);
		cancel.setBounds(850, 440 + SHIFT_AMOUNT * SHIFT_SPEED / 2, 75, 75);
		shiftUp.add(cancel);
		panel.add(cancel);

		logout = new JButton(
				new ImageIcon(new ImageIcon("images/back2.png").getImage().getScaledInstance(140, 65, 0)));
		logout.setBounds(40, 40, 150, 75);
		logout.addActionListener(this);
		shiftUp.add(logout);
		panel.add(logout);

		loadLibrary();

	}

	private void loadLibrary() {

		File projectFolder = new File("libraries");
		File selectedProject = null;

		for (int i = 0; i < projectFolder.listFiles().length; i++) {

			if (projectFolder.listFiles()[i].getName().equals(LoginState.finalUsername)) {

				selectedProject = projectFolder.listFiles()[i];
			}

		}

		Scanner input;
		try {

			input = new Scanner(selectedProject);

			while (input.hasNext()) {

				if (input.nextLine().equals("X")) {

					Projects project = new Projects(input.nextLine(), new HashMap<String, Integer>(), new JTextArea());
					projectLibrary.add(project);
					addProjectToScrollPanel(project);
					
					project.getEnvironmentalField().setText(input.nextLine());
					
					while(true) {
						
						String condition = input.nextLine().trim();
						if(condition.equals("END")) {
							
							break;
							
						}
						
						project.getMaterials().put(input.nextLine(), Integer.parseInt(input.nextLine()));
						
					}

				}

			}

		} catch (FileNotFoundException error) {

			error.printStackTrace();

		}

	}

	//
	private void addProjectToScrollPanel(Projects project) {

		JButton projectButton = new JButton(project.getName());
		projectButton.setBounds(0, 0, 175, 175);
		projectButton.setMaximumSize(projectButton.getSize());
		projectButton.setMinimumSize(projectButton.getSize());
		projectButton.setPreferredSize(projectButton.getSize());
		projectButton
				.setIcon(new ImageIcon(new ImageIcon("images/project.png").getImage().getScaledInstance(175, 175, 0)));
		projectButton.setHorizontalTextPosition(SwingConstants.CENTER);
		projectButton.setForeground(new Color(68, 225, 212));
		projectButton.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		projectButton.setOpaque(true);
		projectButton.setBorderPainted(false);
		projectButton.addActionListener(this);
		projectButtonContainer.add(projectButton);
		projectCartPanel.add(projectButton);

		repaint();

	}

	private void saveInfo() {

		try {

			PrintWriter pr = new PrintWriter(
					new BufferedWriter(new FileWriter("libraries/" + LoginState.finalUsername, true)));

			pr.println("X");
			pr.println(nameField.getText());
			pr.println("");
			pr.println("END");

			pr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	} 
	
	private void updateInfo() {
		
		try {

			PrintWriter pr = new PrintWriter("libraries/" + LoginState.finalUsername);

			for(Projects project: projectLibrary) {
				
				pr.println("X");
				pr.println(project.getName());
				pr.println(project.getEnvironmentalField().getText());
				
				for(HashMap.Entry<String,Integer> material : project.getMaterials().entrySet()) {    
				      
					pr.println("---");
					pr.println(material.getKey());
					pr.println(material.getValue());
					
				}

				pr.println("END");
				
			}

			pr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		for (int i = 0; i < projectButtonContainer.size(); i++) {

			if (event.getSource() == projectButtonContainer.get(i)) {

				if(selectedButton != null) {
					selectedButton.setIcon(new ImageIcon("images/project.png"));
				}
				
				selectedProject = projectLibrary.get(i);
				selectedButton = projectButtonContainer.get(i);
				projectButtonContainer.get(i).setIcon(new ImageIcon(
						new ImageIcon("images/currentProject.png").getImage().getScaledInstance(175, 175, 0)));
				repaint();

				nameField.setText(selectedProject.getName());
				

			}

		}

		if (event.getSource() == componentForwardTimer) {

			for (JComponent component : shiftUp) {

				component.setBounds(component.getX(), component.getY() - SHIFT_SPEED / 2, component.getWidth(),
						component.getHeight());

			}
			for (JComponent component : shiftDown) {

				component.setBounds(component.getX(), component.getY() + SHIFT_SPEED / 2, component.getWidth(),
						component.getHeight());

			}
			for (JComponent component : shiftLeft) {

				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(),
						component.getHeight());

			}
			for (JComponent component : shiftRight) {

				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(),
						component.getHeight());

			}

			nameField.setBounds(nameField.getX() + 8, nameField.getY() - 4, nameField.getWidth(),
					nameField.getHeight());

			repaint();

			moveAmount--;

			if (moveAmount == 0) {

				componentForwardTimer.stop();

			}

		} else if (event.getSource() == componentBackwardTimer) {

			for (JComponent component : shiftUp) {

				component.setBounds(component.getX(), component.getY() + SHIFT_SPEED / 2, component.getWidth(),
						component.getHeight());

			}
			for (JComponent component : shiftDown) {

				component.setBounds(component.getX(), component.getY() - SHIFT_SPEED / 2, component.getWidth(),
						component.getHeight());

			}
			for (JComponent component : shiftLeft) {

				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(),
						component.getHeight());

			}
			for (JComponent component : shiftRight) {

				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(),
						component.getHeight());

			}

			nameField.setBounds(nameField.getX() - 8, nameField.getY() + 4, nameField.getWidth(),
					nameField.getHeight());

			repaint();

			moveAmount--;

			if (moveAmount == 0) {

				nameField.setEditable(false);
				componentBackwardTimer.stop();

			}

		} else if (event.getSource() == addNewProject) {

			nameField.setEditable(true);

			componentForwardTimer.start();
			moveAmount = SHIFT_AMOUNT;

			nameField.setText("");

			selectedProject = new Projects("", new HashMap<String, Integer>(), new JTextArea());

		} else if (event.getSource() == logout) {

			new LoginState();
			this.dispose();;

		} else if (event.getSource() == save) {

			if (selectedProject != null) {

				projectLibrary.add(new Projects(nameField.getText(), new HashMap<String, Integer>(), new JTextArea()));

				selectedProjectButton = new JButton(nameField.getText());
				selectedProjectButton.setIcon(
						new ImageIcon(new ImageIcon("images/project.png").getImage().getScaledInstance(175, 175, 0)));
				selectedProjectButton.setBounds(0, 0, 175, 175);
				selectedProjectButton.setMaximumSize(selectedProjectButton.getSize());
				selectedProjectButton.setMinimumSize(selectedProjectButton.getSize());
				selectedProjectButton.setPreferredSize(selectedProjectButton.getSize());
				selectedProjectButton.setHorizontalTextPosition(SwingConstants.CENTER);
				selectedProjectButton.setForeground(new Color(68, 225, 212));
				selectedProjectButton.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
				selectedProjectButton.setOpaque(true);
				selectedProjectButton.setBorderPainted(false);
				selectedProjectButton.addActionListener(this);

				projectButtonCart.add(selectedProjectButton);
				projectButtonContainer.add(selectedProjectButton);
				projectCartPanel.add(selectedProjectButton);

				projectCartPanel.revalidate();
				projectCartPanel.repaint();

				saveInfo();

				nameField.setText("");
				componentBackwardTimer.start();
				moveAmount = SHIFT_AMOUNT;

			} else // display message dialogue for invalid input
				JOptionPane.showMessageDialog(null, "Please select a project\n\n" + "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);

		} else if (event.getSource() == cancel) {

			selectedProject = null;

			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();

		} else if (event.getSource() == openProject) {

			if(selectedProject != null) {
				
				currentProject = selectedProject;
				new MaterialState();
				this.dispose();
				
			} else {
				
				JOptionPane.showMessageDialog(null, "Please select a project\n\n" + "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
			}

		} else if(event.getSource() == removeProject) {
			
			if(selectedProject != null) {
				
				projectLibrary.remove(selectedProject);
				projectButtonCart.remove(selectedProjectButton);
				projectButtonContainer.remove(selectedProjectButton);
				projectCartPanel.remove(selectedProjectButton);
				
				updateInfo();

				for(int i = 0; i < projectLibrary.size(); i++) {
					
					projectButtonContainer.get(i).setText(projectLibrary.get(i).getName());
					
				}
					
				projectCartPanel.revalidate();
				projectCartPanel.repaint();
				
				repaint();
				
				selectedButton = null;
				selectedProjectButton = null;
				
				nameField.setText("");
				
			} else {
				
				JOptionPane.showMessageDialog(null, "Please select a project\n\n" + "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
			}
			
		}

	}

}
