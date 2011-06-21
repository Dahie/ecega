package org.ecega;

import java.awt.geom.AffineTransform;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class TransformPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	
	private AffineTransform _transform;
	
	 public TransformPanel() {
		  initComponents();
	 }

	 public void setAffineTransform(final AffineTransform transform) {
		if (!transform.equals(_transform)) {
			final double[] m = new double[6];
			transform.getMatrix(m);
			
			_m00.setValue(m[0]); _m01.setValue(m[2]); _m02.setValue(m[4]);
			_m10.setValue(m[1]); _m11.setValue(m[3]); _m12.setValue(m[5]);
			_m20.setValue(0); 	_m21.setValue(0); 	_m22.setValue(1);
			
			_transform = transform;
		}
	 }

	 private void initComponents() {
		  java.awt.GridBagConstraints gridBagConstraints;

		  _m00 = new javax.swing.JFormattedTextField();
		  _m01 = new javax.swing.JFormattedTextField();
		  _m02 = new javax.swing.JFormattedTextField();
		  _m10 = new javax.swing.JFormattedTextField();
		  _m11 = new javax.swing.JFormattedTextField();
		  _m12 = new javax.swing.JFormattedTextField();
		  _m20 = new javax.swing.JFormattedTextField();
		  _m21 = new javax.swing.JFormattedTextField();
		  _m22 = new javax.swing.JFormattedTextField();
		  
		  setLayout(new java.awt.GridBagLayout());
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m00, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m01, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m02, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 1;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m10, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 1;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m11, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 2;
		  gridBagConstraints.gridy = 1;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m12, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 0;
		  gridBagConstraints.gridy = 2;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m20, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 1;
		  gridBagConstraints.gridy = 2;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m21, gridBagConstraints);
		  gridBagConstraints = new java.awt.GridBagConstraints();
		  gridBagConstraints.gridx = 2;
		  gridBagConstraints.gridy = 2;
		  gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		  gridBagConstraints.weightx = 1.0;
		  add(_m22, gridBagConstraints);
		  
	 }


	 private javax.swing.JFormattedTextField _m00;
	 private javax.swing.JFormattedTextField _m01;
	 private javax.swing.JFormattedTextField _m02;
	 private javax.swing.JFormattedTextField _m10;
	 private javax.swing.JFormattedTextField _m11;
	 private javax.swing.JFormattedTextField _m12;
	 private javax.swing.JFormattedTextField _m20;
	 private javax.swing.JFormattedTextField _m21;
	 private javax.swing.JFormattedTextField _m22;

}

