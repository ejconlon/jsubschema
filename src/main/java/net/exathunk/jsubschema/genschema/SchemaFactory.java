package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class SchemaFactory implements DomainFactory<Schema> {


    public Class<Schema> getDomainClass() { return Schema.class; }
    public Schema makeDomain() { return new Schema(); }

}
