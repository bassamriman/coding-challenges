package common;

public class Tuple<L, R> {
    private final L left;
    private final R right;

    private Tuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Tuple<L, R> a(L left, R right) {
        return new Tuple(left, right);
    }

    public L left() {
        return left;
    }

    public R right() {
        return right;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (!left.equals(tuple.left)) return false;
        return right.equals(tuple.right);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
