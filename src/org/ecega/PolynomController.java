package org.ecega;

import java.awt.geom.AffineTransform;
import java.util.EventObject;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jenetics.Float64Gene;
import org.jenetics.GeneticAlgorithm;
import org.jenetics.MeanAlterer;
import org.jenetics.Mutator;
import org.jenetics.NumberStatistics;
import org.jenetics.Phenotype;
import org.jscience.mathematics.number.Float64;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class PolynomController implements StepListener {
	private final EnergyConsumption _energyConsumptionFrame;
	
	private final InitAction _initAction = new InitAction(this);
	private final StartAction _startAction = new StartAction(this);
	private final StopAction _stopAction = new StopAction(this);
	private final StepAction _stepAction = new StepAction(this);
	private final PauseAction _pauseAction = new PauseAction(this);
	
	private final PopulationSpinnerModel 
		_populationSizeSpinnerModel = new PopulationSpinnerModel(this);
	private final MaximalPhenotypeAgeSpinnerModel
		_maximalPhenotypeAgeSpinnerModel = new MaximalPhenotypeAgeSpinnerModel(this);
	private final OffspringFractionRangeModel
		_offspringFractionRangeModel = new OffspringFractionRangeModel(this);
	private final MutationProbabilityRangeModel
		_mutationProbabilityRangeModel = new MutationProbabilityRangeModel(this);
	
	private GeneticAlgorithm<Float64Gene, Float64> _ga;
	private GA.Function _function;
	private EnergyDataSet _sourceConsumptionDataSet;
	private Float64[] _targetPolynom;
	private Stepable _stepable;
	private Thread _thread;
	private ExecutorService _threads;
	
	private static final long MIN_REPAINT_TIME = 50;
	private long _lastRepaintTime = 0;
	private Phenotype<Float64Gene, Float64> _populationBestPhenotype;
	private Phenotype<Float64Gene, Float64> _gaBestPhenotype;
	private int _generation = 0;
	
	PolynomController(final EnergyConsumption consumptionFrame) {
		_energyConsumptionFrame = consumptionFrame;
		
		_energyConsumptionFrame.setInitAction(_initAction);
		_energyConsumptionFrame.setStartAction(_startAction);
		_energyConsumptionFrame.setStopAction(_stopAction);
		_energyConsumptionFrame.setStepAction(_stepAction);
		_energyConsumptionFrame.setPauseAction(_pauseAction);
		
		_energyConsumptionFrame.setPopulationSpinnerModel(_populationSizeSpinnerModel);
		_energyConsumptionFrame.setMaximalPhenotypeAgeSpinnerModel(_maximalPhenotypeAgeSpinnerModel);
		_energyConsumptionFrame.setOffspringFractionRangeModel(_offspringFractionRangeModel);
		_energyConsumptionFrame.setMutationProbabilityRangeModel(_mutationProbabilityRangeModel);
		
		init();
	}
	
	void init() {
		_sourceConsumptionDataSet = GA.getSourcePolynom();
		_function = new GA.Function(_sourceConsumptionDataSet, _targetPolynom);
		
		_ga = GA.getGA(_function);
		_ga.setPopulationSize(_populationSizeSpinnerModel.getNumber().intValue());
		
		_energyConsumptionFrame.setSourcePolygon(_sourceConsumptionDataSet);
		_energyConsumptionFrame.setTargetPolygon(_targetPolynom);
		
		if (_stepable != null) {
			_stepable.removeStepListener(this);
		}
		_stepable = new Stepable(new Runnable() {
			@Override public void run() {
				if (_ga.getGeneration() == 0) {
					_ga.setup();
				} else {
					_ga.evolve();
				}
			}
		});
		_stepable.addStepListener(this);
		
		if (_thread != null) {
			_thread.interrupt();
		}
		_thread = new Thread(_stepable);
		_thread.setPriority(Thread.MIN_PRIORITY);
		_thread.start();
		
		_threads = Executors.newFixedThreadPool(2);
		
		_energyConsumptionFrame.setTargetFunction(_transform);
		_energyConsumptionFrame.setPopulationBestPolynom(new AffineTransform());
		_energyConsumptionFrame.setGABestPolynom(new AffineTransform());
		_energyConsumptionFrame.setGeneration(0);
		_energyConsumptionFrame.repaint();
		
		_startAction.setEnabled(true);
		_stopAction.setEnabled(false);
		_pauseAction.setEnabled(false);
		_stepAction.setEnabled(true);
		_initAction.setEnabled(false);
	}
	
	void start() {
		_stepable.start();
		
		_startAction.setEnabled(false);
		_stopAction.setEnabled(true);
		_pauseAction.setEnabled(true);
		_stepAction.setEnabled(false);
		_initAction.setEnabled(false);
	}
	
	void stop() {
		_stepable.stop();
		_ga.getLock().lock();
		try {
			_thread.interrupt();
		} finally {
			_ga.getLock().unlock();
		}
		
		_startAction.setEnabled(false);
		_stopAction.setEnabled(false);
		_pauseAction.setEnabled(false);
		_stepAction.setEnabled(false);
		_initAction.setEnabled(true);
	}
	
	void pause() {
		_stepable.stop();
		
		_startAction.setEnabled(true);
		_stopAction.setEnabled(true);
		_pauseAction.setEnabled(false);
		_stepAction.setEnabled(true);
		_initAction.setEnabled(false);
	}
	
	void step() {
		_stepable.step();
		
		_startAction.setEnabled(true);
		_stopAction.setEnabled(true);
		_pauseAction.setEnabled(false);
		_stepAction.setEnabled(true);
		_initAction.setEnabled(false);
	}
	
	void setPopulationSize(final int size) {
		if (_ga != null) {
			_threads.submit(new Runnable() {
				@Override
				public void run() {
					_ga.getLock().lock();
					try {
						_ga.setPopulationSize(size);
						System.out.println("Population size: " + size);
					} finally {
						_ga.getLock().unlock();
					}
				}
			});
		}
	}
	
	void setMaximalPhenotypeAge(final int age) {
		if (_ga != null) {
			_threads.submit(new Runnable() {
				@Override
				public void run() {
					_ga.getLock().lock();
					try {
						_ga.setMaximalPhenotypeAge(age);
						System.out.println("Phenotype age: " + age);
					} finally {
						_ga.getLock().unlock();
					}
				}
			});			
		}
	}
	
	void setOffspringFraction(final double fraction) {
		if (_ga != null) {
			_threads.submit(new Runnable() {
				@Override
				public void run() {
					_ga.getLock().lock();
					try {
						_ga.setOffspringFraction(fraction);
						System.out.println("Offspring fraction: " + fraction);
					} finally {
						_ga.getLock().unlock();
					}
				}
			});
		}
	}
	
	void setMutationProbability(final double probability) {
		if (_ga != null) {
			_threads.submit(new Runnable() {
				@Override
				public void run() {
					_ga.getLock().lock();
					try {
						_ga.setAlterer(new Mutator<Float64Gene>(probability));
						_ga.addAlterer(new MeanAlterer<Float64Gene>());
						System.out.println("Mutation probability: " + probability);
					} finally {
						_ga.getLock().unlock();
					}
				}
			});
		}
	}

	@Override
	public void stepped(EventObject event) {
		final NumberStatistics<Float64Gene, Float64> statistics = 
			(NumberStatistics<Float64Gene, Float64>)_ga.getStatistics();
		final Phenotype<Float64Gene, Float64> populationBest = statistics.getBestPhenotype();
		final Phenotype<Float64Gene, Float64> gaBest = _ga.getBestPhenotype();
		final int generation = _ga.getGeneration();
		
		
//		if (_populationBestPhenotype == null || 
//			_populationBestPhenotype.compareTo(populationBest) < 0) 
//		{
			_populationBestPhenotype = populationBest;
			_gaBestPhenotype = gaBest;
			_generation = generation;
//		}
		
		//Prevent from extensive repainting.
		final long time = System.currentTimeMillis();
		if (time - _lastRepaintTime > MIN_REPAINT_TIME) {
			_energyConsumptionFrame.setPopulationBestPolynom(
					_function.convert(_populationBestPhenotype.getGenotype())
				);
			_energyConsumptionFrame.setGABestPolynom(
					_function.convert(_gaBestPhenotype.getGenotype())
				);
			_energyConsumptionFrame.repaint();
			_energyConsumptionFrame.setGeneration(_generation);
			_energyConsumptionFrame.setFitnessMean(statistics.getFitnessMean());
			_energyConsumptionFrame.setFitnessVariance(statistics.getFitnessVariance());
			
			_lastRepaintTime = time;
			_populationBestPhenotype = null;
			_gaBestPhenotype = null;
			_generation = 0;
		}
	}

	@Override
	public void finished(EventObject event) {
		System.out.println("GA finished");
	}
	
}