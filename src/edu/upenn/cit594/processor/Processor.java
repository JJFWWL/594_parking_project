package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.CSVPropertyReader;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.TxtPopulationReader;

public class Processor {
	protected ParkingReader parkingReader;
	protected CSVPropertyReader propertyReader;
	protected TxtPopulationReader populationReader;

	private int totalPopulation = 0;
	private List<ParkingViolation> violation;
	private List<Population> population;
	private List<Property> properties;
	
	private Map<Integer, Integer> finesMap = new HashMap<>();
	private Map<Integer, Double> valueMap = new HashMap<>();
	private Map<Integer, Double> areaMap = new HashMap<>();
	private Map<Integer, Double> finesPerCapita = new TreeMap<>();
	private Map<Integer, Double> valuePerCapita = new HashMap<>();
	private Map<Integer, Double> areaPerCapita = new HashMap<>();

	private Map<Integer, Integer> violationMap = new HashMap<>();
	private Map<Integer, List<Property>> propertyMap = new HashMap<>();
	private Map<Integer, Integer> populationMap = new HashMap<>();

	public Processor(ParkingReader parkingReader, CSVPropertyReader propertyReader,
			TxtPopulationReader populationReader) {
		this.parkingReader = parkingReader;
		this.propertyReader = propertyReader;
		this.populationReader = populationReader;
		this.violation = this.parkingReader.readAll();
		this.properties = this.propertyReader.readAll();
		this.population = this.populationReader.readAll();
		//System.out.println(this.violation.size());
		//System.out.println(this.properties.size());
		this.updatePopulationMap();
	}

	/**
	 * @return the violation
	 */
	public List<ParkingViolation> getViolation() {
		return violation;
	}

	/**
	 * @return the population
	 */
	public List<Population> getPopulation() {
		return population;
	}

	/**
	 * @return the properties
	 */
	public List<Property> getProperties() {
		return properties;
	}
	
	
	/**
	 * @return the valueMap
	 */
	public Map<Integer, Double> getValueMap() {
		return valueMap;
	}

	/**
	 * @return the areaMap
	 */
	public Map<Integer, Double> getAreaMap() {
		return areaMap;
	}

	/**
	 * @return the totalPopulation
	 */
	public int getTotalPopulation() {
		if (totalPopulation == 0)
			this.calculateTotalPopulation();
		return totalPopulation;
	}

	/**
	 * @return the finesPerCapita
	 */
	public Map<Integer, Double> getFinesPerCapita() {
		if(finesPerCapita == null || finesPerCapita.size() == 0)
			this.updateFinesPerCapita();
		return finesPerCapita;
	}

	/**
	 * @return the violationMap
	 */
	public Map<Integer, Integer> getViolationMap() {
		return violationMap;
	}

	/**
	 * @return the propertyMap
	 */
	public Map<Integer, List<Property>> getPropertyMap() {
		return propertyMap;
	}

	/**
	 * @return the populationMap
	 */
	public Map<Integer, Integer> getPopulationMap() {
		return populationMap;
	}

	/**
	 * 
	 * @param zipCode
	 * @return the number of vialation for the input zipcode
	 */
	public int getTotalViolationPerArea(int zipCode) {
		return this.violationMap.getOrDefault(zipCode, 0);
	}

	/**
	 * Build a map that keys are zipCode and value are corresponding population for
	 * that specific zipCode. There are much less population data, so we don't need
	 * to do memoization for this case, just build the map for all.
	 */
	public void updatePopulationMap() {
		this.populationMap = new HashMap<>();
		for (Population p : this.population) {
			populationMap.put(p.getZipCode(), p.getPopulation());
		}
	}


	/**
	 * 1. Calculate total population and update the private variable
	 * totalPopulation.
	 */
	public void calculateTotalPopulation() {
		for (Population pop : population) {
			totalPopulation += pop.getPopulation();
		}
	}

	/**
	 * 2. Update Total Fines Per Capita and store the calculated data in the Map
	 * finesPerCapita
	 */
	public void updateFinesPerCapita() {

		for (ParkingViolation v : this.violation) {
			if (v.getZipCode() == 0 || !v.getState().equals("PA"))
				continue;
			int zip = v.getZipCode();
			finesMap.put(zip, finesMap.getOrDefault(zip, 0) + v.getFine());
		}
		for (int zip : finesMap.keySet()) {
			int fines = finesMap.get(zip);
			if (fines == 0 || !populationMap.containsKey(zip))
				continue;
			finesPerCapita.put(zip, (double) (fines) / (double) populationMap.get(zip));
		}
	}

	/**
	 * 3.Average Market Value & 4.Average Total Livable Area, both utilized strategy pattern.
	 * 
	 * @param zipCode
	 * @param propertyMap
	 * @param calculator
	 * @return The average mark value or average livable area for input zipCode.
	 */
	public double calculateAverageValue(int zipCode, List<Property> properties, Map<Integer, Double> averageMap,
			Map<Integer, List<Property>> propertyMap, PropertyCalculator calculator) {
		return calculator.getAverageValue(zipCode, properties, averageMap, propertyMap);
	}
	/**
	 * 5.Total Residential Market Value Per Capita
	 * 
	 * @param zipCode
	 * @return Total Residential Market Value Per Capita
	 */
	public double getTotalValuePerCapita(int zipCode) {
		if (this.valuePerCapita.containsKey(zipCode))
			return this.valuePerCapita.get(zipCode);
		int population = this.populationMap.getOrDefault(zipCode, 0);
		if (population == 0)
			return 0;
		
		double sum = 0;
		List<Property> list = null;
		if(this.propertyMap.containsKey(zipCode)) list = propertyMap.get(zipCode);
		else {
			list = new ArrayList<>();
			for (Property p : this.properties) {
				if(p.getZipCode() == zipCode && p.getMarketValue() != 0)
					list.add(p);
			}
			propertyMap.put(zipCode, list);
		}
		for(Property p : list) {
			sum += p.getMarketValue();
		}
		return sum / population;
	}
	
	/**
	 * 6.Total Livable Area Per Capita
	 * 
	 * @param zipCode
	 * @return Total Residential Market Value Per Capita
	 */
	public double getTotalAreaPerCapita(int zipCode) {
		if (this.areaPerCapita.containsKey(zipCode))
			return this.areaPerCapita.get(zipCode);
		int population = this.populationMap.getOrDefault(zipCode, 0);
		if (population == 0)
			return 0;
		
		double sum = 0;
		List<Property> list = null;
		if(this.propertyMap.containsKey(zipCode)) list = propertyMap.get(zipCode);
		else {
			list = new ArrayList<>();
			for (Property p : this.properties) {
				if(p.getZipCode() == zipCode && p.getMarketValue() != 0)
					list.add(p);
			}
			propertyMap.put(zipCode, list);
		}
		for(Property p : list) {
			sum += p.getLivableArea();
		}
		return sum / population;
	}
}
