package store.storeapp.model.value;

import java.util.Objects;

public class Name implements Comparable<Name> {

    public final String name;

    public Name(String name) {
        if (name == null) {
            ProductNameException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }

        name = name.strip();
        if (name.isEmpty()) {
            ProductNameException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }

        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name that)) {
            return false;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public int compareTo(Name o) {
        if (o == null) {
            ProductNameException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return name.compareTo(o.name);
    }

    private enum ProductNameException {

        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ProductNameException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
