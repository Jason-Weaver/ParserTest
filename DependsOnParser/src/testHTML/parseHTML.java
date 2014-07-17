package testHTML;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class parseHTML {

	public static void main(String[] args) {
		
		// create Document first
		File input = new File("/Users/jasonweaver/git/ParserTestRepository/DependsOnParser/src/testHTML/index.html");
		Document doc = null;
		try { 
			doc = (Document) Jsoup.parse(input, "UTF-8");
		} catch (IOException e) { 
			e.printStackTrace(); }
		
		Set<String> dependants = new HashSet<String>(Arrays.asList(		// initial three classes used
			"org.apache.poi.xwpf.usermodel.XWPFDocument",
			"org.apache.poi.xwpf.usermodel.XWPFParagraph",
			"org.apache.poi.xwpf.usermodel.XWPFRun"));
		
		Set<String> supporters = findAllClasses(doc, dependants, dependants);		// get all the classes that the dependents need
		
		for (String supporter : supporters) System.out.println(supporter);			// show results
	}
	
	
	// If there isn't a matching element, that means that class doesn't have a dependency
	public static Set<String> findAllClasses(Document doc, Set<String> totalClasses, Set<String> currentDependants) {
		Set<String> supporters = new HashSet<String>();
		
		for (String dependantString: currentDependants) {
			Element dependant = null;
			Elements foundElements = null, matchingElements = null;
			String regex = "^" + dependantString + "$";
			
			try {																		
				foundElements = doc.getElementsMatchingOwnText(regex);		// has correct text			
				matchingElements = foundElements.select("td:eq(0)");					// has correct position
				
				if (matchingElements.size() == 0) continue;								// no supporters
				else if (matchingElements.size() == 1) ;
				else throw new NumberFormatException();							// Check uniqueness
				
				dependant = matchingElements.first();
				
			} catch (NullPointerException e) {														
				continue; 
			} catch (Exception e) {	
				System.out.println("Regular Expression is: " + regex);
				System.out.println("Elements are: ");
				for (Element element: matchingElements) System.out.println(element.text());	
				
				e.printStackTrace(); 
			}															
			
			String supportersString = dependant.nextElementSibling().ownText();		// update total classes needed
			supporters.addAll(Arrays.asList(supportersString.split("\\s*,\\s*")));
		  }
			
		Set<String> untestedSupporters = supporters;
		untestedSupporters.removeAll(totalClasses);				// only check ones that haven't been yet
		
		if (untestedSupporters.size() == 0) return totalClasses;		// no further supporters possible!!!

		totalClasses.addAll(supporters);						// add found supporters/classes to total classes
		
		// Recursion: check if supports are also dependents
		return findAllClasses(doc, totalClasses, supporters); 	
	}

}
