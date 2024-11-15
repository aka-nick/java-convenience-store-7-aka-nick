package store.storeapp.model;

import store.storeapp.value.Won;

public final class TotalBuyPrice {

    private Won won;

    public TotalBuyPrice() {
        this.won = Won.of(0);
    }

    public TotalBuyPrice(Won won) {
        if (won == null) {
            TotalBuyPriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.won = won;
    }

    public Won add(Won other) {
        if (other == null) {
            TotalBuyPriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        won = won.add(other);
        return won;
    }

    public Won won() {
        return won;
    }

    @Override
    public String toString() {
        return won.toString();
    }

    private enum TotalBuyPriceException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("총구매액은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        TotalBuyPriceException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
