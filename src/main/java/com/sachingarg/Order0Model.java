package com.sachingarg;

import java.util.Arrays;

//Java Source code for Range Coder
//By Sachin Garg, 2006
//
//This range coder is based upon the carry-less range coder 
//implementation by Dmitry Subbotin, and uses 64-bit variables 
//for improved performance.
//
//http://www.sachingarg.com/compression/entropy_coding/java_range_coder

public final class Order0Model implements RCModel {
	
	protected int[] Frequency;

	protected final int NoOfSymbols;

	public Order0Model() {
		this(256);
	}

	public Order0Model(int n) {
		NoOfSymbols=n+1; // + EOF
		Frequency=new int[NoOfSymbols+1];

		for(int i=0;i<NoOfSymbols+1;i++) {
			Frequency[i]=i;
		}
	}

    /* (non-Javadoc)
	 * @see com.sachingarg.RCModel#getCumulativeFrequency(int)
	 */
	@Override
	public int getCumulativeFrequency(int i)
	{
		return this.Frequency[i];
	}
    /* (non-Javadoc)
	 * @see com.sachingarg.RCModel#getNumberOfSymbols()
	 */
	@Override
	public int getNumberOfSymbols()
	{
		return this.NoOfSymbols;
	}
	protected void Rescale() {
		if(Frequency[Frequency.length-1]>=RangeCoder64.MaxRange) {
			int Total=0;
			for(int i=1;i<Frequency.length-1;i++) {
				Total+=((Frequency[i]-Frequency[i-1])/2)+1;
				Frequency[i]=Frequency[i-1]+((Frequency[i]-Frequency[i-1])/2)+1;
			}
			Frequency[Frequency.length-1]=Total;
		}
	}

	@Override
	public void update(int i) {
		for(int j=i+1;j<Frequency.length;j++)
			Frequency[j]++;

		Rescale();
	}

	@Override
	public int getSymbolForFrequency(int count) {
		int result = Arrays.binarySearch(Frequency, count);
		if (result < 0) {
		    result = -result - 1;
		}
		if (result >= NoOfSymbols) {
		    return NoOfSymbols-1;
		}
		return result - 1;
	}
}
