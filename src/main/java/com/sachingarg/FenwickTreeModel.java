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

	protected final int NoOfSymbols=257; //256 + EOF
	private final FenwickTree fw = new FenwickTree(NoOfSymbols + 1);
	
	public FenwickTreeModel()
	{
		// don't increment the count of the first item because Order0Model doesn't
		for(int i = 1; i != getNumberOfSymbols(); ++i)
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
		this.fw.addValue(i, 1);
	}

}
