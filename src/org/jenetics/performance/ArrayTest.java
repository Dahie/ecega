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

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

import org.jenetics.util.Array;
import org.jenetics.util.ArrayUtils;
import org.jenetics.util.Factory;
import org.jenetics.util.Predicate;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: ArrayTest.java 888 2011-05-10 21:05:52Z fwilhelm $
 */
@Suite("Array")
public class ArrayTest {

	private static final Predicate<Integer> GETTER = new Predicate<Integer>() {
		@Override 
		public boolean evaluate(final Integer value) {
			return true;
		}
	};
	
	private static final Factory<Integer> INTEGER_FACTORY = new Factory<Integer>() {
		@Override
		public Integer newInstance() {
			return 1;
		}
	};
	
	private static final int LOOPS = 20;
	private static int SIZE = 1000000;
	
	private final Array<Integer> _array = new Array<Integer>(SIZE);
	
	public ArrayTest() {
	}
	
	@Test(1)
	public final TestCase forLoopGetter = new TestCase("for-loop (getter)", LOOPS, SIZE) {
		{
			_array.fill(INTEGER_FACTORY);
			for (int i = _array.length(); --i >= 0;) {
				_array.get(i);
			}
		}
		
		@Override 
		protected void test() {
			for (int i = _array.length(); --i >= 0;) {
				_array.get(i);
			}
		}
	};
	
	@Test(2)
	public final TestCase foreachLoopGetter = new TestCase("foreach(GETTER)", LOOPS, SIZE) {
		@Override 
		protected void test() {
			_array.foreach(GETTER);
		}
	};
	
	@Test(3)
	public final TestCase foreachLoopSetter = new TestCase("for-loop (setter)", LOOPS, SIZE) {
		@Override 
		protected void test() {
			for (int i = _array.length(); --i >= 0;) {
				_array.set(i, 1);
			}
		}
	};
	
	@Test(4)
	public final TestCase fill = new TestCase("fill(1)", LOOPS, SIZE) {
		@Override 
		protected void test() {
			_array.fill(1);
		}
	};
	
	@Test(5)
	public final TestCase fillFactory = new TestCase("fill(Factory)", LOOPS, SIZE) {
		@Override
protected void test() {
			_array.fill(INTEGER_FACTORY);
		}
	};
	
	@Test(6)
	public final TestCase iterator = new TestCase("iterator()", LOOPS, SIZE) {
		@Override 
		protected void test() {
			for (Iterator<Integer> it = _array.iterator(); it.hasNext();) {
				it.next();
			}
		}
	};
	
	@Test(7)
	public final TestCase listIterator = new TestCase("listIterator()", LOOPS, SIZE) {
		@Override 
		protected void test() {
			for (ListIterator<Integer> it = _array.listIterator(); it.hasNext();) {
				it.next();
				it.set(1);
			}
		}
	};
	
	@Test(8)
	public final TestCase sort = new TestCase("sort()", 50, SIZE) {
		private final Comparator<Integer> _comparator = new Comparator<Integer>() {
			@Override 
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};
		
		@Override 
		protected void beforeTest() {
			for (int i = _array.length(); --i >= 0;) {
				_array.set(i, i);
			}
			ArrayUtils.shuffle(_array);
		}
		
		@Override 
		protected void test() {
			ArrayUtils.sort(_array, _comparator);
		}
		
		@Override
		protected void afterTest() {
			if (!ArrayUtils.isSorted(_array)) {
				throw new IllegalArgumentException("Error: array not sorted");
			}
		}
	};
	
	@Test(9)
	public final TestCase quicksort = new TestCase("quicksort()", 50, SIZE) {
		@Override 
		protected void beforeTest() {
			for (int i = _array.length(); --i >= 0;) {
				_array.set(i, i);
			}
			ArrayUtils.shuffle(_array);
		}
		
		@Override 
		protected void test() {
			_array.sort();
		}
		
		@Override
		protected void afterTest() {
			if (!ArrayUtils.isSorted(_array)) {
				throw new IllegalArgumentException("Error: array not sorted");
			}
		}
	};	
	
	@Test(10)
	public final TestCase copy = new TestCase("copy()", LOOPS, SIZE) {
		@Override 
		protected void test() {
			_array.copy();
		}
	};
	
}
