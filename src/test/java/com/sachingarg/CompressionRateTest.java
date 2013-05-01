package com.sachingarg;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class CompressionRateTest {
    @Test
    public void test() throws IOException {
        int N = 10000;
        int[] value = new int[N * 2];
        int[] frequency = new int[256];

        Arrays.fill(frequency, 1);

        Random random = new Random();
        double cost = 0;
        int total = 256;
        for (int i = 0; i < N; ++i) {
            int n = random.nextInt(128); // use only half of the values
            value[i] = n;
            cost += Math.log(((double) total) / frequency[n]) / Math.log(2);
            frequency[n]++;
            total++;
        }

        for (int i = 0; i < N; ++i) {
            int n = 240;
            value[i + N] = n;
            cost += Math.log(((double) total) / frequency[n]) / Math.log(2);
            frequency[n]++;
            total++;
        }

        List<RCModel> models = Arrays.asList(new FenwickTreeModel(),
                new Order0Model());
        for (RCModel model : models) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            OutputStream compressed = new CompressedOutputStream(outStream,
                    model);

            for (int i = 0; i < value.length; ++i) {
                compressed.write(value[i]);
            }
            compressed.close();
            byte[] bytes = outStream.toByteArray();
            int sizeDifference = bytes.length * 8
                    - ((int) Math.ceil(cost) + 64);
            assertTrue(sizeDifference >= 0);
            assertTrue(sizeDifference < bytes.length / 100);
        }
    }
}
