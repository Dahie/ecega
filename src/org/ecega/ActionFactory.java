package org.ecega;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;



/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class InitAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final PolynomController _controller;
	
	public InitAction(final PolynomController controller) {
		super("Init");
		_controller = controller;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		_controller.init();
	}
	
}

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class StartAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final PolynomController _controller;
	
	public StartAction(final PolynomController controller) {
		super("Start");
		_controller = controller;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		_controller.start();
	}
	
}

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class StopAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final PolynomController _controller;
	
	public StopAction(final PolynomController controller) {
		super("Stop");
		_controller = controller;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		_controller.stop();
	}
	
}

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class PauseAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final PolynomController _controller;
	
	public PauseAction(final PolynomController controller) {
		super("Pause");
		_controller = controller;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		_controller.pause();
	}
	
}

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class StepAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final PolynomController _controller;
	
	public StepAction(final PolynomController controller) {
		super("Step");
		_controller = controller;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		_controller.step();
	}
	
}