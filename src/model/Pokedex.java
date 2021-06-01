package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	 * ID number and Pokemon. Used for instant lookups for Pokemon
	 */
	private final Map<String, String> myPokedex;
	
	/*
	 * Alternative map for looking up the name
	 */
	private final Map<String, String> myNameDex;
	
	/*
	 * Generations that have been selected to play on
	 */
	private final Set<Integer> mySelectedGens;
	

	/* How many pokemon currently in pokedex */
	private int myCounter;
	
	

	/**
	 * Constructor to initialize pokedex
	 */
	private Pokedex() {
		myPokedex = new HashMap<String, String>();
		myNameDex = new HashMap<String, String>();
		mySelectedGens = new HashSet<Integer>();
		myCounter = 0;

		// "empty" pokemon used in the case where there is no pokemon found

		// fill pokedex with database
		try {
			addGenToDex(DEFAULT_GEN); //gen 1 by default
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//joke addition easter egg: "a jigglypuff seen from above"
		final String jigglyAbove = "JigglypuffSeenFromAbove";
		myPokedex.put("999", jigglyAbove);
		myNameDex.put("meme", jigglyAbove);
		//only found from the insert pokemon cheat
		
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
	 * Reads off of Gen#Pokedex.db file
	 * Adds a specific pokemon generation number 1-7 to the pokedex
	 * 
	 */
	private void fillPokedex(final int theNum) throws Exception {
		
		if(theNum > MAX_GEN || theNum < 1) {
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

		System.out.println("Opened " + databaseName + " database successfully!");

		System.out.println("Selecting all rows from " + databaseName +" table to add to Pokedex map...");
		final String query = "SELECT * FROM " + databaseName;

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

			System.out.println("Finished adding pokemon from " + databaseName + "\n");
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
		if(theGenNum > MAX_GEN || theGenNum < 1) {
			throw new Exception("Not a valid gen number");
		}
		
		if(!mySelectedGens.contains(theGenNum)) {
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
		for(int i = 1; i <= MAX_GEN; i++) {
			try {
				addGenToDex(i);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Removes a pokemon gen from the pokedex by resetting the pokedex
	 * and reading the current selection databases again.
	 * 
	 * @param theGenNum
	 * @throws Exception
	 */
	public void removeGenFromDex(final int theGenNum) throws Exception {
		if(theGenNum > MAX_GEN || theGenNum < 1) {
			throw new Exception("Not a valid gen number");
		}
		
		if(!mySelectedGens.contains(theGenNum)) {
			throw new Exception(theGenNum + " is not currently selected.");
		} else if (mySelectedGens.size() == 1) {
			throw new Exception("Must have at least one pokemon generation selected.");
		} else {
			mySelectedGens.remove(theGenNum);
			resetPokedex();
			
			for(final int i : mySelectedGens) {
				fillPokedex(i);
			}
			
		}
	}
	
	/*
	 * Restores pokedex map to default
	 * Map will only have gen 1 pokemon.
	 * 
	 */
	public void restoreGensToDefault() {
		resetPokedex();
		try {
			addGenToDex(DEFAULT_GEN);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Can remove pokemon gens if the current is greater than 1
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
		mySelectedGens.clear();
		myCounter = 0;
	}
	
	
	/**
	 * Add a pokemon to the pokedex map and the name map
	 * 
	 * @param String theID of the pokemon based on official pokedex
	 * @param String theName of the pokemon
	 */
	public void addPokemon(final String theID, final String theName) {
		final String pkmn = theName;
		final String formatName = formatString(theName);
		myCounter++;
		myPokedex.put(theID, pkmn);
		myNameDex.put(formatName, pkmn);
	}

	/**
	 * Pokedex getter
	 * 
	 * @return a map of pokemon
	 */
	public Map<String, String> getPokedex() {
		final Map<String, String> copy = myPokedex;
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
	        final String id = ("000" + theID).substring(String.valueOf(theID).length());
		Pokemon res = MISSING; // missingno in case not found
		if (myPokedex.containsKey(id)) {
			res = new Pokemon(id, myPokedex.get(id)); // if found return pokemon
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
		Pokemon res = new Pokemon("000", myPokedex.get("000"));// missingno in case not found
		if (myNameDex.containsKey(formatName)) {
		        final String pokeKey = getKeyByValue(myNameDex.get(formatName));
		        final String id = ("000" + pokeKey).substring(String.valueOf(pokeKey).length());
		        res = new Pokemon(id, myNameDex.get(formatName)); // if found return pokemon
		}

		return res;
	}
	
	private String getKeyByValue(final String theName) {
                // TODO Auto-generated method stub
	        String res = "000";
	        for (final Entry<String, String> entry : myPokedex.entrySet()) {
	                if (Objects.equals(theName, entry.getValue())) {
	                    res = entry.getKey();
	                }
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
