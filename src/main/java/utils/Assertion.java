package utils;

public class Assertion {
    public static void assertEquals(Object actual, Object expected, String message) {
        if (!actual.equals(expected)) {
            throw new AssertionError(message);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
