public class IntegerAccountKeyExtractor implements KeyExtractor<Integer, Account>{
    @Override
    public Integer extract(Account value) {
        return value.hashCode();
    }
}
