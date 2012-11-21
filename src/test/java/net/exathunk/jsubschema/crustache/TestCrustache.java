package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.TypeException;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * charolastra 11/19/12 11:08 PM
 */
public class TestCrustache {

    private String makeTemplate() {
        return "{{yawn}}{{burp}} Hello, {{person}}! My name is {{me}}. "+
                "{{#secret}}Nice hat, {{dude}}.{{>next}}{{/secret}} {{#secret2}}{{>next2}}{{/secret2}} {{{html}}} And some more {{&more}} {{^bollocks}} Mind them!{{/bollocks}}...";
    }

    @Test
    public void testMatch() {
        List<String> matches = Crustache.contained(makeTemplate());
        assertEquals(Arrays.asList("{{yawn}}", "{{burp}}", "{{person}}", "{{me}}",
                "{{#secret}}", "{{dude}}", "{{>next}}", "{{/secret}}",
                "{{#secret2}}", "{{>next2}}", "{{/secret2}}", "{{{html}}}", "{{&more}}", "{{^bollocks}}", "{{/bollocks}}"), matches);
    }

    @Test
    public void testInline() throws TypeException {
        List<String> parts = Crustache.inline(makeTemplate());
        assertEquals(Arrays.asList("{{yawn}}", "{{burp}}", " Hello, ", "{{person}}", "! My name is ", "{{me}}", ". ",
                "{{#secret}}", "Nice hat, ", "{{dude}}", ".", "{{>next}}", "{{/secret}}", " ", "{{#secret2}}", "{{>next2}}", "{{/secret2}}", " ", "{{{html}}}", " And some more ", "{{&more}}",
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
                Either.<Tag, String>makeFirst(new Tag("next", Tag.Type.PARTIAL)),
                Either.<Tag, String>makeFirst(new Tag("secret", Tag.Type.SECTION_END)),
                Either.<Tag, String>makeSecond(" "),
                Either.<Tag, String>makeFirst(new Tag("secret2", Tag.Type.SECTION_START)),
                Either.<Tag, String>makeFirst(new Tag("next2", Tag.Type.PARTIAL)),
                Either.<Tag, String>makeFirst(new Tag("secret2", Tag.Type.SECTION_END)),
                Either.<Tag, String>makeSecond(" "),
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
            sub.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("next", Tag.Type.PARTIAL)));
            treed.getElements().add(Either3.<Tag, String, Section>makeThird(sub));
        }

        treed.getElements().add(Either3.<Tag, String, Section>makeSecond(" "));

