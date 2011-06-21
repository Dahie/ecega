package org.ecega;

import java.util.EventObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: Geometry.java 826 2011-02-25 10:41:09Z fwilhelm $
 */
public class Stepable implements Runnable {
	private final Lock _lock = new ReentrantLock();
	private final Condition _run = _lock.newCondition();

	private final List<StepListener> _listeners = new CopyOnWriteArrayList<StepListener>();
	
	private volatile int _steps = 0;
	private final Runnable _stepTask;

	public Stepable(final Runnable stepTask) {
		_stepTask = stepTask;
	}

	public void start() {
		_lock.lock();
		try {
			_steps = Integer.MAX_VALUE;
			_run.signalAll();
		} finally {
			_lock.unlock();
		}
	}

	public void stop() {
		_lock.lock();
		try {
			_steps = 0;
			_run.signalAll();
		} finally {
			_lock.unlock();
		}
	}

	public void step(int steps) {
		_lock.lock();
		try {
			_steps += steps;
			_run.signalAll();
		} finally {
			_lock.unlock();
		}
	}

	public void step() {
		step(1);
	}

	private void waiting() throws InterruptedException {
		_lock.lock();
		try {
			while (_steps <= 0) {
				_run.await();
			}
		} finally {
			_lock.unlock();
		}
	}

	private boolean canExecute() {
		_lock.lock();
		try {
			return _steps > 0;
		} finally {
			_lock.unlock();
		}
	}
	
	private void execute() {
		_stepTask.run();
		
		_lock.lock();
		--_steps;
		_lock.unlock();
		
		final EventObject event = new EventObject(this);
		for (StepListener listener : _listeners) {
			listener.stepped(event);
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				waiting();
				
				while (canExecute()) {
					execute();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			final EventObject event = new EventObject(this);
			for (StepListener listener : _listeners) {
				listener.finished(event);
			}
		}
	}
	
	public void addStepListener(final StepListener listener) {
		_listeners.add(listener);
	}
	
	public void removeStepListener(final StepListener listener) {
		_listeners.remove(listener);
	}

}