package net.exathunk.jsubschema.genschema.stringmultimap;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import net.exathunk.jsubschema.genschema.stringmultimap.declarations.stringarray.StringArray;
import net.exathunk.jsubschema.genschema.stringmultimap.declarations.stringarray.StringArrayLike;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Stringmultimap.class)
public interface StringmultimapLike extends Map<String, StringArrayLike> {

}
