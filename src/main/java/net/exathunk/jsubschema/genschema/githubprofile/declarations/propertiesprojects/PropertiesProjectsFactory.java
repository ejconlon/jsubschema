package net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojects;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class PropertiesProjectsFactory implements DomainFactory<PropertiesProjects> {

    @Override
    public Class<PropertiesProjects> getDomainClass() {
        return PropertiesProjects.class;
    }

    @Override
    public PropertiesProjects makeDomain() {
        return new PropertiesProjects();
    }

}
