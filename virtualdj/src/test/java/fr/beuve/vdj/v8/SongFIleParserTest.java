package fr.beuve.vdj.v8;

import static org.junit.Assert.*;

import org.junit.Test;

public class SongFIleParserTest {
	@Test
	public void test() {
		// UNIX FILE SEPARATOR
		SongFileParser i = new SongFileParser("D:/ROCK_60/ROCKOLLECTION.wav");
		assertEquals("unknown",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
		assertTrue(!i.canRename());
		
		// can rename
		i = new SongFileParser("F:/dj/ROCK_60/LAURENT VOULZY-ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
		assertTrue(i.canRename());
		
		// cannot rename (artist is a song number)
		i = new SongFileParser("F:/dj/ROCK_60/1 -ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
		assertTrue(i.canRename());
		
		// cannot rename (2016 folder)
		i = new SongFileParser("F:/dj/2016/LAURENT VOULZY-ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("2016",i.style());
		assertTrue(!i.canRename());
		
		// WINDOWS PATH SEPARATOR
		i = new SongFileParser("D:\\ROCK_60\\LAURENT VOULZY-ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
	}

}
