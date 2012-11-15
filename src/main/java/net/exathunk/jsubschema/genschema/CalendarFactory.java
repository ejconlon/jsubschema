package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class CalendarFactory implements DomainFactory<CalendarFactory> {


    public Class<CalendarFactory> getDomainClass() { return CalendarFactory.class; }
    public CalendarFactory makeDomain() { return new CalendarFactory(); }

}
