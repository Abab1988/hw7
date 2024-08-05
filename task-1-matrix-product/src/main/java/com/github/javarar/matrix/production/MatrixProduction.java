package com.github.javarar.matrix.production;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixProduction {

    public static Matrix product(Matrix a, Matrix b) {
        if (a.cols != b.rows) throw new IllegalArgumentException();
        long[][] result = new long[a.rows][b.cols];
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                final int fi = i;
                final int fj = j;
                futures.add(CompletableFuture.runAsync(() -> {
                    for (var k = 0; k < a.cols; k++) {
                        result[fi][fj] += a.values[fi][k] * b.values[k][fj];
                    }
                }));
            }
        }
        futures.forEach(CompletableFuture::join);
        return new Matrix(result);
    }

    public static Matrix bruteForce(Matrix a, Matrix b) {
        long[][] c = new long[a.rows][b.cols];
        for (int i = 0; i < a.rows; ++i) {
            for (int j = 0; j < b.cols; ++j) {
                for (int k = 0; k < a.cols; ++k) {
                    c[i][j] += a.values[i][k] * b.values[k][j];
                }
            }
        }
        return new Matrix(c);
    }

    public static class Matrix {

        int rows;
        int cols;
        long[][] values;

        public Matrix(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.values = new long[rows][cols];
        }

        public Matrix(long[][] values) {
            this.values = values;
            this.rows = this.values.length;
            this.cols = this.values[0].length;
        }

        public static Matrix generate(int rows, int cols) {
            Matrix matrix = new Matrix(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix.values[i][j] = ThreadLocalRandom.current().nextInt(0, 100);
                }
            }
            return matrix;
        }

    }
}
