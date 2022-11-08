package lab1;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Problem2 {

    /**
     * Метод segregateEvenAndOddNumbers разделяет четные и нечетные числа в массиве array, т.у. возвращает массив,
     * в котором сперва записаны все четные числа массива array в порядке их следования, а затем все нечетные числа
     * массива array в порядке их следования.
     *
     * @param array массив положительных чисел
     * @return массив с разделенными четными и нечетными числами
     *
     * ПРИМЕР:
     * Вход: array = [2, 1, 5, 6, 8]
     * Выход: [2, 6, 8, 1, 5]
     */
    public static int[] segregateEvenAndOddNumbers(int[] array) {
        List<Integer> evenNumbers = new ArrayList<>();
        List<Integer> oddNumbers = new ArrayList<>();

        for (int elem: array) {
            if(elem % 2 == 0)
            {
                evenNumbers.add(elem);
            }
            else
            {
                oddNumbers.add(elem);
            }
        }

        int[] result = Ints.toArray(Stream.concat(evenNumbers.stream(), oddNumbers.stream()).toList());

        return result;
    }

}