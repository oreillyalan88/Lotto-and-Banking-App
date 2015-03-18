package ie.wit.sort;

import ie.wit.bankaccount.Bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MapSort {

	public static ArrayList sortByValue(Map unsortedMap) {		//method used to view bank hashmap accounts alone, and order them

		if (unsortedMap != null) {								// checks if hashmap is empty
			ArrayList<Bank> values = new ArrayList<Bank>();		//if its not create an array list of type bank to store the values of the hashmap passed in
			values.addAll(unsortedMap.values());				// add the values of the hashmap
			
			Collections.sort(values, new Comparator<Bank>() {	//call Collections algorithm SORT, pass in the values (Bank Object, my ADT), and an instance of the comparator interface of type bank
				public int compare(Bank o1, Bank o2) {		
				    int c;											// int c stores weather the passed attributes of Bank match (which returns 0), attribute 01 is greater than 02 (returns 1) and if 02 is greater (returns 2)
				    c = (int) (o2.getBalance() - o1.getBalance());	// try match by balance (reversed so it displays high to low)
				    if (c == 0)										// if they are the same 
				       c = o1.getfName().toUpperCase().compareTo(o2.getfName().toUpperCase());	// match by order by first name
				    if (c == 0)									// if they are also the same
				    	c = o1.getlName().toUpperCase().compareTo(o2.getlName().toUpperCase()); // order by last name
				    if (c == 0)									// and the
				    	c = o1.getAddress().toUpperCase().compareTo(o2.getAddress().toUpperCase()); // by some miracle there all the same, match by address, which is last because its usually the most diverse input
				    return c;
			
				}
			});

			return values;									// return the sorted values
		}

		return null;							// or null if they dont exist
	}

	public static Map sortByKey(Map unsortedMap) {  // tree maps are sorted by key, by default, no need for comparator
		Map sortedMap = new TreeMap();
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
}