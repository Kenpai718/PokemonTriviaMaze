package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.sqlite.SQLiteDataSource;

/**
 * Stores information about every Pokemon such as their ID number and name.
 * Initially reads off an SQL database file to get Pokemon information.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class Pokedex implements Serializable {

	/**
	     * 
	     */
	private static final long serialVersionUID = 1230447359362563837L;

	private static final Pokemon MISSING = new Pokemon("000", "MissingNo");

	/*
	 * Maximum supported pokemon generations
	 */
	final int MAX_GEN = 7;

	/*
	 * Default gen to play on reset or startup
	 */
	final int DEFAULT_GEN = 1;

	/*
	 * Singleton pokedex
	 */
	public static Pokedex singleDex = null;

	/*
	 * key = number id of when the pokemon was added to pokedex (myCounter) value =
	 * Arraylist<String> to store pokemon fields, [0]: official pokedex id num
	 * "025", [1]: name "pikachu"
	 * 
	 * Used for look ups for pokemon based on random generation
	 */
	private final Map<Integer, ArrayList<String>> myPokedex;
	// private final Map<String, String> myPokedex;

	/*
	 * Alternative map for looking up the name
	 * 
	 * Key = String formatted name of the pokemon, value = id number when added to
	 * pokedex
	 * 
	 * Used in conjunction with myPokedex to get info used to make a pokemon
	 */
	private final Map<String, Integer> myNameDex;

	/*
	 * Generations that have been selected to play on
	 */
	private final Set<Integer> mySelectedGens;

	/*
	 * How many pokemon currently in pokedex Also used as an id number for when
	 * pokemon are added into the maps.
	 *
	 */
	private int myCounter;

	/**
	 * Constructor to initialize pokedex
	 */
	private Pokedex() {
		myPokedex = new HashMap<Integer, ArrayList<String>>();
		myNameDex = new HashMap<String, Integer>();
		mySelectedGens = new HashSet<Integer>();
		myCounter = 0;

		// "empty" pokemon used in the case where there is no pokemon found
		ArrayList<String> empty = new ArrayList<String>();
		empty.add(0, "000");
		empty.add(1, "MissingNo");

		// fill pokedex with database
		try {
			addGenToDex(DEFAULT_GEN); // gen 1 by default
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// joke addition easter egg: "a jigglypuff seen from above" final
		ArrayList<String> joke = new ArrayList<String>();
		joke.add(0, "999");
		joke.add(1, "JigglypuffSeenFromAbove");
		myPokedex.put(999, joke);
		myNameDex.put("meme", 999);
		// only found from the insert pokemon cheat

	}

	/*
	 * Singleton instantiation
	 */
	public static Pokedex getInstance() {
		if (singleDex == null) {
			singleDex = new Pokedex();
		}
		return singleDex;
	}

	/*
	 * Read from the pokedex database file with SQLite to create pokemon objects to
	 * add to the pokedex map.
	 * 
	 * Reads off of Gen#Pokedex.db file Adds a specific pokemon generation number
	 * 1-7 to the pokedex
	 * 
	 */
	private void fillPokedex(final int theNum) throws Exception {

		if (theNum > MAX_GEN || theNum < 1) {
			throw new Exception(theNum + " is not a playable generation for the pokedex.");
		}

		SQLiteDataSource ds = null;
		final String databaseName = "Gen" + theNum + "Pokedex";

		// establish connection (creates db file if it does not exist :-)
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:" + databaseName + ".db");

		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// System.out.println("Opened " + databaseName + " database successfully!");

		// System.out.println("Selecting all rows from " + databaseName+ " table to add
		// to Pokedex map...");
		final String query = "SELECT * FROM " + databaseName;

		try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement();) {

			final ResultSet rs = stmt.executeQuery(query);

			// walk through each 'row' of results, grab data by column/field
			// name
			// and print it
			while (rs.next()) {
				final String id = rs.getString("ID");
				final String name = rs.getString("NAME");

				addPokemon(id, name);
			}

			// System.out.println("Finished adding pokemon from " + databaseName + "\n");
		} catch (final SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Add a pokemon generation to the pokedex
	 * 
	 * @param the gen to add: 1- 7
	 */
	public void addGenToDex(final int theGenNum) throws Exception {
		if (theGenNum > MAX_GEN || theGenNum < 1) {
			throw new Exception("Not a valid gen number");
		}

		if (!mySelectedGens.contains(theGenNum)) {
			mySelectedGens.add(theGenNum);
			fillPokedex(theGenNum);
		}
	}

	/*
	 * Add all gens to the pokedex
	 * 
	 */
	public void addAllGensToDex() {
		resetPokedex();
		for (int i = 1; i <= MAX_GEN; i++) {
			try {
				addGenToDex(i);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Removes a pokemon gen from the pokedex by resetting the pokedex and reading
	 * the current selection databases again.
	 * 
	 * @param theGenNum
	 * @throws Exception
	 */
	public void removeGenFromDex(final int theGenNum) throws Exception {
		if (theGenNum > MAX_GEN || theGenNum < 1) {
			throw new Exception("Not a valid gen number");
		}

		if (!mySelectedGens.contains(theGenNum)) {
			throw new Exception(theGenNum + " is not currently selected.");
		} else if (mySelectedGens.size() == 1) {
			throw new Exception("Must have at least one pokemon generation selected.");
		} else {
			mySelectedGens.remove(theGenNum);
			resetPokedex();

			for (final int i : mySelectedGens) {
				fillPokedex(i);
			}

		}
	}

	/*
	 * Restores pokedex map to default Map will only have gen 1 pokemon.
	 * 
	 */
	public void restoreGensToDefault() {
		resetPokedex();
		mySelectedGens.clear();
		try {
			addGenToDex(DEFAULT_GEN);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Can remove pokemon gens if the current is greater than 1
	 * 
	 * @return boolean
	 */
	public boolean canRemoveGen() {
		return mySelectedGens.size() >= 1;
	}

	/*
	 * Reset pokedex maps
	 */
	private void resetPokedex() {
		myPokedex.clear();
		myNameDex.clear();
		myCounter = 0;
	}

	/**
	 * Add a pokemon to the pokedex map and the name map
	 * 
	 * @param String theID of the pokemon based on official pokedex
	 * @param String theName of the pokemon
	 */
	public void addPokemon(final String theID, final String theName) {
		final String pkmnName = theName;
		final String formatName = formatString(theName);
		myCounter++;

		// store pokemon field info
		final String id = idConverter(theID);
		ArrayList<String> pokemonInfo = new ArrayList<String>();
		pokemonInfo.add(0, id);
		pokemonInfo.add(1, theName);

		myPokedex.put(myCounter, pokemonInfo);
		myNameDex.put(formatName, myCounter);
	}

	/**
	 * Convert a string to a proper id format a pokemon object can read
	 * 
	 * @return String formatted ex: 0 to "000"
	 */
	private String idConverter(final String theID) {
		return ("000" + theID).substring(String.valueOf(theID).length());
	}

	/**
	 * Pokedex getter
	 * 
	 * @return a map of pokemon
	 */
	public Map<Integer, ArrayList<String>> getPokedex() {
		final Map<Integer, ArrayList<String>> copy = myPokedex;
		return copy;
	}

	/***
	 * Pokedex counter
	 * 
	 * @return a map of pokemon
	 */
	public int getCount() {
		return myCounter;
	}

	/**
	 * Method to format string the same to put in name map
	 * 
	 * @param theString
	 * @return formatted string lowercase and stripped
	 */
	private String formatString(final String theString) {
		return theString.toLowerCase().trim();
	}

	/**
	 * Lookup pokemon based on id number in map. If not found return missingno.
	 * 
	 * @return Pokemon at that ID number
	 */
	public Pokemon findPokemon(final int theID) {

		Pokemon res;
		if (myPokedex.containsKey(theID)) {
			ArrayList<String> info = myPokedex.get(theID);
			res = new Pokemon(info.get(0), info.get(1)); // if found return
															// pokemon
		} else {
			System.out.println(theID + " not found in pokedex! MissingNo will be returned.");
			res = MISSING; // missingno in case not found
		}

		return res;
	}

	/**
	 * Lookup pokemon based on name in map. If not found return missingno.
	 * 
	 * @return Pokemon with that name
	 */
	public Pokemon findPokemon(final String theName) {
		final String formatName = formatString(theName);
		Pokemon res;
		if (myNameDex.containsKey(formatName)) {
			int theID = myNameDex.get(theName);
			ArrayList<String> pokemonInfo = myPokedex.get(theID);
			res = new Pokemon(pokemonInfo.get(0), pokemonInfo.get(1));

		} else {
			System.out.println(theName + " not found in pokedex! MissingNo will be returned.");
			res = MISSING;
		}

		return res;
	}

	/*
	 * private String getKeyByValue(final String theName) { // TODO Auto-generated
	 * method stub String res = "000"; for (final Entry<String, String> entry :
	 * myPokedex.entrySet()) { if (Objects.equals(theName, entry.getValue())) { res
	 * = entry.getKey(); } } return res; }
	 */

	/**
	 * Lookup pokemon based on name in map. If not found return false
	 * 
	 * @return boolean if that pokemon is in pokedex
	 */
	public Boolean hasPokemon(final int theID) {
		final String id = ("000" + theID).substring(String.valueOf(theID).length());
		return myPokedex.containsKey(id);
	}

	/**
	 * Lookup pokemon based on name in map. If not found return false
	 * 
	 * @return boolean if that pokemon is in pokedex
	 */
	public Boolean hasPokemon(final String theName) {
		final String formatName = formatString(theName);
		return myNameDex.containsKey(formatName);
	}

	@Override
	public String toString() {
		return myPokedex.toString();
	}

}
