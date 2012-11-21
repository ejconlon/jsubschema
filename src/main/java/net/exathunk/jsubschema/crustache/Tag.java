package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.functional.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * charolastra 11/19/12 11:18 PM
 */
public class Tag {

    public static enum Type { NORMAL, SECTION_START, SECTION_END, INVERTED_START, ESCAPE, PARTIAL }

    private final String label;
    private final Type type;
    private final List<String> filters;

    public Tag(String label, Type type) {
        this(label, type, new ArrayList<String>());
    }

    public Tag(String label, Type type, List<String> filters) {
        this.label = label;
        this.type = type;
        this.filters = filters;
        assert label != null;
        assert type != null;
        assert filters != null;
    }

    public final String getLabel() {
        return label;
    }

    public final Type getType() {
        return type;
    }

    public List<String> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "label='" + label + '\'' +
                ", type=" + type +
                ", filters='" + filters + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        if (!filters.equals(tag.filters)) return false;
        if (!label.equals(tag.label)) return false;
        if (type != tag.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + filters.hashCode();
        return result;
    }

    public static Either<Tag, String> parse(final String tag) {
        final String inner;
        final boolean isEscape;
        final boolean isOpen;
        final boolean isClose;
        final boolean isInverted;
        final boolean isPartial;
        Matcher doubleMatches = Crustache.DOUBLE_COMPILED.matcher(tag);
        Matcher tripleMatches = Crustache.TRIPLE_COMPILED.matcher(tag);
        if (doubleMatches.find()) {
            if (doubleMatches.start() == 0 && doubleMatches.end() == tag.length()) {
              String t = tag.substring(2, tag.length()-2);
              isEscape = (t.charAt(0) == '&');
              isOpen = (t.charAt(0) == '#');
              isClose = (t.charAt(0) == '/');
              isInverted = (t.charAt(0) == '^');
              isPartial = (t.charAt(0) == '>');
              if (isEscape || isOpen || isClose || isInverted || isPartial) {
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
                if (Util.asSet('&', '#', '/', '^', '>').contains(inner.charAt(0))) {
                    throw new IllegalArgumentException("Bad tag: "+tag);
                }
                isOpen = isClose = isInverted = isPartial = false;
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
        else if (isPartial) type = Type.PARTIAL;
        else type = Type.NORMAL;

        final String label;
        final List<String> filters = new ArrayList<String>();
        String[] parts = inner.split("\\|");
        label = parts[0];
        for (int i = 1; i < parts.length; ++i) filters.add(parts[i]);
        return Either.makeFirst(new Tag(label, type, filters));
    }
}
