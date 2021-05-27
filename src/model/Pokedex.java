package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.sqlite.SQLiteDataSource;

/**
 * Stores information about every Pokemon such as their ID number and name.
 * Initially reads off an SQL database file to get Pokemon information.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class Pokedex {

	/*
	 * Singleton pokedex
	 */
	public static Pokedex singleDex = null;

	/*
	 * ID number and Pokemon. Used for instant lookups for Pokemon
	 */
	private final Map<Integer, Pokemon> myPokedex;
	
	/*
	 * Alternative map for looking up the name
	 */
	private final Map<String, Pokemon> myNameDex;

	/* How many pokemon in pokedex */
	int myCounter;

	/**
	 * Constructor to initialize pokedex
	 */
	private Pokedex() {
		myPokedex = new HashMap<Integer, Pokemon>();
		myNameDex = new HashMap<String, Pokemon>();
		myCounter = 0;

		// "empty" pokemon used in the case where there is no pokemon found
		final Pokemon missingNo = new Pokemon("000", "MissingNo");
		myPokedex.put(0, missingNo);

		// fill pokedex with database
		fillPokedex();
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
	 * Read from the pokedex database file with SQLite to create pokemon objects
	 * to add to the pokedex map.
	 * 
	 * Reads off of Pokedex.db SQLite file
	 * 
	 */
	private void fillPokedex() {
		SQLiteDataSource ds = null;

		// establish connection (creates db file if it does not exist :-)
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:Pokedex.db");
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		//System.out.println("Opened pokedex database successfully");

		//System.out.println("Selecting all rows from pokedex table");
		final String query = "SELECT * FROM Pokedex";

		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement();) {

			final ResultSet rs = stmt.executeQuery(query);

			// walk through each 'row' of results, grab data by column/field
			// name
			// and print it
			while (rs.next()) {
				final String id = rs.getString("ID");
				final String name = rs.getString("NAME");

				addPokemon(id, name);
			}

			//System.out.println("Finished adding pokemon to pokedex.");
			// System.out.println(myPokedex);
		} catch (final SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Add a pokemon to the pokedex map and the name map
	 * 
	 * @param String theID of the pokemon based on official pokedex
	 * @param String theName of the pokemon
	 */
	public void addPokemon(final String theID, final String theName) {
		final Pokemon pkmn = new Pokemon(theID, theName);
		String formatName = formatString(theName);
		myPokedex.put(Integer.parseInt(theID), pkmn);
		myNameDex.put(formatName, pkmn);
		myCounter++;
	}

	/**
	 * Pokedex getter
	 * 
	 * @return a map of pokemon
	 */
	public Map<Integer, Pokemon> getPokedex() {
		final Map<Integer, Pokemon> copy = myPokedex;
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
		return theString.toLowerCase().strip();
	}

	/**
	 * Lookup pokemon based on id number in map. If not found return missingno.
	 * 
	 * @return Pokemon at that ID number
	 */
	public Pokemon findPokemon(final int theID) {
		Pokemon res = myPokedex.get(0); // missingno in case not found
		if (myPokedex.containsKey(theID)) {
			res = (myPokedex.get(theID)); // if found return pokemon
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
		Pokemon res = myPokedex.get(0); // missingno in case not found
		if (myNameDex.containsKey(formatName)) {
			res = (myNameDex.get(formatName)); // if found return pokemon
		}

		return res;
	}
	
	/**
	 * Lookup pokemon based on name in map. If not found return false
	 * 
	 * @return boolean if that pokemon is in pokedex
	 */
	public Boolean hasPokemon(final int theID) {
		boolean res = false;
		if (myPokedex.containsKey(theID)) {
			res = true;
		}

		return res;
	}
	
	/**
	 * Lookup pokemon based on name in map. If not found return false
	 * 
	 * @return boolean if that pokemon is in pokedex
	 */
	public Boolean hasPokemon(final String theName) {
		final String formatName = formatString(theName);
		boolean res = false;
		if (myNameDex.containsKey(formatName)) {
			res = true;
		}

		return res;
	}
	
	
	@Override
	public String toString() {
		return myPokedex.toString();
	}

}
