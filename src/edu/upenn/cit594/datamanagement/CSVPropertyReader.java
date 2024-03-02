package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.upenn.cit594.data.Property;

public class CSVPropertyReader {
	protected String fileName;

	public CSVPropertyReader(String fileName) {
		this.fileName = fileName;
	}

	public List<Property> readAll() {
		List<Property> results = new ArrayList<Property>(600000);
		File file = new File(fileName);
		// define file reader
		FileReader fileReader = null;
		// define buffered reader
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			String titleLine = bufferedReader.readLine();// read the title line to get the index for required data
			String titleItem[] = titleLine.split(",");
			int indexMarketValue = 0;
			int indexLivableArea = 0;
			int indexZipCode = 0;

			for (int i = 0; i < titleItem.length; i++) {
				if (titleItem[i].equals("market_value")) {
					indexMarketValue = i;
				}

				if (titleItem[i].equals("total_livable_area")) {
					indexLivableArea = i;
				}

				if (titleItem[i].equals("zip_code")) {
					indexZipCode = i;
				}

			}

			// read the main data
			while ((line = bufferedReader.readLine()) != null) {
				String item[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");// comma and ignore comma in ""
				String strMarketValue = item[indexMarketValue];
				String strLivableArea = item[indexLivableArea];
				String strZipCode = item[indexZipCode];

				// parse to int, including null condition and truncated the zipcode
				double marketValue = 0;
				double livableArea = 0;
				int zipCode = 0;

				// parse marketValue
				try {
					marketValue = Double.parseDouble(strMarketValue);
				} catch (NumberFormatException e) {
					marketValue = 0.0;
				} catch (NullPointerException e) {
					marketValue = 0.0;
				}

				// parse livableArea
				try {
					livableArea = Double.parseDouble(strLivableArea);
				} catch (NumberFormatException e) {
					livableArea = 0.0;
				} catch (NullPointerException e) {
					livableArea = 0.0;
				}

				// parse zipCode
				if (strZipCode.length() < 5) {
					zipCode = 0;
				} else {
					try {
						zipCode = Integer.parseInt(strZipCode.substring(0, 5));

						if (zipCode < 0) {
							zipCode = 0;
						}
					} catch (NumberFormatException e) {
						zipCode = 0;
					} catch (NullPointerException e) {
						zipCode = 0;
					}
				}

				// add data to the result list
				results.add(new Property(marketValue, livableArea, zipCode));
			}
			return results;

		} catch (FileNotFoundException e) {
			System.out.println("Sorry, " + fileName + " not found");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			// close file objects
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
