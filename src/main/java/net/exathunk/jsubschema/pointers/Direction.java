package net.exathunk.jsubschema.pointers;

/**
 *
 * DOWN <==> /foo/0 <==> (cons 0 (cons "foo" nil)) <==> points to 1 in { "foo": [1,2,3] }
 * UP <==> /0/foo <==> (cons "foo" (cons 0 nil)) <==> points to [1,2,3] in [{"foo": [1,2,3]}, ...]
 *
 * charolastra 11/17/12 4:10 AM
 */
public enum Direction {
    UP, DOWN;

    public Direction reversed() {
        if (UP.equals(this)) {
            return DOWN;
        } else {
            return UP;
        }
    }
}
