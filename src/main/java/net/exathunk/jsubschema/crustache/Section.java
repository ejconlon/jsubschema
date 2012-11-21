package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.functional.Maybe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    public boolean hasParentTag() {
        return tag.isJust();
    }

    public boolean hasChildTags() {
        for (Either3<Tag, String, Section> e : getElements()) {
            if (e.isFirst()) return true;
        }
        return false;
    }

    public Iterator<Tag> tagIterator() {
        return new TagIterator(this);
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

    private static class TagIterator implements Iterator<Tag> {

        private final Iterator<Either3<Tag, String, Section>> it;
        private Tag openTag;
        private Tag nextTag;
        private Iterator<Tag> nextTagIt;

        public TagIterator(Section section) {
            if (section.getTag().isJust()) {
                openTag = section.getTag().getJust();
                nextTag = openTag;
            }
            it = section.getElements().iterator();
        }

        private void advance() {
            while (!hasNextInner() && it.hasNext()) {
                Either3<Tag, String, Section> e = it.next();
                if (e.isFirst()) {
                    nextTag = e.getFirst();
                } else if (e.isThird()) {
                    nextTagIt = e.getThird().tagIterator();
                }
            }
        }

        private boolean hasNextInner() {
            return nextTag != null || (nextTagIt != null && nextTagIt.hasNext());
        }

        @Override
        public boolean hasNext() {
            advance();
            return hasNextInner() || openTag != null;
        }

        @Override
        public Tag next() {
            advance();
            if (nextTag != null) {
                Tag ret = nextTag;
                nextTag = null;
                return ret;
            } else if (nextTagIt != null && nextTagIt.hasNext()) {
                return nextTagIt.next();
            } else if (openTag != null) {
                Tag ret = new Tag(openTag.getLabel(), Tag.Type.SECTION_END);
                openTag = null;
                return ret;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public TagTree tagTree() {
        return tagTreeInner(tagIterator(), new TagTree());
    }

    private TagTree tagTreeInner(Iterator<Tag> it, TagTree current) {
        while (it.hasNext()) {
            Tag t = it.next();
            if (t.getType().equals(Tag.Type.SECTION_START) || t.getType().equals(Tag.Type.INVERTED_START)) {
                TagTree next = tagTreeInner(it, new TagTree(t));
                current.getChildren().add(next);
            } else if (t.getType().equals(Tag.Type.SECTION_END)) {
                return current;
            } else {
                current.getChildren().add(new TagTree(t));
            }
        }
        return current;
    }
}
