package net.exathunk.jsubschema.genschema.stringmultimap;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import net.exathunk.jsubschema.genschema.stringmultimap.declarations.stringarray.StringArray;
import net.exathunk.jsubschema.genschema.stringmultimap.declarations.stringarray.StringArrayLike;

public class Stringmultimap extends TreeMap<String, StringArrayLike> implements Cloneable, Serializable, StringmultimapLike {

}
