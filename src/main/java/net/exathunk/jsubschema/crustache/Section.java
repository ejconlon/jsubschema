package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.functional.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/20/12 12:06 AM
 */
public class Section {
    private final Maybe<Tag> tag;
    private final List<Either3<Tag, String, Section>> elements;

    public Section() {
        this(Maybe.<Tag>nothing(), new ArrayList<Either3<Tag, String, Section>>());
    }

    public Section(Tag tag) {
        this(Maybe.just(tag), new ArrayList<Either3<Tag, String, Section>>());
    }

    private Section(Maybe<Tag> tag, List<Either3<Tag, String, Section>> elements) {
        this.tag = tag;
        this.elements = elements;
        assert tag != null;
        assert elements != null;
    }

    public Maybe<Tag> getTag() {
        return tag;
    }

    public List<Either3<Tag, String, Section>> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "Section{" +
                "tag=" + tag +
                ", elements=" + elements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;

        Section section = (Section) o;

        if (!elements.equals(section.elements)) return false;
        if (!tag.equals(section.tag)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tag.hashCode();
        result = 31 * result + elements.hashCode();
        return result;
    }
}
