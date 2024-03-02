package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.upenn.cit594.data.ParkingViolation;


public class CSVParkingReader implements ParkingReader {
	
	protected String fileName;
	
	/**
	 * constructor
	 * @param name
	 */
	public CSVParkingReader (String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<ParkingViolation> readAll() {
		List<ParkingViolation> results=new ArrayList<ParkingViolation>(30000);
		File file=new File(this.fileName);
		//define file reader
		FileReader fileReader=null;
		//define buffered reader
		BufferedReader bufferedReader=null;
		
		try {
			fileReader=new FileReader(file);
			bufferedReader=new BufferedReader(fileReader);
			String line = null;
			
			while ((line=bufferedReader.readLine())!=null) {
				String item[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");//comma and ignore comma in ""
				int fine=Integer.parseInt(item[1]);
				String state=item[4];
				int zipCode=0;
				if(item.length==7) {
					
					zipCode=Integer.parseInt(item[6]);
				}
				
				//add data to the result list
				results.add(new ParkingViolation(state,fine,zipCode));
			}
			return results;
			
		}catch (FileNotFoundException e) {
			System.out.println("Sorry, "+ this.fileName +" not found");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			//close file objects
			try {
				fileReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}

}
