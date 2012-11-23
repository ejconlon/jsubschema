package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.FullRefResolver;
import net.exathunk.jsubschema.base.MetaResolver;
import net.exathunk.jsubschema.base.SelfResolver;
import net.exathunk.jsubschema.base.TypeException;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
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
                "{{#secret}}Nice hat, {{dude}}.{{>next}}{{/secret}} {{#secret2}}{{>next2}}{{/secret2}} "+
                "{{{html}}} And some more {{&more}} {{^bollocks}} Mind them!{{/bollocks}}..."+
                "{{#empty}}{{/empty}}{{#single}}{{subSingle}}{{/single}}";
    }

    @Test
    public void testMatch() {
        List<String> matches = Crustache.contained(makeTemplate());
        assertEquals(Arrays.asList("{{yawn}}", "{{burp}}", "{{person}}", "{{me}}",
                "{{#secret}}", "{{dude}}", "{{>next}}", "{{/secret}}",
                "{{#secret2}}", "{{>next2}}", "{{/secret2}}", "{{{html}}}", "{{&more}}", "{{^bollocks}}", "{{/bollocks}}",
                "{{#empty}}", "{{/empty}}", "{{#single}}", "{{subSingle}}", "{{/single}}"), matches);
    }

    @Test
    public void testInline() throws TypeException {
        List<String> parts = Crustache.inline(makeTemplate());
        assertEquals(Arrays.asList("{{yawn}}", "{{burp}}", " Hello, ", "{{person}}", "! My name is ", "{{me}}", ". ",
                "{{#secret}}", "Nice hat, ", "{{dude}}", ".", "{{>next}}", "{{/secret}}", " ", "{{#secret2}}", "{{>next2}}", "{{/secret2}}", " ", "{{{html}}}", " And some more ", "{{&more}}",
                " ", "{{^bollocks}}", " Mind them!", "{{/bollocks}}", "...",
                "{{#empty}}", "{{/empty}}", "{{#single}}", "{{subSingle}}", "{{/single}}"), parts);
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
                Either.<Tag, String>makeSecond("..."),
                Either.<Tag, String>makeFirst(new Tag("empty", Tag.Type.SECTION_START)),
                Either.<Tag, String>makeFirst(new Tag("empty", Tag.Type.SECTION_END)),
                Either.<Tag, String>makeFirst(new Tag("single", Tag.Type.SECTION_START)),
                Either.<Tag, String>makeFirst(new Tag("subSingle", Tag.Type.NORMAL)),
                Either.<Tag, String>makeFirst(new Tag("single", Tag.Type.SECTION_END))
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

        {
            Section sub = new Section(new Tag("empty", Tag.Type.SECTION_START));
            treed.getElements().add(Either3.<Tag, String, Section>makeThird(sub));
        }

        {
            Section sub = new Section(new Tag("single", Tag.Type.SECTION_START));
            sub.getElements().add(Either3.<Tag, String, Section>makeFirst(new Tag("subSingle", Tag.Type.NORMAL)));
            treed.getElements().add(Either3.<Tag, String, Section>makeThird(sub));
        }
        
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
                (new Tag("bollocks", Tag.Type.SECTION_END)),
                (new Tag("empty", Tag.Type.SECTION_START)),
                (new Tag("empty", Tag.Type.SECTION_END)),
                (new Tag("single", Tag.Type.SECTION_START)),
                (new Tag("subSingle", Tag.Type.NORMAL)),
                (new Tag("single", Tag.Type.SECTION_END))
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

        TagTree sub4 = new TagTree(new Tag("empty", Tag.Type.SECTION_START));
        tagTree.getChildren().add(sub4);

        TagTree sub5 = new TagTree(new Tag("single", Tag.Type.SECTION_START));
        sub5.getChildren().add(new TagTree(new Tag("subSingle", Tag.Type.NORMAL)));
        tagTree.getChildren().add(sub5);

        assertEquals(tagTree, treed.tagTree());

        NameResolver resolver = new NameResolverImpl("http://example.com/whee");

        SchemaLike schema = TagTyper.makeTreeSchema("woo", tagTree, resolver);
        //System.out.println(Util.quickUnbind(schema));

        assertEquals("http://example.com/whee/woo", schema.getId());
        assertEquals("object", schema.getType());
        assertEquals(true, schema.hasProperties());
        assertEquals(false, schema.hasItems());
        assertEquals(Util.asSet("yawn", "burp", "person", "me", "secret", "secret2", "html", "more", "bollocks", "empty", "single"), schema.getProperties().keySet());
        for (String k : new String[] {"yawn", "burp", "person", "me", "html", "more"}) {
            assertEquals(k, "string", schema.getProperties().get(k).getType());
        }
        for (String k : new String[] {"secret", "secret2", "single"}) {
            assertEquals(k, "array", schema.getProperties().get(k).getType());
            assertEquals(k, "object", schema.getProperties().get(k).getItems().getType());
        }
        for (String k : new String[] {"empty", "bollocks"}) {
            assertEquals(k, "array", schema.getProperties().get(k).getType());
            assertEquals(k, false, schema.getProperties().get(k).hasItems());
            assertEquals(k, false, schema.getProperties().get(k).hasProperties());
        }
        assertEquals("string", schema.getProperties().get("secret").getItems().getProperties().get("dude").getType());
        assertEquals(Util.asList("http://example.com/whee/next"), schema.getProperties().get("secret").getItems().getExtensions());
        assertEquals(false, schema.getProperties().get("secret").getItems().has__dollar__ref());
        assertEquals("http://example.com/whee/next2", schema.getProperties().get("secret2").getItems().get__dollar__ref());
        assertEquals(false, schema.getProperties().get("secret2").getItems().hasExtensions());
        assertEquals(false, schema.getProperties().get("single").getItems().has__dollar__ref());
        assertEquals(false, schema.getProperties().get("single").getItems().hasExtensions());

        assertEquals(Util.asSet("subSingle"), schema.getProperties().get("single").getItems().getProperties().keySet());
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

    private static SchemaLike makeSchema(String s) throws IOException, TypeException {
        return Util.quickBind(Util.parse(s), new SchemaFactory());
    }

    private static List<String> satisfyErrors(SchemaLike schema, TagTree tagTree) {
        FullRefResolver refResolver = new MetaResolver(new SelfResolver(schema));
        return TagTyper.satisfyErrors(schema, tagTree, refResolver);
    }

    @Test
    public void testSatisfies() throws IOException, TypeException {
        TagTree tagTree = Crustache.treed(Crustache.parsed(Crustache.inline(makeTemplate()))).tagTree();
        TagTree yawnTagTree = tagTree.getChildren().get(0);
        assertEquals("yawn", yawnTagTree.getParent().getJust().getLabel());
        TagTree secretTagTree = tagTree.getChildren().get(4);
        assertEquals("secret", secretTagTree.getParent().getJust().getLabel());
        TagTree secret2TagTree = tagTree.getChildren().get(5);
        assertEquals("secret2", secret2TagTree.getParent().getJust().getLabel());
        TagTree moreTagTree = tagTree.getChildren().get(7);
        assertEquals("more", moreTagTree.getParent().getJust().getLabel());
        TagTree bollocksTagTree = tagTree.getChildren().get(8);
        assertEquals("bollocks", bollocksTagTree.getParent().getJust().getLabel());
        TagTree emptyTagTree = tagTree.getChildren().get(9);
        assertEquals("empty", emptyTagTree.getParent().getJust().getLabel());
        TagTree singleTagTree = tagTree.getChildren().get(10);
        assertEquals("single", singleTagTree.getParent().getJust().getLabel());

        final List<String> empty = new ArrayList<String>();

        // with ref!
        assertEquals(empty, satisfyErrors(makeSchema("{ \"declarations\" : {\"strDecl\" : { \"type\": \"string\" }}, \"$ref\":\"#/declarations/strDecl\" }"), yawnTagTree));

        // NORMAL node accepts scalar types
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"string\" }"), yawnTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"boolean\" }"), yawnTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"integer\" }"), yawnTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"number\" }"), yawnTagTree));
        assertEquals(Util.asList("yawn: expected schema with scalar type, found: object"), satisfyErrors(makeSchema("{ \"type\": \"object\" }"), yawnTagTree));
        assertEquals(Util.asList("yawn: expected schema with scalar type, found: array"), satisfyErrors(makeSchema("{ \"type\": \"array\" }"), yawnTagTree));

        // ESCAPE node accepts scalar types
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"string\" }"), moreTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"boolean\" }"), moreTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"integer\" }"), moreTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"number\" }"), moreTagTree));
        assertEquals(Util.asList("more: expected schema with scalar type, found: object"), satisfyErrors(makeSchema("{ \"type\": \"object\" }"), moreTagTree));
        assertEquals(Util.asList("more: expected schema with scalar type, found: array"), satisfyErrors(makeSchema("{ \"type\": \"array\" }"), moreTagTree));
        
        // INVERTED_START node accepts all types
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"string\" }"), bollocksTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"boolean\" }"), bollocksTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"integer\" }"), bollocksTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"number\" }"), bollocksTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"object\" }"), bollocksTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"array\" }"), bollocksTagTree));

        // SECTION_START accepts object or array, unless empty!
        // empty
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"string\" }"), emptyTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"boolean\" }"), emptyTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"integer\" }"), emptyTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"number\" }"), emptyTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"object\" }"), emptyTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"array\" }"), emptyTagTree));

        // single, valid obj schema
        assertEquals(Util.asList("single: expected container type, found: string"), satisfyErrors(makeSchema("{ \"type\": \"string\" }"), singleTagTree));
        assertEquals(Util.asList("single: expected container type, found: boolean"), satisfyErrors(makeSchema("{ \"type\": \"boolean\" }"), singleTagTree));
        assertEquals(Util.asList("single: expected container type, found: integer"), satisfyErrors(makeSchema("{ \"type\": \"integer\" }"), singleTagTree));
        assertEquals(Util.asList("single: expected container type, found: number"), satisfyErrors(makeSchema("{ \"type\": \"number\" }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"subSingle\" : { \"type\" : \"string\" } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"subSingle\" : {\"type\" : \"string\"} } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"subSingle\" : { \"type\" : \"boolean\" } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"subSingle\" : {\"type\" : \"boolean\"} } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"subSingle\" : { \"type\" : \"integer\" } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"subSingle\" : {\"type\" : \"integer\"} } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"subSingle\" : { \"type\" : \"number\" } } }"), singleTagTree));
        assertEquals(empty, satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"subSingle\" : {\"type\" : \"number\"} } } }"), singleTagTree));

        // invalid object schema or invalid type
        assertEquals(Util.asList("single: missing key: subSingle"), satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"NOT_IT\" : { \"type\" : \"string\" } } }"), singleTagTree));
        assertEquals(Util.asList("single: missing key: subSingle"), satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"NOT_IT\" : {\"type\" : \"string\"} } } }"), singleTagTree));
        assertEquals(Util.asList("subSingle: expected schema with scalar type, found: object"), satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"subSingle\" : { \"type\" : \"object\" } } }"), singleTagTree));
        assertEquals(Util.asList("subSingle: expected schema with scalar type, found: array"), satisfyErrors(makeSchema("{ \"type\": \"object\", \"properties\" : { \"subSingle\" : { \"type\" : \"array\" } } }"), singleTagTree));
        assertEquals(Util.asList("subSingle: expected schema with scalar type, found: object"), satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"subSingle\" : {\"type\" : \"object\"} } } }"), singleTagTree));
        assertEquals(Util.asList("subSingle: expected schema with scalar type, found: array"), satisfyErrors(makeSchema("{ \"type\": \"array\", \"items\" : {\"type\":\"object\", \"properties\" : { \"subSingle\" : {\"type\" : \"array\"} } } }"), singleTagTree));

        // partials
        //assertEquals(empty, TagTyper.satisfyErrors(makeSchema("{\"type\":\"object\", \"extensions\":[\"http://example.com/whee/next\"]}"), secretTagTree));
        //assertEquals(empty, satisfyErrors(makeSchema("{\"$ref\":\"http://example.com/whee/next2\"}"), secret2TagTree));

        //NameResolver resolver = new NameResolverImpl("http://example.com/whee");
        //SchemaLike schema = TagTyper.makeTreeSchema("woo", tagTree, resolver);
        //assertEquals(empty, TagTyper.satisfyErrors(schema, tagTree));
    }
}
