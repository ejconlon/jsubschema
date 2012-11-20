package net.exathunk.jsubschema.genschema.stringmultimap.declarations.stringarray;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class StringArrayFactory implements DomainFactory<StringArray> {

    @Override
    public Class<StringArray> getDomainClass() {
        return StringArray.class;
    }

    @Override
    public StringArray makeDomain() {
        return new StringArray();
    }

}
