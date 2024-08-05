package com.github.javarar.animal.faces;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Executors;


class AnimalFacesPipelineTest {

    private final AnimalFacesPipeline pipe = new AnimalFacesPipeline();

    @Test
    void testAsync() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        pipe.downloadAsync(Executors.newCachedThreadPool());
        long endTime = System.currentTimeMillis();
        System.out.println("Time async = " + (endTime - startTime));
    }

    @Test
    void test() throws IOException {
        long startTime = System.currentTimeMillis();
        pipe.download();
        long endTime = System.currentTimeMillis();
        System.out.println("Time = " + (endTime - startTime));
    }

}