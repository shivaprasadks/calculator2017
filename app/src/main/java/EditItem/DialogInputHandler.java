package EditItem;

import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.DrawerLayout;

import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.calclib.Addition;
import com.citizen.calculator2017.calclib.Division;
import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.InputType;
import com.citizen.calculator2017.calclib.Multiplication;
import com.citizen.calculator2017.calclib.OperationType;
import com.citizen.calculator2017.calclib.Subtraction;
import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.UIappConstants;

import static android.widget.Spinner.MODE_DROPDOWN;

public class DialogInputHandler implements UIappConstants {
    private HistoryElements hitem;
    String operandStr;
    String operationString;
    private InputType userInput;

    public DialogInputHandler() {
        this.operandStr = BuildConfig.FLAVOR;
        this.operationString = BuildConfig.FLAVOR;
    }

    public DialogInputHandler(HistoryElements he) {
        this.operandStr = BuildConfig.FLAVOR;
        this.operationString = BuildConfig.FLAVOR;
        this.userInput = new InputType();
        this.hitem = he;
        this.userInput.setStr(NumberFormatter.formatDoubleWithoutScientificNotation(this.hitem.operand.doubleValue()));
        this.operationString = this.hitem.prevOperation;
        this.operandStr = this.userInput.getInputStr();
    }

    public void handleInput(char ch) {
        String str;
        if ('\u0006' == ch) {
            str = "00";
        } else {
            str = BuildConfig.FLAVOR + ch;
        }
        if (true == this.userInput.append(str)) {
            this.operandStr = updateOutputString();
        } else {
            PersistencyManager.traceLog("failed To input char");
        }
    }

    private String updateOutputString() {
        double mAnswer = this.userInput.getDoubleFromStr();
        if (mAnswer == 0.0d) {
            if (this.userInput.decimalPresent) {
                this.operandStr = this.userInput.getInputStr();
            } else {
                this.operandStr = "0";
            }
        } else if (this.userInput.decimalPresent && this.userInput.getInputStr().endsWith("0")) {
            this.operandStr = this.userInput.getInputStr();
        } else {
            this.operandStr = NumberFormatter.formatDecStr(mAnswer, PersistencyManager.getIndianFormat());
        }
        return this.operandStr;
    }

    String getOperandString() {
        return this.operandStr;
    }

    String getOperationString() {
        return this.operationString;
    }

    double getOperand() {
        return this.userInput.getDoubleFromStr();
    }

    public void handleDecimalPoint() {
        this.userInput.appendDecimalPoint();
        updateOutputString();
    }

    public void backspace() {
        this.userInput.backspace();
        updateOutputString();
    }

    public void handleOperation(int op) {
        OperationType mCurrentInputType;
        switch (op) {
            case MODE_DROPDOWN /*1*/:
                mCurrentInputType = new Addition();
                break;
            case DrawerLayout.STATE_SETTLING /*2*/:
                mCurrentInputType = new Subtraction();
                break;
            case WearableExtender.SIZE_MEDIUM /*3*/:
                mCurrentInputType = new Division();
                break;
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                mCurrentInputType = new Multiplication();
                break;
            default:
                mCurrentInputType = new Addition();
                break;
        }
        updateOutputString();
        this.operationString = mCurrentInputType.getoperationString();
    }

    public void handleNegation() {
        this.userInput.setStr(NumberFormatter.formatDoubleWithoutScientificNotation(this.userInput.getDoubleFromStr() * -1.0d));
        updateOutputString();
    }

    public void handleAllclear() {
        this.userInput.clearAll();
        updateOutputString();
    }
}
