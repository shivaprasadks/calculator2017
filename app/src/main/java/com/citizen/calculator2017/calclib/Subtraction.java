package com.citizen.calculator2017.calclib;

import android.support.v4.widget.DrawerLayout;

import static android.widget.Spinner.MODE_DROPDOWN;

public class Subtraction extends OperationType implements IappConstants {
    double getValue(double mLeftOperand, double mRightOperand) {
        return mLeftOperand - mRightOperand;
    }

    public String getoperationString() {
        return "-";
    }

    double getPercentageValue(double mLeftOperand, double mRightOperand, int mode) {
        switch (mode) {
            case MODE_DROPDOWN /*1*/:
                return mLeftOperand - super.getPercentageValue(mLeftOperand, mRightOperand, mode);
            case DrawerLayout.STATE_SETTLING /*2*/:
                return mLeftOperand / (1.0d + (mRightOperand / 100.0d));
            default:
                return 0.0d;
        }
    }
}
