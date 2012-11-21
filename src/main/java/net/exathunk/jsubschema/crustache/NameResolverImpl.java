package net.exathunk.jsubschema.crustache;

/**
 * charolastra 11/21/12 11:46 AM
 */
public class NameResolverImpl implements NameResolver {

    private final String base;

    public NameResolverImpl(String base) {
        this.base = base;
    }

    @Override
    public String resolveName(String name) {
        return base + "/" + name;
    }
}
