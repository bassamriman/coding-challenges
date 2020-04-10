package common;

public class Triple<L, M, R> {
    private final L left;
    private final M middle;
    private final R right;

    private Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> Triple<L, M, R> a(L left, M middle, R right) {
        return new Triple(left, middle, right);
    }

    public L left() {
        return left;
    }

    public M middle() {
        return middle;
    }

    public R right() {
        return right;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "left=" + left +
                ", middle=" + middle +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;

        if (!left.equals(triple.left)) return false;
        if (!middle.equals(triple.middle)) return false;
        return right.equals(triple.right);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + middle.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
