package testExcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;

	// proves that indeed, the classdependson feature of TattleTale is not transitive. Check and check.
public class parseSingleElementToConsole {

	public static void main(String[] args) {
		File file = null;
		Scanner input = null;
		List<String> dependOns = new ArrayList<String>();
		List<String> newDependOns = new ArrayList<String>();


	  for (int i=1; i<=4; i++) {
		switch(i) {
		case 1: file = new File("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testExcel/XWPFDocument.txt"); break;
		case 2: file = new File("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testExcel/XWPFParagraph.txt"); break;
		case 3 :file = new File("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testExcel/XWPFRun.txt"); break;
		case 4 :file = new File("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testExcel/POIXMLDocument.txt"); break;
		default: System.out.println("What the fuck man!  You can't even count to four... SMH");
		}
		
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String inputString = input.nextLine();
		newDependOns = Arrays.asList(inputString.split("\\s*,\\s*"));
		dependOns.addAll(newDependOns);
		
		input.close();
	  }
	  
	    Set<String> dependOnsSet = new HashSet<String>(dependOns);
	    // dependOnsSet.removeAll(newDependOns);
	    
		for (String dependOn : dependOnsSet) {
			System.out.println(dependOn);
		}
		
		// System.out.println("\n------------------------------\nThank you for your patience."
		// 		+ "\nPlease note the set difference has not been tested.");

	}

}


