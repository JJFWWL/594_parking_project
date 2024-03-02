package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;

public class JsonParkingReader implements ParkingReader {

	protected String fileName;

	public JsonParkingReader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<ParkingViolation> readAll() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// create a parser
		JSONParser parser = new JSONParser();

		// open the file and get the array of JSON objects
		JSONArray file;
		
		try {
			// read the file_temp into filereader
			File file_temp = new File(fileName);
			FileReader fileReader = new FileReader(file_temp);

			// change to jsonarray
			file = (JSONArray) parser.parse(fileReader);
			List<ParkingViolation> result = new ArrayList<ParkingViolation>(30000);

			// use an iterator to iterate over each element of the array
			Iterator iter = file.iterator();
			// iterate while there are more objects in array
			while (iter.hasNext()) {
				// get the next JSON object
				JSONObject item = (JSONObject) iter.next();

				// get the location and text from jason file
				String state = item.get("state").toString();
				int zipCode =0;
				if(!item.get("zip_code").toString().equals("")) {
					zipCode=Integer.parseInt(item.get("zip_code").toString());
				}
					

				int fine = 0;
				if(!item.get("fine").toString().equals("")) {
					fine = Integer.parseInt(item.get("fine").toString());
				}

				// add data to the result list
				result.add(new ParkingViolation(state, fine, zipCode));

			}

			return result;

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
