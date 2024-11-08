package store.storeapp.model.value;

public record Promotion(PromotionName name, PromotionQuantity quantity, Period period) {

    public static Promotion of(PromotionName name, PromotionQuantity quantity, Period period) {
        return Promotion.of(name, quantity, period);
    }

}
