package displays;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/*
 * Author: Alan Sun
 * 
 * class that handles the user login
 * enables the user to register for a new account
 * loads the databases to a specific user
 */
public class LoginState extends State {
		
	// container to store all the passwords to a username
	private HashMap<String, String> passwords;
	
	// JComponents of this state
	private JTextField usernameEntry, newUsernameEntry;
	private JPasswordField passwordEntry , newPasswordEntry, newPasswordReEntry;
	private JButton login, register, createAccount, back;
	private JLabel passwordLabel, usernameLabel, newUsernameLabel, newPasswordLabel, newPasswordReLabel;
	private JPanel panel;
	
	// ArrayList that contains all the JCompoents in its proper shift orientation
	// used when user switches from login to register
	private ArrayList<JComponent> loginShift;
	private ArrayList<JComponent> registerShift;
	
	// timer used for the animation between the login and registers
	private Timer componentForwardTimer, componentBackwardTimer;
	
	// other variables associated with this screen
	private int moveAmount;
	private boolean isClicked;
	private File passwordFile;
	
	// logged in username variable that enables other class to access
	public static String finalUsername = "";
	
	// the init method initializes all variables and calls base methods
	@Override
	public void init() {
		
		// Initializes all the variables and lists associated with this class
		passwords = new HashMap<String, String>();
		
		loginShift = new ArrayList<JComponent>();
		registerShift = new ArrayList<JComponent>();
		
		componentForwardTimer = new Timer(1, this);
		componentBackwardTimer = new Timer(1, this);
		
		moveAmount = 0;
		
		// method that reads all the usernames and passwords from the stored password file
		readPasswords();
		
	}

	// method that initializes all JComponents and places them onto the screen
	@Override
	public void addJComponents() {
		
		// a JPanel that allows other JComponents to be placed on top of it
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		
		//######################## Login Page ########################
		
		// username entry for login
		usernameEntry = new JTextField();
		// sets the font to Gill Sans MT Condensed size 40 and plain text
		usernameEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		// set the location and size of the text field
		usernameEntry.setBounds(WIDTH/2-200, 250, 400, 50);
		// add it to the login page for the sliding animation 
		loginShift.add(usernameEntry);
		panel.add(usernameEntry);
		
		// password entry for login
		passwordEntry = new JPasswordField();
		passwordEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		passwordEntry.setBounds(WIDTH/2-200, 350, 400, 50);
		// hide all the passwords using *
		passwordEntry.setEchoChar('*');
		loginShift.add(passwordEntry);
		panel.add(passwordEntry);
		
		// label that indicates the login input
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		usernameLabel.setForeground(new Color(68, 225, 212));
		usernameLabel.setBounds(WIDTH/2-200, 200, 400, 50);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginShift.add(usernameLabel);
		panel.add(usernameLabel);
		
		// label that indicates the password input
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		passwordLabel.setForeground(new Color(68, 225, 212));
		passwordLabel.setBounds(WIDTH/2-200, 300, 400, 50);
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginShift.add(passwordLabel);
		panel.add(passwordLabel);

		// button that enables the user to login
		login = new JButton(new ImageIcon(new ImageIcon("images/login.png").getImage().getScaledInstance(170, 90, 0)));
		// enable button action for this button
		login.addActionListener(this);
		login.setBounds(WIDTH/2-200, 450, 180, 100);
		loginShift.add(login);
		panel.add(login);
		
		// button that enables the user to register
		register = new JButton(new ImageIcon(new ImageIcon("images/register.png").getImage().getScaledInstance(170, 90, 0)));
		register.addActionListener(this);
		register.setBounds(WIDTH/2-200 + 220, 450, 180, 100);
		loginShift.add(register);
		panel.add(register);
		
		//######################## Register Page ########################
		
		// new username entry for register
		newUsernameEntry = new JTextField();
		newUsernameEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newUsernameEntry.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 200, 400, 50);
		// add it to the register page for sliding animation
		registerShift.add(newUsernameEntry);
		panel.add(newUsernameEntry);
		
