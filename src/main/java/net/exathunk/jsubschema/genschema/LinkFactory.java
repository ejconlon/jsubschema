package net.exathunk.jsubschema.genschema;
import net.exathunk.jsubschema.gendeps.DomainFactory;
public class LinkFactory implements DomainFactory<Link> {
    @Override
    public Class<Link> getDomainClass() {        return Link.class;
}
    @Override
    public Link makeDomain() {        return new Link();
}
}
