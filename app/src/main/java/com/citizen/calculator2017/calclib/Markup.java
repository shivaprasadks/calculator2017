package com.citizen.calculator2017.calclib;

public class Markup extends OperationType {
    double getValue(double mLeftOperand, double mRightOperand) {
        return getMU(mLeftOperand, mRightOperand);
    }

    public String getoperationString() {
        return "MU";
    }

    double getPercentageValue(double mLeftOperand, double mRightOperand, int mode) {
        return getMU(mLeftOperand, mRightOperand);
    }

    private double getMU(double f, double d) {
        d = 1.0d - (d / 100.0d);
        if (d != 0.0d) {
            return f / d;
        }
        return 0.0d;
    }
}
