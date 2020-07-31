package displays;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import displays.ReportScreen;
import javax.swing.*;
import javax.swing.border.EmptyBorder;



@SuppressWarnings("serial")
public class PossibleAlternatives extends State implements ActionListener {

	//Variables
	private JPanel contentPane, panel;
	private JLabel lblProjectColour;
	private ReportScreen global;
	private JButton back;

	
	public PossibleAlternatives() {

		
	}


	
	@Override
	public void init() {
		//Allowing access to ReportScreen variables
		global = new ReportScreen();

	}
	
	public void addJComponents() {

		//Adding JComponents
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(27, 62, 90));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		back = new JButton("New button");
		back.setBounds(23, 11, 275, 72);
		back.addActionListener(this);
		back.setIcon(new ImageIcon (new ImageIcon ("images/back.png").getImage().getScaledInstance(265, 62, 0)));
		contentPane.add(back);
		
		JLabel title = new JLabel("Possible Alternatives");
		title.setBounds(551, 20, 275, 42);
		title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		title.setForeground(new Color(68, 225, 212));
		contentPane.add(title);
		
		panel = new JPanel();
		panel.setBounds(23, 192, 593, 538);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel messagesTitle = new JLabel("Messages for User");
		messagesTitle.setBounds(235, 139, 170, 42);
		messagesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		messagesTitle.setForeground(new Color(68, 225, 212));
		contentPane.add(messagesTitle);
		
		JLabel artistsTitle = new JLabel("Artists");
		artistsTitle.setBounds(913, 613, 61, 42);
		artistsTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		artistsTitle.setForeground(new Color(68, 225, 212));
		contentPane.add(artistsTitle);
		
		JTextArea artistParagraph = new JTextArea();
		artistParagraph.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		artistParagraph.setBackground(new Color(255, 250, 240));
		artistParagraph.setText("Many of the things we use in our daily lives often do not end up being\r\nrecycled. These items will often end up in landfills or the ocean and \r\ncause serious damage to ecosystems because they were simply too much \r\nwork to recycle and reuse. The money is put before the environment \r\nwhich is one of the major issues we have today which prevents us from \r\nsolving waste management issues. Some artists however use this to \r\ntheir advantage and use garbage and convert it to a form of art. This \r\nnot only raises awareness about the problem but also the waste that \r\nwould otherwise damage the environment is being reused to create \r\nsomething new. Here are a few artists that do this type of work.");
		artistParagraph.setBounds(695, 369, 522, 212);
		contentPane.add(artistParagraph);
		
		JLabel artist1 = new JLabel("Leo Swell:");
		artist1.setFont(new Font("Tahoma", Font.BOLD, 11));
		artist1.setBounds(809, 666, 82, 14);
		artist1.setForeground(new Color(68, 225, 212));
		contentPane.add(artist1);
		
		JLabel artist2 = new JLabel("Jane Perkins:");
		artist2.setFont(new Font("Tahoma", Font.BOLD, 11));
		artist2.setBounds(799, 691, 82, 14);
		artist2.setForeground(new Color(68, 225, 212));
		contentPane.add(artist2);
		
		JLabel artist3 = new JLabel("Michelle Reader:");
		artist3.setFont(new Font("Tahoma", Font.BOLD, 11));
		artist3.setBounds(773, 716, 95, 14);
		artist3.setForeground(new Color(68, 225, 212));
		contentPane.add(artist3);
		
		JTextArea link1 = new JTextArea("https://www.leosewell.net/about.php");
		link1.setBounds(887, 666, 303, 14);
		link1.setBackground(new Color(27, 62, 90));
		link1.setForeground(new Color(68, 225, 212));
		contentPane.add(link1);
		
		JTextArea link2 = new JTextArea("http://www.bluebowerbird.co.uk/info.htm");
		link2.setBounds(887, 691, 330, 14);
		link2.setBackground(new Color(27, 62, 90));
		link2.setForeground(new Color(68, 225, 212));
		contentPane.add(link2);
		
		JTextArea link3 = new JTextArea(" http://www.michelle-reader.co.uk/artist-profile.html");
		link3.setBounds(871, 716, 359, 14);
		link3.setBackground(new Color(27, 62, 90));
		link3.setForeground(new Color(68, 225, 212));
		contentPane.add(link3);
		
		lblProjectColour = new JLabel("Project Colour:");
		lblProjectColour.setBounds(707, 222, 170, 72);
		lblProjectColour.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		lblProjectColour.setForeground(new Color(68, 225, 212));
		contentPane.add(lblProjectColour);
		
		JLabel colour = new JLabel("New label");
		colour.setBounds(944, 222, 211, 88);
		colour.setIcon(new ImageIcon (global.projectColour));
		contentPane.add(colour);
	
		setMessages();
	}

	private void setMessages() {
		
		JTextArea messages = new JTextArea("");
		messages.setBounds(10, 11, 573, 516);
		messages.setFont(new Font("times new roman", Font.ITALIC, 30));
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		panel.add(messages);
		
		//Determine what message to display depending on amount of toxic items
		if (global.totalToxic == 1) 
			messages.setText(messages.getText() + "You have one toxic item in your cart. Try using something that is not as toxic or add your own non toxic material like water colour paint\n\n");
		else if (global.totalToxic > 1)
			messages.setText(messages.getText() + "You have multiple items that are toxic towards the environment. Try using an item that is not as toxic to imporve your project colour\n\n");
		else if (global.totalToxic == 0)
			messages.setText(messages.getText() + "You have 0 toxic items in your cart, good job!\n\n");
		
		//Determine what message to display depending on amount of items that damage the environment
		if (global.totalDamage == 1) 
			messages.setText(messages.getText() + "You have one item that can cause damage to the environment. Global warning is a real issue, try using something that is not as harmful\n\n");
		else if (global.totalDamage > 1)
			messages.setText(messages.getText() + "You have multiple items that can cause damage to the environment. Global warning is a real issue, try using something that is not as harmful\n\n ");

		//Determine what message to display depending on amount of items that damage the environment
		if (global.totalNegative == 1) 
			messages.setText(messages.getText() + "You have one item that will leave a negative impact on our earth. You should only use items that will not impact the earth in a negative way\n\n");
		else if (global.totalNegative > 1)
			messages.setText(messages.getText() + "You have multiple items that will leave a negative impact on our earth. You should only use items that will not impact the earth in a negative way\n\n ");

		
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//Goes back to ReportScreen
		if(event.getSource() == back) {
			new ReportScreen();
			this.dispose();
		}
	
	}
}