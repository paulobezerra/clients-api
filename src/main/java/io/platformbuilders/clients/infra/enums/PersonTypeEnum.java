package io.platformbuilders.clients.infra.enums;

import java.util.Optional;

public enum PersonTypeEnum {
    NATURAL("FISICA"),
    LEGAL("JURIDICA");

    private final String value;

    PersonTypeEnum(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }


}
