package net.exathunk.jsubschema.base;

import java.util.List;

/**
 * charolastra 11/15/12 11:41 AM
 */
public class VError {
    public final Either<Reference, String> eitherReference;
    public final String message;

    public VError(Either<Reference, String> eitherReference, String message) {
        this.eitherReference = eitherReference;
        this.message = message;
    }

    public VError(Reference reference, String message) {
        this(Either.<Reference, String>makeFirst(reference), message);
    }

    @Override
    public String toString() {
        return "VError: "+eitherReference+" "+message+"\n";
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
