package com.citizen.calculator2017.calclib;

import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.DrawerLayout;
import com.citizen.calculator2017.utils.UIappConstants;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.citizen.calculator2017.utils.ParametersFromHistory;
import com.citizen.calculator2017.utils.ProductOut;

public class calculatorFactory implements IappConstants, IsystemStates {
    private static int formatType;
    public static Double mAnswer;
    static calculatorFactory onlyObject;
    private int checkMode;
    private CorrectHandler correctHandler;
    private HistoryManager historyMgr;
    private HistoryElements historyObject;
    private boolean isAnswer;
    private OperationType mAdd;
    private String mAnswerString;
    private int mCheckStep;
    private OperationType mCurrentInputType;
    private OperationType mDiv;
    private Double mGt;
    private Double mLeftOperand;
    private OperationType mMarkup;
    private OperationType mMul;
    private OperationType mOperation;
    private Double mRightOperand;
    private int mStep;
    private OperationType mSub;
    private double mTaxRate;
    private double memory;
    private boolean memoryRecalled;
    private int operationMode;
    private String operationString;
    private boolean percentageCalculated;
    private double percentageLeftOperand;
    private OperationType prevOperation;
    private double prevRightOperand;
    private ProductOut result;
    private double taxCalculated;
    private boolean userClearingInput;
    private InputType userInput;

    static {
        formatType = 0;
    }

    public static calculatorFactory getCalcFactory() {
        if (onlyObject != null) {
            return onlyObject;
        }
        onlyObject = new calculatorFactory();
        return onlyObject;
    }

    private calculatorFactory() {
        this.operationMode = 0;
        this.checkMode = 0;
        this.isAnswer = false;
        this.mTaxRate = 10.0d;
        this.operationString = BuildConfig.FLAVOR;
        this.mAnswerString = BuildConfig.FLAVOR;
        this.memory = 0.0d;
        this.memoryRecalled = false;
        this.percentageCalculated = false;
        this.percentageLeftOperand = 0.0d;
        this.taxCalculated = 0.0d;
        this.mGt = Double.valueOf(0.0d);
        initFactory();
    }

    public void initFactory() {
        this.operationMode = 0;
        this.result = new ProductOut();
        this.mCurrentInputType = null;
        this.mAdd = new Addition();
        this.mSub = new Subtraction();
        this.mMul = new Multiplication();
        this.mDiv = new Division();
        this.mMarkup = new Markup();
        this.userInput = new InputType();
        this.mLeftOperand = Double.valueOf(0.0d);
        this.mStep = 0;
        this.mRightOperand = Double.valueOf(0.0d);
        mAnswer = Double.valueOf(0.0d);
        this.mAnswerString = BuildConfig.FLAVOR;
        this.mCheckStep = -1;
        this.historyMgr = HistoryManager.getHistoryManager();
        this.historyMgr.init();
        this.correctHandler = new CorrectHandler();
        this.correctHandler.init();
    }

    static void traceLog(String str) {
        System.out.println(str);
    }

    public double getMemValue() {
        return this.memory;
    }

    public int getOperationMode() {
        return this.operationMode;
    }

    public void handleInput(char ch) {
        this.percentageCalculated = false;
        if (this.checkMode != 0) {
            this.correctHandler.handleInput(ch);
            return;
        }
        String str;
        if (this.operationMode == 2) {
            this.operationMode = 0;
            this.operationString = BuildConfig.FLAVOR;
        }
        if ('\u0006' == ch) {
            str = "00";
        } else {
            str = BuildConfig.FLAVOR + ch;
        }
        if (true == this.userInput.append(str)) {
            updateOutputString();
        }
    }

    public void refreshFromHistory() {
        if (this.operationMode == 1) {
            this.mLeftOperand = Double.valueOf(this.correctHandler.evalulateHistory());
        } else {
            mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        }
        updateOutputString(mAnswer.doubleValue());
    }

    public void setIndianFormat(boolean b) {
        if (b) {
            formatType = 0;
        } else {
            formatType = 1;
        }
    }

    public void setIndianFormat(int type) {
        switch (type) {
            case SpinnerCompat.MODE_DIALOG /*0*/:
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
            case DrawerLayout.STATE_SETTLING /*2*/:
                formatType = type;
            default:
                formatType = 0;
        }
    }

