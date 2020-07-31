package utils;

import java.util.Comparator;

import objects.Materials;

/*
 * class that uses a comparator to compare 2 materials
 * the lower alphabetical word will be returned
 */
public class SortAToZ implements Comparator<Materials> {

	public int compare(Materials a, Materials b) {
		
		return a.getName().compareTo(b.getName());
		
	}

}
