package com.citizen.calculator2017.calclib;

public class Addition extends OperationType implements IappConstants {
    double getValue(double mLeftOperand, double mRightOperand) {
        return mLeftOperand + mRightOperand;
    }

    public String getoperationString() {
        return "+";
    }

    double getPercentageValue(double mLeftOperand, double mRightOperand, int mode) {
        return mLeftOperand + super.getPercentageValue(mLeftOperand, mRightOperand, mode);
    }
}
