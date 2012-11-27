package net.exathunk.jsubschema.genschema.card;

import net.exathunk.jsubschema.gendeps.DomainFactory;

public class CardFactory implements DomainFactory<Card> {

    @Override
    public Class<Card> getDomainClass() {
        return Card.class;
    }

    @Override
    public Card makeDomain() {
        return new Card();
    }

}
