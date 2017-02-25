package fr.beuve.vdj.v8;

import java.io.IOException;
import java.text.ParseException;

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
	private M3uParser history;
	
	public Vdj8XmlSongParser(Node _node, M3uParser playlist){
		node = _node;
		history = playlist;
		
		file = node.getAttributes().getNamedItem("FilePath").getNodeValue();
		display = display(node);
		if(display!=null){
			artist = get(AUTHOR);
			title = get(TITLE);
			genre = get(GENRE);
			year =  get(YEAR);
			stars = get(STARS);
		}
	}
	private String get(String name){
		Node attr = display.getAttributes().getNamedItem(name);
		if(attr!=null) return attr.getNodeValue();
		return null;
	}
	private void set(String name, String value){
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
	public void fix() throws ParseException, IOException{
		SongFileParser song = new SongFileParser(file);
		
		if(!song.accept()) {
			System.out.print(".");
			return;
		}
		
		logger.info(file);
		set(AUTHOR,song.artist());
		set(TITLE,song.title());
		set(GENRE,song.style());
		set(YEAR,song.year());
		set(STARS,Integer.toString(song.stars(history)));
		//TODO comment HIT/NEW ?
	}
}
