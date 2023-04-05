package app.model;

public class Utility {

    public static long generateRandom(long min, long max) {
        if (min > max) {
            long temp = min;
            min = max;
            max = temp;
        }

        return (long) (Math.random() * (max-min) + min);
    }
}
