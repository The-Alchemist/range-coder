package com.sachingarg;

import java.io.IOException;

/**
 * Abstraction for the range coders so that I can have multiple models
 * @author TheAlchemist
 *
 */
public interface IRangeEncoder
{
	public abstract void EncodeRange(int SymbolLow, int SymbolHigh, int TotalRange) throws IOException;

	public abstract void Flush() throws IOException;

}