		// new password entry for register
		newPasswordEntry = new JPasswordField();
		newPasswordEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordEntry.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 400, 50);
		newPasswordEntry.setEchoChar('*');
		registerShift.add(newPasswordEntry);
		panel.add(newPasswordEntry);
		
		// password retype entry for register
		newPasswordReEntry = new JPasswordField();
		newPasswordReEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordReEntry.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 400, 400, 50);
		newPasswordReEntry.setEchoChar('*');
		registerShift.add(newPasswordReEntry);
		panel.add(newPasswordReEntry);
		
		// label that indicates the new username entry
		newUsernameLabel = new JLabel("New Username: ");
		newUsernameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newUsernameLabel.setForeground(new Color(68, 225, 212));
		newUsernameLabel.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 150, 400, 50);
		newUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerShift.add(newUsernameLabel);
		panel.add(newUsernameLabel);
		
		// label that indicates the new password entry
		newPasswordLabel = new JLabel("New Password: ");
		newPasswordLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordLabel.setForeground(new Color(68, 225, 212));
		newPasswordLabel.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 250, 400, 50);
		newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerShift.add(newPasswordLabel);
		panel.add(newPasswordLabel);
		
		// label that indicates the retype password entry
		newPasswordReLabel = new JLabel("Retype Password: ");
		newPasswordReLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordReLabel.setForeground(new Color(68, 225, 212));
		newPasswordReLabel.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 350, 400, 50);
		newPasswordReLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerShift.add(newPasswordReLabel);
		panel.add(newPasswordReLabel);

		// button that finalizes creating an account
		createAccount = new JButton(new ImageIcon(new ImageIcon("images/create account.png").getImage().getScaledInstance(170, 65, 0)));
		createAccount.addActionListener(this);
		createAccount.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 180, 75);
		registerShift.add(createAccount);
		panel.add(createAccount);
		
		// button that goes back to the login
		back = new JButton(new ImageIcon(new ImageIcon("images/back2.png").getImage().getScaledInstance(170, 65, 0)));
		back.addActionListener(this);
		back.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED + 220, 500, 180, 75);
		registerShift.add(back);
		panel.add(back);
		
		// add the panel back to the frame
		add(panel);
		
	}
	
	// method that loads all the passwords and usernames stored in file
	private void readPasswords() {
		
		// opens the password file
		passwordFile = new File("others/passwords");
		
		// a scanner is declared to read the file
		Scanner input;
		
		// try and catch to see if the password file exist
		try {
			
			input = new Scanner(passwordFile);
			
			// read each username and password to the map
			while(input.hasNext()) {
				
				passwords.put(input.next(), input.next());
				
			}
			
		} catch (FileNotFoundException error) {
			
			// if the file is not found, then display error message
			error.printStackTrace();
			
		}
		
	}
	
	// method that enables the user to save their password after register
	private void savePasswords() {
		
		// try and catch to see if the password file location exist
		try {
			
			// print writer is declared to write the new username and password to the password file
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(passwordFile.getAbsolutePath(), true)));
			
			out.println(newUsernameEntry.getText() + " " + String.valueOf(newPasswordEntry.getPassword()));
			
			out.close();
			
		} catch (IOException error) {
			
			// if the file is not found, then display error message
			error.printStackTrace();
			
		}
		
	}
	
	// method that takes charge of component actions
	@Override
	public void actionPerformed(ActionEvent event) {
		
		// forward timer plays the animation to go to the register page
		if(event.getSource() == componentForwardTimer) {
			
			for(JComponent component: loginShift) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: registerShift) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			// repaint the screen to display updates
			repaint();
			
			// every time when the components move, the move amount timer will subtract by one
			moveAmount--;
			
			// when move amount is zero, the shifting stops and animation is over
			if(moveAmount == 0) {
				
				componentForwardTimer.stop();
				// while shifting, other components cannot be clicked
				isClicked = false;
				
			}
			
		} 
		
		// backward timer reverses the animation to go back to the login screen
		else if(event.getSource() == componentBackwardTimer) {
			
			for(JComponent component: loginShift) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: registerShift) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			repaint();
			
			moveAmount--;
			
			if(moveAmount == 0) {
				
				componentBackwardTimer.stop();
				isClicked = false;
				
			}
			
		} 
		
		// button action for the login button
		else if(event.getSource() == login) {
			
			// if shift animation is active, then user cannot use this button
			if(isClicked)
				return;
			
			// loads a temporary password from the inputed username
			String actualPassword = passwords.get(usernameEntry.getText());
			
			// if the password of the username is the the same as inputed, then login to the database of that user
			if(passwords.containsKey(usernameEntry.getText()) && actualPassword.equals(String.valueOf(passwordEntry.getPassword()))) {
				
				// set the logged in user to the username on the entry
				finalUsername = usernameEntry.getText();
				
				// open the database and dispose this state
				new DatabaseState();
				this.dispose();
				
			} else {
				
				// if password inputed does not match stored, then output invalid password message
				JOptionPane.showMessageDialog(null,
						"Invalid password or username! \n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
			}
			
		} 
		
		// if the user registers, then start the timer to go to the register page
		else if(event.getSource() == register) {
			
			moveAmount = SHIFT_AMOUNT;
			componentForwardTimer.start();
			isClicked = true;
			
			// reset the text fields in the register page
			newUsernameEntry.setText("");
			newPasswordEntry.setText("");
			newPasswordReEntry.setText("");
			
		} 
		
		// button action for when user goes back from register to login
		else if(event.getSource() == back) {
			
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			isClicked = true;
			
			passwordEntry.setText("");
			usernameEntry.setText("");
			
		} 
		
		// button action for when the user creates an account
		else if(event.getSource() == createAccount) {
			
			if(isClicked)
				return;
			
			// checks if the username is taken
			if(passwords.containsKey(newUsernameEntry.getText())) {
				
				JOptionPane.showMessageDialog(null,
						"ACCOUNT TAKEN, please try a different username! \n\n"
								+ "click 'ok' to continue...",
						"FAILED", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			} 
			
			// checks if the new username and password is not empty
			else if(String.valueOf(newPasswordEntry.getPassword()).length() == 0 || newUsernameEntry.getText().length() == 0) {
				
				JOptionPane.showMessageDialog(null,
						"Username and password cannot be empty! \n\n"
								+ "click 'ok' to continue...",
						"FAILED", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			} 
			
			// checks if the password typed is the same as the one retyped
			else if(!String.valueOf(newPasswordEntry.getPassword()).equals(String.valueOf(newPasswordReEntry.getPassword()))) {
				
				JOptionPane.showMessageDialog(null,
						"Password and retyped password does not match! \n\n"
								+ "click 'ok' to continue...",
						"FAILED", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			// if all the above are successful, then store the new username and password
			passwords.put(newUsernameEntry.getText(), String.valueOf(newPasswordEntry.getPassword()));
			
			// creates a new file under libraries for the database
			File newProject = new File("libraries/" + newUsernameEntry.getText());
			
			try {
				
				// creates a new file under libraries for the database only if the directory exists
				newProject.createNewFile();
				
			} catch (IOException e) {
				
				JOptionPane.showMessageDialog(null,
						"File Creation Failed! \n\n"
								+ "click 'ok' to continue...",
						"FAILURE", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			// save the new username and password
			savePasswords();
			
			// display account created message
			JOptionPane.showMessageDialog(null,
					"ACCOUNT CREATED! \n\n"
							+ "click 'ok' to continue...",
					"SUCCESS", JOptionPane.INFORMATION_MESSAGE);
			
			// shift back to login page
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			isClicked = true;
			
			passwordEntry.setText("");
			usernameEntry.setText("");
			
		}
		
	}
	
}
