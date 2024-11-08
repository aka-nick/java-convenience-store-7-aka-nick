package store.storeapp.model;

import store.storeapp.model.value.Quantity;

public final class ProductQuantity {

    private Quantity quantity;

    public ProductQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Integer get() {
        return quantity.get();
    }

    public ProductQuantity minus(Quantity purcharsedQuantity) {
        thrownByNullParams(purcharsedQuantity);
        thrownByUnableMinusCalculation(purcharsedQuantity.get());
        quantity = Quantity.of(quantity.get() - purcharsedQuantity.get());
        return this;
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

    private static void thrownByNullParams(Quantity purchasedQuantity) {
        if (purchasedQuantity == null) {
            ProductQuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        thrownByNullParams(purchasedQuantity.get());
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
