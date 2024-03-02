package edu.upenn.cit594.data;

public class ParkingViolation {
	private String state;
	private int fine;
	private int zipCode;
	
	
	public ParkingViolation(String state, int fine, int zipCode) {
		this.state = state;
		this.fine = fine;
		this.zipCode = zipCode;
	}
	
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the fine
	 */
	public int getFine() {
		return fine;
	}
	/**
	 * @param fine the fine to set
	 */
	public void setFine(int fine) {
		this.fine = fine;
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
	
	
}
