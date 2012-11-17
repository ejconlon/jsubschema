package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Pointer;
import net.exathunk.jsubschema.pointers.Reference;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * charolastra 11/16/12 8:40 PM
 */
public class TestReferences {

    private static Map<String, Either<Reference, String>> makeCases() {
        Map<String, Either<Reference, String>> cs = new HashMap<String, Either<Reference, String>>();
        cs.put("http://example.com#/foo/0/bar", Either.<Reference, String>makeFirst(
                new Reference("http://example.com", new Pointer().cons(Part.asKey("foo")).cons(Part.asIndex(0)).cons(Part.asKey("bar")))));
        cs.put("a#/b", Either.<Reference, String>makeFirst(
                new Reference("a", new Pointer().cons(Part.asKey("b")))));
        cs.put("a#b", Either.<Reference, String>makeSecond(
                "Invalid pointer: b"));
        cs.put("a/b", Either.<Reference, String>makeFirst(
                new Reference("a/b", new Pointer())));
        cs.put("/a/b", Either.<Reference, String>makeFirst(
                new Reference("", new Pointer().cons(Part.asKey("a")).cons(Part.asKey("b")))));
        cs.put("#/a/b", Either.<Reference, String>makeFirst(
                new Reference("", new Pointer().cons(Part.asKey("a")).cons(Part.asKey("b")))));
        cs.put("", Either.<Reference, String>makeFirst(new Reference("", new Pointer())));
        cs.put(null, Either.<Reference, String>makeSecond("Null reference"));
        return cs;
    }

    @Test
    public void testReferenceParsing() {
        Map<String, Either<Reference, String>> cs = makeCases();
        for (Map.Entry<String, Either<Reference, String>> entry : cs.entrySet()) {
            final String refString = entry.getKey();
            final Either<Reference, String> expectedEitherReference = entry.getValue();
            assertEquals(refString, expectedEitherReference, Reference.fromReferenceString(refString));
        }
    }

}
