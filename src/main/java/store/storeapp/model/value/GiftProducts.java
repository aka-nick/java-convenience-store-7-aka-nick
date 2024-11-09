package store.storeapp.model.value;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class GiftProducts {

    private final Set<GiftProduct> giftProducts;

    public GiftProducts() {
        this.giftProducts = new TreeSet<>();
    }

    public GiftProducts(Set<GiftProduct> giftProducts) {
        this.giftProducts = giftProducts;
    }

    public static GiftProducts of() {
        return new GiftProducts();
    }

    public static GiftProducts of(Set<GiftProduct> giftsAndCounts) {
        if (giftsAndCounts == null) {
            GiftProductsException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new GiftProducts(giftsAndCounts);
    }

    public void addGifts(GiftProduct nowGift) {
        GiftProduct updatedGift = giftProducts.stream()
                .filter(gift -> gift.equals(nowGift))
                .findFirst()
                .map(exists -> GiftProduct.of(
                        nowGift.name(),
                        exists.quantity().add(nowGift.quantity())))
                .orElse(nowGift);
        giftProducts.remove(updatedGift);
        giftProducts.add(updatedGift);
    }

    public Stream<GiftProduct> stream() {
        return giftProducts.stream();
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