    public static int getIndianFormatType() {
        return formatType;
    }

    public boolean isMemoryPresent() {
        if (this.memory != 0.0d) {
            return true;
        }
        return false;
    }

    void checkOperationModeComplete() {
        this.memoryRecalled = false;
        if (this.operationMode != 2) {
        }
    }

    void resetAll() {
        clearAll();
    }

    public String getTaxCalculated() {
        return formatDecStr(this.taxCalculated);
    }

    public void handleDecimalPoint() {
        if (this.checkMode != 0) {
            this.correctHandler.handleDecimalPoint();
            return;
        }
        checkOperationModeComplete();
        this.userInput.appendDecimalPoint();
        updateOutputString();
    }

    public void handleGTbutton() {
        if (this.operationMode == 1 && this.userInput.inputStr.isEmpty()) {
            this.userInput.setStr(this.mGt.doubleValue());
        }
        updateOutputString();
        updateResult();
    }

    public int getSteps() {
        return this.mStep;
    }

    private void updateOutputString() {
        mAnswer = Double.valueOf(this.userInput.getDoubleFromStr());
        if (mAnswer.doubleValue() == 0.0d) {
            if (this.userInput.decimalPresent) {
                this.mAnswerString = this.userInput.getInputStr();
            } else if (1 == this.operationMode && this.userInput.inputStr.isEmpty() && !this.userClearingInput) {
                this.mAnswerString = getDoubleString(this.mLeftOperand.doubleValue());
            } else {
                this.mAnswerString = "0";
            }
        } else if (this.userInput.decimalPresent && this.userInput.getInputStr().endsWith("0")) {
            this.mAnswerString = this.userInput.getInputStr();
        } else {
            this.mAnswerString = getDoubleString(mAnswer.doubleValue());
        }
    }

