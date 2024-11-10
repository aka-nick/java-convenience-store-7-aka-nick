package store.storeapp.data;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import store.config.data.CsvFileDataReader;
import store.storeapp.model.Product;
import store.storeapp.model.ProductQuantity;
import store.storeapp.value.Period;
import store.storeapp.value.Price;
import store.storeapp.value.ProductName;
import store.storeapp.value.Promotion;
import store.storeapp.value.PromotionName;
import store.storeapp.value.PromotionQuantity;
import store.storeapp.value.Quantity;

public final class StoreDataReader {

    private static final Path PATH_OF_PROMOTION = Path.of("src/main/resources/promotions.md");
    private static final Path PATH_OF_PRODUCT = Path.of("src/main/resources/products.md");

    private StoreDataReader() {

    }

    public static Map<PromotionName, Promotion> readPromotions() {
        return CsvFileDataReader.readDataTable(PATH_OF_PROMOTION).values().stream()
                .collect(Collectors.toMap(row -> PromotionName.of(row.get("name")),
                        convertRowToPromotion()));
    }

    public static Map<ProductName, Product> readProducts() {
        Map<PromotionName, Promotion> promotions = readPromotions();
        return CsvFileDataReader.readDataTable(PATH_OF_PRODUCT).values().stream()
                .collect(Collectors.toMap(row -> ProductName.of(row.get("name")),
                        collectProductRowDividedOnPromotion(promotions),
                        mergeDuplicatedRowsBasedOnPromotion(promotions)));
    }

    private static Function<Map<String, String>, Promotion> convertRowToPromotion() {
        return row -> new Promotion(PromotionName.of(row.get("name")),
                PromotionQuantity.of(
                        Quantity.of(Integer.valueOf(row.get("buy"))),
                        Quantity.of(Integer.valueOf(row.get("get")))),
                Period.of(row.get("start_date"), row.get("end_date")));
    }

    private static Function<Map<String, String>, Product> collectProductRowDividedOnPromotion(
            Map<PromotionName, Promotion> promotions) {
        return row -> {
            if (promotions.containsKey(PromotionName.of(row.get("promotion")))) {
                return makePromotionProduct(promotions, row);
            }
            return makeRegularProduct(row);
        };
    }

    private static Product makePromotionProduct(Map<PromotionName, Promotion> promotions, Map<String, String> row) {
        return new Product(ProductName.of(row.get("name")),
                Price.of(Integer.valueOf(row.get("price"))),
                new ProductQuantity(Quantity.of(0)),
                new ProductQuantity(Quantity.of(Integer.valueOf(row.get("quantity")))),
                promotions.get(PromotionName.of(row.get("promotion"))));
    }

    private static Product makeRegularProduct(Map<String, String> row) {
        return new Product(ProductName.of(row.get("name")),
                Price.of(Integer.valueOf(row.get("price"))),
                new ProductQuantity(Quantity.of(Integer.valueOf(row.get("quantity")))),
                new ProductQuantity(Quantity.of(0)),
                Promotion.empty());
    }

    private static BinaryOperator<Product> mergeDuplicatedRowsBasedOnPromotion(
            Map<PromotionName, Promotion> promotions) {
        return (exist, duplicate) -> {
            if (promotions.containsKey(PromotionName.of(exist.name().getName()))) {
                return mergeProductRegularIntoPromotion(exist, duplicate);
            }
            return mergeProductPromotionIntoRegular(exist, duplicate);
        };
    }

    private static Product mergeProductRegularIntoPromotion(Product exist, Product duplicate) {
        return new Product(exist.name(), exist.price(),
                duplicate.regularQuantity(),
                exist.promotionQuantity(), exist.promotion());
    }

    private static Product mergeProductPromotionIntoRegular(Product exist, Product duplicate) {
        return new Product(exist.name(), exist.price(),
                exist.regularQuantity(),
                duplicate.promotionQuantity(), duplicate.promotion());
    }

}
