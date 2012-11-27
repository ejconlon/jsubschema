package net.exathunk.jsubschema.genschema.card.declarations.propertiesorg;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class PropertiesOrgFactory implements DomainFactory<PropertiesOrg> {

    @Override
    public Class<PropertiesOrg> getDomainClass() {
        return PropertiesOrg.class;
    }

    @Override
    public PropertiesOrg makeDomain() {
        return new PropertiesOrg();
    }

}
