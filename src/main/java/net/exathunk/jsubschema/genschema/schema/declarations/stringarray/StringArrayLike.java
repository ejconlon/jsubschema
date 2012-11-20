package net.exathunk.jsubschema.genschema.schema.declarations.stringarray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = StringArray.class)
public interface StringArrayLike extends List<String> {

}
