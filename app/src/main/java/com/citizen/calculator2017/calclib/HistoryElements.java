package com.citizen.calculator2017.calclib;


import com.citizen.calculator2017.BuildConfig;

public class HistoryElements implements IappConstants {
    public String comment;
    public String commentOnAnswerNode;
    public double computedAnswer;
    public boolean isAnswer;
    public Double operand;
    public String operation;
    public double percentageAmount;
    public int percentageMode;
    public String prevOperation;

    public HistoryElements() {
        this.operation = BuildConfig.FLAVOR;
        this.prevOperation = BuildConfig.FLAVOR;
        this.operand = Double.valueOf(0.0d);
        this.percentageMode = 0;
        this.isAnswer = false;
        this.computedAnswer = 0.0d;
        this.percentageAmount = 0.0d;
        this.comment = BuildConfig.FLAVOR;
        this.commentOnAnswerNode = BuildConfig.FLAVOR;
    }

    public HistoryElements(HistoryElements h) {
        this.operation = h.operation;
        this.prevOperation = h.prevOperation;
        this.operand = h.operand;
        this.percentageMode = h.percentageMode;
        this.isAnswer = h.isAnswer;
        this.percentageAmount = h.percentageAmount;
        this.computedAnswer = h.computedAnswer;
        this.comment = h.comment;
        this.commentOnAnswerNode = h.commentOnAnswerNode;
    }
}
