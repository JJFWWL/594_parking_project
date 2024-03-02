package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.Property;

public class AreaCalculator implements PropertyCalculator {

	@Override
	public double getAverageValue(int zipCode, List<Property> properties, Map<Integer, Double> areaMap,
			Map<Integer, List<Property>> zipMap) {

		if (areaMap.containsKey(zipCode))
			return areaMap.get(zipCode);
		List<Property> list = null;
		if (zipMap.containsKey(zipCode))
			list = zipMap.get(zipCode);
		else {
			list = new ArrayList<>();
			for (Property p : list) {
				if (p.getZipCode() == zipCode && p.getLivableArea() != 0)
					list.add(p);
			}
			zipMap.put(zipCode, list);
		}

		double result = this.getAverageArea(list);
		areaMap.put(zipCode, result);
		return result;
	}

	public double getAverageArea(List<Property> list) {
		if (list == null || list.size() == 0)
			return 0;
		double sum = 0;
		int count = 0;
		for (Property p : list) {
			sum += p.getLivableArea();
			count++;
		}
		// System.out.println("sum= " + sum + " count= " + count + "\n");
		return sum / count;
	}
}
