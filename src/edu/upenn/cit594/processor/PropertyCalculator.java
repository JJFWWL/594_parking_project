package edu.upenn.cit594.processor;

import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.Property;

public interface PropertyCalculator {
	
	public double getAverageValue(int zipCode, List<Property> properties,
			Map<Integer, Double> averageMap, Map<Integer, List<Property>> zipMap);

}
