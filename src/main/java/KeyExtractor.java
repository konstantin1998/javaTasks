public interface KeyExtractor<K, V> {
    K extract(V value);
}
