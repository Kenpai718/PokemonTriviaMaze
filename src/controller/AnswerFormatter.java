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
	 * Formats the answers the same removes all whitespace, replaces - and _ with "
	 * ". Makes all toLowercase.
	 * 
	 *
	 * @param theString to format
	 * 
	 * @return formatted string
	 */
	public static String formatAnswer(final String theString) {

		return theString.toLowerCase().strip().replaceAll(" ", "").replaceAll("-", "").replaceAll("_", "");
	}

	/**
	 * Same formatting as formatAnswer but without toLowercase and "-" remover
	 * 
	 * @param theString
	 * @return
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
