package org.ecega;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ActionFactory {

}



/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
class InitAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final GeometryController _controller;
	
	public InitAction(final GeometryController controller) {
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

	private final GeometryController _controller;
	
	public StartAction(final GeometryController controller) {
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

	private final GeometryController _controller;
	
	public StopAction(final GeometryController controller) {
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

	private final GeometryController _controller;
	
	public PauseAction(final GeometryController controller) {
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

	private final GeometryController _controller;
	
	public StepAction(final GeometryController controller) {
		super("Step");
		_controller = controller;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		_controller.step();
	}
	
}