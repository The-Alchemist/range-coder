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

/**
 * Started work on a quasi-static model.
 * 
 * The key design consideration will be that the <pre>TotalRange</pre> will always
 * be 256, allowing for quick division (i.e., just a right-shift).
 * @author TheAlchemist
 *
 */
public final class RangeEncoderQS64 extends RangeCoder64 implements IRangeEncoder {

	public RangeEncoderQS64(OutputStream out) {
		_out=out;
	}
	
	public void EncodeRange(int SymbolLow,int SymbolHigh,int TotalRange) throws IOException {
		Low += SymbolLow*(Range/=256);
		Range *= SymbolHigh-SymbolLow;

		while (((Low ^ (Low+Range)) & 0x00FFFFFFFFFFFFFFL)<Top || Range<Bottom) {
			if(Range<Bottom && (((Low ^ (Low+Range)) & 0x00FFFFFFFFFFFFFFL)>=Top))
				Range= (-Low & 0x00FFFFFFFFFFFFFFL)& (Bottom-1);
			final long bAsLong = (Low>>48)&0xFF;
			final int bAsInt = (int)bAsLong;
			final byte bAsByte = (byte)(bAsInt - 128);
			_out.write(bAsInt);
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
