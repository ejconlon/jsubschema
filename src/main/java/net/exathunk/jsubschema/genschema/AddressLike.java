package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;

public interface AddressLike {

    boolean hasPost__dash__office__dash__box();
    String getPost__dash__office__dash__box();
    void setPost__dash__office__dash__box(String post__dash__office__dash__box);
    boolean hasExtended__dash__address();
    String getExtended__dash__address();
    void setExtended__dash__address(String extended__dash__address);
    boolean hasStreet__dash__address();
    String getStreet__dash__address();
    void setStreet__dash__address(String street__dash__address);
    boolean hasLocality();
    String getLocality();
    void setLocality(String locality);
    boolean hasRegion();
    String getRegion();
    void setRegion(String region);
    boolean hasPostal__dash__code();
    String getPostal__dash__code();
    void setPostal__dash__code(String postal__dash__code);
    boolean hasCountry__dash__name();
    String getCountry__dash__name();
    void setCountry__dash__name(String country__dash__name);
}
