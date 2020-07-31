package objects;

import java.util.HashMap;

import javax.swing.JTextArea;

public class Projects {
	
	private String name,path;
	private HashMap<String, Integer> materials;
	private JTextArea environmentalField;

	public Projects(String name, HashMap<String, Integer> materials, JTextArea environmentalField) {
		
		this.name = name;
		
		this.materials = materials;
		this.environmentalField = environmentalField;
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Integer> getMaterials() {
		return materials;
	}

	public void setMaterials(HashMap<String, Integer> materials) {
		this.materials = materials;
	}

	public JTextArea getEnvironmentalField() {
		return environmentalField;
	}

	public void setEnvironmentalField(JTextArea environmentalField) {
		this.environmentalField = environmentalField;
	}
	
}
