package store.storeapp.value;

import java.util.List;
import java.util.stream.Stream;

public final class TotalReceipt {

    private final List<ReceiptOfProduct> receipts;

    public TotalReceipt(List<ReceiptOfProduct> receipts) {
        if (receipts == null) {
            TotalReceiptException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.receipts = List.copyOf(receipts);
    }

    public Stream<ReceiptOfProduct> stream() {
        return receipts.stream();
    }

    private enum TotalReceiptException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("영수증은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        TotalReceiptException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
