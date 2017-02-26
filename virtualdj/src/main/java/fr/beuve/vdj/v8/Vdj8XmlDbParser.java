package fr.beuve.vdj.v8;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Vdj8XmlDbParser {
	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private File file;
	private Document xml;
	
	private static Logger logger = Logger.getLogger(Vdj8XmlDbParser.class);
	
	private M3uParser playlist;
	
	public static void main(String[] args) throws Exception{
		Vdj8XmlDbParser vdj = new Vdj8XmlDbParser(new File(args[0]),new File("m3u"));
		vdj.load();
		vdj.fix();
		vdj.store(false);
	}
	public Document fix() throws XPathExpressionException, DOMException, ParseException, IOException{
		NodeList list = songs();
		for(int i=0; i<list.getLength();i++){
			fix(list.item(i));
		}
		return xml;
	}
	public void store(boolean copy) throws IOException, TransformerFactoryConfigurationError, TransformerException{
		if(copy){
			copy();
			save(file);
		}else{
			save(new File(file.getPath()+".jfb.xml"));
		}
	}
	private void fix(Node node) throws DOMException, ParseException, IOException{
		new Vdj8XmlSongParser(node,playlist).fix();
	}
	public Vdj8XmlDbParser(File _xml, File _m3u) throws ParseException, IOException{
		file = _xml;
		playlist = new M3uParser(_m3u);
	}
	public void load() throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Reader reader = new FileReader(file);
		InputSource is = new InputSource(reader);
		xml = db.parse(is);
	}
	
	private void copy() throws IOException{
		String path = file.getPath();
		int slash = path.lastIndexOf(File.separator);
		if(slash>-1)path = path.substring(0,slash);
		String name = file.getName();
		int dot = name.lastIndexOf(".");
		name = name.substring(0,dot+1);
		name += df.format(new Date());
		name += ".xml";
		File copy = new File(path+File.separator+name);
		FileUtils.copyFile(file, copy);
		logger.info("COPY "+file+" TO "+copy);
	}
	private void save(File target) throws TransformerFactoryConfigurationError, TransformerException, IOException{
        //FIXME output encoding
		logger.info("SAVE "+target);
		Source source = new DOMSource(xml);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Result result = new StreamResult(new FileWriter(target));
        xformer.transform(source, result);
	}
	
	public NodeList songs() throws XPathExpressionException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//VirtualDJ_Database/Song");
	    
		Object result = expr.evaluate(xml, XPathConstants.NODESET);
	    NodeList list = (NodeList) result;
	    
	    return list;
	}
}
