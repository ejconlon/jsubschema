package net.exathunk.jsubschema.functional;

import java.util.Map;

/**
 * charolastra 11/19/12 3:21 PM
 */
public class Pair<K, V> implements Map.Entry<K, V> {
    final K key;
    final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map.Entry)) return false;

        Map.Entry<K, V> pair = (Map.Entry<K, V>) o;

        if (key != null ? !key.equals(pair.getKey()) : pair.getKey() != null) return false;
        if (value != null ? !value.equals(pair.getValue()) : pair.getValue() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
