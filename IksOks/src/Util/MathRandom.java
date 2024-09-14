package Util;

import java.util.Random;

public class MathRandom {

    public static int[] generateRandomNumbers() {

        int numberOne = (int) (Math.random() * 3);
        int numberTwo = (int) (Math.random() * 3);
        return new int[]{numberOne, numberTwo};

    }
}
