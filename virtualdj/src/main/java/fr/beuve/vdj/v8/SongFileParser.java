package fr.beuve.vdj.v8;

import java.io.File;

public class SongFileParser {
	private String title, artist, style, file;
	private int count;
	private boolean parsed = false;
	private static final String UNKNOWN = "unkonwn";
	
	public SongFileParser(String path){
		count=1;
		
		String separator = File.separator;
		if(path.indexOf(separator)<0) separator = "\\\\";
		
		String[] split = path.split(separator);
		parsed = split[1].equals("dj");
		file=split[split.length-1];
		style=split[split.length-2];
		split = file.split("\\.");
		title=split[0];
		split = split[0].split("-");
		if(split.length>1){
			artist = split[0];
			//FIXME find the regexp for 1-n digits
			if(artist.matches("[0-9] ")) {
				parsed=false;
				artist=UNKNOWN;
			}else{
				parsed=true;
				title = split[1];
			}
		}else{
			parsed = false;
			artist = UNKNOWN;
		}
		if(style.equals("2016")) parsed=false;
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
	@Override
	public int hashCode() {
		return file == null ? 0 : file.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SongFileParser other = (SongFileParser) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}
	public int add(SongFileParser song) {
		if(!this.equals(song)) throw new RuntimeException(this+" <> "+song);
		return count++;
	}
	@Override
	public String toString() {
		return file;
	}
	public int stars(int max){
		int stars = 5 * count / max;
		if(stars==0) stars=1;
		return stars;
	}
	public boolean canRename(){
		return parsed;
	}
	public String year(){
		//TODO get last file change date
		return null;
	}
}
