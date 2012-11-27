package net.exathunk.jsubschema.genschema.card.declarations.propertiestel;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class PropertiesTelFactory implements DomainFactory<PropertiesTel> {

    @Override
    public Class<PropertiesTel> getDomainClass() {
        return PropertiesTel.class;
    }

    @Override
    public PropertiesTel makeDomain() {
        return new PropertiesTel();
    }

}
