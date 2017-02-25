package fr.beuve.vdj.v8;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class M3uParser {
	protected Logger logger = Logger.getLogger(M3uParser.class);
	private static M3uParser instance = null;
	
	public static M3uParser getInstance() throws ParseException, IOException{
		if(instance==null) instance = new M3uParser(new File("m3u"));
		return instance;
	}
	
	private Map<String, SongFileParser> history = new HashMap<String,SongFileParser>();
	private int maxCount = 0;
	
	public M3uParser(File folder) throws ParseException, IOException{
		for (File m3u : folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				//logger.debug(name);
				//if (dir.isDirectory())	return false;
				if (name.endsWith(".m3u")){
					//logger.debug(">> OK");
					return true;
				}else{
					return false;
				}
			}
		})) {
			load(m3u);
		}
		for(SongFileParser f:history.values()){
			logger.info(f.toString());
		}
	}

	private void load(File m3u) throws ParseException, IOException {
		logger.debug("> "+m3u);
		LineNumberReader reader = new LineNumberReader(new FileReader(m3u));
		try{
			String line = reader.readLine();
			while(line!=null){
				
				boolean skip = false;
				if(line.trim().startsWith("#")) skip = true;
				if(line.trim().equals("")) skip = true;

				if(!skip){
					//logger.debug(line);
					String file = SongFileParser.file(line);
					SongFileParser song = history.get(file);
					if(song==null){ 
						song = new SongFileParser(line);
						history.put(file,song);
					}
					int count = song.add();
					if(count>maxCount) maxCount=count;
					//logger.debug(file+" "+count+"/"+maxCount);
				}
				line = reader.readLine();	
			}
		}finally{
			reader.close();
		}
	}
	public int max(){
		return maxCount;
	}
	public SongFileParser get(String path){
		String file = SongFileParser.file(path);
		return history.get(file);
	}
}
