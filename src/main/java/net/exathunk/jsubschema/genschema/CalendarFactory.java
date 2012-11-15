package net.exathunk.jsubschema.genschema;

import net.exathunk.jsubschema.base.DomainFactory;

public class CalendarFactory implements DomainFactory<Calendar> {


    public Class<Calendar> getDomainClass() { return Calendar.class; }
    public Calendar makeDomain() { return new Calendar(); }

}
