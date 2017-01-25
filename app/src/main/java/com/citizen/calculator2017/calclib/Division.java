package com.citizen.calculator2017.calclib;

public class Division extends OperationType {
    double getValue(double mLeftOperand, double mRightOperand) {
        if (mRightOperand != 0.0d) {
            return mLeftOperand / mRightOperand;
        }
        return Double.POSITIVE_INFINITY;
    }

    public String getoperationString() {
        return "\u00f7";
    }

    double getPercentageValue(double mLeftOperand, double mRightOperand, int mode) {
        return getValue(mLeftOperand, mRightOperand) * 100.0d;
    }
}
