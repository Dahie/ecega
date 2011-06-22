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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 * 	 Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 * 	 
 */
package org.ecega;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.NumberFormat;
import java.util.Dictionary;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jscience.mathematics.number.Float64;


/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
public class EnergyConsumption extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	
	


	public EnergyConsumption() {
		initComponents();
	}	
	
	void setStartAction(final Action action) {
		_startButton.setAction(action);
	}
	
	void setStopAction(final Action action) {
		_stopButton.setAction(action);
	}
	
	void setInitAction(final Action action) {
		_initButton.setAction(action);
	}
	
	void setPauseAction(final Action action) {
		_pauseButton.setAction(action);
	}
	
	void setStepAction(final Action action) {
		_stepButton.setAction(action);
	}
	
	void setPopulationSpinnerModel(final SpinnerModel model) {
		_populationSizeSpinner.setModel(model);
	}
	
	void setMaximalPhenotypeAgeSpinnerModel(final SpinnerModel model) {
		_maxPTAgeSpinner.setModel(model);
	}
	
	void setOffspringFractionRangeModel(final LabeledBoundedRangeModel model) {
		_offspringFractionSlider.setModel(model);
		_offspringFractionSlider.setLabelTable(model.getLables());
	}
	
	void setMutationProbabilityRangeModel(final LabeledBoundedRangeModel model) {
		_mutationProbabilitySlider.setModel(model);
		_mutationProbabilitySlider.setLabelTable(model.getLables());
	}
	
	void setSourcePolygon(final EnergyDataSet consumptionFunction) {
		if (SwingUtilities.isEventDispatchThread()) {
			((DrawPanel)_drawPanel).setSourceDataSet(consumptionFunction);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					((DrawPanel)_drawPanel).setSourceDataSet(consumptionFunction);
				}
			});
		}
	}
	
	void setTargetPolygon(final Float64[] polynom) {
		if (SwingUtilities.isEventDispatchThread()) {
			((DrawPanel)_drawPanel).setTargetPolygon(polynom);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					((DrawPanel)_drawPanel).setTargetPolygon(polynom);
				}
			});
		}
	}
	
	void setFitnessMean(final double mean) {
		if (SwingUtilities.isEventDispatchThread()) {
			_fitnessMeanTextField.setValue(format(mean));
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					_fitnessMeanTextField.setValue(format(mean));
				}
			});
		}
	}
	
	void setFitnessVariance(final double variance) {
		if (SwingUtilities.isEventDispatchThread()) {
			_fitnessVarianceTextField.setValue(format(variance));
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					_fitnessVarianceTextField.setValue(format(variance));
				}
			});
		}
	}
	
	void setGeneration(final int generation) {
		if (SwingUtilities.isEventDispatchThread()) {
			_generationTextField.setValue(generation);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					_generationTextField.setValue(generation);
				}
			});
		}
	}
	
	void setTargetFunction(final Float64[] consumptionFunction) {
		if (SwingUtilities.isEventDispatchThread()) {
			((PolynomPanel)_targetTransformPanel).setPolynom(consumptionFunction);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					((PolynomPanel)_targetTransformPanel).setPolynom(consumptionFunction);
				}
			});
		}
	}
	
	void setGABestPolynom(final Float64[] transform) {
		if (SwingUtilities.isEventDispatchThread()) {
			((DrawPanel)_drawPanel).setAlltimeBestTransform(transform);
			((PolynomPanel)_gaBestTransformPanel).setPolynom(transform);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					((DrawPanel)_drawPanel).setAlltimeBestTransform(transform);
					((PolynomPanel)_gaBestTransformPanel).setPolynom(transform);
				}
			});
		}
	}
	
	void setPopulationBestPolynom(final Float64[] transform) {
		if (SwingUtilities.isEventDispatchThread()) {
			((DrawPanel)_drawPanel).setPopulationBestTransform(transform);
			((PolynomPanel)_populationBestTransformPanel).setPolynom(transform);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					((DrawPanel)_drawPanel).setPopulationBestTransform(transform);
					((PolynomPanel)_populationBestTransformPanel).setPolynom(transform);
				}
			});
		}
	}
	
	private static String format(final double value) {
		final NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		
		return f.format(value);
	}
	
	@Override
	public void repaint() {
		if (SwingUtilities.isEventDispatchThread()) {
			super.repaint();
			_drawPanel.repaint();
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					EnergyConsumption.super.repaint();
					_drawPanel.repaint();
				}
			});
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	 // <editor-fold defaultstate="collapsed" descending="Generated Code">//GEN-BEGIN:initComponents
	 private void initComponents() {
		  java.awt.GridBagConstraints gridBagConstraints;
		  
		  // TODO add fields for displaying calculation distance?

		  javax.swing.JSplitPane drawToolSplitPane=new javax.swing.JSplitPane();
		  _toolBasePanel = new javax.swing.JPanel();
		  _toolPanel = new javax.swing.JPanel();
		  _startButton = new javax.swing.JButton();
		  _stopButton = new javax.swing.JButton();
		  _initButton = new javax.swing.JButton();
		  _pauseButton = new javax.swing.JButton();
		  _stepButton = new javax.swing.JButton();
		  _generationLabel = new javax.swing.JLabel();
		  _generationTextField = new javax.swing.JFormattedTextField();
		  _populationBestTransformPanel = new PolynomPanel();
		  _gaBestTransformPanel = new PolynomPanel();
		  _populationSizeLabel = new javax.swing.JLabel();
		  _populationSizeSpinner = new javax.swing.JSpinner();
		  _maxPTAgeLabel = new javax.swing.JLabel();
		  _maxPTAgeSpinner = new javax.swing.JSpinner();
		  _offspringFractionSlider = new javax.swing.JSlider();
		  _offspringFractionLabel = new javax.swing.JLabel();
		  _populationTransformBestLabel = new javax.swing.JLabel();
		  _gaBestTransformLabel = new javax.swing.JLabel();
		  _targetTransformPanel = new PolynomPanel();
		  _targetTransformLabel = new javax.swing.JLabel();
		  _mutationProbabilityLabel = new javax.swing.JLabel();
		  _mutationProbabilitySlider = new javax.swing.JSlider();
		  _fitenssMeanLabel = new javax.swing.JLabel();
		  _fitnessMeanTextField = new javax.swing.JFormattedTextField();
		  _fitnessVarianceLabel = new javax.swing.JLabel();
		  _fitnessVarianceTextField = new javax.swing.JFormattedTextField();
		  _drawPanel = new DrawPanel();

		  setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		  drawToolSplitPane.setDividerLocation(500);
		  drawToolSplitPane.setResizeWeight(0.5);

		  _toolBasePanel.setMinimumSize(new java.awt.Dimension(200, 200));
		  _toolBasePanel.setLayout(new java.awt.GridBagLayout());

		  _toolPanel.setLayout(new java.awt.GridBagLayout());

		  _startButton.setText("Start");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 5;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
		  _toolPanel.add(_startButton, gridBagConstraints);

		  _stopButton.setText("Stop");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 6;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
		  _toolPanel.add(_stopButton, gridBagConstraints);

		  _initButton.setText("Init");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 4;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(20, 5, 3, 0);
		  _toolPanel.add(_initButton, gridBagConstraints);

		  _pauseButton.setText("Pause");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 8;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
		  _toolPanel.add(_pauseButton, gridBagConstraints);

		  _stepButton.setText("Step");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 7;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
		  _toolPanel.add(_stepButton, gridBagConstraints);

		  _generationLabel.setText("Generation:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 11;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		  _toolPanel.add(_generationLabel, gridBagConstraints);

		  _generationTextField.setEditable(false);
		  _generationTextField.setPreferredSize(new java.awt.Dimension(100, 24));
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 11;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 0);
		  _toolPanel.add(_generationTextField, gridBagConstraints);

		  _populationBestTransformPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 13;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.weighty = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
		  _toolPanel.add(_populationBestTransformPanel, gridBagConstraints);

		  _gaBestTransformPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 14;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.weighty = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(2, 5, 3, 0);
		  _toolPanel.add(_gaBestTransformPanel, gridBagConstraints);

		  _populationSizeLabel.setText("Population size:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 0;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		  _toolPanel.add(_populationSizeLabel, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 0;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
		  _toolPanel.add(_populationSizeSpinner, gridBagConstraints);

		  _maxPTAgeLabel.setText("Maximal PT age:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 1;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		  _toolPanel.add(_maxPTAgeLabel, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 1;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
		  _toolPanel.add(_maxPTAgeSpinner, gridBagConstraints);

		  _offspringFractionSlider.setMajorTickSpacing(10);
		  _offspringFractionSlider.setMaximum(90);
		  _offspringFractionSlider.setMinimum(10);
		  _offspringFractionSlider.setMinorTickSpacing(5);
		  _offspringFractionSlider.setPaintLabels(true);
		  _offspringFractionSlider.setPaintTicks(true);
		  _offspringFractionSlider.setValue(30);
		  _offspringFractionSlider.setBorder(null);
		  _offspringFractionSlider.setName(""); // NOI18N
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 2;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
		  _toolPanel.add(_offspringFractionSlider, gridBagConstraints);

		  _offspringFractionLabel.setText("Offspring fraction:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 2;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		  _toolPanel.add(_offspringFractionLabel, gridBagConstraints);

		  _populationTransformBestLabel.setText("Population best:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 13;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		  _toolPanel.add(_populationTransformBestLabel, gridBagConstraints);

		  _gaBestTransformLabel.setText("GA best:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 14;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		  _toolPanel.add(_gaBestTransformLabel, gridBagConstraints);

		  _targetTransformPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 12;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.weighty = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
		  _toolPanel.add(_targetTransformPanel, gridBagConstraints);

		  _targetTransformLabel.setText("Target transform:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 12;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
		  _toolPanel.add(_targetTransformLabel, gridBagConstraints);

		  _mutationProbabilityLabel.setText("Mutation probability:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 3;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		  _toolPanel.add(_mutationProbabilityLabel, gridBagConstraints);

		  _mutationProbabilitySlider.setMajorTickSpacing(100);
		  _mutationProbabilitySlider.setMaximum(500);
		  _mutationProbabilitySlider.setMinorTickSpacing(50);
		  _mutationProbabilitySlider.setPaintLabels(true);
		  _mutationProbabilitySlider.setPaintTicks(true);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 3;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
		  _toolPanel.add(_mutationProbabilitySlider, gridBagConstraints);

		  _fitenssMeanLabel.setText("Fitness mean:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 9;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(20, 3, 0, 3);
		  _toolPanel.add(_fitenssMeanLabel, gridBagConstraints);

		  _fitnessMeanTextField.setEditable(false);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 9;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(20, 5, 3, 0);
		  _toolPanel.add(_fitnessMeanTextField, gridBagConstraints);

		  _fitnessVarianceLabel.setText("Fitness variance:");
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 10;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
		  _toolPanel.add(_fitnessVarianceLabel, gridBagConstraints);

		  _fitnessVarianceTextField.setEditable(false);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 10;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 0);
		  _toolPanel.add(_fitnessVarianceTextField, gridBagConstraints);

		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 0;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		  gridBagConstraints.weightx = 1.0;
		  gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
		  _toolBasePanel.add(_toolPanel, gridBagConstraints);

		  drawToolSplitPane.setRightComponent(_toolBasePanel);

		  _drawPanel.setBackground(java.awt.Color.white);
		  _drawPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		  javax.swing.GroupLayout _drawPanelLayout = new javax.swing.GroupLayout(_drawPanel);
		  _drawPanel.setLayout(_drawPanelLayout);
		  _drawPanelLayout.setHorizontalGroup(
				_drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 498, Short.MAX_VALUE)
		  );
		  _drawPanelLayout.setVerticalGroup(
				_drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 542, Short.MAX_VALUE)
		  );

		  drawToolSplitPane.setLeftComponent(_drawPanel);

		  getContentPane().add(drawToolSplitPane, java.awt.BorderLayout.CENTER);

		  pack();
	 }// </editor-fold>//GEN-END:initComponents

	
	/**
	 * @param args
	 * 			  the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override public void run() {
				CSV c = new CSV();
				c.readData("energyConsumption.csv");
				EnergyDataSet dataSet = c.getDataSet();
				
				final EnergyConsumption consumptionFrame = new EnergyConsumption();
				consumptionFrame.setVisible(true);
				new PolynomController(consumptionFrame);
			}
		});
	}

	 // Variables declaration - do not modify//GEN-BEGIN:variables
	 private javax.swing.JPanel _drawPanel;
	 private javax.swing.JLabel _fitenssMeanLabel;
	 private javax.swing.JFormattedTextField _fitnessMeanTextField;
	 private javax.swing.JLabel _fitnessVarianceLabel;
	 private javax.swing.JFormattedTextField _fitnessVarianceTextField;
	 private javax.swing.JLabel _gaBestTransformLabel;
	 private javax.swing.JPanel _gaBestTransformPanel;
	 private javax.swing.JLabel _generationLabel;
	 private javax.swing.JFormattedTextField _generationTextField;
	 private javax.swing.JButton _initButton;
	 private javax.swing.JLabel _maxPTAgeLabel;
	 private javax.swing.JSpinner _maxPTAgeSpinner;
	 private javax.swing.JLabel _mutationProbabilityLabel;
	 private javax.swing.JSlider _mutationProbabilitySlider;
	 private javax.swing.JLabel _offspringFractionLabel;
	 private javax.swing.JSlider _offspringFractionSlider;
	 private javax.swing.JButton _pauseButton;
	 private javax.swing.JPanel _populationBestTransformPanel;
	 private javax.swing.JLabel _populationSizeLabel;
	 private javax.swing.JSpinner _populationSizeSpinner;
	 private javax.swing.JLabel _populationTransformBestLabel;
	 private javax.swing.JButton _startButton;
	 private javax.swing.JButton _stepButton;
	 private javax.swing.JButton _stopButton;
	 private javax.swing.JLabel _targetTransformLabel;
	 private javax.swing.JPanel _targetTransformPanel;
	 private javax.swing.JPanel _toolBasePanel;
	 private javax.swing.JPanel _toolPanel;
	 // End of variables declaration//GEN-END:variables

}




/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class PopulationSpinnerModel extends SpinnerNumberModel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	
	private final PolynomController _controller;
	
	public PopulationSpinnerModel(final PolynomController controller) {
		setMinimum(5);
		setMaximum(Integer.MAX_VALUE);
		setValue(20);
		_controller = controller;
		
		addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		_controller.setPopulationSize(getNumber().intValue());
	}
	
}

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class MaximalPhenotypeAgeSpinnerModel extends SpinnerNumberModel 
	implements ChangeListener 
{
	private static final long serialVersionUID = 1L;
	
	private final PolynomController _controller;
	
	public MaximalPhenotypeAgeSpinnerModel(final PolynomController controller) {
		setMinimum(1);
		setMaximum(Integer.MAX_VALUE);
		setValue(35);
		_controller = controller;
		
		addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		_controller.setMaximalPhenotypeAge(getNumber().intValue());
	}
	
}

interface LabeledBoundedRangeModel extends BoundedRangeModel {
	
	public Dictionary<Integer, JComponent> getLables();
	
}

class OffspringFractionRangeModel extends DefaultBoundedRangeModel 
	implements ChangeListener, LabeledBoundedRangeModel 
{

	private static final long serialVersionUID = 1L;
	
	private static final int MIN = 10;
	private static final int MAX = 90;
	private static final int VALUE = 20;

	private final PolynomController _controller;
	
	public OffspringFractionRangeModel(final PolynomController controller) {
		setMinimum(MIN);
		setMaximum(MAX);
		setValue(VALUE);
		_controller = controller;
		
		addChangeListener(this);
	}
	
	@Override
	public Dictionary<Integer, JComponent> getLables() {
		final Dictionary<Integer, JComponent> lables = new Hashtable<Integer, JComponent>();
		
		for (int i = MIN; i <= MAX; i += 10) {
			final JLabel label = new JLabel("." + i/10);
			lables.put(i, label);
		}
		
		return lables;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				_controller.setOffspringFraction(getValue()/100.0);
			}
		}).start();
	}
	
}

class MutationProbabilityRangeModel extends DefaultBoundedRangeModel 
	implements ChangeListener, LabeledBoundedRangeModel 
{
	
	private static final long serialVersionUID = 1L;
	
	private static final int MIN = 0;
	private static final int MAX = 800;
	private static final int VALUE = 50;
	
	private final PolynomController _controller;
	
	public MutationProbabilityRangeModel(final PolynomController controller) {
		setMinimum(MIN);
		setMaximum(MAX);
		setValue(VALUE);
		_controller = controller;
		
		addChangeListener(this);
	}
	
	@Override
	public Dictionary<Integer, JComponent> getLables() {
		final Dictionary<Integer, JComponent> lables = new Hashtable<Integer, JComponent>();
		
		for (int i = MIN; i <= MAX; i += 100) {
			final JLabel label = new JLabel("." + i/100);
			lables.put(i, label);
		}
		
		return lables;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		_controller.setMutationProbability(getValue()/1000.0);
	}

}


/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
interface StepListener extends EventListener {
	
	public void stepped(EventObject event);
	
	public void finished(EventObject event);
	
}


