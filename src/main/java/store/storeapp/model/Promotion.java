package store.storeapp.model;

import store.storeapp.model.value.Period;
import store.storeapp.model.value.PromotionName;
import store.storeapp.model.value.PromotionQuantity;

public class Promotion {

    private final PromotionName name;
    private final PromotionQuantity quantity;
    private final Period period;

    public Promotion(PromotionName name,
                     PromotionQuantity quantity,
                     Period period) {
        this.name = name;
        this.quantity = quantity;
        this.period = period;
    }

}
