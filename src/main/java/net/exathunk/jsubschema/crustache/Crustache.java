package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.functional.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * charolastra 11/19/12 10:57 PM
 */
public class Crustache {

    public static final String INNER_STRING = "(\\w|[#&^/])?(\\w|[?!\\/.-|])*";
    public static final String DOUBLE_STRING = "(?<!\\{)\\{\\{"+INNER_STRING+"\\}\\}(?!\\})";
    public static final String TRIPLE_STRING = "\\{\\{\\{"+INNER_STRING+"\\}\\}\\}";
    public static final String EITHER_STRING = "("+DOUBLE_STRING+")|("+TRIPLE_STRING+")";
    public static final Pattern DOUBLE_COMPILED = Pattern.compile(DOUBLE_STRING);
    public static final Pattern TRIPLE_COMPILED = Pattern.compile(TRIPLE_STRING);
    public static final Pattern EITHER_COMPILED = Pattern.compile(EITHER_STRING);

    public static List<String> contained(String template) {
        Matcher m = EITHER_COMPILED.matcher(template);
        List<String> l = new ArrayList<String>();
        while (m.find()) {
            l.add(template.substring(m.start(), m.end()));
        }
        return l;
    }

    public static List<String> inline(String template) {
        Matcher m = EITHER_COMPILED.matcher(template);
        List<String> l = new ArrayList<String>();
        int lastStart = 0;
        while (m.find()) {
            if (m.start() > lastStart) {
                l.add(template.substring(lastStart, m.start()));
            }
            l.add(template.substring(m.start(), m.end()));
            lastStart = m.end();
        }
        if (lastStart < template.length()) {
            l.add(template.substring(lastStart, template.length()));
        }
        return l;
    }

    public static List<Either<Tag, String>> parsed(List<String> inlined) {
        List<Either<Tag, String>> l = new ArrayList<Either<Tag, String>>(inlined.size());
        for (String s : inlined) {
            l.add(Tag.parse(s));
        }
        return l;
    }

    public static Section treed(List<Either<Tag, String>> parsed) {
        if (parsed.isEmpty()) return new Section();
        else {
            final Section current;
            final int index;
            if (parsed.get(0).isFirst()) {
                Tag pt = parsed.get(0).getFirst();
                if (pt.getType().equals(Tag.Type.SECTION_START) || pt.getType().equals(Tag.Type.INVERTED_START)) {
                    current = new Section(parsed.get(0).getFirst());
                    index = 1;
                } else {
                    current = new Section();
                    index = 0;
                }
            } else {
                current = new Section();
                index = 0;
            }
            final Pair<Section, Integer> pair = treedInner(current, parsed, index);
            if (pair.getValue().equals(parsed.size())) return pair.getKey();
            else throw new IllegalArgumentException("Bad parse");
        }
    }



    private static Pair<Section, Integer> treedInner(Section current, List<Either<Tag, String>> parsed, int index) {
        final String label;
        if (current.getTag().isJust()) {
            label = current.getTag().getJust().getLabel();
        } else {
            label = null;
        }

        for (; index < parsed.size(); ++index) {
            final Either<Tag, String> p = parsed.get(index);
            if (p.isFirst()) {
                final Tag pt = p.getFirst();
                if (pt.getType().equals(Tag.Type.SECTION_END)) {
                    if (pt.getLabel().equals(label)) {
                        return new Pair<Section, Integer>(current, index);
                    } else {
                        throw new IllegalArgumentException("Invalid section end: "+label+" "+pt.getLabel());
                    }
                } else if (pt.getType().equals(Tag.Type.SECTION_START) || pt.getType().equals(Tag.Type.INVERTED_START)) {
                    Pair<Section, Integer> inner = treedInner(new Section(pt), parsed, index+1);
                    current.getElements().add(Either3.<Tag, String, Section>makeThird(inner.getKey()));
                    index = inner.getValue();
                } else {
                    current.getElements().add(Either3.<Tag, String, Section>makeFirst(pt));
                }
            } else {
                final String ps = p.getSecond();
                current.getElements().add(Either3.<Tag, String, Section>makeSecond(ps));
            }
        }

        return new Pair<Section, Integer>(current, index);
    }
}
