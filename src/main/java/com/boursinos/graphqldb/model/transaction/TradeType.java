package com.boursinos.graphqldb.model.transaction;

public enum TradeType {
    BUY("buy"),
    SELL("sell");
    private final String value;

    TradeType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
