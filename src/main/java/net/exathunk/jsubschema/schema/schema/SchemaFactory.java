package net.exathunk.jsubschema.schema.schema;

import net.exathunk.jsubschema.base.DomainFactory;

/**
 * charolastra 11/14/12 9:24 PM
 */
public class SchemaFactory implements DomainFactory<Schema> {
    public Class<Schema> getDomainClass() { return Schema.class; }
    public Schema makeDomain() { return new Schema(); }
}