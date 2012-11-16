package net.exathunk.jsubschema.base;

import java.util.List;

/**
 * charolastra 11/15/12 11:41 AM
 */
public class VError {
    public final Path path;
    public final String message;

    public VError(Path path, String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public String toString() {
        return "VError: "+path.toPointer()+" "+message+"\n";
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
