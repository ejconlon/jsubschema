package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class ProptreeFactory implements DomainFactory<Proptree> {

    @Override
    public Class<Proptree> getDomainClass() {
        return Proptree.class;
    }

    @Override
    public Proptree makeDomain() {
        return new Proptree();
    }

}
