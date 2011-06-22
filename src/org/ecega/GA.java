package org.ecega;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.HashMap;

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
					Converter<Genotype<Float64Gene>, AffineTransform> 
	{
		private static final long serialVersionUID = 1L;
	
		private final EnergyDataSet _sourceConsumptionDataSet;
		private final Float64[] _targetPolynom;
	
		public Function() {
			this(null, null);
		}
		
		public Function(final EnergyDataSet source, final Float64[] target) {
			_sourceConsumptionDataSet = source != null ? source.clone() : source;
			_targetPolynom = target != null ? target.clone() : target;
		}
	
		@Override
		public Float64 evaluate(final Genotype<Float64Gene> genotype) {
			return distance(genotype);
			//return area(genotype);
		}
		
		Float64 distance(final Genotype<Float64Gene> genotype) {

			// TODO calculate distance between estimated curve given by the target-polynom
			// and the actual energy consumption dataset
			double error = 0;
			for (int i = 0; i < _sourceConsumptionDataSet.size(); ++i) {
	
				error += _sourceConsumptionDataSet[i].distance(point);
			}
	
			return Float64.valueOf(error);
		}
		
		Float64 area(final Genotype<Float64Gene> genotype) {
			// TODO maybe check areal distance
			return new Float64();
		}
	
		@Override
		public AffineTransform convert(final Genotype<Float64Gene> genotype) {
			
			// TODO get rid of, prepare for polynoms
			
			System.out.println(genotype);
			final double theta = genotype.getChromosome(0).getGene().doubleValue();
			final double tx = genotype.getChromosome(1).getGene(0).doubleValue();
			final double ty = genotype.getChromosome(1).getGene(1).doubleValue();
			final double shx = genotype.getChromosome(2).getGene(0).doubleValue();
			final double shy = genotype.getChromosome(2).getGene(1).doubleValue();
	
			final AffineTransform rotate = AffineTransform.getRotateInstance(theta);
			final AffineTransform translate = AffineTransform.getTranslateInstance(tx, ty);
			final AffineTransform shear = AffineTransform.getShearInstance(shx,shy);
	
			final AffineTransform transform = new AffineTransform();
			transform.concatenate(shear);
			transform.concatenate(rotate);
			transform.concatenate(translate);
	
			return transform;
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
	
	public static EnergyDataSet getSourceConsumptionDataSet() {
		// TODO quantized values from year/consumption chart
		
		return SOURCE_POLYGON;
	}
	
	public static Point2D[] getTargetPolygon(final AffineTransform transform) {	
		final Point2D[] target = new Point2D[SOURCE_POLYGON.length];
		
		for (int i = 0; i < SOURCE_POLYGON.length; ++i) {
			//target[i]  = transform.inverseTransform(SOURCE_POLYGON[i], null);
		}
		return target;
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
