package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

/**
 * 
 * Uses builder design principle to fill a database with pokemon info given a
 * file directory of pokemon images and a database name.
 * 
 * @author tom capaul
 * @author Kenneth Ahrens
 *
 *
 */
public class SQLPokedexFiller {

	/*
	 * SQL
	 */
	private static SQLiteDataSource ds = null;
	/*
	 * Where all the pokemon images are stored. Just add the number at the end
	 */
	private final static String IMAGE_PATH = "./src/images/pokedex/Gen";

	/**
	 * Make the database given the name and the folder that has the images
	 * 
	 * @param String databaseName
	 * @param File   genFolder
	 */
	public static void buildDatabase(final String databaseName, final int theGen) {
		File imageFolder = new File(IMAGE_PATH + theGen);
		setupSQL(databaseName);
		createTable(databaseName, imageFolder);
		printTable(databaseName);
		System.out.println(databaseName + " has been created!" + "\n");

	}

	/**
	 * Initialize SQL
	 * 
	 * @param databaseName
	 */
	public static void setupSQL(String databaseName) {
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:" + databaseName + ".db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Opened database successfully");
	}

	/**
	 * Make SQL table
	 * 
	 * @param databaseName
	 * @param theFolder
	 */
	public static void createTable(String databaseName, File theFolder) {
		// now create a table
		String query = "CREATE TABLE IF NOT EXISTS " + databaseName + " ( " + "ID TEXT NOT NULL, "
				+ "NAME TEXT NOT NULL )";
		try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement();) {
			int rv = stmt.executeUpdate(query);
			System.out.println("executeUpdate() returned " + rv);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Created " + databaseName + " table successfully");

		// next insert two rows of data
		System.out.println("Attempting to insert pokemon into pokedex table");

		fillDatabase(databaseName, theFolder);

	}

	/**
	 * show results of SQL Table
	 * 
	 * @param databaseName
	 */
	public static void printTable(String databaseName) {
		// now query the database table for all its contents and display the results
		System.out.println("Selecting all rows from pokedex table");
		String query = "SELECT * FROM " + databaseName;

		try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement();) {

			ResultSet rs = stmt.executeQuery(query);

			// walk through each 'row' of results, grab data by column/field name
			// and print it
			System.out.println("Results of " + databaseName);
			while (rs.next()) {
				String question = rs.getString("ID");
				String answer = rs.getString("NAME");

				System.out.println("Pokedex ID = " + question + ", NAME = " + answer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Read a file directory of images and add it to the database
	 * 
	 * @param databaseName
	 * @param theFolder
	 */
	public static void fillDatabase(String databaseName, final File theFolder) {
		for (final File fileEntry : theFolder.listFiles()) {

			String fileName = fileEntry.getName();
			// only add the file if it is correct
			if (fileName.endsWith(".png")) {
				String id = fileName.substring(0, 3);
				//only add if the file has a valid id signature
				if (verifyID(id)) {
					String pokemonName = fileName.substring(3).replace(".png", "");
					verifyID(fileName);

					String query1 = "INSERT INTO " + databaseName + " ( ID, NAME ) VALUES ( '" + id.trim() + "', '"
							+ pokemonName.trim() + "' )";

					try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement();) {
						int rv = stmt.executeUpdate(query1);
					} catch (SQLException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
	}

	/**
	 * Checks if an id is the proper number format
	 * Ex: theID = "000" = true
	 * 	   theID = "hey" = false
	 * 
	 * @return false if any char is not a number
	 */
	private static boolean verifyID(final String theID) {
		boolean result = true;
		for (int i = 0; i < theID.length(); i++) {
			String c = "" + theID.charAt(i);
			try {
				int num = Integer.parseInt(c);
			} catch (NumberFormatException e) {
				// not a number
				result = false;
				break;
			}

		}

		return result;

	}

}