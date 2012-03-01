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
	private final FenwickTree fw = new FenwickTree(NoOfSymbols);
	
	public FenwickTreeModel()
	{
		for(int i = 0; i != getNumberOfSymbols(); ++i)
		{
			this.fw.addValue(i + 1, 1);
		}
	}

	/* (non-Javadoc)
	 * @see com.sachingarg.RCModel#getCumulativeFrequency(int)
	 */
	@Override
	public int getCumulativeFrequency(int i)
	{
		return this.fw.getCumulativeFrequency(i);
	}

	/* (non-Javadoc)
	 * @see com.sachingarg.RCModel#getNumberOfSymbols()
	 */
	@Override
	public int getNumberOfSymbols()
	{
		return this.NoOfSymbols;
	}

	/* (non-Javadoc)
	 * @see com.sachingarg.RCModel#Update(int)
	 */
	@Override
	public void update(int i)
	{
		this.fw.addValue(i + 1, 1);
	}

}
