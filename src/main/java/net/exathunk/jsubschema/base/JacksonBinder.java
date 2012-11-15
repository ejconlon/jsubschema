package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * charolastra 11/14/12 9:13 PM
 */
public class JacksonBinder<T> implements Binder<T> {

    private final ObjectMapper mapper;
    private final DomainFactory<T> factory;

    public JacksonBinder(ObjectMapper mapper, DomainFactory<T> factory) {
        this.mapper = mapper;
        this.factory = factory;
    }

    @Override
    public T bind(JsonNode node) throws TypeException {
        try {
            final T domain = mapper.treeToValue(node, factory.getDomainClass());
            return domain;
        } catch (IOException e) {
            throw new TypeException(e);
        }
    }

    @Override
    public JsonNode unbind(T domain) throws TypeException {
        final JsonNode node = mapper.valueToTree(domain);
        return node;
    }
}
