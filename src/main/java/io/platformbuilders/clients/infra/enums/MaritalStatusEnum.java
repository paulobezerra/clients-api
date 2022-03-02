package io.platformbuilders.clients.infra.enums;

public enum MaritalStatusEnum {
    MARRIED("CASADO"),
    WIDOWED("VIÚVO"),
    SEPARATED("SEPARADO"),
    DIVORCED("DIVORCIADO"),
    SINGLE("SOLTEIRO");

    private final String value;

    MaritalStatusEnum(String value) {
        this.value = value;
    }

    public boolean equals(String value) {
        return this.value.equals(value.toUpperCase());
    }

    @Override
    public String toString() {
        return this.value;
    }

}
