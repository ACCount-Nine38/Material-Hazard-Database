package utils;

import java.util.Comparator;

import objects.Materials;

/*
 * class that uses a comparator to compare 2 materials
 * the one with a higher recently added variable will be returned
 */
public class SortByRecentlyAdded implements Comparator<Materials> {

	public int compare(Materials a, Materials b) {
		
		return -Integer.valueOf(a.getAddOrder()).compareTo(Integer.valueOf(b.getAddOrder()));
		
	}

}