package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.upenn.cit594.data.Population;


public class TxtPopulationReader {
	
	protected String fileName;
	
	/**
	 * constructor
	 * @param name
	 */
	public TxtPopulationReader (String fileName) {
		this.fileName = fileName;
	}

	
	public List<Population> readAll() {
		List<Population> results=new ArrayList<Population>(50);
		File file=new File(fileName);
		//define file reader
		FileReader fileReader=null;
		//define buffered reader
		BufferedReader bufferedReader=null;
		
		try {
			fileReader=new FileReader(file);
			bufferedReader=new BufferedReader(fileReader);
			String line = null;
			
			while ((line=bufferedReader.readLine())!=null) {
				String item[] = line.split("\s+");
				int zipCode=Integer.parseInt(item[0]);
				int population=Integer.parseInt(item[1]);
			
				
				//add data to the result list
				results.add(new Population(zipCode, population));
			}
			return results;
			
		}catch (FileNotFoundException e) {
			System.out.println("Sorry, "+ fileName +" not found");
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
