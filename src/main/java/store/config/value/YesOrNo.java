package store.config.value;

public enum YesOrNo {

    YES("Y", true),
    NO("N", false);

    private final String representation;
    private final boolean value;

    YesOrNo(String representation, boolean value) {
        this.representation = representation;
        this.value = value;
    }

    public static YesOrNo findByRepresentation(String representation) {
        for (YesOrNo field : YesOrNo.values()) {
            if (field.representation.equalsIgnoreCase(representation)) {
                return field;
            }
        }
        throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
    }

    public boolean judge() {
        return this.value;
    }
}
