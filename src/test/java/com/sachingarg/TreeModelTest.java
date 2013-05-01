/**
 *
 */
package com.sachingarg;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

/**
 * @author TheAlchemist
 *
 */
public class TreeModelTest
{

	@Test
	public void testUpdate()
	{
		// first, create the models
		final FenwickTreeModel ftm = new FenwickTreeModel();
		final Order0Model stm = new Order0Model();
		// sanity checks
		assertEquals(257, stm.getNumberOfSymbols());
		assertEquals(257, ftm.getNumberOfSymbols());
		compareAndVerify(stm, ftm);

		stm.update(0);
		ftm.update(0);
		compareAndVerify(stm, ftm);
		// then, perform identical operations on them and make sure the numbers add up
		Random random = new Random();
		for (int i = 0; i < 1000; ++i) {
			int n = random.nextInt(256);
			stm.update(n);
			ftm.update(n);
			compareAndVerify(stm, ftm);
		}
		assertEquals(257, stm.getNumberOfSymbols());
		assertEquals(257, ftm.getNumberOfSymbols());
	}

	private void compareAndVerify(Order0Model stm, FenwickTreeModel ftm)
	{
		for(int i = 0; i <= stm.getNumberOfSymbols(); ++i)
		{
			final int stmCumFreq = stm.getCumulativeFrequency(i);
			final int ftmCumFreq = ftm.getCumulativeFrequency(i);
			assertEquals(stmCumFreq, ftmCumFreq);
		}
	}

}
