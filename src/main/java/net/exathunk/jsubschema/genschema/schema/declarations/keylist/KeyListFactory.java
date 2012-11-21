package net.exathunk.jsubschema.genschema.schema.declarations.keylist;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class KeyListFactory implements DomainFactory<KeyList> {

    @Override
    public Class<KeyList> getDomainClass() {
        return KeyList.class;
    }

    @Override
    public KeyList makeDomain() {
        return new KeyList();
    }

}
