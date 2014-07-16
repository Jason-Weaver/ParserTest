package testHTML;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
		
		List<String> dependants = Arrays.asList(	// initial three classes used
			"org.apache.poi.xwpf.usermodel.XWPFDocument",
			"org.apache.poi.xwpf.usermodel.XWPFParagraph",
			"org.apache.poi.xwpf.usermodel.XWPFRun");
		
		Set<String> supporters = traverseHTML(doc, dependants);		// get all the classes that the dependants need
		supporters.addAll(dependants);							// set initial classes in total classes needed too
		
		for (String supporter : supporters) System.out.println(supporter);
	}
	
	
	public static Set<String> traverseHTML(Document doc, List<String> list) {
		int dpendantsSize = list.size();
		
		if (dpendantsSize == 0) {		// Stopping condition
			Set<String> supporters = new HashSet<String>();
			return supporters;
		}
		
		if (dpendantsSize == 1) {		// Stopping condition
			Element dependant = null;
			Elements foundElements = null, matchingElements = null;
			
			try {				// create corresponding element/dependent
				foundElements = doc.getElementsContainingOwnText(list.get(0));	// has correct text			
				matchingElements = foundElements.select("td:eq(0)");							// has correct position
				dependant = matchingElements.first();
				
				if (matchingElements.size() != 1) throw new NumberFormatException();				// Check uniqueness
			} catch (Exception e) {
				for (Element matchingElement : matchingElements) System.out.println(matchingElement.text());
				e.printStackTrace(); }
			
			String supportersString = dependant.nextElementSibling().ownText();		// update total classes needed
			Set<String> supporters = new HashSet<String>(Arrays.asList(supportersString.split("\\s*,\\s*")));		
			
			return supporters;
		}
		
		// Divide
		Set<String> supporters1 = traverseHTML(doc, list.subList(0, (dpendantsSize/2)-1));
		Set<String> supporters2 = traverseHTML(doc, list.subList(dpendantsSize/2, dpendantsSize));
		
		// Conquer
		supporters1.addAll(supporters2);
		
		return supporters1;
	}

}
