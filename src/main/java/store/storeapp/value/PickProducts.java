package store.storeapp.value;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record PickProducts(List<PickProduct> pickProducts) {

    public PickProducts {
        pickProducts = List.copyOf(pickProducts);
    }

    public Stream<PickProduct> stream() {
        return pickProducts.stream();
    }

    public void forEach(Consumer<? super PickProduct> action) {
        pickProducts.forEach(action);
    }

}
