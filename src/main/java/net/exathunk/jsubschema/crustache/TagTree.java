package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/20/12 1:26 PM
 */
public class TagTree {
    private final Maybe<Tag> parent;
    private final List<TagTree> children;

    public TagTree() {
        this(Maybe.<Tag>nothing(), new ArrayList<TagTree>());
    }

    public TagTree(Tag tag) {
        this(Maybe.just(tag), new ArrayList<TagTree>());
    }

    private TagTree(Maybe<Tag> parent, List<TagTree> children) {
        this.parent = parent;
        this.children = children;
        assert parent != null;
        assert children != null;
    }

    public Maybe<Tag> getParent() {
        return parent;
    }

    public List<TagTree> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "TagTree{" +
                "parent=" + parent +
                ", children=" + children +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagTree)) return false;

        TagTree tagTree = (TagTree) o;

        if (!children.equals(tagTree.children)) return false;
        if (!parent.equals(tagTree.parent)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }
}