    private String getDoubleStringNoFormat(double d) {
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

    private void updateOutputString(double d) {
        mAnswer = Double.valueOf(d);
        if (mAnswer.doubleValue() == 0.0d) {
            if (this.userInput.decimalPresent) {
                this.mAnswerString = this.userInput.getInputStr();
            } else if (1 == this.operationMode && this.userInput.inputStr.isEmpty()) {
                this.mAnswerString = getDoubleString(this.mLeftOperand.doubleValue());
            } else {
                this.mAnswerString = "0";
            }
        } else if (this.userInput.decimalPresent && this.userInput.getInputStr().endsWith("0")) {
            this.mAnswerString = this.userInput.getInputStr();
        } else {
            this.mAnswerString = getDoubleString(mAnswer.doubleValue());
        }
    }

    public String formatDecStr(double decNum) {
        DecimalFormat decimalFormat = new DecimalFormat(getFormatString(decNum, formatType));
        if (decNum != 0.0d) {
            return decimalFormat.format(decNum);
        }
        return "0";
    }

    private String getFormatString(double d, int indianFormat) {
        switch (indianFormat) {
            case SpinnerCompat.MODE_DIALOG /*0*/:
                return getFormatString(d);
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
                return getInternationalFormatString(d);
            case DrawerLayout.STATE_SETTLING /*2*/:
                return getInternationalFormatString(d);
            default:
                return getFormatString(d);
        }
    }

    private String getTapeCalcFormatString(double d) {
        switch (getDoubleStringNoFormat(d).indexOf(".")) {
            case SpinnerCompat.MODE_DIALOG /*0*/:
                return ".##";
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
                return "#.##";
            case DrawerLayout.STATE_SETTLING /*2*/:
                return "##.##";
            case WearableExtender.SIZE_MEDIUM /*3*/:
                return "###.##";
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                return "#,###.##";
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return "##,###.##";
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return "###,###.##";
            case UIappConstants.NO_OPERATION /*7*/:
                return "###,###.##";
            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                return "###,###.##";
            case UIappConstants.BUTTON_8 /*9*/:
                return "###,###.##";
            default:
                return "###,###.##";
        }
    }

    private String getInternationalFormatString(double d) {
        switch (getDoubleStringNoFormat(d).indexOf(".")) {
            case SpinnerCompat.MODE_DIALOG /*0*/:
                return ".##########";
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
                return "#.#########";
            case DrawerLayout.STATE_SETTLING /*2*/:
                return "##.########";
            case WearableExtender.SIZE_MEDIUM /*3*/:
                return "###.#######";
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                return "#,###.######";
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return "##,###.#####";
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return "###,###.####";
            case UIappConstants.NO_OPERATION /*7*/:
                return "###,###.###";
            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                return "###,###.##";
            case UIappConstants.BUTTON_8 /*9*/:
                return "###,###.#";
            default:
                return "###,###,###";
        }
    }

    private String getFormatString(double d) {
        switch (getDoubleStringNoFormat(d).indexOf(".")) {
            case SpinnerCompat.MODE_DIALOG /*0*/:
                return ".##########";
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
                return "#.#########";
            case DrawerLayout.STATE_SETTLING /*2*/:
                return "##.########";
            case WearableExtender.SIZE_MEDIUM /*3*/:
                return "###.#######";
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                return "#,###.######";
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return "##,###.#####";
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return "#,##,###.####";
            case UIappConstants.NO_OPERATION /*7*/:
                return "##,##,###.###";
            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                return "#,##,##,###.##";
            case UIappConstants.BUTTON_8 /*9*/:
                return "##,##,##,###.#";
            default:
                return "#,##,##,##,###";
        }
    }

    private void updateResult() {
        traceLog("update result called");
        if (this.checkMode == 1) {
            this.result = this.correctHandler.getResult();
            return;
        }
        this.result.checkMode = this.checkMode;
        if (mAnswer.doubleValue() != 0.0d) {
            this.mAnswerString = getDoubleString(mAnswer.doubleValue());
        }
        this.result.answer = formatAnswerString(this.mAnswerString);
        this.result.LeftOperand = getDoubleString(this.mLeftOperand.doubleValue());
        this.result.operation = this.operationString;
        this.result.StepNum = getStepNum(this.mStep);
        if (formatType == 2) {
            if (this.operationMode == 1 && this.userInput.inputStr.isEmpty()) {
                this.result.answer = BuildConfig.FLAVOR;
            }
            if (this.operationMode == 0 && this.userInput.inputStr.isEmpty()) {
                this.result.answer = BuildConfig.FLAVOR;
            }
        }
    }

    private String formatAnswerString(String str) {
        if (str.equalsIgnoreCase("Inf")) {
            return "Inf";
        }
        if (str.isEmpty()) {
            return "0";
        }
        if (str.length() > 14) {
            str = str.subSequence(0, 14).toString();
        }
        return str;
    }

    private String getStepNum(int step) {
        if (this.result.operation.equalsIgnoreCase("=")) {
            step++;
        }
        if (step == 0 && this.userInput.inputStr.isEmpty()) {
            return "00";
        }
        String str = Integer.valueOf(step + 1).toString();
        if (str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    private String getDoubleString(double d) {
        if (d == Double.POSITIVE_INFINITY) {
            return "Inf";
        }
        if (d == 0.0d) {
            return BuildConfig.FLAVOR;
        }
        String str = formatDecStr(d);
        String temp;
        if (d != ((double) ((long) d))) {
            temp = new String(this.userInput.inputStr);
            while (temp.endsWith("0")) {
                temp = temp.substring(0, temp.length() - 1);
                str = str + "0";
            }
            return str;
        } else if (!this.userInput.decimalPresent) {
            return str;
        } else {
            str = str + ".";
            if (!this.userInput.inputStr.endsWith("0")) {
                return str;
            }
            temp = new String(this.userInput.inputStr);
            while (temp.endsWith("0")) {
                temp = temp.substring(0, temp.length() - 1);
                str = str + "0";
            }
            return str;
        }
    }

    public ArrayList<HistoryElements> getHistory() {
        return this.historyMgr.getHistory();
    }

    public void clearAll() {
        if (this.checkMode == 1) {
            exitCheckMode();
        }
        this.userInput.clearAll();
        this.mStep = 0;
        this.operationMode = 0;
        this.mLeftOperand = Double.valueOf(0.0d);
        this.mRightOperand = Double.valueOf(0.0d);
        mAnswer = Double.valueOf(0.0d);
        this.mAnswerString = BuildConfig.FLAVOR;
        this.operationString = BuildConfig.FLAVOR;
        this.memoryRecalled = false;
        this.percentageCalculated = false;
        this.historyMgr.clearHistory();
    }

    public void handleClearAllButton() {
        this.historyMgr.savehofh();
        clearAll();
    }

    public void backspace() {
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            if (this.checkMode != 0) {
                this.correctHandler.handleCorrectButton();
            } else if (this.operationMode == 2) {
                this.memoryRecalled = false;
            } else {
                this.userInput.backspace();
                this.userClearingInput = true;
                updateOutputString();
            }
        }
    }

    public ProductOut getResult() {
        updateResult();
        return this.result;
    }

    void handlePercentAction(int mode) {
        if (this.checkMode != 0) {
            exitCheckMode();
        } else if (this.operationMode == 1) {
            this.operationString = BuildConfig.FLAVOR;
            this.historyMgr.addElement(this.operationString, this.userInput.getDoubleFromStr());
            this.mRightOperand = Double.valueOf(this.userInput.getDoubleFromStr());
            mAnswer = Double.valueOf(this.mCurrentInputType.getPercentageValue(this.mLeftOperand.doubleValue(), this.mRightOperand.doubleValue(), mode));
            this.historyMgr.setComputedAnswerForLast(mAnswer);
            this.taxCalculated = Math.abs(mAnswer.doubleValue() - this.mLeftOperand.doubleValue());
            this.historyMgr.setPercentagePresent(mode, this.taxCalculated);
            this.operationMode = 2;
            this.mAnswerString = getDoubleString(mAnswer.doubleValue());
            this.percentageCalculated = true;
            this.percentageLeftOperand = this.mLeftOperand.doubleValue();
            this.userInput.clearAll();
        }
    }

    public void handlepercentage() {
        handlePercentAction(1);
    }

    public void handlememPlus() {
        this.memoryRecalled = false;
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            this.memory += mAnswer.doubleValue();
        }
    }

    public void handleNegation() {
        if (this.checkMode == 0) {
            double val = getOperandForUnary();
            if (!isInvalidDouble(val)) {
                mAnswer = Double.valueOf(-1.0d * val);
                this.userInput.setStr(mAnswer.doubleValue());
                updateOutputString();
                updateResult();
            }
        }
    }

    public void handleCheckPlus() {
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            int step = this.mStep;
            if (this.mStep != 0) {
                if (1 == this.operationMode) {
                    exitOperationMode(true);
                }
                if (this.checkMode == 0) {
                    this.checkMode = 1;
                }
                if (this.correctHandler.handleCheckPlus(step)) {
                    exitCheckMode();
                }
            }
        }
    }

