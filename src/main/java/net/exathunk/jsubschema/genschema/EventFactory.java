package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class EventFactory implements DomainFactory<Event> {


    public Class<Event> getDomainClass() { return Event.class; }
    public Event makeDomain() { return new Event(); }

}
