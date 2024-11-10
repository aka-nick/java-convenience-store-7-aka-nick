package store.storeapp.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import store.storeapp.value.ProductName;

public class ProductStock {

    private final Map<ProductName, Product> stock;

    public ProductStock(Map<ProductName, Product> stock) {
        this.stock = Map.copyOf(stock);
    }

    public Stream<Entry<ProductName, Product>> entryStream() {
        return stock.entrySet().stream();
    }

}
