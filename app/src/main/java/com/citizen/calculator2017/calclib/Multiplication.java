package com.citizen.calculator2017.calclib;

public class Multiplication extends OperationType {
    double getValue(double mLeftOperand, double mRightOperand) {
        return mLeftOperand * mRightOperand;
    }

    public String getoperationString() {
        return "x";
    }

    double getPercentageValue(double mLeftOperand, double mRightOperand, int mode) {
        return super.getPercentageValue(mLeftOperand, mRightOperand, mode);
    }
}
