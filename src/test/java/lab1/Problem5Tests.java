package lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Problem5Tests {

    @Test
    void shouldReturnTrue_ifIsProgression() {
        // given
        String example = "1,2,4,8,16";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertTrue(result);
    }
    @Test
    void shouldReturnTrue_ifProgressionRepeatedNumber() {
        // given
        String example = "1,1,1,1";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnTrue_ifOneNumberGiven() {
        // given
        String example = "1";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnTrue_ifIsMixedUpProgression() {
        // given
        String example = "16,2,8,1,4";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_ifEmptyStringGiven() {
        // given
        String example = "";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_ifIsNotProgression() {
        // given
        String example = "2,3,5";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertFalse(result);
    }
    @Test
    void shouldReturnFalse_ifIsNotProgression2() {
        // given
        String example = "4,6,9";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertFalse(result);
    }
    @Test
    void shouldReturnFalse_ifIsNotProgression3() {
        // given
        String example = "1,3,7";

        // when
        boolean result = Problem5.isGeometricProgression(example);

        // then
        assertFalse(result);
    }

}
