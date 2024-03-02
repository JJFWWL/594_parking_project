package edu.upenn.cit594.data;

public class Population {
	int zipCode;
	int population;
	
	public Population(int zipCode, int population) {
		this.zipCode = zipCode;
		this.population = population;
	}

	/**
	 * @return the zipCode
	 */
	public int getZipCode() {
		return zipCode;
	}

	/**
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

}
