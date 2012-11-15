package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class AddressFactory implements DomainFactory<AddressFactory> {


    public Class<AddressFactory> getDomainClass() { return AddressFactory.class; }
    public AddressFactory makeDomain() { return new AddressFactory(); }

}
