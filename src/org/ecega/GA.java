package org.ecega;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.jenetics.ExponentialScaler;
import org.jenetics.FitnessFunction;
import org.jenetics.Float64Chromosome;
import org.jenetics.Float64Gene;
import org.jenetics.GeneticAlgorithm;
import org.jenetics.Genotype;
import org.jenetics.Mutator;
import org.jenetics.NumberStatistics;
import org.jenetics.Optimize;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.util.Converter;
import org.jenetics.util.Factory;
import org.jscience.mathematics.number.Float64;


class GA {
	
	static class Function 
		implements FitnessFunction<Float64Gene, Float64>, 
					Converter<Genotype<Float64Gene>, Float64[]> 
	{
		private static final long serialVersionUID = 1L;
	
		private final EnergyDataSet _sourceConsumptionDataSet;
		private final Float64[] _targetPolynom;
	
		public Function() {
			this(null, null);
		}
		
		public Function(final EnergyDataSet source, final Float64[] target) {
			_sourceConsumptionDataSet = source;
			_targetPolynom = target != null ? target.clone() : target;
		}
	
		@Override
		public Float64 evaluate(final Genotype<Float64Gene> genotype) {
			return distance(genotype);
			//return area(genotype);
		}
		
		/**
		 * calculate distance between estimated curve given by the target-polynom
		 * and the actual energy consumption dataset
		 * @param genotype
		 * @return
		 */
		Float64 distance(final Genotype<Float64Gene> genotype) {

			float error = 0;
			Float64 realYearConsumption, estimatedYearConsumption;
			for(Integer year : _sourceConsumptionDataSet.keySet()) {
				realYearConsumption = _sourceConsumptionDataSet.get(year);
				estimatedYearConsumption = EnergyDataSet.calculateEstimation(year, convert(genotype));
				error += realYearConsumption.minus(estimatedYearConsumption).abs().floatValue();
			}
			
			return Float64.valueOf(error);
		}
		
		Float64 area(final Genotype<Float64Gene> genotype) {
			// TODO maybe check areal distance
			return Float64.ZERO;
		}
	
		@Override
		public Float64[] convert(final Genotype<Float64Gene> genotype) {
			
			// TODO get rid of, prepare for polynoms
			
			System.out.println(genotype);
			Float64[] polynom = new Float64[4];
			Float64 a;
			for (int i = 0; i < polynom.length; i++) {
				a = genotype.getChromosome(i).getGene().getNumber();
			}
			
			return polynom;
		}
	
	}
	
	private GA() {
	}
	
	public static Factory<Genotype<Float64Gene>> getGenotypeFactory() {
		return Genotype.valueOf(
			//Rotation
			new Float64Chromosome(-Math.PI, Math.PI),
			
			//Translation
			new Float64Chromosome(-300.0, 300.0, 2),
			
			//Shear
			new Float64Chromosome(-0.5, 0.5, 2)
		);
	}
	
	
	public static GeneticAlgorithm<Float64Gene, Float64> getGA(final Function function) {
		final GeneticAlgorithm<Float64Gene, Float64> ga = 
			new GeneticAlgorithm<Float64Gene, Float64>(
				GA.getGenotypeFactory(), function, new ExponentialScaler(2), Optimize.MINIMUM
			);
		ga.addAlterer(new Mutator<Float64Gene>(0.1));
		ga.setSelectors(new RouletteWheelSelector<Float64Gene, Float64>());
		ga.setPopulationSize(25);
		ga.setMaximalPhenotypeAge(30);
		ga.setOffspringFraction(0.3);
		ga.setStatisticsCalculator(new NumberStatistics.Calculator<Float64Gene, Float64>());
		
		return ga;
	}

}
