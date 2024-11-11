package store.storeapp.model;

import store.storeapp.value.Date;
import store.storeapp.value.Price;
import store.storeapp.value.ProductName;
import store.storeapp.value.Promotion;
import store.storeapp.value.Quantity;
import store.storeapp.value.QuantityOfPaidAndProvided;

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

    public boolean isPromotionPeriod(Date standardDate) {
        return !promotion.isEmpty() && promotion.isPromotionPeriod(standardDate);
    }

    // TODO: 불필요한 메서드, 추후 제거해야 함. 아래 메서드로 전환함.
    public boolean isApplicablePromotion(Quantity purchased) {
        return isPromotionPeriod(Date.now()) &&
                promotion.isSatisfyForPromotionRequiredQuantity(purchased) &&
                promotionQuantity.isGreaterThanOrEqualTo(
                        promotion.getQuantityProvidedAtOnce());
    }

    public boolean isApplicablePromotionButNotReceived(Date standardDate, Quantity pickedQuantity) {
        if (!isPromotionPeriod(standardDate)) {
            return false;
        }
        Quantity requiredAndProvided = promotion.quantity().calculateQuantityProvidedAtOnce();
        Quantity candidateQuantity = pickedQuantity.remainder(requiredAndProvided);
        return candidateQuantity.equals(promotion.quantity().requiredForApply());
    }

    public boolean isApplicablePromotionButLackOfStock(Date standardDate, Quantity pickedQuantity) {
        if (!isPromotionPeriod(standardDate)) {
            return false;
        }
        return pickedQuantity.isGreaterThan(getMaxQuantityOfProvidedAvailable());
    }

    public Quantity calculateQuantityOfPromotionNotAvailable(Quantity pickedQuantity) {
        return pickedQuantity.minus(getMaxQuantityOfProvidedAvailable());
    }

    public boolean isTotalStockGreaterThanOrEqualTo(Quantity requiredQuantity) {
        Quantity totalStock = regularQuantity.add(promotionQuantity.get());
        return 0 <= totalStock.compareTo(requiredQuantity);
    }

    public Quantity getMaxQuantityOfProvidedAvailable() {
        Quantity requiredAndProvided = promotion.quantity().calculateQuantityProvidedAtOnce();
        Quantity promotionStockQuantity = promotionQuantity.get();
        return promotionStockQuantity.divide(requiredAndProvided).multiply(requiredAndProvided);
    }

    public QuantityOfPaidAndProvided calculatePaidAndProvided(Quantity pickQuantity, Date standardDate) {
        if (isPromotionPeriod(standardDate)) {
            return calculatePaidAndProvidedInPromotion(pickQuantity);
        }
        return new QuantityOfPaidAndProvided(pickQuantity, Quantity.ZERO);
    }

    public void deductionQuantity(Quantity pickQuantity) {
        Quantity remainingQuantity = promotionQuantity.getRemainingQuantityAfterDeduction(pickQuantity);
        regularQuantity.minus(remainingQuantity);
    }

    private QuantityOfPaidAndProvided calculatePaidAndProvidedInPromotion(Quantity pickQuantity) {
        if (isPromotionApplyInAnyQuantity(pickQuantity)) {
            return new QuantityOfPaidAndProvided(
                    countPromotionBundle(pickQuantity).multiply(promotion.quantity().requiredForApply()),
                    countPromotionBundle(pickQuantity).multiply(promotion.quantity().providedByPromotion()));
        }
        return new QuantityOfPaidAndProvided(
                calculateQuantityOfPromotionNotAvailable(pickQuantity),
                getMaxQuantityOfProvidedAvailable());
    }

    private boolean isPromotionApplyInAnyQuantity(Quantity pickQuantity) {
        return Quantity.ZERO.equals(
                pickQuantity.remainder(promotion.quantity().calculateQuantityProvidedAtOnce()));
    }

    private Quantity countPromotionBundle(Quantity pickQuantity) {
        return pickQuantity.divide(promotion.quantity().calculateQuantityProvidedAtOnce());
    }

    private void outFromStock(Quantity quantity) {

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
