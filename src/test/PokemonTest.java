/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.MissingPokemonPictureException;
import model.Pokemon;

/**
 * @author ajdow
 *
 */
class PokemonTest {
	
	/*
	 * Default testing pokemon
	 * Guaranteed to work
	 */
	Pokemon pikachu;
	String pikaID;
	String pikaName;
	int pikaGen;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		pikaID = "025";
		pikaName = "Pikachu";
		pikaGen = 1;
		pikachu = new Pokemon(pikaID, pikaName, pikaGen);
	}

	/**
	 * Test method for
	 * {@link model.Pokemon#Pokemon(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	void testPokemon() {
		boolean hasError = false;
		try {
			Pokemon p = new Pokemon(pikaID, pikaName, pikaGen);
		} catch (Exception e) {
			hasError = true;
		}

		assertFalse(hasError, "Pokemon constructor has an error on correct input!");
	}

	/**
	 * Test method for
	 * {@link model.Pokemon#Pokemon(java.lang.String, java.lang.String, int)}.
	 * @throws MissingPokemonPictureException 
	 */
	@Test
	void testPokemonInvalidID() throws MissingPokemonPictureException {
		boolean hasError = false;

		try {
			Pokemon pikachu = new Pokemon("999999", pikaName, pikaGen);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			hasError = true;
		}

		assertTrue(hasError, "Pokemon constructor did not detect invalid id parameters!");
	}
	
	/**
	 * Test method for
	 * {@link model.Pokemon#Pokemon(java.lang.String, java.lang.String, int)}.
	 * @throws MissingPokemonPictureException 
	 */
	@Test
	void testPokemonInvalidGen() {
		boolean hasError = false;

		try {
			Pokemon pikachu = new Pokemon("000", "TomCapaul", -1);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			hasError = true;
		}

		assertTrue(hasError, "Pokemon constructor did not detect invalid gen parameters!");
	}

	/**
	 * Test method for {@link model.Pokemon#getName()}.
	 */
	@Test
	void testGetName() {
		assertTrue(pikachu.getName().equals(pikaName));
	}

	/**
	 * Test method for {@link model.Pokemon#getID()}.
	 */
	@Test
	void testGetID() {
		assertTrue(pikachu.getID().equals(pikaID));
	}

	/**
	 * Test method for {@link model.Pokemon#getNum()}.
	 */
	@Test
	void testGetNum() {
		assertTrue(pikachu.getName().equals(pikaName));
	}

	/**
	 * Test method for {@link model.Pokemon#getPNG()}.
	 * @throws IOException 
	 */
	@Test
	void testGetPNG() throws IOException {
		String filePath = "./src/images/pokedex/Gen1/025Pikachu.png";
		BufferedImage pikaPic = ImageIO.read(new File(filePath));
		BufferedImage original = pikachu.getPNG();
		int expected = original.getData().getDataBuffer().getDataType();
		int actual = pikaPic.getData().getDataBuffer().getDataType();
		assertTrue(expected == actual);
	}

	/**
	 * Test method for {@link model.Pokemon#compareTo(java.lang.Object)}.
	 */
	@Test
	void testCompareToTrue() {
		Pokemon p = new Pokemon(pikaID, pikaName, pikaGen);
		int res = p.compareTo(pikachu);
		assertTrue(res == 1);

	}
	
	/**
	 * Test method for {@link model.Pokemon#compareTo(java.lang.Object)}.
	 */
	@Test
	void testCompareToFalse() {
		Pokemon p = new Pokemon("001", "Bulbasaur", 1);
		int res = p.compareTo(pikachu);
		assertTrue(res == -1);

	}

	/**
	 * Test method for {@link model.Pokemon#toString()}.
	 */
	@Test
	void testToString() {
		String s = pikaID + pikaName;
		assertTrue(s.equals(pikachu.toString()));
	}

}
