package store.storeapp.value;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class BuyProducts {

    private final Set<BuyProduct> buyProducts;

    public BuyProducts() {
        this.buyProducts = new TreeSet<>();
    }

    public BuyProducts(Set<BuyProduct> buyProducts) {
        this.buyProducts = buyProducts;
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
        BuyProduct updatedGift = buyProducts.stream()
                .filter(gift -> gift.equals(nowBuy))
                .findFirst()
                .map(exists -> BuyProduct.of(
                        nowBuy.name(),
                        exists.quantity().add(nowBuy.quantity()),
                        exists.price().add(nowBuy.price())))
                .orElse(nowBuy);
        buyProducts.remove(updatedGift);
        buyProducts.add(updatedGift);
    }

    public Stream<BuyProduct> stream() {
        return buyProducts.stream();
    }

    private enum BuyProductsException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("구매한 상품 목록은 null로 초기화할 수 없습니다.");
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
