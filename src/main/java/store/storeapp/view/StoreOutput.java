package store.storeapp.view;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.storeapp.model.Product;
import store.storeapp.model.ProductQuantity;
import store.storeapp.model.ProductStock;
import store.storeapp.value.TotalReceipt;

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

    public void giveReceipt(TotalReceipt totalReceipt) {
        String headerForBuyProduct = """
                ==============W 편의점================
                상품명		        수량       금액
                """;
        String formatForBuyProduct = """
                %s                  %s        %s
                """;
        String contentOfBuyProduct = totalReceipt.stream()
                .map(receipt -> formatForBuyProduct.formatted(receipt.buyProduct().name().getName(),
                        receipt.buyProduct().quantity().toString(),
                        receipt.buyProduct().getTotalPrice().toString()))
                .collect(Collectors.joining("", headerForBuyProduct, ""));

        String headerForProvided = """
                =============증     정===============
                """;
        String formatForProvided = """
                %s                  %s
                """;
        String contentOfProvided = totalReceipt.stream()
                .map(receipt -> formatForProvided.formatted(receipt.promotionProduct().name().getName(),
                        receipt.promotionProduct().quantity()))
                .collect(Collectors.joining("", headerForProvided, ""));

        String formatForResult = """
                ====================================
                총구매액              %s         %s
                행사할인			              -%s
                멤버십할인			              -%s
                내실돈			               %s
                """;

        long totalPurchaseQuantity = totalReceipt.stream()
                .mapToLong(receipt -> receipt.buyProduct().quantity().longValue())
                .sum();
        long totalPurchaseAmount = totalReceipt.stream()
                .mapToLong(receipt -> receipt.buyProduct().getTotalPrice().longValue())
                .sum();
        long totalPromotionDiscountPrice = totalReceipt.stream()
                .mapToLong(receipt -> receipt.promotionDiscountPrice().longValue())
                .sum();
        long totalMembershipDiscountPrice = totalReceipt.stream()
                .mapToLong(receipt -> receipt.membershipDiscountPrice().longValue())
                .sum();
        long finalPaymentAmount = totalPurchaseAmount - totalPromotionDiscountPrice - totalMembershipDiscountPrice;
        String contentForResult = formatForResult.formatted(formatWithCommas(totalPurchaseQuantity),
                formatWithCommas(totalPurchaseAmount),
                formatWithCommas(totalPromotionDiscountPrice),
                formatWithCommas(totalMembershipDiscountPrice),
                formatWithCommas(finalPaymentAmount));

        System.out.println(String.join("", contentOfBuyProduct, contentOfProvided, contentForResult));
    }

    private static String collectProductStockMessage(ProductStock productStock) {
        return productStock.entryStream().map(Entry::getValue)
                .sorted(Comparator.comparing(Product::name))
                .map(product -> {
                    if (product.promotion().isEmpty()) {
                        return formatInRegularOf(product);
                    }
                    return formatInRegularAndPromotionOf(product);
                })
                .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
    }

    private static String formatInRegularOf(Product product) {
        return "- %s %s원 %s개".formatted(
                product.name(), product.price(), product.regularQuantity());
    }

    private static String formatInRegularAndPromotionOf(Product product) {
        String formattedPromotionQuantity = countQuantityOrDispayedOutOfStock(product.promotionQuantity());
        String formattedRegularQuantity = countQuantityOrDispayedOutOfStock(product.regularQuantity());
        return """
                - %s %s원 %s %s
                - %s %s원 %s""".formatted(
                product.name(), product.price(), formattedPromotionQuantity, product.promotion().getName(),
                product.name(), product.price(), formattedRegularQuantity);
    }

    private static String countQuantityOrDispayedOutOfStock(ProductQuantity productQuantity) {
        if (productQuantity.intValue() == 0) {
            return "재고 없음";
        }
        return productQuantity + "개";
    }

    private static String formatWithCommas(long number) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(number);
    }

}
