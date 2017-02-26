package fr.beuve.vdj.v8;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Vdj8XmlDbParserTest {
	@Test
	public void test() throws ParseException, IOException, ParserConfigurationException, SAXException, XPathExpressionException, DOMException, TransformerException {
		Vdj8XmlDbParser db = new Vdj8XmlDbParser(new File("src/test/java/fr/beuve/vdj/v8/db1.xml"), new File("src/test/java/fr/beuve/vdj/v8"));
		db.load();
		Document xml = db.fix();
		//print(node("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/pop/IMAGES-LES DEMONS DE MINUIT.wav']",xml));
		assertEquals("pop",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/pop/IMAGES-LES DEMONS DE MINUIT.wav']/Tags/@Genre",xml));
		assertEquals("IMAGES",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/pop/IMAGES-LES DEMONS DE MINUIT.wav']/Tags/@Author",xml));
		assertEquals("LES DEMONS DE MINUIT",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/pop/IMAGES-LES DEMONS DE MINUIT.wav']/Tags/@Title",xml));
		assertEquals("2017",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/pop/IMAGES-LES DEMONS DE MINUIT.wav']/Tags/@Year",xml));
		assertEquals("5",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/pop/IMAGES-LES DEMONS DE MINUIT.wav']/Tags/@Stars",xml));
		
		//print(node("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/rock/L5-MANIAC.mp3']",xml));
		assertEquals("rock",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/rock/L5-MANIAC.mp3']/Tags/@Genre",xml));
		assertEquals("L5",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/rock/L5-MANIAC.mp3']/Tags/@Author",xml));
		assertEquals("MANIAC",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/rock/L5-MANIAC.mp3']/Tags/@Title",xml));
		assertEquals("2017",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/rock/L5-MANIAC.mp3']/Tags/@Year",xml));
		assertEquals("0",xpath("//VirtualDJ_Database/Song[@FilePath='src/test/java/fr/beuve/vdj/v8/rock/L5-MANIAC.mp3']/Tags/@Stars",xml));
	}
	private String xpath(String query, Document xml) throws XPathExpressionException{
		    return node(query,xml).getNodeValue();
	}
	private Node node(String query, Document xml) throws XPathExpressionException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(query);
	    
		Object result = expr.evaluate(xml, XPathConstants.NODESET);
	    NodeList list = (NodeList) result;
	    return list.item(0);
	}

    private static void print(Node node) throws TransformerException
    {
    	Transformer transformer = TransformerFactory.newInstance().newTransformer();
    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    	//initialize StreamResult with File object to save to file
    	StreamResult result = new StreamResult(new StringWriter());
    	DOMSource source = new DOMSource(node);
    	transformer.transform(source, result);
    	String xmlString = result.getWriter().toString();
    	System.out.println(xmlString);
    }
}
