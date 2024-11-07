package store.storeapp.model.value;

public record ProductName(String name) implements Comparable<ProductName> {

    public static final int MAX_LENGTH_OF_NAME = 15;

    public ProductName {
        if (name == null) {
            ProductNameException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }

        name = name.strip();
        if (name.isEmpty()) {
            ProductNameException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }
        if (name.length() > MAX_LENGTH_OF_NAME) {
            ProductNameException.NAME_IS_TOO_LONG.raise();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(ProductName o) {
        if (o == null) {
            ProductNameException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return name.compareTo(o.name);
    }

    private enum ProductNameException {

        NAME_IS_TOO_LONG(() -> {
            throw new IllegalArgumentException(String.format(
                    "상품명의 최대 길이는 %d입니다.", ProductName.MAX_LENGTH_OF_NAME));
        }),
        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException("상품명은 공백일 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ProductNameException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
