package net.exathunk.jsubschema.genschema.stringmultimap;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class StringmultimapFactory implements DomainFactory<Stringmultimap> {

    @Override
    public Class<Stringmultimap> getDomainClass() {
        return Stringmultimap.class;
    }

    @Override
    public Stringmultimap makeDomain() {
        return new Stringmultimap();
    }

}
