package model;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.sqlite.SQLiteDataSource;

import controller.AnswerFormatter;
import controller.SQLPokedexFiller;
import exceptions.MissingPokemonException;

/**
 * Stores information about every Pokemon such as their ID number and name.
 * Initially reads off an SQL database file(s) to get Pokemon information.
 * Stores the data from the database into a map for other classes to easily
 * gather info to make a Pokemon.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class Pokedex implements Serializable {

	/**
	     * 
	     */
	private static final long serialVersionUID = 1230447359362563837L;

	/*
	 * Entry to use if finding a pokemon returns missing
	 */
	private Pokemon myMissingPokemon;

	/*
	 * Maximum supported pokemon generations
	 */
	public final int MAX_GEN = 7;

	/*
	 * Default gen to play on reset or startup
	 */
	public final int DEFAULT_GEN = 1;

	/*
	 * Singleton pokedex
	 */
	public static Pokedex singleDex = null;

	/*
	 * key = number id of when the pokemon was added to pokedex (myCounter) value =
	 * Arraylist<String> to store pokemon fields, [0]: official pokedex id num
	 * "025", [1]: name "pikachu", 2 = "game gen number"
	 * 
	 * Used for look ups for pokemon based on random generation
	 */
	private Map<Integer, ArrayList<String>> myPokedex;

	/*
	 * Alternative map for looking up the name
	 * 
	 * Key = String formatted name of the pokemon, value = id number when added to
	 * pokedex
	 * 
	 * Used in conjunction with myPokedex to get info used to make a pokemon
	 */
	private Map<String, Integer> myNameDex;

	/*
	 * Holds list of all pokemon added. Maninly used for the toString method.
	 * Format: ID + " " + NAME + " " + GEN
	 */
	private ArrayList<String> myPokemonList;

	/*
	 * Generations that have been selected to play on
	 */
	private Set<Integer> mySelectedGens;

	/*
	 * How many pokemon currently in pokedex. Also used as an id number for when
	 * pokemon are added into the maps.
	 *
	 */
	private int myCounter;

	/*
	 * Missingno
	 */
	private final ArrayList<String> myMissing;

	/*
	 * Controls if mega pokemon should be added to pokedex
	 */
	private boolean myUseMegas;

	/**
	 * Constructor to initialize pokedex
	 */
	private Pokedex() {
		myPokedex = new HashMap<Integer, ArrayList<String>>();
		myNameDex = new HashMap<String, Integer>();
		myPokemonList = new ArrayList<String>();
		mySelectedGens = new HashSet<Integer>();
		myUseMegas = false;
		myCounter = 0;

		// "empty" pokemon used in the case where there is no pokemon found
		myMissingPokemon = null;
		try {
			myMissingPokemon = new Pokemon("000", "MissingNo", 0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		myMissing = new ArrayList<String>();
		myMissing.add(0, "000");
		myMissing.add(1, "MissingNo");
		myMissing.add(2, "0");

		// fill pokedex with database
		try {
			addGenToDex(DEFAULT_GEN); // gen 1 by default
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/**
	 * Read from the pokedex database file with SQLite to create pokemon objects to
	 * add to the pokedex map.
	 * 
	 * Reads off of Gen#Pokedex.db file.Adds a specific pokemon generation number
	 * 1-7 to the pokedex.
	 * 
	 * Makes the database if it does not exist
	 * 
	 * @param theNum generation number
	 * 
	 */
	private void fillPokedex(final int theNum) throws Exception {

		if (theNum > MAX_GEN || theNum < 1) {
			throw new Exception(theNum + " is not a playable generation for the pokedex.");
		}

		SQLiteDataSource ds = null;
		final String databaseName = "Gen" + theNum + "Pokedex";

		// make a new database if it does not exist
		File database = new File(databaseName + ".db");
		if (!database.exists()) {
			System.out.println(databaseName + "does not exist! Now making the database...");
			SQLPokedexFiller.buildDatabase(databaseName, theNum);
		}

		// establish connection (creates db file if it does not exist :-)
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:" + databaseName + ".db");

		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// System.out.println("Opened " + databaseName + " database
		// successfully!");

		// System.out.println("Selecting all rows from " + databaseName+ " table
		// to add
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
				final boolean isMega = name.toLowerCase().contains("mega");

				// don't add mega pokemon unless myUseMegas is true
				// anything else is fair game
				if (!isMega) {
					addPokemon(id, name, theNum);
				} else if (myUseMegas && isMega) {
					// System.out.println("adding a mega " + name);
					addPokemon(id, name, theNum);
				}

			}

			// System.out.println("Finished adding pokemon from " + databaseName
			// + "\n");
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
		mySelectedGens.clear();
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

	/**
	 * Refreshes pokedex and adds all gen pokemon still in the game
	 * 
	 * @throws Exception
	 */
	public void refreshSelectGens() throws Exception {
		resetPokedex();
		for (final int i : mySelectedGens) {
			fillPokedex(i);
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
		return mySelectedGens.size() > 1;
	}

	/*
	 * Reset pokedex maps and counter
	 */
	private void resetPokedex() {
		myPokedex.clear();
		myNameDex.clear();
		myPokemonList.clear();
		myCounter = 0;
	}

	/**
	 * Add a pokemon to the pokedex map and the name map
	 * 
	 * @param String theID of the pokemon based on official pokedex
	 * @param String theName of the pokemon
	 */
	public void addPokemon(final String theID, final String theName, final int theGenNum) {
		final String pkmnName = theName;
		final String formatName = AnswerFormatter.formatAnswer(theName);
		myCounter++;

		// store pokemon field info
		final String id = AnswerFormatter.idConverter(theID);
		final ArrayList<String> pokemonInfo = new ArrayList<String>();
		final String fullInfo = id + " " + pkmnName + " " + theGenNum;

		pokemonInfo.add(0, id);
		pokemonInfo.add(1, theName);
		pokemonInfo.add(2, String.valueOf(theGenNum));

		myPokedex.put(myCounter, pokemonInfo);
		myNameDex.put(formatName, myCounter);
		myPokemonList.add(fullInfo);
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

	/***
	 * Pokedex counter
	 * 
	 * @return set of selected gens
	 */
	public Set<Integer> getSelectedGens() {
		return mySelectedGens;
	}

	/**
	 * Lookup pokemon based on id number in map. If not found return missingno.
	 * 
	 * @return Pokemon at that ID number
	 * @throws MissingPokemonException
	 */
	public Pokemon findPokemon(final int theID) throws MissingPokemonException {

		Pokemon res = myMissingPokemon;
		if (myPokedex.containsKey(theID)) {
			final ArrayList<String> info = myPokedex.get(theID);
			try {
				res = new Pokemon(info.get(0), info.get(1), Integer.parseInt(info.get(2)));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			res = myMissingPokemon; // missingno in case not found
			throw new MissingPokemonException();
		}

		return res;
	}

	/**
	 * Lookup pokemon based on name in map. If not found return missingno.
	 * 
	 * @param theName the name of the pokemon to find
	 * @return Pokemon with that name
	 */
	public Pokemon findPokemon(final String theName) throws MissingPokemonException {
		final String formatName = AnswerFormatter.formatAnswer(theName);
		Pokemon res = myMissingPokemon;
		if (myNameDex.containsKey(formatName)) {
			final int theID = myNameDex.get(formatName);
			final ArrayList<String> pokemonInfo = myPokedex.get(theID);
			try {
				res = new Pokemon(pokemonInfo.get(0), pokemonInfo.get(1), Integer.parseInt(pokemonInfo.get(2)));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				res = myMissingPokemon;
			} catch (Exception e) {
				e.printStackTrace();
				res = myMissingPokemon;
			}

		} else {
			throw new MissingPokemonException(theName);
		}

		return res;
	}

	/**
	 * Grabs the name of the pokemon with the passed ID
	 * 
	 * @param theID the ID number of the pokemon
	 * @return the name of the pokemon
	 */
	public String findPokemonName(final int theID) {
		String res;
		if (myPokedex.containsKey(theID)) {
			final ArrayList<String> info = myPokedex.get(theID);
			res = info.get(1); // if found return
								// pokemon
		} else {
			System.out.println(theID + " not found in pokedex! MissingNo will be returned.");
			res = "MissingNo"; // missingno in case not found
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
		return myPokedex.containsKey(theID);
	}

	/**
	 * Lookup pokemon based on name in map. If not found return false
	 * 
	 * @return boolean if that pokemon is in pokedex
	 */
	public Boolean hasPokemon(final String theName) {
		final String formatName = AnswerFormatter.formatAnswer(theName);
		return myNameDex.containsKey(formatName);
	}

	/**
	 * 
	 * @return myNameDex
	 */
	public Map getNameDex() {
		return myNameDex;
	}

	/**
	 * 
	 * @param theState
	 */
	public void setUseMegas(final boolean theState) {
		myUseMegas = theState;
	}

	/**
	 * Resolve singleton serialization
	 * 
	 * @return object this pokedex
	 */
	private Object readResolve() {

		final Pokedex instance = getInstance();
		instance.myPokedex = myPokedex;
		instance.myNameDex = myNameDex;
		instance.mySelectedGens = mySelectedGens;
		instance.myCounter = myCounter;
		return instance;
	}

	/**
	 * 
	 * List official pokedex number, pokemon name and gen per line
	 * 
	 * @return every pokemon's info currently in the pokedex
	 */

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		String line = "-------------------------------------\n";
		String id;
		String name;
		String gen;
		String format = String.format("%-6s%-27s%3s", " ID", "Pokemon Name", " Gen");
		sb.append(format + "\n");
		sb.append(line);

		// print each pokemon in order
		Collections.sort(myPokemonList);
		int count = 1;
		for (String pkmn : myPokemonList) {
			// in list they are separated by spaces
			String[] info = pkmn.split(" ");
			id = info[0];
			name = info[1];
			gen = info[2];

			// put a intersection between new generations
			if (count != Integer.parseInt(gen)) {
				count = Integer.parseInt(gen);
				sb.append(line);
			}

			format = String.format("%-6s%-27s%3s", id, name, gen);
			sb.append(format + "\n");
		}

		sb.append(line);
		format = String.format("%36s", "Total Entries: " + myCounter);
		sb.append(format);

		return sb.toString();
	}

}
