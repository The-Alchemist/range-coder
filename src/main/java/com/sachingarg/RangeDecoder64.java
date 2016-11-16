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

public final class RangeDecoder64 extends RangeCoder64 {
	
	public RangeDecoder64(InputStream in) throws IOException {
		_in=in;

		for(int i=0;i<7;i++) {
			Code = (Code << 8) | (_in.read() & 0xff);
		}
	}
	
	public int GetCurrentCount(int TotalRange) {
		return (int)((Code-Low)/(Range/=TotalRange));		
	}
	
	public void RemoveRange(int SymbolLow,int SymbolHigh,int TotalRange) throws IOException {
		Low += SymbolLow*Range;
		Range *= SymbolHigh-SymbolLow;

		while (((Low ^ Low+Range) & 0x00FFFFFFFFFFFFFFL)<Top || Range<Bottom) {
			
			if(Range<Bottom && (((Low ^ (Low+Range)) & 0x00FFFFFFFFFFFFFFL)>=Top))
				Range= (-Low & 0x00FFFFFFFFFFFFFFL)& (Bottom-1);

			Code= Code<<8 | (_in.read() & 0xFF);
			Range<<=8;
			Low<<=8;
		}
	}
	
	private long Code=0;
	private InputStream _in;
}
