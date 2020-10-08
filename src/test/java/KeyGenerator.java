public class KeyGenerator implements KeyExtractor<Integer, String>{
    @Override
    public Integer extract(String value) {
        return value.hashCode();
    }
}
