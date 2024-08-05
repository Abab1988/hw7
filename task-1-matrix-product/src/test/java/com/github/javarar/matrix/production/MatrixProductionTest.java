package com.github.javarar.matrix.production;

import com.github.javarar.matrix.production.MatrixProduction.Matrix;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixProductionTest {

    @DisplayName("Задание 2. Вычисление произведения квадратных матриц")
    @ParameterizedTest
    @MethodSource("matrixProducer")
    public void validateMatrixProduction(Matrix a, Matrix b) {
        Matrix actual = MatrixProduction.product(a, b);
        Matrix expected = MatrixProduction.bruteForce(a, b);
        assertTrue(Arrays.deepEquals(expected.values, actual.values));
    }

    private static Stream<Arguments> matrixProducer() {
        return Stream.of(
                Arguments.of(Matrix.generate(3, 3), Matrix.generate(3, 3)),
                Arguments.of(Matrix.generate(100, 100), Matrix.generate(100, 100)),
                Arguments.of(Matrix.generate(1000, 1000), Matrix.generate(1000, 1000))
        );
    }

}
