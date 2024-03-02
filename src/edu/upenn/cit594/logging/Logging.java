package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class Logging {
	private static Logging log = null;
	private static String fileName;
	private PrintWriter writer;

	private Logging() {
		try {
			FileWriter fw = new FileWriter(Logging.fileName);
			writer = new PrintWriter(fw, true);
		} catch (IOException e) {
			System.out.println("Error in writting log file.");
		}
	}

	/**
	 * @return the fileName
	 */
	public static String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public static void setFileName(String fileName) {
		Logging.fileName = fileName;
	}

	public static Logging getInstance() {
		if (log == null) {
			log = new Logging();
		}
		return log;
	}


	public void logUpdate(String logging) {
		if (logging == null)
			return;

		writer.println(logging);

	}

}