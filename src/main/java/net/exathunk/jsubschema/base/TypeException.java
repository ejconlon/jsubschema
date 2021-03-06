package net.exathunk.jsubschema.base;

/**
 * charolastra 11/13/12 10:39 PM
 */
public class TypeException extends Exception {
    public TypeException(String message) {
        super(message);
    }

    public TypeException(Exception cause) {
        super(cause);
    }

    public TypeException(String message, Exception cause) {
        super(message, cause);
    }

    public static void assertThat(boolean condition, String message) throws TypeException {
        if (!condition) {
            throw new TypeException(message);
        }
    }
}
