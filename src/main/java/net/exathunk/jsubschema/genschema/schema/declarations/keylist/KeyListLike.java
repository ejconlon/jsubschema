package net.exathunk.jsubschema.genschema.schema.declarations.keylist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = KeyList.class)
public interface KeyListLike extends List<String> {

}
