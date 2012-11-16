package net.exathunk.jsubschema.genschema;
import net.exathunk.jsubschema.gendeps.DomainFactory;
public class AddressFactory implements DomainFactory<Address> {
    @Override
    public Class<Address> getDomainClass() {        return Address.class;
}
    @Override
    public Address makeDomain() {        return new Address();
}
}
