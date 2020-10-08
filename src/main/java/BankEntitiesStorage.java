import java.util.List;

public interface BankEntitiesStorage<K,V> {
    void save(V item);
    void saveAll(List<? extends V> list);
    V findByKey(K key);
    List<V> findAll();
    void deleteByKey(K key);
    void deleteAll(List<? extends V> values);
}
