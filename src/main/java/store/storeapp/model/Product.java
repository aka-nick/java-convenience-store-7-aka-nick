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

}
