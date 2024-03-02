package edu.upenn.cit594.data;

public class Property {
	private int zipCode;
	private double marketValue;
	private double livableArea;
	
	public Property(double marketValue, double livableArea, int zipCode) {
		
		this.marketValue = marketValue;
		this.livableArea = livableArea;
		this.zipCode = zipCode;
	}

	/**
	 * @return the zipCode
	 */
	public int getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the marketValue
	 */
	public double getMarketValue() {
		return marketValue;
	}

	/**
	 * @param marketValue the marketValue to set
	 */
	public void setMarketValue(int marketValue) {
		this.marketValue = marketValue;
	}

	/**
	 * @return the livableArea
	 */
	public double getLivableArea() {
		return livableArea;
	}

	/**
	 * @param livableArea the livableArea to set
	 */
	public void setLivableArea(int livableArea) {
		this.livableArea = livableArea;
	}

}
