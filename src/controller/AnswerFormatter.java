package controller;

/**
 * Helper class to make answer choices the same formatting Used to make it
 * easier to do name comparisons.
 * 
 * @author Kenneth Ahrens
 *
 */
public class AnswerFormatter {

	/**
	 * Formats the answers the same removes all non alphabetical characters
	 * and makes it lowercase.
	 * 
	 * The use is to compare user input and make sure the user can input it
	 * slightly wrong and still get it right.
	 * 
	 *
	 * @param theString to format
	 * 
	 * @return formatted string
	 */
	public static String formatAnswer(final String theString) {

		return theString.toLowerCase().strip().replaceAll("[^a-zA-Z0-9]", "");  
	}

	/**
	 * remove trailing white space and make _ a space
	 * 
	 * @param theString
	 * @return formatted string
	 */
	public static String formatMultipleChoiceAnswer(final String theString) {
		return theString.strip().replaceAll("_", " "); 
	}

	/**
	 * Convert a string to a proper id format a pokemon object can read
	 * 
	 * @return String formatted ex: 0 to "000"
	 */
	public static String idConverter(final String theID) {
		return ("000" + theID).substring(String.valueOf(theID).length());
	}

}
