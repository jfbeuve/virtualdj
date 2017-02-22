package fr.beuve.vdj.v8;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vdj8XmlSongParser {
	Node node, display;
	String file, title, artist, genre, year, stars;
	private static String AUTHOR = "Author";
	private static String TITLE = "Title";
	private static String GENRE = "Genre";
	private static String DISPLAY = "Tags";
	private static String YEAR = "Year";
	private static String STARS = "Stars";
	protected Logger logger = Logger.getLogger(Vdj8XmlSongParser.class);
	
	public Vdj8XmlSongParser(Node _node){
		node = _node;
		file = node.getAttributes().getNamedItem("FilePath").getNodeValue();
		logger.debug("*** "+file+" ***");
		display = display(node);
		if(display!=null){
			artist = getDisplayAttribute(AUTHOR);
			title = getDisplayAttribute(TITLE);
			genre = getDisplayAttribute(GENRE);
			year =  getDisplayAttribute(YEAR);
			stars = getDisplayAttribute(STARS);
		}
	}
	private String getDisplayAttribute(String name){
		Node attr = display.getAttributes().getNamedItem(name);
		if(attr!=null) return attr.getNodeValue();
		return null;
	}
	private void setDisplayAttribute(String name, String value){
		Node attr = display.getAttributes().getNamedItem(name);
		if(attr==null) {
			attr = node.getOwnerDocument().createAttribute(name);
			display.getAttributes().setNamedItem(attr);
		}
		attr.setNodeValue(value);
	}
	
	private Node display(Node song){
		NodeList list = song.getChildNodes();
		for(int i=0; i<list.getLength();i++){
			Node item = list.item(i);
			if(item.getNodeName().equals(DISPLAY)) return item;
		}
		return null;
	}
	public boolean fix(){
		boolean changed = false;
		SongFileParser song = new SongFileParser(file);
		//TODO fix display attributes if required
		return changed;
	}
}
