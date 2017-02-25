package fr.beuve.vdj.v8;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import static org.junit.Assert.*;

import org.junit.Test;

public class M3uParserTest {
	@Test
	public void test() throws ParseException, IOException {
		M3uParser p = new M3uParser(new File("src/test/java/fr/beuve/vdj/v8"));
		assertEquals(5, p.get("IMAGES-LES DEMONS DE MINUIT.wav").stars(p));
		assertEquals(4, p.get("01 Sweat (Snoop Dogg vs. David Guett.mp3").stars(p));
		assertEquals(3, p.get("1-12 Titanium (feat. Sia).mp3").stars(p));
		assertEquals(2, p.get(" 01 - Maroon 5  - Moves Like Jagger (feat. Christina Aguilera) [Studio Recording from The Voice Performance].m4a").stars(p));
		assertEquals(1, p.get("THIERRY HAZARD-LE JERK.wav").stars(p));
		assertEquals(0,new SongFileParser("/titi/toto.mp3").stars(p));
	}

}
