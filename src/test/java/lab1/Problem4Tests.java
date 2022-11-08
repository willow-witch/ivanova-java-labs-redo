package test.java.lab1;

import main.java.lab1.Problem4;
import org.junit.jupiter.api.Test;

import com.google.common.primitives.Ints;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Problem4Tests {

    public int[][] transposeMatrix(int [][] m){
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
    @Test
    void shouldReturnTrue_ifEmptyMatrixGiven() {
        // given
        int[][] example = {{}, {}, {}};
        int[] answer = {};

        // when
        int[] result = Problem4.flattenMatrix(example);

        boolean actual = Arrays.equals(answer, result);

        // then
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrue_ifMatrixContainsOneElement() {
        // given
        int[][] example = {{1}};
        int[] answer = {1};

        // when
        int[] result = Problem4.flattenMatrix(example);

        boolean actual = Arrays.equals(answer, result);

        // then
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrue_ifEvenAndOddNumbersSegregated() {
        // given
        int[][] example = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        int[] answer = Ints.concat(this.transposeMatrix(example));

        // when
        int[] result = Problem4.flattenMatrix(example);

        boolean actual = Arrays.equals(answer, result);

        // then
        assertTrue(actual);
    }
}
