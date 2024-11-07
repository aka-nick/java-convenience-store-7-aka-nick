package store.storeapp.model.value;

public class ProductName extends Name {

    public static final String KOREAN_NOTATION = "상품명";
    public static final int MAX_LENGTH_OF_PRODUCT_NAME = 15;

    private ProductName(String name) {
        super(name);
    }

    public static ProductName of(String name) {
        if (name == null) {
            ProductNameException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }

        name = name.strip();
        if (MAX_LENGTH_OF_PRODUCT_NAME < name.length()) {
            ProductNameException.NAME_IS_TOO_LONG.raise();
        }
        return new ProductName(name);
    }

    private enum ProductNameException {

        NAME_IS_TOO_LONG(() -> {
            throw new IllegalArgumentException(String.format(
                    "%s의 최대 길이는 %d입니다.",
                    KOREAN_NOTATION,
                    ProductName.MAX_LENGTH_OF_PRODUCT_NAME));
        }),
        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException(String.format(
                    "%s은 공백일 수 없습니다.",
                    KOREAN_NOTATION));
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
