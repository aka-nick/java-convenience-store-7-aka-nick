package store.storeapp.model.value;

import java.util.Objects;

public class PromotionQuantity {

    private final Quantity requiredForApply;
    private final Quantity providedByPromotion;

    private PromotionQuantity(Quantity requiredForApply, Quantity providedByPromotion) {
        this.requiredForApply = requiredForApply;
        this.providedByPromotion = providedByPromotion;
    }

    public static PromotionQuantity of(Quantity requiredForApply, Quantity providedByPromotion) {
        if (requiredForApply == null || providedByPromotion == null) {
            PromotionQuantityException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }
        if (requiredForApply.equals(Quantity.of(0)) || providedByPromotion.equals(Quantity.of(0))) {
            PromotionQuantityException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }
        return new PromotionQuantity(requiredForApply, providedByPromotion);
    }

    public boolean isRequiredQuantityLessThanOrEqual(Quantity purchased) {
        return requiredForApply.compareTo(purchased) <= 0;
    }

    public Quantity calculateQuantityProvidedAtOnce() {
        return requiredForApply.add(providedByPromotion);
    }

    public Quantity requiredForApply() {
        return requiredForApply;
    }

    public Quantity providedByPromotion() {
        return providedByPromotion;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        var that = (PromotionQuantity) other;
        return Objects.equals(this.requiredForApply, that.requiredForApply) &&
                Objects.equals(this.providedByPromotion, that.providedByPromotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiredForApply, providedByPromotion);
    }

    private enum PromotionQuantityException {

        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException("프로모션 수량은 양의 정수로 초기화할 수 있습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        PromotionQuantityException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
