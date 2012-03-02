package com.sachingarg;

public class NullRCModel implements RCModel
{

	private static final int NoOfSymbols = 257;
	private int[] Frequency;

	public NullRCModel()
	{
		Frequency = new int[NoOfSymbols + 1];

		for (int i = 0; i < NoOfSymbols + 1; i++)
		{
			Frequency[i] = i;
		}
	}

	@Override
	public int getCumulativeFrequency(int i)
	{
		return this.Frequency[i];
	}

	@Override
	public int getNumberOfSymbols()
	{
		return NullRCModel.NoOfSymbols;
	}

	@Override
	public void update(int i)
	{
	}

}
