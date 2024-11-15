package store.storeapp.value;

public record Promotion(PromotionName name, PromotionQuantity quantity, Period period) {

    public static Promotion of(PromotionName name, PromotionQuantity quantity, Period period) {
        return new Promotion(name, quantity, period);
    }

    public static Promotion empty() {
        return new Promotion(null, null, null);
    }

    public boolean isPromotionPeriod(Date now) {
        return period.include(now);
    }

    public boolean isEmpty() {
        return name == null || quantity == null || period == null;
    }

    public boolean isSatisfyForPromotionRequiredQuantity(Quantity purchased) {
        return quantity.isRequiredQuantityLessThanOrEqual(purchased);
    }

    public Quantity getQuantityProvidedAtOnce() {
        return quantity.calculateQuantityProvidedAtOnce();
    }

    public String getName() {
        return name.getName();
    }

}
