package com.citizen.calculator2017.calclib;


import com.citizen.calculator2017.BuildConfig;

public abstract class OperationType {
    abstract double getValue(double d, double d2);

    public abstract String getoperationString();

    String getDoubleString(double d) {
        if (d == 0.0d) {
            return BuildConfig.FLAVOR;
        }
        if (d == ((double) ((long) d))) {
            return String.format("%d", new Object[]{Long.valueOf((long) d)});
        }
        return String.format("%s", new Object[]{Double.valueOf(d)});
    }

    double getPercentageValue(double mLeftOperand, double mRightOperand, int mode) {
        if (mRightOperand != 0.0d) {
            return (mRightOperand * 0.01d) * mLeftOperand;
        }
        return 0.01d * mLeftOperand;
    }
}
