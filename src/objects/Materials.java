package objects;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/*
 * object class that contains all the properties of a material
 * sets all fields of a material object
 * contains methods that enables the player to access specific materials
 */
public class Materials {
	
	// fields of the material object
	private ImageIcon icon;
	private String name, type, brand, storageCondition, hyperlinkToPurchase, hyperlinkToMSD, path, iconPath, state;
	private JTextArea environmentalConcerns, stabilityAndReactFirstAidMeasures, dangers;
	private int addOrder, dangerRating, amount;
	
	// constructor of the materials object initializes all the variables of the object
	public Materials(String name, String type, String brand, String storageCondition, String hyperlinkToPurchase, 
			String hyperlinkToMSD, JTextArea environmentalConcerns, 
			JTextArea stabilityAndReactFirstAidMeasures, JTextArea dangers, String iconPath, int addOrder, String path,
			int dangerRating, int amount, String state) {
		
		this.iconPath = iconPath;
		icon = new ImageIcon(iconPath);
		
		this.name = name;
		this.type = type;
		this.brand = brand;
		this.storageCondition = storageCondition;
		this.hyperlinkToPurchase = hyperlinkToPurchase;
		this.hyperlinkToMSD = hyperlinkToMSD;
		this.amount = amount;
		this.dangerRating = dangerRating;
		this.state = state;
		
		this.environmentalConcerns = environmentalConcerns;
		this.stabilityAndReactFirstAidMeasures = stabilityAndReactFirstAidMeasures;
		this.dangers = dangers;
		this.addOrder = addOrder;
		this.path = path;
		
	}

	// getters and setters are below-
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStorageCondition() {
		return storageCondition;
	}

	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}

	public String getHyperlinkToPurchase() {
		return hyperlinkToPurchase;
	}

	public void setHyperlinkToPurchase(String hyperlinkToPurchase) {
		this.hyperlinkToPurchase = hyperlinkToPurchase;
	}

	public String getHyperlinkToMSD() {
		return hyperlinkToMSD;
	}

	public void setHyperlinkToMSD(String hyperlinkToMSD) {
		this.hyperlinkToMSD = hyperlinkToMSD;
	}
	
	public JTextArea getEnvironmentalConcerns() {
		return environmentalConcerns;
	}

	public void setEnvironmentalConcerns(JTextArea environmentalConcerns) {
		this.environmentalConcerns = environmentalConcerns;
	}

	public JTextArea getStabilityAndReactFirstAidMeasures() {
		return stabilityAndReactFirstAidMeasures;
	}

	public void setStabilityAndReactFirstAidMeasures(JTextArea stabilityAndReactFirstAidMeasures) {
		this.stabilityAndReactFirstAidMeasures = stabilityAndReactFirstAidMeasures;
	}

	public JTextArea getDangers() {
		return dangers;
	}

	public void setDangers(JTextArea dangers) {
		this.dangers = dangers;
	}

	public int getAddOrder() {
		return addOrder;
	}

	public void setAddOrder(int addOrder) {
		this.addOrder = addOrder;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getDangerRating() {
		return dangerRating;
	}

	public void setDangerRating(int dangerRating) {
		this.dangerRating = dangerRating;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
