package com.citizen.calculator2017.utils;

public class PersistableValues {
    double gt;
    boolean indianFormat;
    boolean mButtonSoundFlag;
    boolean mVibrateFlag;
    boolean startup;
    double taxRate;

    public PersistableValues() {
        this.mButtonSoundFlag = true;
        this.mVibrateFlag = true;
        this.indianFormat = true;
        this.startup = true;
        this.taxRate = 10.0d;
        this.gt = 0.0d;
    }
}
