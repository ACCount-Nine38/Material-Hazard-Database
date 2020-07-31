package displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HelpState extends State {

	private JPanel panel;
	private JButton backButton;
	private JLabel titleLabel, MS1Label, MS2Label, MS3Label, MS4Label;
	public static ImageIcon addNewMaterialImage, materialSelect1, materialSelect2, materialSelect3, materialSelect4, materialSelect5; 
	private JLabel addNewMaterial, materialSelectList, MaterialSelectAdd, MaterialSelectNewMaterial, MaterialSelectCart, MaterialSelectProceed;

	@Override
	public void init() {

	}

	@Override
	public void addJComponents() {
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);

		titleLabel = new JLabel("Instructions");
		titleLabel.setBounds(540, 25, 200, 40);
		titleLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		titleLabel.setForeground(new Color(68, 225, 212));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(titleLabel);

		addNewMaterialImage = new ImageIcon("images/AddNewMaterialHelp.png");
		addNewMaterial = new JLabel(addNewMaterialImage);
		addNewMaterial.setBounds(100, 100, 497, 304);
		//panel.add(addNewMaterial);

		materialSelect1 = new ImageIcon("images/MaterialSelect1.png");
		materialSelectList = new JLabel(materialSelect1);
		materialSelectList.setBounds(40, 0, 500, 304);
		panel.add(materialSelectList);

		MS1Label = new JLabel("<html>" + "This is the list of materials that are pre-loaded for you to choose from. "
				+ "You can find a material using the scroll bar on the side or the search bar on the top. "
				+ "Once you find a material you would like to add, you can click on it and move on to the next step.");
		MS1Label.setBounds(30, 280, 570, 100);
		MS1Label.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 16));
		MS1Label.setForeground(new Color(68, 225, 212));
		panel.add(MS1Label);

		materialSelect2 = new ImageIcon("images/MaterialSelect2.png");
		MaterialSelectAdd = new JLabel(materialSelect2);
		MaterialSelectAdd.setBounds(740, 0, 500, 304);
		panel.add(MaterialSelectAdd);
		
		MS2Label = new JLabel("<html>" + "If there is a material you want to add, but it's not included in the pre-loaded list, "
				+ "you can add it using the 'Add New Material' button. It will bring you to a screen that allows you to input all the "
				+ "information to add a new material to the list.");
		MS2Label.setBounds(710, 280, 570, 100);
		MS2Label.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 18));
		MS2Label.setForeground(new Color(68, 225, 212));
		panel.add(MS2Label);

		materialSelect3 = new ImageIcon("images/MaterialSelect3.png");
		MaterialSelectNewMaterial = new JLabel(materialSelect3);
		MaterialSelectNewMaterial.setBounds(40, 374, 500, 304);
		panel.add(MaterialSelectNewMaterial);
		
		MS3Label = new JLabel("<html>" + "Once you've chosen your material, you must now choose the quantity. Enter the desired "
				+ "amount in the text field, then click 'Save Amount'. If you wish to remove a material, select it, and click 'Remove From Library'.");
		MS3Label.setBounds(30, 654, 570, 100);
		MS3Label.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 18));
		MS3Label.setForeground(new Color(68, 225, 212));
		panel.add(MS3Label);

		materialSelect4 = new ImageIcon("images/MaterialSelect4.png");
		MaterialSelectCart = new JLabel(materialSelect4);
		MaterialSelectCart.setBounds(740, 374, 500, 304);
		panel.add(MaterialSelectCart);
		
		MS4Label = new JLabel("<html>" + "As you add new materials, they will progressively show up in the 'cart' at the top of the screen. "
				+ "Once you see that all the materials you want are in the cart, you may click the 'Proceed' button and move on to the final"
				+ "screens.");
		MS4Label.setBounds(710, 654, 570, 100);
		MS4Label.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 18));
		MS4Label.setForeground(new Color(68, 225, 212));
		panel.add(MS4Label);

		materialSelect5 = new ImageIcon("images/MaterialSelect5.png");
		MaterialSelectProceed = new JLabel(materialSelect5);
		MaterialSelectProceed.setBounds(600, 100, 500, 304);
		//panel.add(MaterialSelectProceed);

		backButton = new JButton(new ImageIcon(new ImageIcon("images/back2.png").getImage().getScaledInstance(100, 33, 0)));
		backButton.setBounds(584, 525, 113, 45);
		backButton.addActionListener(this);
		backButton.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(backButton);

		add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.dispose();
	}

}
