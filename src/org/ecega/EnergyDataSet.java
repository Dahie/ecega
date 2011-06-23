package org.ecega;

import java.util.TreeMap;

import org.jscience.mathematics.number.Float64;

public class EnergyDataSet extends TreeMap<Integer, Float64> {

	/**
	 * Fill in the Polynom-factors a, b, c and d from the Float64-array
	 * @param x
	 * @param convert
	 * @return
	 */
	public static Float64 calculateEstimation(int x, Float64[] convert) {
		// TODO ok to be consistent we should use Float64 here as well, 
		// but I'm just not in the mood for the formula syntax 
		float a, b, c, d;
		a = convert[0].floatValue();
		b = convert[1].floatValue();
		c = convert[2].floatValue();
		d = convert[3].floatValue();
		
		return Float64.valueOf((a* (x*x*x)) + (b * (x*x)) + (c * (x) ) + (d));
	}

}
