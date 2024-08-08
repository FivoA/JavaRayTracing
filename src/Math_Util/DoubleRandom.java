package Math_Util;

import java.util.Random;

public class DoubleRandom {
    public static double getRandomNumber(double rangeMin, double rangeMax) {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }
}