    public void handleCheckminus() {
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            int step = this.mStep;
            if (this.mStep != 0) {
                if (1 == this.operationMode) {
                    exitOperationMode(true);
                }
                if (this.checkMode == 0) {
                    this.checkMode = 1;
                }
                if (this.correctHandler.handleCheckMinus(step)) {
                    exitCheckMode();
                }
            }
        }
    }

    public void handlememRecall() {
        if (this.operationMode == 2 || this.checkMode != 0) {
            return;
        }
        if (this.memoryRecalled) {
            this.memory = 0.0d;
            this.memoryRecalled = false;
            return;
        }
        this.memoryRecalled = true;
        this.userInput.setStr(this.memory);
        updateOutputString();
        updateResult();
    }

    public void handleEquals() {
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            if (this.checkMode == 1) {
                exitCheckMode();
                return;
            }
            if (2 == this.operationMode && this.prevOperation != null) {
                handleOperation(getOperationType(this.prevOperation.getoperationString()));
                this.userInput.setStr(this.prevRightOperand);
            }
            if (1 != this.operationMode) {
                return;
            }
            if (this.userInput.inputStr.isEmpty()) {
                exitOperationMode(this.percentageCalculated);
            } else {
                exitOperationMode(true);
            }
        }
    }

    private void exitOperationMode(boolean eval) {
        if (this.checkMode == 1) {
            exitCheckMode();
        } else if (this.operationMode == 1) {
            if (this.userInput.inputStr.isEmpty()) {
                this.historyMgr.editElement(BuildConfig.FLAVOR, this.historyMgr.getlength() - 1);
            } else {
                this.historyMgr.addElement(BuildConfig.FLAVOR, this.userInput.getDoubleFromStr());
            }
            if (eval) {
                if (this.userInput.inputStr.isEmpty()) {
                    this.mRightOperand = this.mLeftOperand;
                    if (this.percentageCalculated) {
                        this.percentageCalculated = false;
                        this.mLeftOperand = Double.valueOf(this.percentageLeftOperand);
                    }
                } else {
                    this.mRightOperand = Double.valueOf(this.userInput.getDoubleFromStr());
                }
                mAnswer = Double.valueOf(this.mCurrentInputType.getValue(this.mLeftOperand.doubleValue(), this.mRightOperand.doubleValue()));
                this.mGt = mAnswer;
            } else {
                mAnswer = this.mLeftOperand;
            }
            this.historyMgr.setComputedAnswerForLast(mAnswer);
            this.operationMode = 2;
            this.mAnswerString = getDoubleString(mAnswer.doubleValue());
            this.operationString = "=";
            this.userInput.clearAll();
        }
    }

    private void exitCheckMode() {
        this.mCheckStep = 0;
        this.checkMode = 0;
        this.operationMode = 2;
        this.correctHandler.exitCheckMode();
        this.mAnswerString = getDoubleString(mAnswer.doubleValue());
        this.operationString = "=";
        this.userInput.clearAll();
    }

    private boolean isInvalidDouble(double val) {
        if (val == Double.NaN || val == Double.POSITIVE_INFINITY || val == Double.NEGATIVE_INFINITY) {
            return true;
        }
        return false;
    }

    public void handleOperation(int op) {
        boolean addToHistory = true;
        this.userClearingInput = false;
        if (this.checkMode != 0) {
            exitCheckMode();
        }
        this.memoryRecalled = false;
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            if (this.operationMode == 1) {
                if (!this.userInput.inputStr.isEmpty()) {
                    this.mRightOperand = Double.valueOf(this.userInput.getDoubleFromStr());
                    this.mLeftOperand = Double.valueOf(this.mCurrentInputType.getValue(this.mLeftOperand.doubleValue(), this.mRightOperand.doubleValue()));
                }
            } else if (this.operationMode == 2) {
                this.mLeftOperand = mAnswer;
                this.historyMgr.editLastElement(getOperationType(op).getoperationString());
                HistoryElements he = this.historyMgr.getLastElement();
                if (he != null) {
                    this.userInput.setStr(he.operand.doubleValue());
                } else {
                    this.userInput.setStr(0.0d);
                }
                this.operationMode = 1;
                addToHistory = false;
            } else {
                this.mLeftOperand = Double.valueOf(this.userInput.getDoubleFromStr());
                this.operationMode = 1;
            }
            this.mCurrentInputType = getOperationType(op);
            this.prevOperation = this.mCurrentInputType;
            this.prevRightOperand = this.userInput.getDoubleFromStr();
            this.operationString = this.mCurrentInputType.getoperationString();
            updateSteps(addToHistory);
            this.userInput.clearAll();
            updateOutputString();
        }
    }

    private OperationType getOperationType(int op) {
        switch (op) {
            case SpinnerCompat.MODE_DROPDOWN /*1*/:
                return this.mAdd;
            case DrawerLayout.STATE_SETTLING /*2*/:
                return this.mSub;
            case WearableExtender.SIZE_MEDIUM /*3*/:
                return this.mDiv;
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                return this.mMul;
            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                return this.mMarkup;
            default:
                return this.mAdd;
        }
    }

    public void handleMemMinus() {
        this.memoryRecalled = false;
        if (!isInvalidDouble(mAnswer.doubleValue())) {
            this.memory -= mAnswer.doubleValue();
        }
    }

    private void updateSteps(boolean addToHistory) {
        if (!addToHistory) {
            this.mStep = this.historyMgr.getSteps();
        } else if (!this.userInput.inputStr.isEmpty()) {
            this.historyMgr.addElement(this.operationString, this.userInput.getDoubleFromStr());
            this.mStep = this.historyMgr.getSteps();
        } else if (this.mStep > 0) {
            this.historyMgr.editLastElement(this.mCurrentInputType.getoperationString());
        }
    }

    private int getOperationType(String operation) {
        if (operation.equalsIgnoreCase("+")) {
            return 1;
        }
        if (operation.equalsIgnoreCase("-")) {
            return 2;
        }
        if (operation.equalsIgnoreCase("X")) {
            return 4;
        }
        if (operation.equalsIgnoreCase("\u00f7")) {
            return 3;
        }
        if (operation.equalsIgnoreCase("%")) {
            return 5;
        }
        if (operation.isEmpty()) {
            return 6;
        }
        return 1;
    }

    public void handleSqrt() {
        if (this.checkMode == 0) {
            double val = getOperandForUnary();
            if (val >= 0.0d && !isInvalidDouble(mAnswer.doubleValue())) {
                this.userInput.setStr(Math.sqrt(val));
                updateOutputString();
                updateResult();
            }
        }
    }

    public void handletaxplus() {
        if (this.checkMode == 0) {
            handleOperation(1);
            this.userInput.setStr(Double.toString(this.mTaxRate));
            handlePercentAction(2);
        }
    }

    private String getFormattedDoubleString(String str) {
        if (str == null) {
            return BuildConfig.FLAVOR;
        }
        if (str.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        if (str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }

    public static void setAnswer(Double a) {
        mAnswer = a;
    }

    public void handleTaxminus() {
        if (this.checkMode == 0) {
            handleOperation(2);
            this.userInput.setStr(Double.toString(this.mTaxRate));
            handlePercentAction(2);
        }
    }

    public void handleClearCurrent() {
        if (this.checkMode != 0) {
            this.correctHandler.clearCurrentString();
        } else if (this.operationMode != 2) {
            this.userClearingInput = true;
            this.userInput.clearAll();
            updateOutputString();
        }
    }

    public void setTaxRate(double rate) {
        this.mTaxRate = rate;
    }

    public double getTaxRate() {
        return this.mTaxRate;
    }

    private double getOperandForUnary() {
        if (this.operationMode == 2) {
            return mAnswer.doubleValue();
        }
        return this.userInput.getDoubleFromStr();
    }

    public void setHistory(ArrayList<HistoryElements> h) {
        clearAll();
        if (h != null && !h.isEmpty()) {
            ParametersFromHistory p = this.historyMgr.setHistory(h);
            this.mLeftOperand = Double.valueOf(p.leftOp);
            this.userInput.setStr(p.answer);
            if (p.operation.isEmpty()) {
                this.mStep = p.size - 1;
                mAnswer = Double.valueOf(p.answer);
                this.operationMode = 2;
            } else {
                this.mStep = p.size - 1;
                mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
                if (mAnswer.doubleValue() == Double.POSITIVE_INFINITY) {
                    clearAll();
                    updateResult();
                    updateOutputString();
                    return;
                }
                this.operationMode = 2;
                handleOperation(getOperationType(p.operation));
            }
            updateResult();
            updateOutputString();
        }
    }

    public void printList() {
        this.historyMgr.printlist();
    }

    public void correct2ndnum() {
        this.historyMgr.editSecondNum();
        mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        updateSteps(true);
    }

    public void correct4thnum() {
        mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        updateSteps(true);
    }

    public void editItemAt(int index, String operation, double val, int percentageMode) {
        this.historyMgr.editElement(operation, val, index, percentageMode);
        mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        this.mStep = this.historyMgr.getSteps();
        this.historyMgr.updateCommentAfterEdit(index);
    }

    public void deleteRow(int historyIndex, boolean isAnswerNode) {
        this.historyMgr.deleteRow(historyIndex, isAnswerNode);
        mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        this.mStep = this.historyMgr.getSteps();
    }

    public void addRowBelow(HistoryElements item, int historyIndex) {
        this.historyMgr.addItemBelow(item, historyIndex);
        mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        this.mStep = this.historyMgr.getSteps();
    }

    public void editPrevOperation(int index, String operation) {
        this.historyMgr.editOperation(operation, index);
        mAnswer = Double.valueOf(this.correctHandler.evalulateHistory());
        this.mStep = this.historyMgr.getSteps();
    }

    public String getComment(int index, boolean forAnswerNode) {
        return this.historyMgr.getComment(index, forAnswerNode);
    }

    public void setComment(String comment, int index, boolean forAnswerNode) {
        this.historyMgr.setComment(comment, index, forAnswerNode);
    }

    public void setGt(double d) {
        this.mGt = Double.valueOf(d);
    }

    public double getGt() {
        return this.mGt.doubleValue();
    }
}
