package store.storeapp.value;

import java.util.List;

public record PickProducts(List<PickProduct> pickProducts) {

    public PickProducts {
        pickProducts = List.copyOf(pickProducts);
    }

}
