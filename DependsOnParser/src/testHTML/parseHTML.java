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
	
	/* POSSIBLY USEFUL JSOUP METHODS
	 * 
	 * siblingElements()  -> Get sibling elements.
	 * ownText()  -> Gets the text owned by this element only; does not get the combined text of all children.
	 * nextElementSibling()  -> Gets the next sibling element of this element.
	 * getElementsContainingOwnText(String searchText)  -> Find elements that directly contain the specified string.
	 */

	public static void main(String[] args) {
		testJsoupMethods("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testHTML/index.html", "XWPFDocument");

	}
	
	
	public static Set<String> traverseHTML(Document doc, List<String> list) {
		int dpendantsSize = list.size();
		
		if (dpendantsSize == 1) {		// Stopping condition
			Element dependant = null;
			
			try {				// create corresponding element/dependent
				Elements foundElements = doc.getElementsContainingOwnText(list.get(0));	// has correct text			
				dependant = foundElements.select("td:eq(0)").first();							// has correct position
			
				if (foundElements.size() != 1) throw new NumberFormatException();				// Check uniqueness
			} catch (Exception e) {
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
	
	
	
	public static void testJsoupMethods(String filePath, String id) {
		File input = new File(filePath);
		Document doc = null;
		Element element = null;
		
		try { 
			doc = (Document) Jsoup.parse(input, "UTF-8");
			element = doc.getElementById(id);

		} catch (IOException e) { e.printStackTrace(); }
		
		System.out.println("The element's data is " + element.data());
		System.out.println("The element's own text is -" + element.ownText() + "-");
		System.out.println("The element's html is     -" + element.html() + "-");
		System.out.println("The element's sibiling index is " + element.elementSiblingIndex());
		
		System.out.println("------------------------------\n");
		
		Element elementSibiling = element.nextElementSibling();
		boolean sibIndexOne = false;
		if (elementSibiling.elementSiblingIndex() == 1) sibIndexOne = true; 
			System.out.println("The sibiling's index is 1: " + sibIndexOne);
			
		System.out.println("The element's sibiling's own text is " + elementSibiling.ownText());
		
		System.out.println("------------------------------\n");
		
		
		Elements foundElements = doc.getElementsContainingOwnText(element.ownText());		// has correct text
		Elements correctFoundElements = foundElements.select("td:eq(0)");					// has correct position
		
		for (Element correctFoundElement: correctFoundElements) {
			System.out.println("The found element's id is " + correctFoundElement.id());
			System.out.println("The found element's index is " + correctFoundElement.elementSiblingIndex());
		}
		
	}

}
