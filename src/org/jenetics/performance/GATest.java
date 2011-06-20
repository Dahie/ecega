/*
 * Java Genetic Algorithm Library (jenetics-0.8.0.0).
 * Copyright (c) 2007-2011 Franz Wilhelmstötter
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 *     Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 *     
 */
package org.jenetics.performance;

import org.jscience.mathematics.number.Float64;

import org.jenetics.BoltzmannSelector;
import org.jenetics.CharacterChromosome;
import org.jenetics.CharacterGene;
import org.jenetics.FitnessFunction;
import org.jenetics.Float64Gene;
import org.jenetics.GeneticAlgorithm;
import org.jenetics.Genotype;
import org.jenetics.MeanAlterer;
import org.jenetics.Mutator;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.SinglePointCrossover;
import org.jenetics.util.Array;
import org.jenetics.util.ConcurrentEvaluator;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: GATest.java 876 2011-05-06 21:09:25Z fwilhelm $
 */
@Suite("GA")
public class GATest {

	private static final int LOOPS = 20;
	private static final int NGENES = 5;
	private static final int NCHROMOSOMES = 50;
	
	
	private static final class Float64GeneFF 
		implements FitnessFunction<Float64Gene, Float64> 
	{
		private static final long serialVersionUID = 1L;
		
		@Override
		public Float64 evaluate(final Genotype<Float64Gene> genotype) {
			return genotype.getChromosome().getGene().getAllele(); 
		}
	}
	
	private static final class CharacterGeneFF
		implements FitnessFunction<CharacterGene, Float64>
	{
		private static final long serialVersionUID = 1L;

		@Override
		public Float64 evaluate(final Genotype<CharacterGene> genotype) {
			return Float64.valueOf(
					genotype.getChromosome().getGene().getAllele().hashCode()
				);
		}
		
	}
	
	@Test(1)
	public TestCase float64Gene = new TestCase(
		String.format("Float64Gene[G=%s, C=%s]", NGENES, NCHROMOSOMES), 
		LOOPS, NGENES*NCHROMOSOMES
	) {
		private final GenotypeBuilder _gtb = new GenotypeBuilder(); {
			_gtb.ngenes(NGENES);
			_gtb.nchromosomes(NCHROMOSOMES);
		}
		
		private GeneticAlgorithm<Float64Gene, Float64> _ga;
		
		@Override
		protected void beforeTest() {
			_ga = GeneticAlgorithm.valueOf(_gtb.build(), new Float64GeneFF());
			_ga.setAlterer(new MeanAlterer<Float64Gene>());
			_ga.addAlterer(new SinglePointCrossover<Float64Gene>());
			_ga.addAlterer(new Mutator<Float64Gene>(0.2));
			_ga.setOffspringSelector(new RouletteWheelSelector<Float64Gene, Float64>());
			_ga.setSurvivorSelector(new BoltzmannSelector<Float64Gene, Float64>());
			_ga.setEvaluator(new ConcurrentEvaluator());
			_ga.setup();
		}
		
		@Override
		protected void test() {
			_ga.evolve(200);
		}
	};
	
	@Test(2)
	public TestCase characterGene = new TestCase(
		String.format("CharacterGene[G=%s, C=%s]", NGENES, NCHROMOSOMES), 
		LOOPS, NGENES*NCHROMOSOMES
	) {
		private final Array<CharacterChromosome> _chromosomes = new Array<CharacterChromosome>(NCHROMOSOMES);
		{
			_chromosomes.fill(new CharacterChromosome(NGENES).asFactory());
		}
		private final Genotype<CharacterGene> _gt = Genotype.valueOf(_chromosomes.toISeq());
		
		private GeneticAlgorithm<CharacterGene, Float64> _ga;
		
		@Override
		protected void beforeTest() {
			_ga = GeneticAlgorithm.valueOf(_gt, new CharacterGeneFF());
			_ga.addAlterer(new SinglePointCrossover<CharacterGene>());
			_ga.addAlterer(new Mutator<CharacterGene>(0.2));
			_ga.setOffspringSelector(new RouletteWheelSelector<CharacterGene, Float64>());
			_ga.setSurvivorSelector(new BoltzmannSelector<CharacterGene, Float64>());
			_ga.setEvaluator(new ConcurrentEvaluator());
			_ga.setup();
		}
		
		@Override
		protected void test() {
			_ga.evolve(200);
		}
		
	};
	
}





