package com.citizen.calculator2017.calclib;

import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.DrawerLayout;
import java.util.ArrayList;

import com.citizen.calculator2017.R;
import com.citizen.calculator2017.utils.ListElement;
import com.citizen.calculator2017.utils.PrintTapeClass;
import com.citizen.calculator2017.utils.ProductOut;

public class CorrectHandler implements IappConstants, IsystemStates {
    private ListElement Listobject;
    private int checkMode;
    private HistoryManager historyMgr;
    private ArrayList<ListElement> list;
    private OperationType mAdd;
    double mAnswer;
    String mAnswerString;
    private int mCheckStep;
    private OperationType mCurrentInputType;
    private OperationType mDiv;
    private OperationType mMarkup;
    private OperationType mMul;
    private OperationType mSub;
    ProductOut result;
    private InputType userInput;

    public CorrectHandler() {
        this.checkMode = 0;
        this.userInput = new InputType();
        this.mCheckStep = -1;
        this.historyMgr = HistoryManager.getHistoryManager();
        this.result = new ProductOut();
        this.mAnswerString = BuildConfig.FLAVOR;
        this.mAdd = new Addition();
        this.mSub = new Subtraction();
        this.mMul = new Multiplication();
        this.mDiv = new Division();
        this.mMarkup = new Markup();
    }

    public void init() {
    }

    private void setCheckMode(int mode) {
        this.checkMode = mode;
    }

    private void traceLog(String str) {
        System.out.println(str);
    }

