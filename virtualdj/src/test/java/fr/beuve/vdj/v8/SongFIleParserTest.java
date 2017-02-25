package fr.beuve.vdj.v8;

import static org.junit.Assert.*;

import org.junit.Test;

public class SongFIleParserTest {
	@Test
	public void unix() {
		// UNIX FILE SEPARATOR
		SongFileParser i = new SongFileParser("D:/ROCK_60/ROCKOLLECTION.wav");
		assertEquals("unknown",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
		assertTrue(!i.accept());
	}
	@Test
	public void accept() {
		SongFileParser i = new SongFileParser("F:/dj/ROCK_60/LAURENT VOULZY-ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
		assertTrue(i.accept());
	}
	@Test
	public void notaccept() {
		// cannot rename (artist is a song number)
		SongFileParser i = new SongFileParser("F:/dj/ROCK_60/1 -ROCKOLLECTION.wav");
		assertTrue(!i.accept());
		
		// cannot rename (2016 folder)
		i = new SongFileParser("F:/dj/2016/LAURENT VOULZY-ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("2016",i.style());
		assertTrue(!i.accept());
	}
	@Test
	public void win() {
		// WINDOWS PATH SEPARATOR
		SongFileParser i = new SongFileParser("D:\\ROCK_60\\LAURENT VOULZY-ROCKOLLECTION.wav");
		assertEquals("LAURENT VOULZY",i.artist());
		assertEquals("ROCKOLLECTION",i.title());
		assertEquals("ROCK_60",i.style());
	}
	@Test
	public void file() {
		assertEquals(0,"\\".indexOf("\\"));
		assertEquals(0,"\\".indexOf('\\'));
		assertEquals("IMAGES-LES DEMONS DE MINUIT.wav",SongFileParser.file("IMAGES-LES DEMONS DE MINUIT.wav"));
		assertEquals("IMAGES-LES DEMONS DE MINUIT.wav",SongFileParser.file("D:/dj/ANNEES_80/IMAGES-LES DEMONS DE MINUIT.wav"));
		assertEquals("IMAGES-LES DEMONS DE MINUIT.wav",SongFileParser.file("D:\\MUSIC\\ANNEES_80\\IMAGES-LES DEMONS DE MINUIT.wav"));
	}

}
