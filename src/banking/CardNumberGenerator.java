package banking;

import java.util.Arrays;
import java.util.Random;

public class CardNumberGenerator {

    static String generateCardNumber() {
        int[] arrayWithCardNumber = new int[16];
        int[] arrayForFindingDigitNumberNo16;
        addIinToCardNumber(arrayWithCardNumber);
        add9DigitsToCardNumber(arrayWithCardNumber);
        arrayForFindingDigitNumberNo16 = arrayWithCardNumber.clone();
        multiplyOddDigitsBy2(arrayForFindingDigitNumberNo16);
        subtract9ToNumbersOver9(arrayForFindingDigitNumberNo16);
        arrayWithCardNumber[15] = findLastDigit(arrayForFindingDigitNumberNo16);
        return convertArrayToString(arrayWithCardNumber);
    }

    static void addIinToCardNumber(int[] emptyArray) {
        emptyArray[0] = 4;
        emptyArray[1] = 0;
        emptyArray[2] = 0;
        emptyArray[3] = 0;
        emptyArray[4] = 0;
        emptyArray[5] = 0;

    }

    static void add9DigitsToCardNumber(int[] arrayWithIin) {
        Random random=new Random();
        for (int i = 6; i < 15; i += 1) {
            arrayWithIin[i] = random.nextInt(10);
        }
    }

    public static void multiplyOddDigitsBy2(int[] arrayWith15Digits) {
        for (int i = 0; i < 15; i += 2) {
            arrayWith15Digits[i] *= 2;
        }
    }

    public static void subtract9ToNumbersOver9(int[] arrayWith15Digits) {
        for (int i = 0; i < arrayWith15Digits.length; i += 1) {
            if (arrayWith15Digits[i] > 9) {
                arrayWith15Digits[i] = arrayWith15Digits[i] - 9;
            }
        }
    }

    public static int findLastDigit(int[] arrayWith15Digits) {
        int sum = Arrays.stream(arrayWith15Digits).sum();
        if (sum % 10 == 0) {
            return arrayWith15Digits[15] = 0;
        } else {
            return arrayWith15Digits[15] = 10 - sum % 10;
        }
    }

    static String convertArrayToString(int[] array) {
        return Arrays.toString(array)
                .replace("[", "")
                .replace(",", "")
                .replace("]", "")
                .replace(" ", "");
    }
}
