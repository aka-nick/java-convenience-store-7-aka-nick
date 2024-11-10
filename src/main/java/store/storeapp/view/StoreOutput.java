package store.storeapp.view;

import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.storeapp.model.Product;
import store.storeapp.model.ProductStock;
import store.storeapp.model.Receipt;

public class StoreOutput {

    public void sayGreeting() {
        System.out.println("""
                안녕하세요. W편의점입니다.
                현재 보유하고 있는 상품입니다.
                """);
    }

    public void introduceProductStock(ProductStock productStock) {
        System.out.println(collectProductStockMessage(productStock));
    }

    public void giveReceipt(Receipt receipt) {
        // TODO: 영수증 포맷에 알맞게 추가 구현
        System.out.println(receipt);
    }

    private static String collectProductStockMessage(ProductStock productStock) {
        return productStock.entryStream().map(Entry::getValue)
                .map(product -> {
                    if (product.promotion().isEmpty()) {
                        return formatInRegularOf(product);
                    }
                    return formatInRegularAndPromotionOf(product);
                })
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String formatInRegularOf(Product product) {
        return "- %s %s원 %s개".formatted(
                product.name(), product.price(), product.regularQuantity());
    }

    private static String formatInRegularAndPromotionOf(Product product) {
        return """
                - %s %s원 %s개 %s
                - %s %s원 %s개""".formatted(
                product.name(), product.price(), product.promotionQuantity(), product.promotion().getName(),
                product.name(), product.price(), product.regularQuantity());
    }

}
