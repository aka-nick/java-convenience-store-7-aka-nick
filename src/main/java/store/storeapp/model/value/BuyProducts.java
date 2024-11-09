package store.storeapp.model.value;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class BuyProducts {

    private final Set<BuyProduct> giftProducts;

    public BuyProducts() {
        this.giftProducts = new TreeSet<>();
    }

    public BuyProducts(Set<BuyProduct> giftProducts) {
        this.giftProducts = giftProducts;
    }

    public static BuyProducts of() {
        return new BuyProducts();
    }

    public static BuyProducts of(Set<BuyProduct> giftsAndCounts) {
        if (giftsAndCounts == null) {
            BuyProductsException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new BuyProducts(giftsAndCounts);
    }

    public void addOrUpdate(BuyProduct nowBuy) {
        BuyProduct updatedGift = giftProducts.stream()
                .filter(gift -> gift.equals(nowBuy))
                .findFirst()
                .map(exists -> BuyProduct.of(
                        nowBuy.name(),
                        exists.quantity().add(nowBuy.quantity()),
                        exists.price().add(nowBuy.price())))
                .orElse(nowBuy);
        giftProducts.remove(updatedGift);
        giftProducts.add(updatedGift);
    }

    public Stream<BuyProduct> stream() {
        return giftProducts.stream();
    }

    private enum BuyProductsException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("증정상품목록은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        BuyProductsException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
