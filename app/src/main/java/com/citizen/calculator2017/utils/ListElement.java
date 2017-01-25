package com.citizen.calculator2017.utils;

import android.util.Log;

import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.calclib.calculatorFactory;


public class ListElement implements IappConstants {
    String ansColor;
    public String comment;
    String commentColor;
    String correctionColor;
    public int indexInHistory;
    public boolean isAnswer;
    public String lineNumber;
    String opGreyColor;
    String opRedColor;
    public String operand;
    String operandColor;
    public String operation;
    public int percentMode;
    public double percentageAmount;
    public double val;

    public ListElement() {
        this.isAnswer = false;
        this.percentMode = 0;
        this.percentageAmount = 0.0d;
        this.ansColor = "00e676";
        this.operandColor = "66D9EF";
        this.commentColor = "E6DB74";
        this.opRedColor = "FF851B";
        this.opGreyColor = "464646";
        this.correctionColor = "F92672";
        setDefaults();
    }

    private String getCommentColor() {
        if (calculatorFactory.getIndianFormatType() == 2) {
            return this.commentColor;
        }
        return this.opGreyColor;
    }

    private void setDefaults() {
        this.operation = BuildConfig.FLAVOR;
        this.operand = BuildConfig.FLAVOR;
        this.comment = BuildConfig.FLAVOR;
        this.val = 0.0d;
        this.lineNumber = BuildConfig.FLAVOR;
        this.isAnswer = false;
        this.indexInHistory = -1;
        this.percentMode = 0;
        this.percentageAmount = 0.0d;
    }

    public ListElement(String opr) {
        this.isAnswer = false;
        this.percentMode = 0;
        this.percentageAmount = 0.0d;
        this.ansColor = "00e676";
        this.operandColor = "66D9EF";
        this.commentColor = "E6DB74";
        this.opRedColor = "FF851B";
        this.opGreyColor = "464646";
        this.correctionColor = "F92672";
        setDefaults();
        this.operand = opr;
    }

    public void clearlistElement() {
        setDefaults();
    }

    public String getPrevOperation() {
        String color = this.operandColor;
        if (this.val < 0.0d || this.operation.equalsIgnoreCase("-")) {
            color = this.opRedColor;
        }
        if (calculatorFactory.getIndianFormatType() != 2) {
            return this.operation;
        }
        return htmlcolor(this.operation, color);
    }

    public String getLineNum() {
        String color = getCommentColor();
        if (calculatorFactory.getIndianFormatType() != 2) {
            return this.lineNumber;
        }
        return htmlcolor(this.lineNumber, color);
    }

    private String getStepNum(String str) {
        if (!str.isEmpty() && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    public String getDispStr() {
        return prepareDispStr();
    }

    public String getComment() {
        return htmlcolor(this.comment, this.commentColor);
    }

    private String prepareDispStr() {
        if (this.operand.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        String color = this.operandColor;
        if (this.val < 0.0d || this.operation.equalsIgnoreCase("-")) {
            color = this.opRedColor;
        }
        if (calculatorFactory.getIndianFormatType() != 2) {
            return nonHtmlString();
        }
        String str = htmlcolor(this.operand, color);
        if (this.percentMode != 0) {
            str = htmlcolor(str + "%", color);
        }
        if (this.isAnswer) {
            return htmlBold(htmlcolor(str, getAnswerColor()));
        }
        return str;
    }

    private String nonHtmlString() {
        String str = this.operand;
        if (this.percentMode == 1) {
            return str + "%";
        }
        if (this.percentMode == 2) {
            return str + "%(Tax)";
        }
        return str;
    }

    private String getAnswerColor() {
        if (calculatorFactory.getIndianFormatType() != 2) {
            return this.opGreyColor;
        }
        return this.ansColor;
    }

    private String htmlNewLine() {
        return "<br/>";
    }

    private String htmlColorln(String str, String clr) {
        return "<font color=\"#" + clr + "\">" + str + "</font><br />";
    }

    String htmlhrLine() {
        return "<hr>";
    }

    private String htmlBold(String str) {
        return "<b>" + str + "</b>";
    }

    private String htmlBoldln(String str) {
        return "<b>" + str + "</b><br/>";
    }

    private String htmlcolor(String str, String clr) {
        return "<font color=\"#" + clr + "\">" + str + "</font>";
    }

    private void traceLog(String str) {
        Log.d("ABG", str);
    }
}
