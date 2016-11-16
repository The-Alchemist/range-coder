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

public final class RangeEncoder64 extends RangeCoder64 implements IRangeEncoder {

	public RangeEncoder64(OutputStream out) {
		_out=out;
	}
	
	/* (non-Javadoc)
	 * @see com.sachingarg.IRangeEncoder#EncodeRange(int, int, int)
	 */
	@Override
	public void EncodeRange(int SymbolLow,int SymbolHigh,int TotalRange) throws IOException {
		Low += SymbolLow*(Range/=TotalRange);
		Range *= SymbolHigh-SymbolLow;

		while (((Low ^ (Low+Range)) & 0x00FFFFFFFFFFFFFFL)<Top || Range<Bottom) {
			if(Range<Bottom && (((Low ^ (Low+Range)) & 0x00FFFFFFFFFFFFFFL)>=Top))
				Range= (-Low & 0x00FFFFFFFFFFFFFFL)& (Bottom-1);
			_out.write((int)((Low>>48)&0xFF));
			Range<<=8;
			Low<<=8;
		}
	}
	
	public void Flush() throws IOException {
		if(!Flushed) {
			for(int i=0;i<7;i++) {
				_out.write((int)((Low>>48)&0xFF));
				Low<<=8;
			}
			_out.close();
			Flushed=true;
		}
	}
	
	private boolean Flushed=false;
	private OutputStream _out;
}
