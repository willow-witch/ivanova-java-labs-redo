package lab1;

import lab1.Problem2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Problem2Tests {

    boolean checkArrayForSegregation(int[] array){
        String nunsMods = "";
        for (int i=0; i<array.length; i++) {
            int mod =  array[i]%2;
            nunsMods.concat(String.valueOf(mod));
        }
        String[] splitArray = nunsMods.split("01");

        return (splitArray.length<=2);
    }

    @Test
    void shouldReturnTrue_ifEvenAndOddNumbersSegregated() {
        // given
        int[] example = {2, 1, 5, 6, 8};

        // when
        int[] result = Problem2.segregateEvenAndOddNumbers(example);

        boolean actual = this.checkArrayForSegregation(result);

        // then
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrue_ifEvenNumbersOnly() {
        // given
        int[] example = {2, 4, 8, 6, 8};

        // when
        int[] result = Problem2.segregateEvenAndOddNumbers(example);

        boolean actual = this.checkArrayForSegregation(result);

        // then
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrue_ifOddNumbersOnly() {
        // given
        int[] example = {3, 1, 5, 7, 9};

        // when
        int[] result = Problem2.segregateEvenAndOddNumbers(example);

        boolean actual = this.checkArrayForSegregation(result);

        // then
        assertTrue(actual);
    }

}
