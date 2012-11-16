package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class GeoFactory implements DomainFactory<Geo> {


    @Override
public Class<Geo> getDomainClass() { return Geo.class; }
    @Override
public Geo makeDomain() { return new Geo(); }

}
