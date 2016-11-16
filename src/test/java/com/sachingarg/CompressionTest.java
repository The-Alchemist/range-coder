package com.sachingarg;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class CompressionTest {
    @Test
    public void testCompressor() throws IOException {
        int[] expected = new int[1000];
        Random random = new Random();
        for (int i = 0; i < 1000; ++i) {
            expected[i] = random.nextInt(256);
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        RCModel model = new Order0Model();
        OutputStream compressed = new CompressedOutputStream(outStream, model);

        for (int i = 0; i < expected.length; ++i) {
            compressed.write(expected[i]);
        }
        compressed.close();
        byte[] compressedBytes = outStream.toByteArray();

        ByteArrayInputStream inStream = new ByteArrayInputStream(
                compressedBytes);
        int[] got = new int[expected.length];

        model = new FenwickTreeModel();
        DecompressedInputStream decompressedStream = new DecompressedInputStream(
                inStream, model);
        for (int i = 0; i < expected.length; ++i) {
            int read = decompressedStream.read();
            if (read == -1) {
                break;
            }
            got[i] = read;
        }
        decompressedStream.close();

        assertTrue(Arrays.equals(expected, got));

    }
}
