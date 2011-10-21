package pizzaProgram.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	public static final String FILEPATH = System.getProperty("user.home") + "/pizzaProgram/";
	public static final String LOGFILENAME = "log.txt";
	
	private PrintWriter printWriter;
	
	public void start()
	{
		File directory = new File(FILEPATH);
		File logFile = new File(FILEPATH + LOGFILENAME);
		if(!directory.exists())
		{
			directory.mkdir();
		}
		if(!logFile.exists())
		{
			try {
				logFile.delete();
				logFile.createNewFile();
			} catch (IOException e) {
				System.out.println("fatal: failed to create log file (or delete the old one)");
				e.printStackTrace();
				System.exit(0);
			}
		}
		try {
			printWriter = new PrintWriter(FILEPATH + LOGFILENAME);
		} catch (FileNotFoundException e) {
			System.out.println("fatal: log file was not found");
			e.printStackTrace();
			System.exit(0);
		}
		printWriter.println("----- PizzaProgram log file -----");
	}
	public void log(String message)
	{
		printWriter.println(message);
	}
	public void stop()
	{
		printWriter.close();
	}
}
