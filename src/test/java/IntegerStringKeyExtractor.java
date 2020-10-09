public class IntegerStringKeyExtractor implements KeyExtractor<Integer, String>{
    @Override
    public Integer extract(String value) {
        return value.hashCode();
    }
}
