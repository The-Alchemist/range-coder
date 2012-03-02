package com.sachingarg;

//Java Source code for Range Coder
//By Sachin Garg, 2006
//
//This range coder is based upon the carry-less range coder 
//implementation by Dmitry Subbotin, and uses 64-bit variables 
//for improved performance.
//
//http://www.sachingarg.com/compression/entropy_coding/java_range_coder

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Exception;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import com.google.common.base.Stopwatch;
import com.google.common.io.CountingInputStream;
import com.google.common.io.NullOutputStream;

public final class Test
{

	static void CopyStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] bytes = new byte[8 * 1024];
		int bytesRead = 0;
		while ((bytesRead = in.read(bytes)) != -1)
		{
			out.write(bytes, 0, bytesRead);
		}
		in.close();
		out.close();
	}

	// Takes 3 arguments at command line.
	// First argument is either 'c' or 'd', denoting compression and decompression
	// respectively. Second and third arguments are the input and output file names
	public static void main(String[] args) throws Exception
	{
		if (args.length != 2)
			throw new Exception("Please pass correct number of arguments");

		String OpCode = args[0];
		String InputFile = args[1];
		// String OutputFile=args[2];

		for (int i = 0; i != 7; ++i)
		{
			CountingInputStream in = new CountingInputStream(new BufferedInputStream(new FileInputStream(InputFile)));
			// OutputStream out=new BufferedOutputStream(new FileOutputStream(OutputFile));
			OutputStream out = new NullOutputStream();

			Stopwatch sw = new Stopwatch();
			sw.start();
			if (OpCode.equals("c"))
			{
				CompressedOutputStream Compressed = new CompressedOutputStream(out);
				CopyStream(in, Compressed);
			} else if (OpCode.equals("d"))
			{
				DecompressedInputStream Decompressed = new DecompressedInputStream(in);
				CopyStream(Decompressed, out);

			} else
			{
				throw new Exception("Pass either c or d as opcode");
			}
			sw.stop();
			System.out.format("Take %d: %.2fMB in %s\n", i, in.getCount() / 1000000.0, sw.toString());
		}
	}
}
