package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class LinkFactory implements DomainFactory<LinkFactory> {


    public Class<LinkFactory> getDomainClass() { return LinkFactory.class; }
    public LinkFactory makeDomain() { return new LinkFactory(); }

}
