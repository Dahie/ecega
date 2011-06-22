package org.ecega;

import org.jscience.mathematics.number.Float64;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class PolynomPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

	private Float64[] _polynom;


	private javax.swing.JFormattedTextField _m0;
	private javax.swing.JFormattedTextField _m1;
	private javax.swing.JFormattedTextField _m2;
	private javax.swing.JFormattedTextField _m3;

	public PolynomPanel() {
		initComponents();
	}

	public void setPolynom(final Float64[] polynom) {
		if (!polynom.equals(_polynom)) {
			final double[] m = new double[6];

			_m0.setValue(m[0]); _m1.setValue(m[2]); _m2.setValue(m[4]);
			_m3.setValue(m[1]); 

			_polynom = polynom;
		}
	}

	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		_m0 = new javax.swing.JFormattedTextField();
		_m1 = new javax.swing.JFormattedTextField();
		_m2 = new javax.swing.JFormattedTextField();
		_m3 = new javax.swing.JFormattedTextField();

		setLayout(new java.awt.GridBagLayout());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(_m0, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(_m1, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(_m2, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(_m3, gridBagConstraints);


	}

}

