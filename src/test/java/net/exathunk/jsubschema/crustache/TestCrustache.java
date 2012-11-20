package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * charolastra 11/19/12 11:08 PM
 */
public class TestCrustache {

    private String makeTemplate() {
        return "{{yawn}}{{burp}} Hello, {{person}}! My name is {{me}}. "+
                "{{#secret}}Nice hat, {{dude}}.{{/secret}}  {{{html}}} And some more {{&more}} {{^bollocks}} Mind them!{{/bollocks}}...";
    }

    @Test
    public void testMatch() {
        List<String> matches = Crustache.contained(makeTemplate());
        assertEquals(Arrays.asList("{{yawn}}", "{{burp}}", "{{person}}", "{{me}}",
                "{{#secret}}", "{{dude}}", "{{/secret}}", "{{{html}}}", "{{&more}}", "{{^bollocks}}", "{{/bollocks}}"), matches);
    }

    @Test
    public void testInline() {
        List<String> parts = Crustache.inline(makeTemplate());
        assertEquals(Arrays.asList("{{yawn}}", "{{burp}}", " Hello, ", "{{person}}", "! My name is ", "{{me}}", ". ",
                "{{#secret}}", "Nice hat, ", "{{dude}}", ".", "{{/secret}}", "  ", "{{{html}}}", " And some more ", "{{&more}}",
                " ", "{{^bollocks}}", " Mind them!", "{{/bollocks}}", "..."), parts);
        String s = "";
        for (String part : parts) {
            s += part;
        }
        assertEquals(makeTemplate(), s);

        List<Either<Tag, String>> parsed = Arrays.asList(
                Either.<Tag, String>makeFirst(new Tag("yawn", Tag.Type.NORMAL)),
                Either.<Tag, String>makeFirst(new Tag("burp", Tag.Type.NORMAL)),
                Either.<Tag, String>makeSecond(" Hello, "),
                Either.<Tag, String>makeFirst(new Tag("person", Tag.Type.NORMAL)),
                Either.<Tag, String>makeSecond("! My name is "),
                Either.<Tag, String>makeFirst(new Tag("me", Tag.Type.NORMAL)),
                Either.<Tag, String>makeSecond(". "),
                Either.<Tag, String>makeFirst(new Tag("secret", Tag.Type.SECTION_START)),
                Either.<Tag, String>makeSecond("Nice hat, "),
                Either.<Tag, String>makeFirst(new Tag("dude", Tag.Type.NORMAL)),
                Either.<Tag, String>makeSecond("."),
                Either.<Tag, String>makeFirst(new Tag("secret", Tag.Type.SECTION_END)),
                Either.<Tag, String>makeSecond("  "),
                Either.<Tag, String>makeFirst(new Tag("html", Tag.Type.ESCAPE)),
                Either.<Tag, String>makeSecond(" And some more "),
                Either.<Tag, String>makeFirst(new Tag("more", Tag.Type.ESCAPE)),
                Either.<Tag, String>makeSecond(" "),
                Either.<Tag, String>makeFirst(new Tag("bollocks", Tag.Type.INVERTED_START)),
                Either.<Tag, String>makeSecond(" Mind them!"),
                Either.<Tag, String>makeFirst(new Tag("bollocks", Tag.Type.SECTION_END)),
                Either.<Tag, String>makeSecond("...")
        );
        assertEquals(parsed, Crustache.parsed(parts));

        Section treed = new Section();
        treed.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("yawn", Tag.Type.NORMAL)));
        treed.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("burp", Tag.Type.NORMAL)));
        treed.getElements().add(Either3.<Tag, String, Section>makeSecond(" Hello, "));
        treed.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("person", Tag.Type.NORMAL)));
        treed.getElements().add(Either3.<Tag, String, Section>makeSecond("! My name is "));
        treed.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("me", Tag.Type.NORMAL)));
        treed.getElements().add(Either3.<Tag, String, Section>makeSecond(". "));

        {
            Section sub = new Section(new Tag("secret", Tag.Type.SECTION_START));
            sub.getElements().add(Either3.<Tag, String, Section>makeSecond("Nice hat, "));
            sub.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("dude", Tag.Type.NORMAL)));
            sub.getElements().add(Either3.<Tag, String, Section>makeSecond("."));
            treed.getElements().add(Either3.<Tag, String, Section>makeThird(sub));
        }

        treed.getElements().add(Either3.<Tag, String, Section>makeSecond("  "));
        treed.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("html", Tag.Type.ESCAPE)));
        treed.getElements().add(Either3.<Tag, String, Section>makeSecond(" And some more "));
        treed.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("more", Tag.Type.ESCAPE)));
        treed.getElements().add(Either3.<Tag, String, Section>makeSecond(" "));

        {
            Section sub = new Section(new Tag("bollocks", Tag.Type.INVERTED_START));
            sub.getElements().add(Either3.<Tag, String, Section>makeSecond(" Mind them!"));
            treed.getElements().add(Either3.<Tag, String, Section>makeThird(sub));
        }

        treed.getElements().add(Either3.<Tag, String, Section>makeSecond("..."));
        
        assertEquals(treed, Crustache.treed(parsed));
    }

    @Test
    public void testSite() {
        assertEquals(Either.makeFirst(new Tag("yawn", Tag.Type.NORMAL)), Tag.parse("{{yawn}}"));
        assertEquals(Either.makeFirst(new Tag("secret", Tag.Type.SECTION_START)), Tag.parse("{{#secret}}"));
        assertEquals(Either.makeFirst(new Tag("secret", Tag.Type.SECTION_END)), Tag.parse("{{/secret}}"));
        assertEquals(Either.makeFirst(new Tag("html", Tag.Type.ESCAPE)), Tag.parse("{{{html}}}"));
        assertEquals(Either.makeFirst(new Tag("more", Tag.Type.ESCAPE)), Tag.parse("{{&more}}"));
    }
}
