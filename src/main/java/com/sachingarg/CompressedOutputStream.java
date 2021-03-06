package com.sachingarg;

//Java Source code for Range Coder
//By Sachin Garg, 2006
//
//This range coder is based upon the carry-less range coder 
//implementation by Dmitry Subbotin, and uses 64-bit variables 
//for improved performance.
//
//http://www.sachingarg.com/compression/entropy_coding/java_range_coder

import java.io.IOException;
import java.io.OutputStream;

public final class CompressedOutputStream extends OutputStream {
	
	public CompressedOutputStream(OutputStream out) {

		Encoder=new RangeEncoder64(out);
		Model=new NullRCModel();
	}

	public CompressedOutputStream(OutputStream out, RCModel model)
	{
		Encoder=new RangeEncoder64(out);
		Model = model;
	}

	public void close() throws IOException {
    	Encoder.EncodeRange(Model.getCumulativeFrequency(Model.getNumberOfSymbols()-1),Model.getCumulativeFrequency(Model.getNumberOfSymbols()),Model.getCumulativeFrequency(Model.getNumberOfSymbols()));
		Encoder.Flush();
	}

	public void flush() throws IOException {
    	//Encoder.Flush();
    }
	
    public void write(byte[] bs)  throws IOException {
    	write(bs,0,bs.length);
    }

    public void write(byte[] bs, int off, int len)  throws IOException {
    	while (off < len)
		{
			final byte b = bs[off++];
			final int convertByteToInt = 128 + (int)b;
			write(convertByteToInt);
		}
    }

    public void write(int i)  throws IOException {
    	final int cumFreq = Model.getCumulativeFrequency(i);
		final int cumFreqNext = Model.getCumulativeFrequency(i+1);
		final int cumFreqRange = Model.getCumulativeFrequency(Model.getNumberOfSymbols());
		Encoder.EncodeRange(cumFreq,cumFreqNext,cumFreqRange);
    	Model.update(i);
    }

    private RCModel Model;
	private IRangeEncoder Encoder;
}
