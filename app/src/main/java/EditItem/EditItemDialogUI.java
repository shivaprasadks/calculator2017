package EditItem;

import ThemeHandlers.ThemeManager;
import ThemeHandlers.ThemeType;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citizen.calculator2017.R;
import com.citizen.calculator2017.calclib.CorrectHandler;
import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.HistoryManager;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.calclib.InputType;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.LauncherActivity;
import com.citizen.calculator2017.utils.PersistencyManager;

public class EditItemDialogUI implements IeditItemConstants, OnClickListener, IappConstants {
    Activity activity;
    Button[] allButtons;
    Dialog dialog;
    int editmode;
    calculatorFactory factory;
    Fragment frag;
    HistoryElements hitem;
    HistoryManager hmgr;
    int index;
    DialogInputHandler mDialogInputHandler;
    TextView tvOperand;
    TextView tvOperation;
    InputType userInput;

    public EditItemDialogUI() {
        this.allButtons = new Button[19];
    }

    public void showDialog(Context c, int histIndex, Fragment f, int mode) {
        this.dialog = new Dialog(c);
        this.activity = (Activity) c;
        this.index = histIndex;
        this.frag = f;
        this.editmode = mode;
        this.dialog.setTitle(getTitle(mode, histIndex));
        this.dialog.setContentView(R.layout.edit_item_dialog);
        initDialog(histIndex, mode);
        this.dialog.show();
    }

    private String getTitle(int mode, int index) {
        if (mode == 1) {
            return "Edit Item number : " + (index + 1);
        }
        if (mode == 0) {
            return "Set Tax Rate";
        }
        return "Edit";
    }

    private void initDialog(int i, int mode) {
        bindAllButtons(this.dialog);
        setOnClickListenersForAllButtons();
        this.factory = calculatorFactory.getCalcFactory();
        if (mode == 1) {
            this.hmgr = HistoryManager.getHistoryManager();
            this.hitem = this.hmgr.getElement(i);
        } else {
            this.hitem = new HistoryElements();
            this.hitem.operand = Double.valueOf(this.factory.getTaxRate());
            this.hitem.operation = BuildConfig.FLAVOR;
        }
        ThemeType curTheme = ThemeManager.getThemeManager().getCurrentTheme();
        this.mDialogInputHandler = new DialogInputHandler(this.hitem);
        this.tvOperand = (TextView) this.dialog.findViewById(R.id.tvAnswer);
        this.tvOperation = (TextView) this.dialog.findViewById(R.id.tvOperation);
        LinearLayout numpad = (LinearLayout) this.dialog.findViewById(R.id.dialogLayoutNumpad);
        LinearLayout operations = (LinearLayout) this.dialog.findViewById(R.id.dialogLayoutOperations);
        this.tvOperand.setBackgroundColor(curTheme.getDisplayBg());
        this.tvOperation.setBackgroundColor(curTheme.getDisplayBg());
        this.tvOperand.setTextColor(curTheme.getDisplayFg());
        this.tvOperation.setTextColor(curTheme.getDisplayFg());
        numpad.setBackgroundColor(curTheme.getNumPadBg());
        operations.setBackgroundColor(curTheme.getOperatorsBg());
        setColorForAllButtons(curTheme);
        updateTextViews();
    }

    private void setColorForAllButtons(ThemeType theme) {
        for (int i = 0; i < 19; i++) {
            if (isSpecialOperation(this.allButtons[i].getId())) {
                this.allButtons[i].setTextColor(theme.getOperatorsFg());
            } else {
                this.allButtons[i].setTextColor(theme.getNumPadFg());
            }
        }
    }

    private void bindAllButtons(Dialog d) {
        for (int i = 0; i < 19; i++) {
            this.allButtons[i] = (Button) d.findViewById(allButtonIds[i]);
        }
    }

