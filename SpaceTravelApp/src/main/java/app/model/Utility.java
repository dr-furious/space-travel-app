package app.model;

/**
 * This Utility class contains all methods that are small and not suitable for any other class
 */
public class Utility {

    public static long generateRandom(long min, long max) {
        if (min > max) {
            long temp = min;
            min = max;
            max = temp;
        }

        return (long) (Math.random() * (max-min) + min);
    }

    public static boolean containsNonNumericChars(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return true;
            }
        }

        return false;
    }

    public static String  removeNonNumericChars(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                s = s.replace(Character.toString(s.charAt(i)), "");
                i--;
            }
        }

        return s;
    }

    public static String mask(String s) {

        return "*".repeat(s.length());
    }

    public static String removeLastChar(String s) {
        String newS = "";
        for (int i = 0; i < s.length() - 1; i++) {
            newS += Character.toString(s.charAt(i));
        }

        return newS;
    }

}
