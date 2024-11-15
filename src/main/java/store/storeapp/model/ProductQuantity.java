package store.storeapp.model;

import store.storeapp.value.Quantity;

public final class ProductQuantity {

    private Quantity quantity;

    public ProductQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Quantity add(Quantity other) {
        thrownByNullParams(other);
        quantity = quantity.add(other);
        return quantity;
    }

    public Quantity minus(Quantity other) {
        thrownByNullParams(other);
        thrownByUnableMinusCalculation(other.get());
        quantity = Quantity.of(quantity.get() - other.get());
        return quantity;
    }

    public Integer minus(Integer purchasedQuantity) {
        thrownByNullParams(purchasedQuantity);
        thrownByUnableMinusCalculation(purchasedQuantity);
        quantity = Quantity.of(quantity.get() - purchasedQuantity);
        return quantity.get();
    }

    public boolean isGreaterThanOrEqualTo(Quantity other) {
        return quantity.isGreaterThan(other) || quantity.equals(other);
    }

    public boolean isGreaterThan(Quantity other) {
        return quantity.isGreaterThan(other);
    }

    public Quantity getRemainingQuantityByMinusAsPossible(Quantity pickQuantity) {
        Quantity differenceBetween = pickQuantity.minus(quantity);
        quantity.minus(quantity);
        return differenceBetween;
    }

    public Quantity get() {
        return quantity;
    }

    public Integer intValue() {
        return quantity.get();
    }

    private static void thrownByNullParams(Quantity purchased) {
        if (purchased == null) {
            ProductQuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        thrownByNullParams(purchased.get());
    }

    private static void thrownByNullParams(Integer purchasedQuantity) {
        if (purchasedQuantity == null) {
            ProductQuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
    }

    private void thrownByUnableMinusCalculation(Integer purchasedQuantity) {
        if (quantity.get() - purchasedQuantity < 0) {
            ProductQuantityException.CANNOT_BE_SUBTRACTED_BELOW_ZERO.raise();
        }
    }

    @Override
    public String toString() {
        return quantity.toString();
    }

    public Quantity getRemainingQuantityAfterDeduction(Quantity pickQuantity) {
        if (isGreaterThanOrEqualTo(pickQuantity)) {
            quantity = quantity.minus(pickQuantity);
            return Quantity.ZERO;
        }
        Quantity remaining = pickQuantity.minus(quantity);
        quantity = Quantity.ZERO;
        return remaining;
    }

    private enum ProductQuantityException {

        CANNOT_BE_SUBTRACTED_BELOW_ZERO(() -> {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ProductQuantityException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
