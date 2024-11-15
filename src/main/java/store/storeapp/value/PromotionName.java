package store.storeapp.value;

public class PromotionName extends Name {

    public static final String KOREAN_NOTATION = "프로모션명";
    public static final int MAX_LENGTH_OF_PROMOTION_NAME = 20;

    private PromotionName(String name) {
        super(name);
    }

    public static PromotionName of(String name) {
        if (name == null) {
            ProductNameException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }

        if (MAX_LENGTH_OF_PROMOTION_NAME < name.strip().length()) {
            ProductNameException.NAME_IS_TOO_LONG.raise();
        }
        return new PromotionName(name.strip());
    }

    private enum ProductNameException {

        NAME_IS_TOO_LONG(() -> {
            throw new IllegalArgumentException(String.format(
                    "%s의 최대 길이는 %d입니다.",
                    KOREAN_NOTATION,
                    PromotionName.MAX_LENGTH_OF_PROMOTION_NAME));
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
