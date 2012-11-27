package net.exathunk.jsubschema.genschema.card.declarations.propertiesemail;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class PropertiesEmailFactory implements DomainFactory<PropertiesEmail> {

    @Override
    public Class<PropertiesEmail> getDomainClass() {
        return PropertiesEmail.class;
    }

    @Override
    public PropertiesEmail makeDomain() {
        return new PropertiesEmail();
    }

}
