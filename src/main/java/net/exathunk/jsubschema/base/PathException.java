package net.exathunk.jsubschema.base;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class PathException extends Exception {
    public PathException(String message) {
        super(message);
    }

    public PathException(Exception cause) {
        super(cause);
    }

    public PathException(String message, Exception cause) {
        super(message, cause);
    }

    public static void assertThat(boolean condition, String message) throws PathException {
        if (!condition) {
            throw new PathException(message);
        }
    }
}
