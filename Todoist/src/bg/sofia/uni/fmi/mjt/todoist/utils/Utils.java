package bg.sofia.uni.fmi.mjt.todoist.utils;

public class Utils {

    public static void assertNonNull(Object object, String paramName) {
        if (object == null) {
            throw new IllegalArgumentException(paramName + " should not be null");
        }
    }
}
