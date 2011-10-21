
/**
 * The EntryPoint class's only task is to start the program
 * @author Bart
 *
 */
public class EntryPoint {

	/**
	 * This function starts the program when it is started. It creates an instance of the main class, initializes and runs it.
	 * @param args any command line arguments of the program, are automatically inserted as part of the java specifications
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.initialize();
	}

}
