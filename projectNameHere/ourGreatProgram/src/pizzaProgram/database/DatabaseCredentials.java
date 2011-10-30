package pizzaProgram.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseCredentials {

	private static final String URL_TYPE = "URL";
	private static final String USERNAME_TYPE = "USERNAME";
	private static final String PASSWORD_TYPE = "PASSWORD";

	private String url = "jdbc:";
	private String username = "";
	private String password = "";

	public void loadFromFile(String src) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(src));
			while (fileReader.ready()) {
				String read = fileReader.readLine();
				readLine(read);
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("Unable to find the configurationfile for the database."
							+ "Please make sure ../config/databaseinfo.cfg exists: "
							+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to read the configfile: "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	public String getURL() {
		return this.url;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	private void readLine(String line) {
		if (!(line == null || this.lineIsComment(line))) {
			String[] typeAndValueStringsArray = this
					.extractTypeAndContentStrings(line);
			this.setValueStringByType(typeAndValueStringsArray[0],
					typeAndValueStringsArray[1]);
		}
	}

	private void setValueStringByType(String type, String contents) {
		if (type.equals(URL_TYPE)) {
			url += contents;
		} else if (type.equals(USERNAME_TYPE)) {
			username = contents;
		} else if (type.equals(PASSWORD_TYPE)) {
			password = contents;
		}
	}

	private String[] extractTypeAndContentStrings(String line) {
		String[] typeAndValueStringsArray = line.split(":", 2);
		if (typeAndValueStringsArray.length == 2) {
			typeAndValueStringsArray[1] = typeAndValueStringsArray[1].trim();
			return typeAndValueStringsArray;
		} else {
			System.out
					.println("There is an error in the configuration file in one of the uncommented lines.");
			return new String[] { "none", "none" };
		}
	}

	private boolean lineIsComment(String line) {
		return (line.charAt(0) == '#');
	}
}