/**
 * 
 */
package com.sachingarg;

import kp.FenwickTree;

/**
 * @author thealchemist-user
 *
 */
public class FenwickTreeModel implements RCModel
{

	protected final int NoOfSymbols;
	private final FenwickTree fw;

	public FenwickTreeModel()
	{
		this(256);
	}

	public FenwickTreeModel(int n)
	{
		NoOfSymbols = n + 1; //+ EOF
		fw = new FenwickTree(NoOfSymbols + 1);

		// don't increment the count of the first item because the cumulative
		// frequency at zero is always zero
		for(int i = 1; i != getNumberOfSymbols()+1; ++i)
		{
			this.fw.addValue(i, 1);
		}
	}

	@Override
	public int getCumulativeFrequency(int i)
	{
		return this.fw.getCumulativeFrequency(i);
	}

	@Override
	public int getNumberOfSymbols()
	{
		return this.NoOfSymbols;
	}

	@Override
	public void update(int i)
	{
		this.fw.addValue(i+1, 1);
	}

	@Override
	public int getSymbolForFrequency(int count) {
		int result = fw.indexOfCumulativeFrequency(count);
		return result;
	}

}
