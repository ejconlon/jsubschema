package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class LinkFactory implements DomainFactory<Link> {


    public Class<Link> getDomainClass() { return Link.class; }
    public Link makeDomain() { return new Link(); }

}
