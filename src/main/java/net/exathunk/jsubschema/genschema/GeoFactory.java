package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class GeoFactory implements DomainFactory<Geo> {


    public Class<Geo> getDomainClass() { return Geo.class; }
    public Geo makeDomain() { return new Geo(); }

}
