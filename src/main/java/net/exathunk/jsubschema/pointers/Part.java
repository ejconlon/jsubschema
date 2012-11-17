package net.exathunk.jsubschema.pointers;

/**
 * charolastra 11/15/12 11:49 AM
 */
public class Part {
    private final String key;
    private final Integer index;

    private Part(String key, Integer index) {
        this.key = key;
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public Integer getIndex() {
        return index;
    }

    public boolean hasKey() {
        return key != null;
    }

    public boolean hasIndex() {
        return index != null;
    }

    public static Part asKey(String key) {
        return new Part(key, null);
    }

    public static Part asIndex(Integer index) {
        return new Part(null, index);
    }


    @Override
    public String toString() {
        return "Part{" +
                ((key != null) ? "key='" + key + '\'' : "") +
                ((index != null) ? "index='" + index + '\'' : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part)) return false;

        Part part = (Part) o;

        if (index != null ? !index.equals(part.index) : part.index != null) return false;
        if (key != null ? !key.equals(part.key) : part.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }

    public static Part fromPointerString(String raw) {
        raw = untransform(raw);
        try {
            return Part.asIndex(Integer.parseInt(raw));
        } catch (NumberFormatException nfe) {
            return Part.asKey(raw);
        }
    }

    public String toPointerString() {
        if (key != null) return transform(key);
        else return index.toString();
    }

    private static String transform(String s) {
        return s.replaceAll("~", "~0").replaceAll("/", "~1");
    }

    private static String untransform(String s) {
        return s.replaceAll("~1", "/").replaceAll("~0", "~");
    }
}
