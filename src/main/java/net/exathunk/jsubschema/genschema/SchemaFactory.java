package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class SchemaFactory implements DomainFactory<SchemaFactory> {


    public Class<SchemaFactory> getDomainClass() { return SchemaFactory.class; }
    public SchemaFactory makeDomain() { return new SchemaFactory(); }

}
