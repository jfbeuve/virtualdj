package fr.beuve.vdj.v8;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import static org.junit.Assert.*;

import org.junit.Test;

public class M3uParserTest {
	@Test
	public void test() throws ParseException, IOException {
		M3uParser p = new M3uParser(new File("src/test/java/fr/beuve/vdj8"));
		assertEquals(5, p.stars(new SongFileParser("IMAGES-LES DEMONS DE MINUIT.wav")));
		assertEquals(4, p.stars(new SongFileParser("01 Sweat (Snoop Dogg vs. David Guett.mp3")));
		assertEquals(3, p.stars(new SongFileParser("1-12 Titanium (feat. Sia).mp3")));
		assertEquals(2, p.stars(new SongFileParser(" 01 - Maroon 5  - Moves Like Jagger (feat. Christina Aguilera) [Studio Recording from The Voice Performance].m4a")));
		assertEquals(1, p.stars(new SongFileParser("THIERRY HAZARD-LE JERK.wav")));
		assertEquals(1, p.stars(new SongFileParser("toto.mp3")));
	}

}
