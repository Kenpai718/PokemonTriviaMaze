/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.MissingPokemonException;
import model.Maze;
import model.Pokedex;
import model.Pokemon;

/**
 * @author ajdow
 *
 */
class PokedexTest {

	Pokedex myPokedex;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		myPokedex = Pokedex.getInstance();
		myPokedex.restoreGensToDefault();
	}

	/**
	 * Test method for {@link model.Pokedex#getInstance()}.
	 */
	@Test
	void testGetInstance() {
		assertNotNull(myPokedex);
		// Test that singleton pattern is working
		final Pokedex newPokedex = Pokedex.getInstance();
		assertSame(myPokedex, newPokedex, "Singleton is not working correctly");
	}

	/**
	 * Test method for {@link model.Pokedex#addGenToDex(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddGenToDex() throws Exception {
		int start = myPokedex.getCount();
		myPokedex.addGenToDex(2);

		assertFalse(start == myPokedex.getCount());
	}

	/**
	 * Test method for {@link model.Pokedex#addAllGensToDex()}.
	 */
	@Test
	void testAddAllGensToDex() {
		int start = myPokedex.getCount();
		myPokedex.addAllGensToDex();

		assertFalse(start == myPokedex.getCount());
	}

	/**
	 * Test method for {@link model.Pokedex#removeGenFromDex(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveGenFromDex() throws Exception {
		myPokedex.addGenToDex(3);
		int start = myPokedex.getCount();
		myPokedex.removeGenFromDex(3);

		assertFalse(start == myPokedex.getCount());
	}

	/**
	 * Test method for {@link model.Pokedex#refreshSelectGens()}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testRefreshSelectGens() throws Exception {
		myPokedex.addGenToDex(5);
		Set<Integer> set = myPokedex.getSelectedGens();

		myPokedex.refreshSelectGens();

		Set<Integer> set2 = myPokedex.getSelectedGens();

		assertTrue(set == set2);

	}

	/**
	 * Test method for {@link model.Pokedex#restoreGensToDefault()}.
	 */
	@Test
	void testRestoreGensToDefault() {
		myPokedex.addAllGensToDex();
		myPokedex.restoreGensToDefault();

		assertTrue(myPokedex.getSelectedGens().size() == 1);
	}

	/**
	 * Test method for {@link model.Pokedex#canRemoveGen()}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testCanRemoveGenTrue() throws Exception {
		myPokedex.addGenToDex(2);
		boolean result = myPokedex.canRemoveGen();
		assertTrue(result);
	}

	/**
	 * Test method for {@link model.Pokedex#canRemoveGen()}.
	 */
	@Test
	void testCanRemoveGenFalse() {
		myPokedex.restoreGensToDefault();
		boolean result = myPokedex.canRemoveGen();
		assertFalse(result);
	}

	/**
	 * Test method for
	 * {@link model.Pokedex#addPokemon(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	void testAddPokemon() {
		int start = myPokedex.getCount();
		myPokedex.addPokemon("152", "Chikorita", 2);
		assertTrue(start < myPokedex.getCount());
	}

	/**
	 * Test method for {@link model.Pokedex#getPokedex()}.
	 */
	@Test
	void testGetPokedex() {
		assertTrue(myPokedex.getPokedex() == myPokedex.getPokedex());
	}

	/**
	 * Test method for {@link model.Pokedex#getCount()}.
	 */
	@Test
	void testGetCount() {
		assertTrue(myPokedex.getCount() == myPokedex.getCount());
	}

	/**
	 * Test method for {@link model.Pokedex#getSelectedGens()}.
	 */
	@Test
	void testGetSelectedGens() {
	
		Set<Integer> set = myPokedex.getSelectedGens();
		assertTrue(set == myPokedex.getSelectedGens());
	}

	/**
	 * Test method for {@link model.Pokedex#findPokemon(int)}.
	 * @throws MissingPokemonException 
	 */
	@Test
	void testFindPokemonInt() throws MissingPokemonException {
		Pokemon p = new Pokemon("025", "Pikachu", 1);
		Pokemon p2 = myPokedex.findPokemon(25);
		assertTrue(p.compareTo(p2) == 1);
	}

	/**
	 * Test method for {@link model.Pokedex#findPokemon(java.lang.String)}.
	 * @throws MissingPokemonException 
	 */
	@Test
	void testFindPokemonString() throws MissingPokemonException {
		Pokemon p = new Pokemon("025", "Pikachu", 1);
		Pokemon p2 = myPokedex.findPokemon("Pikachu");
		assertTrue(p.compareTo(p2) == 1);
	}

	/**
	 * Test method for {@link model.Pokedex#findPokemonName(int)}.
	 */
	@Test
	void testFindPokemonName() {
		String name = myPokedex.findPokemonName(25).toLowerCase();
		assertTrue(name.equals("pikachu"));
	}

	/**
	 * Test method for {@link model.Pokedex#hasPokemon(int)}.
	 */
	@Test
	void testHasPokemonInt() {
		assertTrue(myPokedex.hasPokemon(1));
	}

	/**
	 * Test method for {@link model.Pokedex#hasPokemon(java.lang.String)}.
	 */
	@Test
	void testHasPokemonString() {
		assertTrue(myPokedex.hasPokemon("Pikachu"));
	}

	/**
	 * Test method for {@link model.Pokedex#setUseMegas(boolean)}.
	 * @throws Exception 
	 */
	@Test
	void testSetUseMegas() throws Exception {
		int start = myPokedex.getCount();
		myPokedex.setUseMegas(true);
		myPokedex.refreshSelectGens();
		
		assertFalse(start == myPokedex.getCount());
	}

	/**
	 * Test method for {@link model.Pokedex#toString()}.
	 */
	@Test
	void testToString() {
		String s = myPokedex.toString();
		assertTrue(s.equals(myPokedex.toString()));
	}

}
