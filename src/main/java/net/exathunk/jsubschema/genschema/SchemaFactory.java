package net.exathunk.jsubschema.genschema;
import net.exathunk.jsubschema.gendeps.DomainFactory;
public class SchemaFactory implements DomainFactory<Schema> {
    @Override
    public Class<Schema> getDomainClass() {        return Schema.class;
}
    @Override
    public Schema makeDomain() {        return new Schema();
}
}
