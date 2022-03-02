package io.platformbuilders.clients.infra.enums;

public enum LocationTypeEnum {
    COUNTRY("PAIS"),
    STATE("ESTADO"),
    CITY("CIDADE");

    private final String value;

    LocationTypeEnum(String value) {
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