    private void setOnClickListenersForAllButtons() {
        for (int i = 0; i < 19; i++) {
            this.allButtons[i].setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDelete /*2131427398*/:
                this.mDialogInputHandler.backspace();
                break;
            case R.id.buttonAllClear /*2131427399*/:
                this.mDialogInputHandler.handleAllclear();
                break;
            case R.id.button7 /*2131427405*/:
                this.mDialogInputHandler.handleInput('7');
                break;
            case R.id.button8 /*2131427406*/:
                this.mDialogInputHandler.handleInput('8');
                break;
            case R.id.button9 /*2131427407*/:
                this.mDialogInputHandler.handleInput('9');
                break;
            case R.id.buttonDivide /*2131427408*/:
                this.mDialogInputHandler.handleOperation(3);
                break;
            case R.id.button4 /*2131427410*/:
                this.mDialogInputHandler.handleInput('4');
                break;
            case R.id.button5 /*2131427411*/:
                this.mDialogInputHandler.handleInput('5');
                break;
            case R.id.button6 /*2131427412*/:
                this.mDialogInputHandler.handleInput('6');
                break;
            case R.id.buttonMultiply /*2131427413*/:
                this.mDialogInputHandler.handleOperation(4);
                break;
            case R.id.buttonPercentage /*2131427414*/:
                handlepercentage();
                break;
            case R.id.button1 /*2131427415*/:
                this.mDialogInputHandler.handleInput('1');
                break;
            case R.id.button0 /*2131427416*/:
                this.mDialogInputHandler.handleInput('0');
                break;
            case R.id.button2 /*2131427417*/:
                this.mDialogInputHandler.handleInput('2');
                break;
            case R.id.button00 /*2131427418*/:
                this.mDialogInputHandler.handleInput('\u0006');
                break;
            case R.id.button3 /*2131427419*/:
                this.mDialogInputHandler.handleInput('3');
                break;
            case R.id.buttonDecimalAll /*2131427420*/:
                this.mDialogInputHandler.handleDecimalPoint();
                break;
            case R.id.buttonPlus /*2131427421*/:
                this.mDialogInputHandler.handleOperation(1);
                break;
            case R.id.buttonMinus /*2131427422*/:
                this.mDialogInputHandler.handleOperation(2);
                break;
            case R.id.buttonEquals /*2131427423*/:
                commitChanges();
                break;
        }
        updateTextViews();
    }

    private void handlepercentage() {
        if (this.hitem.percentageMode != 0) {
            this.hitem.percentageMode = 1;
        } else {
            this.hitem.percentageMode = 0;
        }
    }

    private void commitChanges() {
        if (this.editmode == 1) {
            HistoryElements prevItem = this.hmgr.getElement(this.index - 1);
            if (prevItem != null) {
                prevItem.operation = this.mDialogInputHandler.getOperationString();
            }
            this.hitem.operand = Double.valueOf(this.mDialogInputHandler.getOperand());
            this.hitem.prevOperation = this.mDialogInputHandler.getOperationString();
            this.factory.refreshFromHistory();
            new CorrectHandler().evalulateHistory();
         //   this.frag.refreshList();
        } else {
            this.factory.setTaxRate(this.mDialogInputHandler.getOperand());
            PersistencyManager.setTaxRate(this.mDialogInputHandler.getOperand());
            LauncherActivity.showToast("Tax rate is " + this.mDialogInputHandler.getOperand(), this.activity);
        }
        this.dialog.cancel();
    }

    private void updateTextViews() {
        this.tvOperand.setText(getOperandString());
        this.tvOperation.setText(getOperationString());
    }

    String getOperationString() {
        return this.mDialogInputHandler.getOperationString();
    }

    String getOperandString() {
        String str = this.mDialogInputHandler.getOperandString();
        if (this.hitem.percentageMode != 0) {
            return str + "%";
        }
        return this.editmode == 0 ? "Tax Rate = " + str + "%" : str;
    }

    boolean isSpecialOperation(int id) {
        switch (id) {
            case R.id.buttonDivide /*2131427408*/:
            case R.id.buttonMultiply /*2131427413*/:
            case R.id.buttonPlus /*2131427421*/:
            case R.id.buttonMinus /*2131427422*/:
                return true;
            default:
                return false;
        }
    }
}
