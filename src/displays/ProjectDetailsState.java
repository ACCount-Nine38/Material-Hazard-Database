package displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

public class ProjectDetailsState extends State {

	private JTextArea textbox2;
	public static JTextArea textbox;
	private JPanel panel;
	private JSlider EISlider;
	private JButton reportButton, back;
	private JLabel aboutLabel, EILabel;
	Hashtable<Integer, JLabel> labelTable;
	public static int EIValue = 0;
	static final int EI_MIN = 1;
	static final int EI_MAX = 5;
	static final int EI_INIT = 3;

	@Override
	public void init() {


	}

	@Override
	public void addJComponents() {
		
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);

		aboutLabel = new JLabel("Description of Project");
		aboutLabel.setBounds(0, 65, 1280, 50);
		aboutLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		aboutLabel.setForeground(new Color(68, 225, 212));
		aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(aboutLabel);
		
		textbox = DatabaseState.currentProject.getEnvironmentalField();
		textbox.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		textbox.setBounds(390, 125, 500, 500);
		textbox.setLineWrap(true);
		textbox.setWrapStyleWord(true);
		JScrollPane textboxScroll = new JScrollPane(textbox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textboxScroll.setBounds(390, 125, 500, 500);
		panel.add(textboxScroll); 
		
		textbox2 = new JTextArea();
		textbox2.setBounds(390, 285, 500, 100);
		//panel.add(textbox2);

		EILabel = new JLabel("Environmental Impact Rating");
		EILabel.setBounds(450, 425, 500, 50);
		EILabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		EILabel.setForeground(new Color(68, 225, 212));
		//panel.add(EILabel);

		//Environmental Impact Rating Slider
		EISlider = new JSlider(JSlider.HORIZONTAL,EI_MIN, EI_MAX, EI_INIT);
		EISlider.setBounds(390, 450, 500, 150);
		EISlider.setBackground(new Color(27, 62, 90));
		EISlider.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		EISlider.setMajorTickSpacing(1);
		EISlider.setPaintTicks(true);
		EISlider.setPaintLabels(true);	
		//panel.add(EISlider);

		//add labels to the slider
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(1, new JLabel("Low"));
		labelTable.put(5, new JLabel("High"));
		EISlider.setLabelTable(labelTable);

		//Proceed Button
		reportButton = new JButton(new ImageIcon(new ImageIcon("images/proceedToReport.png").getImage().getScaledInstance(307, 62, 0)));
		reportButton.setBounds(920, 650, 320, 75);
		reportButton.addActionListener(this);
		panel.add(reportButton);

		back = new JButton(
				new ImageIcon(new ImageIcon("images/back2.png").getImage().getScaledInstance(140, 65, 0)));
		back.setBounds(40, 40, 150, 75);
		back.addActionListener(this);
		panel.add(back);

		add(panel);
		
		addMenuBar();
		
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int EI = (int)source.getValue();
			if (EI == 1)
				EIValue = 1;
			else if (EI == 2)
				EIValue = 2;
			else if (EI == 3)
				EIValue = 3;
			else if (EI == 4)
				EIValue = 4;
			else if (EI == 5)
				EIValue = 5;

		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == back) {
			
			new MaterialState();
			this.dispose();
			
		} else if(event.getSource() == reportButton) {
			new ReportScreen();
			this.dispose();
		}
		
	}

}
