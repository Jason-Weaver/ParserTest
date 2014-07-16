package testHTML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
		File input = new File("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testHTML/index.html");
		Document doc = null;
		try { 
			doc = (Document) Jsoup.parse(input, "UTF-8");
		} catch (IOException e) { 
			e.printStackTrace(); }
		
		Set<String> supporters = null;
		ArrayList<String> dependants = (ArrayList<String>) Arrays.asList(
			"org.apache.poi.xwpf.usermodel.XWPFDocument",
			"org.apache.poi.xwpf.usermodel.XWPFParagraph",
			"org.apache.poi.xwpf.usermodel.XWPFRun");
		
		
		traverseHTML(doc, dependants, supporters);
		// testJsoupMethods("/Users/jasonweaver/Documents/workspace/DependsOnParser/bin/testHTML/index.html", "XWPFDocument");
	}
	
	
	// Set<String> dependOnsSet = new HashSet<String>(dependOns);
	public static Set<String> traverseHTML(Document doc, ArrayList<String> dependants, Set<String> supporters) {
		
		Elements libs = doc.getElementsContainingOwnText("");
		
		if (dependants.size() == 1) {
			Element dependant = null;
			
			try {				// create corresponding element
				Elements foundElements = doc.getElementsContainingOwnText(dependants.get(0));		// has correct text			
				dependant = foundElements.select("td:eq(0)").first();								// has correct position
			
				if (foundElements.size() != 1) throw new NumberFormatException();					// Check uniqueness
			} catch (Exception e) {
				e.printStackTrace(); }
			
			String supportersString = dependant.nextElementSibling().ownText();
			ArrayList<String> supportersList = (ArrayList<String>) Arrays.asList(supportersString.split("\\s*,\\s*"));
			
			
			
			return supporters;
		}
		

		
		
		int libIndex = 0;
		for (Element lib : libs) {
			
			
			libIndex++;
		}
		
		return supporters;
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
