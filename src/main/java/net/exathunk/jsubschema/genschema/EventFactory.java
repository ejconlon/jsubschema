package net.exathunk.jsubschema.genschema;
import net.exathunk.jsubschema.gendeps.DomainFactory;
public class EventFactory implements DomainFactory<Event> {
    @Override
    public Class<Event> getDomainClass() {        return Event.class;
}
    @Override
    public Event makeDomain() {        return new Event();
}
}
