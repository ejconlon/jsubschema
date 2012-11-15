package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/14/12 3:33 PM
 */
public interface Binder<X> {
    X bind(JsonNode node) throws TypeException;
    JsonNode unbind(X domain) throws TypeException;
}
