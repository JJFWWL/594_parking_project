package edu.upenn.cit594.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.processor.AreaCalculator;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.ValueCalculator;

public class UserInterface {
	protected Processor processor;
	protected Scanner in;

	// user input record for log
	protected List<String> inputForLog = new ArrayList<String>();

	/**
	 * Interface Constructor
	 * 
	 * @param processor
	 */
	public UserInterface(Processor processor) {
		this.processor = processor;
		in = new Scanner(System.in);
	}

	/**
	 * @return the inputForLog
	 */
	public List<String> getInputForLog() {
		return inputForLog;
	}

	/**
	 * start method to display the options and prompt the user to specify the next
	 * step to be performed.
	 */
	public void start() {

		int choice = -1;
		while (choice != 0) {
			System.out.println("Enter number 0 - 6 to show the corresponding results,");
			System.out.println("*** 0 to Exist the program.");
			System.out.println("*** 1 for Total population for all ZIP Codes.");
			System.out.println("*** 2 for Total parking fines per capita for each ZIP Code.");
			System.out.println("*** 3 for Average market value for residences in a specified ZIP Code.");
			System.out.println("*** 4 for Average total livable area for residences in a specified ZIP Code.");
			System.out.println("*** 5 for Total residential market value per capita for a specified ZIP Code");
			System.out.println("*** 6 for Total livable area per capita for a specified ZIP Code");

			choice = in.nextInt();
			switch (choice) {
			case 0:
				choice = 0;
				inputForLog.add(System.currentTimeMillis() + " " + choice);
				break;
			case 1:
				showTotalPopulation();
				inputForLog.add(System.currentTimeMillis() + " " + choice);
				break;
			case 2:
				showTotalFinesPerCapita();
				inputForLog.add(System.currentTimeMillis() + " " + choice);
				break;
			case 3:
				showAverageMarketValue();
				break;
			case 4:
				showAverageTotalLivableArea();
				break;
			case 5:
				showTotalMarketValuePerCapita();
				break;
			case 6:
				showTotalLivableAreaPerCapita();
				break;
			default:
				System.out.println("Incorrect input format, application terminated.");
				choice = 0;
			}
		}
		in.close();
	}

	/**
	 * 1. Total Population for All ZIP Codes
	 */
	private void showTotalPopulation() {
		// TODO Auto-generated method stub
		System.out.println(processor.getTotalPopulation());
	}

	/**
	 * 2.Total Fines Per Capita
	 */
	private void showTotalFinesPerCapita() {

		Map<Integer, Double> totalFinesPerCapita = processor.getFinesPerCapita();

		for (Map.Entry<Integer, Double> entry : totalFinesPerCapita.entrySet()) {
			System.out.printf("%d	%.3f%n", entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 3. Average Market Value
	 */
	private void showAverageMarketValue() {
		// TODO Auto-generated method stub
		System.out.println("Please enter a ZIP code for average market value:");
		int zipCode = in.nextInt();

		// log record
		inputForLog.add(System.currentTimeMillis() + " " + 3 + " " + zipCode);
		double value = processor.calculateAverageValue(zipCode, processor.getProperties(), processor.getValueMap(),
				processor.getPropertyMap(), new ValueCalculator());
		System.out.printf("%d%n", (int) value);
	}

	/**
	 * 4.Average Total Livable Area
	 */
	private void showAverageTotalLivableArea() {
		// TODO Auto-generated method stub
		System.out.println("Please enter a ZIP code for average total livable area:");
		int zipCode = in.nextInt();

		// log record
		inputForLog.add(System.currentTimeMillis() + " " + 4 + " " + zipCode);

		double area = processor.calculateAverageValue(zipCode, processor.getProperties(), processor.getAreaMap(),
				processor.getPropertyMap(), new AreaCalculator());
		System.out.printf("%d%n", (int) area);
	}

	/**
	 * 5. Total Residential Market Value Per Capita
	 */
	private void showTotalMarketValuePerCapita() {
		// TODO Auto-generated method stub
		System.out.println("Please enter a ZIP code for total market value per capita:");
		int zipCode = in.nextInt();

		// log record
		inputForLog.add(System.currentTimeMillis() + " " + 5 + " " + zipCode);

		double totalValuePerCapita = processor.getTotalValuePerCapita(zipCode);
		System.out.printf("%d%n", (int) totalValuePerCapita);
	}

	/**
	 * 6. Customized feature: Total Livable Area Per Capita
	 */
	private void showTotalLivableAreaPerCapita() {
		// TODO Auto-generated method stub
		System.out.println("Please enter a ZIP code for total livable area per capita:");
		int zipCode = in.nextInt();
		double totalLivableAreaPerCapita = processor.getTotalAreaPerCapita(zipCode);
		System.out.printf("%d%n", (int) totalLivableAreaPerCapita);
	}
}
