package fr.beuve.vdj.v8;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

public class SongFileParser {
	private static Logger logger = Logger.getLogger(SongFileParser.class);
	
	private String title, artist, style, file, path;
	private int count;
	private boolean accept = false;
	private static final String UNKNOWN = "unknown";
	
	public SongFileParser(String _path){
		//logger.debug(_path);
		
		path = _path;
		count=0;
		
		String separator = "/";
		if(path.indexOf(separator)<0) separator = "\\\\";
		
		String[] split = path.split(separator);
		accept = split[1].equals("dj")||path.indexOf("src/test")>-1;
		
		file=file(path);
		
		style=split[split.length-2];
		split = file.split("\\.");
		title=split[0];
		split = split[0].split("-");
		if(split.length>1){
			artist = split[0];
			if(artist.trim().matches("\\d*")) {
				accept=false;
				artist=UNKNOWN;
			}else{
				accept=true;
				title = split[1];
			}
		}else{
			accept = false;
			artist = UNKNOWN;
		}
		if(style.equals("2016")) accept=false;
	}
	public String title(){
		return title;
	}
	public String artist(){
		return artist;
	}
	public String style(){
		return style;
	}

	public int add() {
		return ++count;
	}
	private int stars(int max){
		logger.debug(count+"/"+max);
		int stars = 5 * count / max;
		if(stars==0) stars=1;
		return stars;
	}
	public int stars(M3uParser history) throws ParseException, IOException{
		SongFileParser song = history.get(file);
		if(song==null) 
			return 0;
		else{
			int max = history.max();
			return song.stars(max);
		}
	}

	public boolean accept(){
		return accept;
	}
	public String year(){
		File f = new File(path);
		long t = f.lastModified();
		Date d = new Date(t);
		int year = d.getYear()+1900;
		return Integer.toString(year);
	}
	public static String file(String path){
		//logger.debug(path+" ("+Character.getNumericValue(path.charAt(2))+") : "+path.indexOf(-1));
		
		String separator = "/";
		int index = path.indexOf(separator);
		//logger.debug(separator+" >> "+index);
		if(index<0) {
			separator = "\\";
			index = path.indexOf(separator);
			//logger.debug(separator+" >> "+index);
			separator = separator+separator;
		}
		if(index>0){
			String[] split = path.split(separator);
			String toReturn = split[split.length-1];
			//logger.debug("SPLIT >> "+toReturn);
			return toReturn;
		}else{
			//logger.debug("RETURN >> "+path);
			return path;
		}
	}
	@Override
	public String toString() {
		return file+" "+count;
	}
	
}
