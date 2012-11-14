package net.exathunk.jsubschema.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/13/12 8:09 PM
 */
public class Thing implements Visitable {
    private final Type type;
    private boolean empty;
    private Object value;

    public Thing(Type type) {
        this.type = type;
        this.empty = true;
        assert type != null;
    }

    public Thing(Type type, Object value) {
        this.type = type;
        this.value = value;
        this.empty = false;
        assert type != null;
    }

    public boolean isA(Type type) {
        return this.type.equals(type);
    }

    public Type getType() {
        return type;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    // --- for convenience

    public boolean isObject() {
        return isA(Type.of("object"));
    }
    public Map<String, Thing> getObject() {
        return (Map<String, Thing>)value;
    }
    public static Thing makeObject(Map<String, Thing> m) {
        return new Thing(Type.of("object"), m);
    }
    public static Thing makeObject() { return makeObject(new TreeMap<String, Thing>()); }
    public void setObject(Map<String, Thing> v) { setValue(v); }

    public boolean isArray() {
        return isA(Type.of("array"));
    }
    public List<Thing> getArray() {
        return (List<Thing>)value;
    }
    public static Thing makeArray(List<Thing> m) {
        return new Thing(Type.of("array"), m);
    }
    public static Thing makeArray() { return makeArray(new ArrayList<Thing>()); }
    public void setArray(List<Thing> v) { setValue(v); }

    /*public boolean isString() {
        return isA(Type.of("string"));
    }
    public String getString() {
        return (String)value;
    }
    public static Thing makeString(String s) {
        return new Thing(Type.of("string"), s);
    }
    public static Thing makeString() { return makeString(null); }
    public void setString(String v) { setValue(v); }

    public boolean isBoolean() {
        return isA(Type.of("boolean"));
    }
    public Boolean getBoolean() {
        return (Boolean)value;
    }
    public static Thing makeBoolean(Boolean s) {
        return new Thing(Type.of("boolean"), s);
    }
    public static Thing makeBoolean() { return makeBoolean(null); }
    public void setBoolean(Boolean v) { setValue(v); }

    public boolean isLong() {
        return isA(Type.of("long"));
    }
    public Long getLong() {
        return (Long)value;
    }
    public static Thing makeLong(Long s) {
        return new Thing(Type.of("long"), s);
    }
    public static Thing makeLong() { return makeLong(null); }
    public void setLong(Long v) { setValue(v); }

    public boolean isDouble() {
        return isA(Type.of("double"));
    }
    public Double getDouble() {
        return (Double)value;
    }
    public static Thing makeDouble(Double s) {
        return new Thing(Type.of("double"), s);
    }
    public static Thing makeDouble() { return makeDouble(null); }
    public void setDouble(Double v) { setValue(v); }*/

    // --- boilerplate

    @Override
    public String toString() {
        return "Node{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thing)) return false;

        Thing thing = (Thing) o;

        if (!type.equals(thing.type)) return false;
        if (value != null ? !value.equals(thing.value) : thing.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    private void acceptObject(ObjectDSL o, String key) {
        if (isObject()) {
            ObjectDSL p = o.seeObject(key);
            for (Map.Entry<String, Thing> thing : getObject().entrySet()) {
                thing.getValue().acceptObject(p, thing.getKey());
            }
        } else if (isArray()) {
            ArrayDSL p = o.seeArray(key);
            for (Thing thing : getArray()) {
                thing.acceptArray(p);
            }
        } else {
            o.seeScalar(key, this);
        }
    }

    private void acceptArray(ArrayDSL a) {
        if (isObject()) {
            ObjectDSL p = a.seeObject();
            for (Map.Entry<String, Thing> thing : getObject().entrySet()) {
                thing.getValue().acceptObject(p, thing.getKey());
            }
        } else if (isArray()) {
            ArrayDSL p = a.seeArray();
            for (Thing thing : getArray()) {
                thing.acceptArray(p);
            }
        } else {
            a.seeScalar(this);
        }
    }

    @Override
    public void accept(DSL visitor) {
        if (isObject()) {
            ObjectDSL o = visitor.seeObject();
            for (Map.Entry<String, Thing> thing : getObject().entrySet()) {
                thing.getValue().acceptObject(o, thing.getKey());
            }
        } else if (isArray()) {
            ArrayDSL a = visitor.seeArray();
            for (Thing thing : getArray()) {
                thing.acceptArray(a);
            }
        } else {
            visitor.seeScalar(this);
        }
    }
}
