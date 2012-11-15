package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class AddressFactory implements DomainFactory<Address> {


    public Class<Address> getDomainClass() { return Address.class; }
    public Address makeDomain() { return new Address(); }

}
