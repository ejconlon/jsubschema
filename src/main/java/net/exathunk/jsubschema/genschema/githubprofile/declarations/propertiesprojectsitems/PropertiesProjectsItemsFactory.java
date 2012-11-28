package net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojectsitems;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class PropertiesProjectsItemsFactory implements DomainFactory<PropertiesProjectsItems> {

    @Override
    public Class<PropertiesProjectsItems> getDomainClass() {
        return PropertiesProjectsItems.class;
    }

    @Override
    public PropertiesProjectsItems makeDomain() {
        return new PropertiesProjectsItems();
    }

}
