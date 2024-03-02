package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.Property;

public class ValueCalculator implements PropertyCalculator {

	@Override
	public double getAverageValue(int zipCode, List<Property> properties, Map<Integer, Double> valueMap,
			Map<Integer, List<Property>> zipMap) {

		if (valueMap.containsKey(zipCode))
			return valueMap.get(zipCode);

		List<Property> list = null;
		if (zipMap.containsKey(zipCode))
			list = zipMap.get(zipCode);
		else {
			list = new ArrayList<Property>();
			int testCount = 0;
			for (Property p : properties) {

				if (p.getZipCode() == zipCode && p.getMarketValue() != 0) {
					list.add(p);
					testCount++;
				}
			}
			zipMap.put(zipCode, list);
			// System.out.println("testcount=" + testCount);
		}

		double result = this.getAverageMarketValue(list);
		valueMap.put(zipCode, result);
		return result;
	}

	public double getAverageMarketValue(List<Property> list) {
		if (list == null || list.size() == 0)
			return 0;
		double sum = 0;
		int count = 0;
		for (Property p : list) {
			sum += p.getMarketValue();
			count++;
		}
		// System.out.println("sum=" + sum);
		// System.out.println("\ncount=" + count);
		return sum / count;
	}

}
