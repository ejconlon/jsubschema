package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Either;

import java.util.regex.Matcher;

/**
 * charolastra 11/19/12 11:18 PM
 */
public class Tag {

    public static enum Type { NORMAL, SECTION_START, SECTION_END, INVERTED_START, ESCAPE }

    private final String label;
    private final Type type;

    public Tag(String label, Type type) {
        this.label = label;
        this.type = type;
        assert label != null;
        assert type != null;
    }

    public final String getLabel() {
        return label;
    }

    public final Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Site{" +
                "label='" + label + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        if (!label.equals(tag.label)) return false;
        if (type != tag.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public static Either<Tag, String> parse(final String tag) {
        final String inner;
        final boolean isEscape;
        final boolean isOpen;
        final boolean isClose;
        final boolean isInverted;
        Matcher doubleMatches = Crustache.DOUBLE_COMPILED.matcher(tag);
        Matcher tripleMatches = Crustache.TRIPLE_COMPILED.matcher(tag);
        if (doubleMatches.find()) {
            if (doubleMatches.start() == 0 && doubleMatches.end() == tag.length()) {
              String t = tag.substring(2, tag.length()-2);
              isEscape = (t.charAt(0) == '&');
              isOpen = (t.charAt(0) == '#');
              isClose = (t.charAt(0) == '/');
              isInverted = (t.charAt(0) == '^');
              if (isEscape || isOpen || isClose || isInverted) {
                  inner = t.substring(1);
              } else {
                  inner = t;
              }
            } else {
                return Either.makeSecond(tag);
            }
        } else if (tripleMatches.find()) {
            if (tripleMatches.start() == 0 && tripleMatches.end() == tag.length()) {
                inner = tag.substring(3, tag.length()-3);
                isEscape = true;
                isOpen = isClose = isInverted = false;
            } else {
                return Either.makeSecond(tag);
            }
        } else {
            return Either.makeSecond(tag);
        }
        final Type type;
        if (isEscape) type = Type.ESCAPE;
        else if (isOpen) type = Type.SECTION_START;
        else if (isClose) type = Type.SECTION_END;
        else if (isInverted) type = Type.INVERTED_START;
        else type = Type.NORMAL;
        return Either.makeFirst(new Tag(inner, type));
    }
}
