package com.citizen.calculator2017.calclib;

import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.utils.ProductOut;

public class SetTaxRateHandler implements IsystemStates, IappConstants {
    double mAnswer;
    String mAnswerString;
    ProductOut result;
    InputType userInput;

    public SetTaxRateHandler() {
        this.userInput = new InputType();
        this.result = new ProductOut();
        this.mAnswer = 0.0d;
        this.mAnswerString = BuildConfig.FLAVOR;
    }

    public void handleInput(char ch) {
        String str;
        if ('\u0006' == ch) {
            str = "00";
        } else {
            str = BuildConfig.FLAVOR + ch;
        }
        if (true == this.userInput.append(str)) {
            updateOutputString();
        }
    }

    private String getDoubleString(double d) {
        if (d == 0.0d) {
            return BuildConfig.FLAVOR;
        }
        if (d != ((double) ((long) d))) {
            return String.format("%s", new Object[]{Double.valueOf(d)});
        } else if (this.userInput.decimalPresent) {
            return String.format("%d", new Object[]{Long.valueOf((long) d)}) + ".";
        } else {
            return String.format("%d", new Object[]{Long.valueOf((long) d)});
        }
    }

    private void updateOutputString() {
        this.mAnswer = this.userInput.getDoubleFromStr();
        if (this.mAnswer != 0.0d) {
            this.mAnswerString = getDoubleString(this.mAnswer);
        } else if (this.userInput.decimalPresent) {
            this.mAnswerString = this.userInput.getInputStr();
        } else {
            this.mAnswerString = "0";
        }
    }

    public ProductOut getResult() {
        updateResult();
        return this.result;
    }

    private void updateResult() {
        this.result.checkMode = 0;
        this.result.answer = formatAnswerString(this.mAnswerString);
        this.result.LeftOperand = BuildConfig.FLAVOR;
        this.result.operation = BuildConfig.FLAVOR;
        this.result.StepNum = BuildConfig.FLAVOR;
    }

    private String formatAnswerString(String str) {
        if (str.isEmpty()) {
            return "0";
        }
        if (str.length() > 12) {
            str = str.subSequence(0, 12).toString();
        }
        return str;
    }

    public void handleDecimalPoint() {
        this.userInput.appendDecimalPoint();
        updateOutputString();
    }

    public void backspace() {
        this.userInput.backspace();
        updateOutputString();
    }

    public double getRate() {
        double rate = this.userInput.getDoubleFromStr();
        this.userInput.clearAll();
        return rate;
    }

    public void clearCurrent() {
        this.userInput.clearAll();
        updateOutputString();
    }

    public void setStr(double mtaxRate) {
        this.userInput.setStr(mtaxRate);
        updateOutputString();
    }
}
