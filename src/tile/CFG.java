// Java Program to Iterate over a List
// using enhanced for loop (for-each)

// Importing all classes of
// java.util package
import java.util.*;

// Class
class GFG {

	// Main driver method
	public static void main(String args[])
	{
		// Creating an Arraylist
		List<String> myList = new ArrayList<String>();

		// Adding elements to the List
		// Custom inputs
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.add("D");

		// Using enhanced for loop(for-each) for iteration
		for (String i : myList) {

			// Print all elements of ArrayList
			System.out.println(i);
		}
	}
}
