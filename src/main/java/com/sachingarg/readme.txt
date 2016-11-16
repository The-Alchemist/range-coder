Java Source code for Range Coder
By Sachin Garg 2006

http://www.sachingarg.com/compression/entropy_coding/java_range_coder

Range coder is a very popular algorithm used for entropy coding in compression algorithms.

One of the major troubles with Java is it's total lack of unsigned variables. Most compression and encryption algorithms depend greatly on concept of 'Byte' which is presumed to be unsigned (0-255), but welcome to Java world, no unsigned variables. Moreover, the result of many arithmetic operations is different when variables are signed, especially when code relies on bit-shifting. This is again very common in compression and encryption algorithms.

Also, reserving (in this case wasting) a bit for sign, results in loss of precision when doing calculations for range coding. As Range Coder is byte based, this wastes 8 higher order bits, leaving very little room for calculations. So I chose to use Java's 64-bit long type. With 64-bit processors becoming increasing popular, it makes sense for software to use this extra available processing power. 

For detailed discussion on effect of precision on compression ratio, read this:

  Effective Entropy Coding
  http://www.sachingarg.com/compression/entropy_coding/64bit


This range coder is based upon the carry-less range coder implementation by Dmitry Subbotin, and uses 64-bit variables for improved performance. Archive contains a generic range coder and decoder, along with a byte stream order-zero model implemented as subclasses of Java's I/O streams.

Please send your suggestions, improvements, errors, feedback etc... 

Read license.txt before using this in anyway.
