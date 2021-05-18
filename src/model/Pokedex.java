package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.sqlite.SQLiteDataSource;

/**
 * Stores information about every Pokemon such as their ID number and name. Initially
 * reads off an SQL database file to get Pokemon information.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class Pokedex {

	/*
	 * ID number and Pokemon. Used for instant lookups for Pokemon
	 */
	private final Map<Integer, Pokemon> myPokedex;

	/* How many pokemon in pokedex */
	int myCounter;

	/**
	 * Constructor to initialize pokedex
	 */
	public Pokedex() {
		// TODO Auto-generated constructor stub
		myPokedex = new HashMap<Integer, Pokemon>();
		myCounter = 0;

		// "empty" pokemon used in the case where there is no pokemon found
		final Pokemon missingNo = new Pokemon("000", "MissingNo");
		myPokedex.put(0, missingNo);

		// fill pokedex with database
		fillPokedex();
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

		System.out.println("Opened pokedex database successfully");

		System.out.println("Selecting all rows from test table");
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

			System.out.println("Finished adding pokemon to pokedex.");
			// System.out.println(myPokedex);
		} catch (final SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Add a pokemon to the pokedex map
	 * 
	 * @param String theID of the pokemon based on official pokedex
	 * @param String theName of the pokemon
	 */
	public void addPokemon(final String theID, final String theName) {
		final Pokemon pkmn = new Pokemon(theID, theName);
		myPokedex.put(Integer.parseInt(theID), pkmn);
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
	 * Lookup pokemon based on id number in map. If not found return missingno.
	 * 
	 * @return Pokemon at that ID number
	 */
	public Pokemon findPokemon(final int theID) {
		Pokemon res = myPokedex.get("000"); // missingno in case not found
		if (myPokedex.containsKey(theID)) {
			res = (myPokedex.get(theID)); // if found return pokemon
		}

		return res;
	}

	@Override
	public String toString() {
		return myPokedex.toString();
	}

}
