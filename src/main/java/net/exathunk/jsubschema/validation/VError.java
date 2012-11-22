package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.base.TypeException;

import java.util.List;

/**
 * charolastra 11/15/12 11:41 AM
 */
public class VError {
    public final String path;
    public final String message;

    public VError(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public VError(SchemaNode schemaNode, String message) {
        this(schemaNode.toPathString(), message);
    }

    @Override
    public String toString() {
        return "VError: "+path+": "+message+"\n";
    }

    public void throwThis(VError ve) throws TypeException {
        throw new TypeException(ve.toString());
    }

    public static void throwAll(List<VError> ves) throws TypeException {
        StringBuilder sb = new StringBuilder();
        for (VError ve : ves) {
            sb.append(ve.toString());
        }
        throw new TypeException(sb.toString());
    }
}
