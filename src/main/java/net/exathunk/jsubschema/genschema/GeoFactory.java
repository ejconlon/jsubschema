package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class GeoFactory implements DomainFactory<GeoFactory> {


    public Class<GeoFactory> getDomainClass() { return GeoFactory.class; }
    public GeoFactory makeDomain() { return new GeoFactory(); }

}
