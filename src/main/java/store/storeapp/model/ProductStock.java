package store.storeapp.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import store.storeapp.value.PickProduct;
import store.storeapp.value.ProductName;
import store.storeapp.value.Quantity;

public class ProductStock {

    private final Map<ProductName, Product> stock;

    public ProductStock(Map<ProductName, Product> stock) {
        if (stock == null || stock.isEmpty()) {
            ProductStockException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }
        this.stock = Map.copyOf(stock);
    }

    public Stream<Entry<ProductName, Product>> entryStream() {
        return stock.entrySet().stream();
    }

    public boolean isSalesAvailable(PickProduct pickProduct) {
        thrownIfIsNotExistsProduct(pickProduct.name());
        thrownIfIsNotEnoughStockLeft(pickProduct.name(), pickProduct.quantity());
        return true;
    }

    private void thrownIfIsNotExistsProduct(ProductName productName) {
        if (stock.get(productName) == null) {
            ProductStockException.IS_NOT_EXISTS_PRODUCT.raise();
        }
    }

    private void thrownIfIsNotEnoughStockLeft(ProductName productName, Quantity quantity) {
        if (!stock.get(productName).isTotalStockGreaterThanOrEqualTo(quantity)) {
            ProductStockException.IS_NOT_ENOUGH_STOCK_LEFT.raise();
        }
    }

    private enum ProductStockException {

        IS_NOT_EXISTS_PRODUCT(() -> {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }),
        IS_NOT_ENOUGH_STOCK_LEFT(() -> {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }),
        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException("공백으로 생성할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ProductStockException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
