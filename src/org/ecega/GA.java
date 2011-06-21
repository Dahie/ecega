package org.ecega;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

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
import org.jenetics.util.RandomRegistry;
import org.jscience.mathematics.number.Float64;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class GA {
	
	static class Function 
		implements FitnessFunction<Float64Gene, Float64>, 
					Converter<Genotype<Float64Gene>, AffineTransform> 
	{
		private static final long serialVersionUID = 1L;
	
		private final Point2D[] _source;
		private final Point2D[] _target;
	
		public Function() {
			this(null, null);
		}
		
		public Function(final Point2D[] source, final Point2D[] target) {
			_source = source != null ? source.clone() : source;
			_target = target != null ? target.clone() : target;
		}
	
		@Override
		public Float64 evaluate(final Genotype<Float64Gene> genotype) {
			return distance(genotype);
			//return area(genotype);
		}
		
		Float64 distance(final Genotype<Float64Gene> genotype) {
			final AffineTransform transform = convert(genotype);
	
			double error = 0;
			Point2D point = new Point2D.Double();
			for (int i = 0; i < _source.length; ++i) {
				point = transform.transform(_target[i], point);
	
				error += _source[i].distance(point);
			}
	
			return Float64.valueOf(error);
		}
		
		Float64 area(final Genotype<Float64Gene> genotype) {
			final AffineTransform transform = convert(genotype);
						
			final Point2D[] points = new Point2D.Double[_source.length];
			for (int i = 0; i < _source.length; ++i) {
				points[i]  = transform.transform(_target[i], null);
			}
			
			return Float64.valueOf(GeometryUtils.area(_source, points));
		}
	
		@Override
		public AffineTransform convert(final Genotype<Float64Gene> genotype) {
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
	
	
	private static final Point2D[] SOURCE_POLYGON = new Point2D[] {
			new Point2D.Double(-100, -100),
			new Point2D.Double(100, -100),
			new Point2D.Double(100, 100),
			new Point2D.Double(-100, 100)
		};
	
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
	
	public static Point2D[] getSourcePolygon() {
		return SOURCE_POLYGON;
	}
	
	public static AffineTransform getTargetTransform() {
		final Random random = RandomRegistry.getRandom();
		final double theta = random.nextDouble()*2*Math.PI - Math.PI;
		final double tx = random.nextInt(600) - 300;
		final double ty = random.nextInt(600) - 300;
		final double shx = random.nextDouble() - 0.5;
		final double shy = random.nextDouble() - 0.5;
		
		final AffineTransform rotate = AffineTransform.getRotateInstance(theta);
		final AffineTransform translate = AffineTransform.getTranslateInstance(tx, ty);
		final AffineTransform shear = AffineTransform.getShearInstance(shx, shy);
		
		final AffineTransform transform = new AffineTransform();
		transform.concatenate(shear);
		transform.concatenate(rotate);
		transform.concatenate(translate);
		
		return transform;
	}
	
	public static Point2D[] getTargetPolygon(final AffineTransform transform) {	
		final Point2D[] target = new Point2D[SOURCE_POLYGON.length];
		try {
			for (int i = 0; i < SOURCE_POLYGON.length; ++i) {
				target[i]  = transform.inverseTransform(SOURCE_POLYGON[i], null);
			}
		} catch (Exception ignore) {
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
