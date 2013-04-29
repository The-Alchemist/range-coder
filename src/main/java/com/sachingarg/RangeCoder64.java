package com.sachingarg;

//Java Source code for Range Coder
//By Sachin Garg, 2006
//
//This range coder is based upon the carry-less range coder 
//implementation by Dmitry Subbotin, and uses 64-bit variables 
//for improved performance.
//
//http://www.sachingarg.com/compression/entropy_coding/java_range_coder

public class RangeCoder64 {
	public static final long Top=1L<<48;
	public static final long Bottom=1L<<40;
	public static final long MaxRange=Bottom;
	
	protected long Low=0;
	protected long Range=0x00FFFFFFFFFFFFFFL;
}
