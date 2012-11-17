package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;

public interface LinkLike {

    boolean hasHref();
    String getHref();
    void setHref(String href);
    boolean hasRel();
    String getRel();
    void setRel(String rel);
    boolean hasMethod();
    String getMethod();
    void setMethod(String method);
    boolean hasEnctype();
    String getEnctype();
    void setEnctype(String enctype);
}
