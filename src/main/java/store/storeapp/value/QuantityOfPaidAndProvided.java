package store.storeapp.value;

public record QuantityOfPaidAndProvided(Quantity paidQuantity, Quantity providedQuantity) {

    public Quantity getTotal() {
        return paidQuantity.add(providedQuantity);
    }

}
