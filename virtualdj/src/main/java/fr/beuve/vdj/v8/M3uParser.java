package fr.beuve.vdj.v8;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.Vector;

public class M3uParser {
	private Vector<SongFileParser> history = new Vector<SongFileParser>();
	private int maxCount = 0;
	
	public M3uParser(File folder) throws ParseException, IOException{
		for (File m3u : folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".m3u"))
					return true;
				return false;
			}
		})) {
			load(m3u);
		}
	}

	private void load(File m3u) throws ParseException, IOException {
		LineNumberReader reader = new LineNumberReader(new FileReader(m3u));
		try{
			String line = reader.readLine();
			while(line!=null){
				if(!line.trim().startsWith("#")) continue;
				if(!line.trim().equals("")) continue;
				
				SongFileParser song = new SongFileParser(line);
				int index = history.indexOf(song);
				if(index<0){ 
					history.add(song);
				}else{
					int count = history.get(index).add(song);
					if(count>maxCount) maxCount=count;
				}
					
			}
			line = reader.readLine();
			
		}finally{
			reader.close();
		}
	}

	public int stars(SongFileParser song){
		int index = history.indexOf(song);
		if(index<0){ 
			return 0;
		}else{
			return history.get(index).stars(maxCount);
		}
	}
}
