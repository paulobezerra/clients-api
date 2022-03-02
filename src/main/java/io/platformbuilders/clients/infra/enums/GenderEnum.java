package io.platformbuilders.clients.infra.enums;

public enum GenderEnum {
    MALE("MASCULINO"),
    FEMALE("FEMININO");

    private final String value;

    GenderEnum(String value) {
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
