package store.storeapp.model.value;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class PromotionProducts {

    private final Set<PromotionProduct> promotionProducts;

    public PromotionProducts() {
        this.promotionProducts = new TreeSet<>();
    }

    public PromotionProducts(Set<PromotionProduct> promotionProducts) {
        this.promotionProducts = promotionProducts;
    }

    public static PromotionProducts of() {
        return new PromotionProducts();
    }

    public static PromotionProducts of(Set<PromotionProduct> promotionProducts) {
        if (promotionProducts == null) {
            GiftProductsException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new PromotionProducts(promotionProducts);
    }

    public void addOrUpdate(PromotionProduct nowPromotionProduct) {
        PromotionProduct updatedGift = promotionProducts.stream()
                .filter(gift -> gift.equals(nowPromotionProduct))
                .findFirst()
                .map(exists -> PromotionProduct.of(
                        nowPromotionProduct.name(),
                        exists.quantity().add(nowPromotionProduct.quantity())))
                .orElse(nowPromotionProduct);
        promotionProducts.remove(updatedGift);
        promotionProducts.add(updatedGift);
    }

    public Stream<PromotionProduct> stream() {
        return promotionProducts.stream();
    }

    private enum GiftProductsException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("증정상품목록은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        GiftProductsException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
