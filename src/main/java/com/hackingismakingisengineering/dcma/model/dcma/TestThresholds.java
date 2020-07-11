package com.hackingismakingisengineering.dcma.model.dcma;

public enum TestThresholds {

    TEN(0.10),
    ZERO(0.00),
    FIVE(0.05),
    NINTY_FIVE(0.95),
    FLOAT(40),
    DURATION(40);


    private Double v;

    TestThresholds(double v) {
        this.v = v;
    }


    public Double getValue() {
        return v;
    }
}
