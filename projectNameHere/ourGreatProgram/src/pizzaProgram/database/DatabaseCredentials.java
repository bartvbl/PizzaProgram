package pizzaProgram.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for reading the URL, password and username of the database from a
 * locally stored configuration file.
 */
public class DatabaseCredentials {

	/**
	 * The String from the config file denoting that the value coming after the colon is the database's URL
	 */
	private static final String URL_TYPE = "URL";
	/**
	 * The String in the config file denoting that the value following the colon is the database's username
	 */
	private static final String USERNAME_TYPE = "USERNAME";
	/**
	 * The String found in the config file denoting that the value following the semicolon is the database user's password.
	 */
	private static final String PASSWORD_TYPE = "PASSWORD";
	/**
	 * A String representing the database URL. It always starts by 'jdbc:', as required by the database driver used.
	 */
	private String url = "jdbc:";
	/**
	 * A String holding the database's username
	 */
	private String username = "";
	/**
	 * A String holding the database user's password.
	 */
	private String password = "";

	/**
	 * The method reads the entire contents of the file, and looks for the URL,
	 * username and password among its contents. Lines in a properly formatted
	 * configuration file starts with either #, if the line is a comment, or the
	 * key-value pairs of the configuration value, eg "PASSWORD: mypassword".
	 * Once found the method, using the helper methods of the class, store these
	 * values in the private fields of the method.
	 * 
	 * @param src
	 *            The filepath to the configurationfile (can be either a
	 *            relative path or an absolute path)
	 */
	public void loadFromFile(String src) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(src));
			while (fileReader.ready()) {
				String read = fileReader.readLine();
				readLine(read);
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("Unable to find the configuration file for the database."
							+ "Please make sure "
							+ src
							+ " exists: "
							+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to read the configuration file: "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Returns the URL of the database the way it was read from the config file
	 * @return The URL to the database
	 */
	String getURL() {
		return this.url;
	}

	/**
	 * Returns the username of the database user as it was read from the database credentials config file
	 * @return The database user's username
	 */
	String getUsername() {
		return this.username;
	}

	/**
	 * Returns the database user's password as it was read from the database credentials config file
	 * @return The dataabse user's password
	 */
	String getPassword() {
		return this.password;
	}

	/**
	 * Method that evaluates wether the provided line from the configuration
	 * file is empty or a comment. If it is not, it splits the line into it's
	 * key-value pairs as a string array, and sends them to the
	 * setValueStringByType method for further processing.
	 * 
	 * @param line
	 *            The line to be processed.
	 */
	private void readLine(String line) {
		if (!(line == null || this.lineIsComment(line))) {
			String[] typeAndValueStringsArray = line.split(":", 2);
			if (typeAndValueStringsArray.length != 2) {
				System.err
						.println("There is an error in the configuration file in one of the uncommented lines.");
				return;
			}
			typeAndValueStringsArray[0] = typeAndValueStringsArray[0].trim();
			typeAndValueStringsArray[1] = typeAndValueStringsArray[1].trim();
			setValueStringByType(typeAndValueStringsArray[0],
					typeAndValueStringsArray[1]);
		}
	}

	/**
	 * Method that evaluates which type of field to be updated with the provided
	 * contents. It then sets the relevant field to its correct value.
	 * 
	 * @param type
	 *            The type of the field that is to be modified.
	 * @param contents
	 *            The data that is to be added to the field.
	 */
	private void setValueStringByType(String type, String contents) {
		if (type.equals(URL_TYPE)) {
			url += contents;
		} else if (type.equals(USERNAME_TYPE)) {
			username = contents;
		} else if (type.equals(PASSWORD_TYPE)) {
			password = contents;
		}
	}

	/**
	 * Method that evaluates wether or not the given line is a comment.
	 * 
	 * @param line
	 *            The line that is to be evaluated.
	 * @return True if the line is a comment, false in all other cases.
	 */
	private boolean lineIsComment(String line) {
		return (line.charAt(0) == '#');
	}
}