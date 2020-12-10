import java.util.*;

public class SimpleEntitiesStorage<K, V> implements BankEntitiesStorage<K, V>{
    private final Map<K, V> storage = new HashMap<>();
    private final KeyExtractor<K, V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<K, V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public void save(V value) {
        storage.put(keyExtractor.extract(value), value);
    }

    @Override
    public void saveAll(List<? extends V> list) {
        for(V value : list){
            save(value);
        }
    }

    @Override
    public V findByKey(K key) {
        return storage.get(key);
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<V>(storage.values());
    }

    @Override
    public void deleteByKey(K key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<? extends V> values) {
        for(V value : values){
            deleteByKey(keyExtractor.extract(value));
        }
    }
}
