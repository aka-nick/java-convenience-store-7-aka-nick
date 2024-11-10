package store.storeapp.model;

import store.storeapp.model.value.Date;
import store.storeapp.model.value.Price;
import store.storeapp.model.value.ProductName;
import store.storeapp.model.value.Promotion;
import store.storeapp.model.value.Quantity;

public final class Product {

    private final ProductName name;
    private final Price price;
    private final ProductQuantity regularQuantity;
    private final ProductQuantity promotionQuantity;
    private final Promotion promotion;

    public Product(ProductName name,
                   Price price,
                   ProductQuantity regularQuantity,
                   ProductQuantity promotionQuantity,
                   Promotion promotion) {
        this.name = name;
        this.price = price;
        this.regularQuantity = regularQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public void thrownByOutOfStock(Quantity purchased) {
        Quantity total = regularQuantity.add(promotionQuantity.get());
        if (purchased.isGreaterThan(total)) {
            ProductException.OUT_OF_STOCK.raise();
        }
    }

    public boolean isApplicablePromotion(Quantity purchased) {
        return promotion.isPromotionPeriod(Date.now()) &&
                promotion.isSatisfyForPromotionRequiredQuantity(purchased) &&
                promotionQuantity.isGreaterThanOrEqualTo(
                        promotion.getQuantityProvidedAtOnce());
    }

    public ProductName name() {
        return name;
    }

    public Price price() {
        return price;
    }

    public ProductQuantity regularQuantity() {
        return regularQuantity;
    }

    public ProductQuantity promotionQuantity() {
        return promotionQuantity;
    }

    public Promotion promotion() {
        return promotion;
    }

    private enum ProductException {

        OUT_OF_STOCK(() -> {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ProductException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
