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
import java.io.InputStream;

public final class DecompressedInputStream extends InputStream
{

	public DecompressedInputStream(InputStream in) throws IOException
	{
		Decoder = new RangeDecoder64(in);
		Model = new Order0Model();
		read(); // Decode the first byte
	}

	public DecompressedInputStream(InputStream inputStream, RCModel modelOut) throws IOException
	{
		Decoder = new RangeDecoder64(inputStream);
		Model = modelOut;
		read(); // Decode the first byte
	}

	public int available()
	{
		return (_nextByte >= 0) ? 1 : 0;
	}

	public void mark(int readLimit)
	{
		// not supported
	}

	public boolean markSupported()
	{
		return false;
	}

	public void close() throws IOException
	{
	}

	public int read(byte[] bs) throws IOException
	{
		return read(bs, 0, bs.length);
	}

	public int read(byte[] bs, int off, int len) throws IOException
	{
		for (int i = off; i < len; ++i)
		{
			int nextByte = read();
			if (nextByte == -1)
				return (i - off); // eof, return length read
			bs[i] = (byte) (nextByte & 0xFF);
		}
		return len > 0 ? len : 0;
	}

	public int read() throws IOException
	{
		int result = _nextByte;
		if (_nextByte == -1)
			return -1;

		int Count = Decoder.GetCurrentCount(Model.getCumulativeFrequency(Model.getNumberOfSymbols()));

		int Symbol = Model.getSymbolForFrequency(Count);

		_nextByte = Symbol == (Model.getNumberOfSymbols() - 1) ? -1 : Symbol;

		Decoder.RemoveRange(Model.getCumulativeFrequency(Symbol), Model.getCumulativeFrequency(Symbol + 1), Model.getCumulativeFrequency(Model.getNumberOfSymbols()));

		Model.update(Symbol);

		return result;
	}

	public long skip(long n) throws IOException
	{
		for (long i = 0; i < n; ++i)
			if (read() == -1)
				return i;
		return n;
	}

	private RCModel Model;
	private RangeDecoder64 Decoder;
	private int _nextByte;
}