        {
            Section sub = new Section(new Tag("secret2", Tag.Type.SECTION_START));
            sub.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("next2", Tag.Type.PARTIAL)));
            treed.getElements().add(Either3.<Tag, String, Section>makeThird(sub));
        }

        treed.getElements().add(Either3.<Tag, String, Section>makeSecond(" "));
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

        List<Tag> iterated = Arrays.asList(
                (new Tag("yawn", Tag.Type.NORMAL)),
                (new Tag("burp", Tag.Type.NORMAL)),
                (new Tag("person", Tag.Type.NORMAL)),
                (new Tag("me", Tag.Type.NORMAL)),
                (new Tag("secret", Tag.Type.SECTION_START)),
                (new Tag("dude", Tag.Type.NORMAL)),
                (new Tag("next", Tag.Type.PARTIAL)),
                (new Tag("secret", Tag.Type.SECTION_END)),
                (new Tag("secret2", Tag.Type.SECTION_START)),
                (new Tag("next2", Tag.Type.PARTIAL)),
                (new Tag("secret2", Tag.Type.SECTION_END)),
                (new Tag("html", Tag.Type.ESCAPE)),
                (new Tag("more", Tag.Type.ESCAPE)),
                (new Tag("bollocks", Tag.Type.INVERTED_START)),
                (new Tag("bollocks", Tag.Type.SECTION_END))
        );

        assertEquals(iterated, Util.asList(treed.tagIterator()));

        TagTree tagTree = new TagTree();
        tagTree.getChildren().add(new TagTree(new Tag("yawn", Tag.Type.NORMAL)));
        tagTree.getChildren().add(new TagTree(new Tag("burp", Tag.Type.NORMAL)));
        tagTree.getChildren().add(new TagTree(new Tag("person", Tag.Type.NORMAL)));
        tagTree.getChildren().add(new TagTree(new Tag("me", Tag.Type.NORMAL)));

        TagTree sub1 = new TagTree(new Tag("secret", Tag.Type.SECTION_START));
        sub1.getChildren().add(new TagTree(new Tag("dude", Tag.Type.NORMAL)));
        sub1.getChildren().add(new TagTree(new Tag("next", Tag.Type.PARTIAL)));
        tagTree.getChildren().add(sub1);

        TagTree sub2 = new TagTree(new Tag("secret2", Tag.Type.SECTION_START));
        sub2.getChildren().add(new TagTree(new Tag("next2", Tag.Type.PARTIAL)));
        tagTree.getChildren().add(sub2);

        tagTree.getChildren().add(new TagTree(new Tag("html", Tag.Type.ESCAPE)));
        tagTree.getChildren().add(new TagTree(new Tag("more", Tag.Type.ESCAPE)));

        TagTree sub3 = new TagTree(new Tag("bollocks", Tag.Type.INVERTED_START));
        tagTree.getChildren().add(sub3);

        assertEquals(tagTree, treed.tagTree());

        NameResolver resolver = new NameResolverImpl("http://example.com/whee");

        SchemaLike schema = TagTyper.makeTreeSchema("woo", tagTree, resolver);

        assertEquals("http://example.com/whee/woo", schema.getId());
        assertEquals("object", schema.getType());
        assertEquals(true, schema.hasProperties());
        assertEquals(false, schema.hasItems());
        assertEquals(Util.asSet("yawn", "burp", "person", "me", "secret", "secret2", "html", "more", "bollocks"), schema.getProperties().keySet());
        for (String k : new String[] {"yawn", "burp", "person", "me", "html", "more"}) {
            assertEquals(k, "string", schema.getProperties().get(k).getType());
        }
        for (String k : new String[] {"secret", "secret2", "bollocks"}) {
            assertEquals(k, "array", schema.getProperties().get(k).getType());
            assertEquals(k, "object", schema.getProperties().get(k).getItems().getType());
        }
        assertEquals("string", schema.getProperties().get("secret").getItems().getProperties().get("dude").getType());
        //System.out.println(Util.quickUnbind(schema));
    }

    @Test
    public void testTagParse() {
        assertEquals(Either.makeFirst(new Tag("yawn", Tag.Type.NORMAL)), Tag.parse("{{yawn}}"));
        assertEquals(Either.makeFirst(new Tag("secret", Tag.Type.SECTION_START)), Tag.parse("{{#secret}}"));
        assertEquals(Either.makeFirst(new Tag("secret", Tag.Type.SECTION_END)), Tag.parse("{{/secret}}"));
        assertEquals(Either.makeFirst(new Tag("html", Tag.Type.ESCAPE)), Tag.parse("{{{html}}}"));
        assertEquals(Either.makeFirst(new Tag("more", Tag.Type.ESCAPE)), Tag.parse("{{&more}}"));
        assertEquals(Either.makeFirst(new Tag("next", Tag.Type.PARTIAL)), Tag.parse("{{>next}}"));
    }

    @Test
    public void testFilters() {
        assertEquals(Either.makeFirst(new Tag("foo", Tag.Type.NORMAL, Arrays.asList("bar", "baz"))), Tag.parse("{{foo|bar|baz}}"));
        assertEquals(Either.makeFirst(new Tag("foo", Tag.Type.ESCAPE, Arrays.asList("bar", "baz"))), Tag.parse("{{{foo|bar|baz}}}"));
        assertEquals(Either.makeFirst(new Tag("foo", Tag.Type.INVERTED_START, Arrays.asList("bar", "baz"))), Tag.parse("{{^foo|bar|baz}}"));
        assertEquals(Either.makeFirst(new Tag("foo", Tag.Type.NORMAL, Arrays.asList("a:1", "b:2"))), Tag.parse("{{foo|a:1|b:2}}"));
        Map<String, String> m = TagTyper.convertKV(Tag.parse("{{foo|a:1|notkv|b:2}}").getFirst().getFilters());
        assertEquals("1", m.get("a"));
        assertEquals("2", m.get("b"));
        assertEquals(2, m.size());
    }
}
