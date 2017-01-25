package com.citizen.calculator2017.calclib;


import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.utils.NumberFormatter;

public class InputType implements IappConstants {
    private final int MAX_INPUT_LENGTH;
    public boolean decimalPresent;
    String inputStr;

    public InputType() {
        this.MAX_INPUT_LENGTH = 10;
        this.decimalPresent = false;
        this.inputStr = BuildConfig.FLAVOR;
    }

    public void clearAll() {
        this.inputStr = BuildConfig.FLAVOR;
        this.decimalPresent = false;
    }

    public void backspace() {
        int len = this.inputStr.length();
        if (len > 1) {
            String str = this.inputStr.substring(0, len - 1);
            this.inputStr = str;
            if (str.equalsIgnoreCase("-")) {
                clearAll();
            }
            this.decimalPresent = checkForDecimalPoint();
            return;
        }
        clearAll();
    }

    void traceLog(String str) {
        System.out.println(str);
    }

    public boolean append(String str) {
        if (str.equalsIgnoreCase("00") && isMaxSizeReached(str)) {
            str = "0";
        }
        if (isMaxSizeReached(str)) {
            return false;
        }
        this.inputStr += str;
        return true;
    }

    public boolean appendDecimalPoint() {
        if (this.decimalPresent) {
            return false;
        }
        if (getValue(this.inputStr) == 0.0d) {
            this.inputStr = "0";
        }
        this.inputStr += ".";
        this.decimalPresent = true;
        return true;
    }

    public void setStr(String str) {
        this.inputStr = getFormattedDoubleString(str);
        if (!str.isEmpty()) {
            if (getValue(this.inputStr) == 0.0d) {
                this.inputStr = BuildConfig.FLAVOR;
            } else {
                this.decimalPresent = checkForDecimalPoint();
            }
        }
    }

    public void setStr(double d) {
        this.inputStr = getFormattedDoubleString(d);
        if (d == 0.0d) {
            this.inputStr = BuildConfig.FLAVOR;
        } else {
            this.decimalPresent = checkForDecimalPoint();
        }
    }

    private String getFormattedDoubleString(double d) {
        if (d == 0.0d) {
            return BuildConfig.FLAVOR;
        }
        String str = NumberFormatter.formatDoubleWithoutScientificNotation(d).replace(",", ".");
        if (str.endsWith(".0")) {
            return str.substring(0, str.length() - 2);
        }
        return str;
    }

    private String getFormattedDoubleString(String str) {
        if (str == null) {
            return BuildConfig.FLAVOR;
        }
        if (str.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        str = NumberFormatter.formatDoubleWithoutScientificNotation(str);
        if (str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        if (str.length() > 10) {
            str = str.substring(0, 10);
        }
        return str;
    }

    private boolean checkForDecimalPoint() {
        if (this.inputStr.contains(".")) {
            return true;
        }
        return false;
    }

    private boolean isMaxSizeReached(String str) {
        int maxlen = 10;
        if (this.decimalPresent) {
            maxlen = 10 + 1;
        }
        if (this.inputStr.length() + str.length() > maxlen) {
            return true;
        }
        return false;
    }

    public double getDoubleFromStr() {
        return getValue(this.inputStr);
    }

    String getClassName() {
        return getClass().getSimpleName();
    }

    public String getInputStr() {
        if (this.inputStr.isEmpty()) {
            return "0";
        }
        return this.inputStr;
    }

    private double getValue(String str) {
        if (str == null || str.isEmpty()) {
            return 0.0d;
        }
        return Double.parseDouble(str);
    }
}
