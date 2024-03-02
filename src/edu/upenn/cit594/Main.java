package edu.upenn.cit594;

import java.util.List;

import edu.upenn.cit594.datamanagement.CSVParkingReader;
import edu.upenn.cit594.datamanagement.CSVPropertyReader;
import edu.upenn.cit594.datamanagement.JsonParkingReader;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.TxtPopulationReader;
import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInterface;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length != 5) {
			System.out.println("Incorrect input arguments for main fucntion.");
			return;
		}
		
		String parkingFileType = args[0].toLowerCase();
		String parkingFileName = args[1];
		String propertyFileName = args[2];
		String populationFileName = args[3];
		String logFileName = args[4];
		
		ParkingReader parkingReader= null;
		
		//create log		
		Logging.setFileName(logFileName);
		Logging log=Logging.getInstance();
		log.logUpdate(System.currentTimeMillis()+" "+parkingFileType+" "+parkingFileName+" "+propertyFileName+" "+populationFileName+" "+logFileName);
		
		//DM
		if(parkingFileType.equals("csv")) {
			parkingReader = new CSVParkingReader(parkingFileName);
			log.logUpdate(System.currentTimeMillis()+" Openfile "+parkingFileName);
		}
		else if(parkingFileType.equals("json")) {
			parkingReader = new JsonParkingReader(parkingFileName);
			log.logUpdate(System.currentTimeMillis()+" Openfile "+parkingFileName);
		}
		else {
			System.out.println("Incorrect type for parking input file.");
			return;
		}
		
		CSVPropertyReader propertyReader = new CSVPropertyReader(propertyFileName);
		log.logUpdate(System.currentTimeMillis()+" Openfile "+propertyFileName);
		
		TxtPopulationReader populationReader = new TxtPopulationReader(populationFileName);
		log.logUpdate(System.currentTimeMillis()+" Openfile "+populationFileName);
		
		Processor processor = new Processor(parkingReader, propertyReader, populationReader);
		UserInterface UI = new UserInterface(processor);
		
		UI.start();
		
		//put the user input information into log
		List <String> userInput=UI.getInputForLog();
		for (String str: userInput) {
			log.logUpdate(str);
		}
						
	}

}
