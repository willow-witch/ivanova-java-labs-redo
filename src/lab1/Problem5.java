package lab1;

import java.util.Arrays;
public class Problem5 {

    /**
     * Метод isGeometricProgression определяет, является ли данная последовательность чисел numbers геометрической
     * прогрессией (возможно, при перестановке элементов)
     *
     * @param numbers строка, содержащая n положительных целых чисел, разделенных запятой
     * @return true, если последовательность является геометрической прогрессией
     *         false, если последовательность не является геометрической прогрессией
     *
     * ПРИМЕР1:
     * Вход: numbers = "1,2,4,8,16"
     * Выход: true
     *
     * ПРИМЕР2:
     * Вход: numbers = "16,2,8,1,4"
     * Выход: true (так как в результате перестановки элементов можно получить геометрическую прогрессию [1,2,4,8,16])
     *
     * ПРИМЕР3:
     * Вход: numbers = "4,6,9"
     * Выход: false
     */
    public static boolean isGeometricProgression(String numbers) {

        if (numbers == "") return false;

        int[] intList = Arrays.stream(numbers.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(intList);

        if (intList.length == 1) return true;

        int floorMult = (int) Math.floor(1.0*intList[1]/intList[0]);
        int ceilMult = (int) Math.floor(1.0*intList[1]/intList[0]);

        if (floorMult != ceilMult) return false;

        for (int i = 1; i < intList.length-1; i++) {

            if(Math.floor(1.0*intList[i+1]/intList[i]) != floorMult ||
                    Math.ceil(1.0*intList[i+1]/intList[i]) != ceilMult ||
                    Math.floor(1.0*intList[i+1]/intList[i]) != Math.ceil(1.0*intList[i+1]/intList[i])){
                return false;
            }
        }

        return true;
    }

}