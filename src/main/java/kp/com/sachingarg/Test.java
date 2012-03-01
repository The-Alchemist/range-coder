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

public final class Test {

    static void CopyStream(InputStream in, OutputStream out) throws IOException {
		while (true) {
			int ch = in.read();
			if (ch == -1) {
				in.close();
				out.close();
				return;
			}
			out.write(ch);
		}
    }

//  Takes 3 arguments at command line.
//  First argument is either 'c' or 'd', denoting compression and decompression 
//  respectively. Second and third arguments are the input and output file names 
	public static void main(String[] args) {
		try {
				if(args.length!=3)
					throw new Exception("Please pass correct number of arguments");

				String OpCode=args[0];
				String InputFile=args[1];
				String OutputFile=args[2];

				InputStream in=new BufferedInputStream(new FileInputStream(InputFile));
				OutputStream out=new BufferedOutputStream(new FileOutputStream(OutputFile));
			
				if(OpCode.equals("c")) {
					CompressedOutputStream Compressed=new CompressedOutputStream(out);
					CopyStream(in,Compressed);
				}
				else if(OpCode.equals("d")) {
					DecompressedInputStream Decompressed=new DecompressedInputStream(in);
					CopyStream(Decompressed,out);
					
				}else {
					throw new Exception("Pass either c or d as opcode");
				}
				
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
}