    void handleInput(char ch) {
        String str;
        if ('\u0006' == ch) {
            str = "00";
        } else {
            str = BuildConfig.FLAVOR + ch;
        }
        if (true == this.userInput.append(str)) {
            updateOutputString();
        } else {
            traceLog("failed To input char");
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

    private String getDoubleString(double d) {
        if (d == 0.0d) {
            return "0";
        }
        if (d != ((double) ((long) d))) {
            return String.format("%s", new Object[]{Double.valueOf(d)});
        } else if (this.userInput.decimalPresent) {
            return String.format("%d", new Object[]{Long.valueOf((long) d)}) + ".";
        } else {
            return String.format("%d", new Object[]{Long.valueOf((long) d)});
        }
    }

    public ProductOut getResult() {
        updateResult();
        return this.result;
    }

    private void updateResult() {
        if (this.checkMode == 1) {
            updateResultForCheckMode();
            return;
        }
        this.result.checkMode = this.checkMode;
        this.result.answer = formatAnswerString(this.mAnswerString);
        this.result.LeftOperand = "0";
        this.result.operation = this.Listobject.operation;
        this.result.StepNum = getStepNum(this.mCheckStep);
    }

    private String getStepNum(int step) {
        String str = Integer.valueOf(step + 1).toString();
        if (str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    private String formatAnswerString(String str) {
        if (str == null) {
            return "0";
        }
        if (str.isEmpty()) {
            return "0";
        }
        return str;
    }

    private void updateResultForCheckMode() {
        this.result.checkMode = this.checkMode;
        this.result.answer = this.Listobject.operand;
        this.result.LeftOperand = BuildConfig.FLAVOR;
        this.result.operation = this.Listobject.operation;
        this.result.StepNum = getStepNum(this.mCheckStep);
    }

    public boolean handleCheckPlus(int mStep) {
        if (this.checkMode == 0) {
            this.checkMode = 1;
            this.list = PrintTapeClass.getListFromHistory(this.historyMgr.getHistory());
            this.mCheckStep = -1;
        }
        if (this.mCheckStep + 1 >= this.list.size()) {
            return true;
        }
        this.mCheckStep++;
        this.Listobject = (ListElement) this.list.get(this.mCheckStep);
        return false;
    }

    public boolean handleCheckMinus(int mStep) {
        if (this.checkMode == 0) {
            this.checkMode = 1;
            this.list = PrintTapeClass.getListFromHistory(this.historyMgr.getHistory());
            this.mCheckStep = -1;
        }
        if (this.mCheckStep <= 0) {
            return true;
        }
        this.mCheckStep--;
        this.Listobject = (ListElement) this.list.get(this.mCheckStep);
        return false;
    }

    void exitCheckMode() {
        this.mCheckStep = 0;
        this.checkMode = 0;
        traceLog("checkmode exit called");
        this.userInput.clearAll();
    }

    private void commitChanges() {
        this.historyMgr.editElement(this.userInput.getDoubleFromStr(), this.Listobject.indexInHistory);
        this.mAnswer = evalulateHistory();
        calculatorFactory.setAnswer(Double.valueOf(this.mAnswer));
        this.list = PrintTapeClass.getListFromHistory(this.historyMgr.getHistory());
        this.Listobject = (ListElement) this.list.get(this.mCheckStep);
    }

    public void handleCorrectButton() {
        if (this.checkMode == 1) {
            this.checkMode = 2;
            clearAll();
        } else if (this.checkMode == 2) {
            this.checkMode = 1;
            commitChanges();
        }
    }

    private void clearAll() {
        this.mAnswer = 0.0d;
        this.mAnswerString = BuildConfig.FLAVOR;
        this.userInput.clearAll();
    }

    private String putBraces(String str) {
        return "(" + str + ")";
    }

    void handleOperation(String op) {
        Object obj = -1;
        switch (op.hashCode()) {
            case R.styleable.Theme_actionButtonStyle /*43*/:
                if (op.equals("+")) {
                    obj = null;
                    break;
                }
                break;
            case R.styleable.Theme_buttonBarButtonStyle /*45*/:
                if (op.equals("-")) {
                    obj = 1;
                    break;
                }
                break;
            case 120:
                if (op.equals("x")) {
                    obj = 2;
                    break;
                }
                break;
            case 247:
                if (op.equals("\u00f7")) {
                    obj = 3;
                    break;
                }
                break;
            case 2472:
                if (op.equals("MU")) {
                    obj = 4;
                    break;
                }
                break;
        }
        switch (obj) {
            case SpinnerCompat.MODE_DIALOG /*0*/:
                this.mCurrentInputType = this.mAdd;
                return;
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
                this.mCurrentInputType = this.mSub;
                return;
            case DrawerLayout.STATE_SETTLING /*2*/:
                this.mCurrentInputType = this.mMul;
                return;
            case WearableExtender.SIZE_MEDIUM /*3*/:
                this.mCurrentInputType = this.mDiv;
                return;
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                this.mCurrentInputType = this.mMarkup;
                break;
        }
        this.mCurrentInputType = this.mAdd;
    }

    public double evalulateHistory() {
        try {
            int len = this.historyMgr.getlength();
            if (len == 0) {
                return 0.0d;
            }
            HistoryElements hObject = this.historyMgr.getElement(0);
            double left = hObject.operand.doubleValue();
            String op = hObject.operation;
            if (op.isEmpty()) {
                return left;
            }
            handleOperation(op);
            for (int i = 1; i < len; i++) {
                double right;
                hObject = this.historyMgr.getElement(i);
                if (hObject.prevOperation.isEmpty()) {
                    right = 0.0d;
                    left = hObject.operand.doubleValue();
                    handleOperation(hObject.operation);
                } else {
                    right = hObject.operand.doubleValue();
                }
                if (hObject.percentageMode != 0) {
                    double val = this.mCurrentInputType.getPercentageValue(left, right, hObject.percentageMode);
                    hObject.percentageAmount = Math.abs(left - val);
                    left = val;
                    hObject.computedAnswer = left;
                    if (!hObject.operation.equalsIgnoreCase("MU")) {
                        handleOperation(hObject.operation);
                    }
                    handleOperation(hObject.operation);
                } else {
                    left = this.mCurrentInputType.getValue(left, right);
                    if (hObject.isAnswer) {
                        hObject.computedAnswer = left;
                    }
                    handleOperation(hObject.operation);
                }
            }
            traceLog("After correct answer is " + left);
            hObject.computedAnswer = left;
            return left;
        } catch (Exception e) {
            return Double.POSITIVE_INFINITY;
        }
    }

    void handleDecimalPoint() {
        this.userInput.appendDecimalPoint();
        updateOutputString();
    }

    public void clearCurrentString() {
        this.userInput.clearAll();
        updateOutputString();
    }
}
