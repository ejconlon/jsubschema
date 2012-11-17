package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;

public interface SchemaLike {

    boolean hasType();
    String getType();
    void setType(String type);
    boolean hasDescription();
    String getDescription();
    void setDescription(String description);
    boolean hasFormat();
    String getFormat();
    void setFormat(String format);
    boolean hasProperties();
    Map<String, Schema> getProperties();
    void setProperties(Map<String, Schema> properties);
    boolean hasDeclarations();
    Map<String, Schema> getDeclarations();
    void setDeclarations(Map<String, Schema> declarations);
    boolean hasId();
    String getId();
    void setId(String id);
    boolean has__dollar__ref();
    String get__dollar__ref();
    void set__dollar__ref(String __dollar__ref);
    boolean hasItems();
    Schema getItems();
    void setItems(Schema items);
    boolean hasRequired();
    Boolean getRequired();
    void setRequired(Boolean required);
    boolean hasRequires();
    List<String> getRequires();
    void setRequires(List<String> requires);
    boolean hasForbids();
    List<String> getForbids();
    void setForbids(List<String> forbids);
}
