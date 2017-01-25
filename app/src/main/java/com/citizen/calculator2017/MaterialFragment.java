package com.citizen.calculator2017;

import EditItem.EditItemDialogUI;
import ThemeHandlers.ThemeManager;
import ThemeHandlers.ThemeType;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.utils.MaterialLayoutConstants;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.UIsystemStates;
import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.ProductOut;

public class MaterialFragment extends Fragment implements OnClickListener, UIsystemStates, OnLongClickListener, IappConstants, MaterialLayoutConstants {
    static Toast mtoast;
    public static Typeface sevenfont;
    Button[] allButtons;
    AudioManager am;
    private final float buttonFontSize;
    EditItemDialogUI editDialog;
    Typeface keyfont;
    calculatorFactory mCalcFactory;
    ProductOut mFactoryResult;
    View mainView;
    private PersistencyManager persistencyManager;
    Typeface robotoBold;
    Typeface robotoFont;
    Typeface robotoRegular;
    boolean showTax;
    int themeB;
    int themeG;
    int themeR;
    TextView tvAnswer;
    TextView tvOperation;
    TextView tvStep;
    TextView tvmemory;
    TextView tvtax;
    int tvthemeB;
    int tvthemeG;
    int tvthemeR;
    Vibrator vibe;

    public MaterialFragment() {
        this.showTax = false;
        this.tvthemeR = 129;
        this.tvthemeG = 212;
        this.tvthemeB = 250;
        this.themeR = 38;
        this.themeG = 50;
        this.themeB = 56;
        this.buttonFontSize = 25.0f;
    }

    public void onResume() {
        super.onResume();
        calculatorFactory com_browndwarf_calclib_calculatorFactory = this.mCalcFactory;
        PersistencyManager persistencyManager = this.persistencyManager;
        com_browndwarf_calclib_calculatorFactory.setIndianFormat(PersistencyManager.getIndianFormat());
        this.mFactoryResult = this.mCalcFactory.getResult();
        updateTextViews();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.persistencyManager = PersistencyManager.getPersistencyManager(getActivity().getApplicationContext());
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainView = inflater.inflate(ThemeManager.getThemeManager().getCurrentTheme().getLayout(), container, false);
        this.allButtons = new Button[24];
        bindAllButtons(this.mainView);
        this.tvAnswer = (TextView) this.mainView.findViewById(R.id.tvAnswer);
        this.tvOperation = (TextView) this.mainView.findViewById(R.id.tvOperation);
        this.tvStep = (TextView) this.mainView.findViewById(R.id.tvStep);
        this.tvtax = (TextView) this.mainView.findViewById(R.id.tvTax);
        this.tvmemory = (TextView) this.mainView.findViewById(R.id.tvMemory);
        this.tvAnswer.setOnClickListener(this);
        setAllButtonListener((ViewGroup) this.mainView.findViewById(R.id.materialLayoutMain));
        Typeface typeface = this.robotoFont;
        this.robotoFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotothin.ttf");
        typeface = sevenfont;
        sevenfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Digital7commaDot.ttf");
        typeface = this.keyfont;
        this.keyfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/keyfont.ttf");
        typeface = this.robotoRegular;
        this.robotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotoregular.ttf");
        typeface = this.robotoBold;
        this.robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotobold.ttf");
        setFontToAllElements(this.robotoRegular);
        setColors();
        setFontSizeToAllTextViews(25);
        this.mCalcFactory = calculatorFactory.getCalcFactory();
        this.mFactoryResult = new ProductOut();
        this.vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        this.am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        return this.mainView;
    }

    private void setColors() {
        ThemeType theme = ThemeManager.getThemeManager().getCurrentTheme();
        ((LinearLayout) this.mainView.findViewById(R.id.tvDisplayLayout)).setBackgroundColor(theme.getDisplayBg());
        setBgColorToTextViews(Color.argb(0, 0, 0, 0));
        setcolorToAllTextViews(theme.getDisplayFg());
        ((LinearLayout) this.mainView.findViewById(R.id.materialLayoutMain)).setBackgroundColor(theme.getOperatorsBg());
        ((LinearLayout) this.mainView.findViewById(R.id.operatorsRow1)).setBackgroundColor(theme.getOperatorsBg());
        ((LinearLayout) this.mainView.findViewById(R.id.operatorsRow2)).setBackgroundColor(theme.getOperatorsBg());
        ((LinearLayout) this.mainView.findViewById(R.id.operatorsMaterial)).setBackgroundColor(theme.getBasicOperatorsBg());
        ((LinearLayout) this.mainView.findViewById(R.id.numPadRow1)).setBackgroundColor(theme.getNumPadBg());
        ((LinearLayout) this.mainView.findViewById(R.id.numPadRow2)).setBackgroundColor(theme.getNumPadBg());
        ((LinearLayout) this.mainView.findViewById(R.id.numPadRow3)).setBackgroundColor(theme.getNumPadBg());
        ((LinearLayout) this.mainView.findViewById(R.id.numPadRow4)).setBackgroundColor(theme.getNumPadBg());
        setFontColorToOperators(theme.getOperatorsFg());
        setFontColorToNumbers(theme.getNumPadFg());
    }

    private void bindAllButtons(View v) {
        for (int i = 0; i < 24; i++) {
            this.allButtons[i] = (Button) v.findViewById(fallButtonIds[i]);
        }
    }

    private void setFontColorToOperators(int fontColor) {
        for (int i = 16; i < 24; i++) {
            this.allButtons[i].setTextColor(fontColor);
        }
    }

    private void setFontColorToNumbers(int fontColor) {
        for (int i = 0; i <= 15; i++) {
            this.allButtons[i].setTextColor(fontColor);
        }
    }

    public void onClick(View arg0) {
        PersistencyManager persistencyManager = this.persistencyManager;
        if (PersistencyManager.getVibrateFlag()) {
            this.vibe.vibrate(75);
        }
        persistencyManager = this.persistencyManager;
        if (PersistencyManager.getButtonSoundFlag()) {
            this.am.playSoundEffect(0, 0.5f);
        } else {
            this.am.playSoundEffect(0, 0.0f);
        }
        switch (arg0.getId()) {
            case R.id.tvAnswer /*2131427395*/:
                this.mCalcFactory.backspace();
                break;
            case R.id.fbuttontaxplus /*2131427445*/:
                this.showTax = true;
                this.mCalcFactory.handletaxplus();
                break;
            case R.id.fbuttontaxminus /*2131427446*/:
                this.showTax = true;
                this.mCalcFactory.handleTaxminus();
                break;
            case R.id.fbuttonpercentage /*2131427447*/:
                this.mCalcFactory.handlepercentage();
                break;
            case R.id.fbuttonmemplus /*2131427449*/:
                handleMemButtonPress();
                this.mCalcFactory.handlememPlus();
                break;
            case R.id.fbuttonmemminus /*2131427450*/:
                handleMemButtonPress();
                this.mCalcFactory.handleMemMinus();
                break;
            case R.id.fbuttonmemrecall /*2131427451*/:
                this.mCalcFactory.handlememRecall();
                break;
            case R.id.fbutton7 /*2131427453*/:
                this.mCalcFactory.handleInput('7');
                break;
            case R.id.fbutton8 /*2131427454*/:
                this.mCalcFactory.handleInput('8');
                break;
            case R.id.fbutton9 /*2131427455*/:
                this.mCalcFactory.handleInput('9');
                break;
            case R.id.fbutton4 /*2131427457*/:
                this.mCalcFactory.handleInput('4');
                break;
            case R.id.fbutton5 /*2131427458*/:
                this.mCalcFactory.handleInput('5');
                break;
            case R.id.fbutton6 /*2131427459*/:
                this.mCalcFactory.handleInput('6');
                break;
            case R.id.fbutton1 /*2131427461*/:
                this.mCalcFactory.handleInput('1');
                break;
            case R.id.fbutton2 /*2131427462*/:
                this.mCalcFactory.handleInput('2');
                break;
            case R.id.fbutton3 /*2131427463*/:
                this.mCalcFactory.handleInput('3');
                break;
            case R.id.fbutton0 /*2131427465*/:
                this.mCalcFactory.handleInput('0');
                break;
            case R.id.fbuttonDecimal /*2131427466*/:
                this.mCalcFactory.handleDecimalPoint();
                break;
            case R.id.fbuttonEquals /*2131427467*/:
                this.mCalcFactory.handleEquals();
                break;
            case R.id.fbuttonClear /*2131427469*/:
                this.mCalcFactory.handleClearAllButton();
                break;
            case R.id.fbuttonMultiply /*2131427470*/:
                this.mCalcFactory.handleOperation(4);
                break;
            case R.id.fbuttonDivide /*2131427471*/:
                this.mCalcFactory.handleOperation(3);
                break;
            case R.id.fbuttonMinus /*2131427472*/:
                this.mCalcFactory.handleOperation(2);
                break;
            case R.id.fbuttonPlus /*2131427473*/:
                this.mCalcFactory.handleOperation(1);
                break;
            case R.id.fbuttonBackspace /*2131427474*/:
                this.mCalcFactory.backspace();
                break;
        }
        this.mFactoryResult = this.mCalcFactory.getResult();
        ListViewFullScreen.listAdapter.notifyDataSetChanged();
        updateTextViews();
    }

    private void handleMemButtonPress() {
        if (this.mCalcFactory.getOperationMode() == 1) {
            this.mCalcFactory.handleEquals();
        }
    }

    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.fbuttontaxplus /*2131427445*/:
            case R.id.fbuttontaxminus /*2131427446*/:
            case R.id.fbuttonpercentage /*2131427447*/:
                this.editDialog = new EditItemDialogUI();
                this.editDialog.showDialog(getActivity(), 0, this, 0);
                break;
        }
        return true;
    }

    private void showToast(String str, Context c) {
        if (mtoast != null) {
            mtoast.cancel();
        }
        Toast toast = mtoast;
        Toast.makeText(c, str, Toast.LENGTH_SHORT).show();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.mCalcFactory != null) {
            calculatorFactory com_browndwarf_calclib_calculatorFactory = this.mCalcFactory;
            PersistencyManager persistencyManager = this.persistencyManager;
            com_browndwarf_calclib_calculatorFactory.setIndianFormat(PersistencyManager.getIndianFormat());
            this.mFactoryResult = this.mCalcFactory.getResult();
            updateTextViews();
            if (ListViewFullScreen.listAdapter != null) {
                ListViewFullScreen.listAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateTextViews() {
        this.tvOperation.setText(this.mFactoryResult.operation);
        this.tvStep.setText(this.mFactoryResult.StepNum);
        this.tvAnswer.setText(this.mFactoryResult.answer);
        this.tvmemory.setText(getMemText());
        if (this.showTax) {
            this.showTax = false;
            this.tvtax.setText("Tax = " + this.mCalcFactory.getTaxCalculated());
            return;
        }
        this.tvtax.setText(BuildConfig.FLAVOR);
    }

    private String getMemText() {
        String mem = "M=";
        if (!this.mCalcFactory.isMemoryPresent()) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder append = new StringBuilder().append(mem);
        double memValue = this.mCalcFactory.getMemValue();
        calculatorFactory com_browndwarf_calclib_calculatorFactory = this.mCalcFactory;
        return append.append(NumberFormatter.formatDecStr(memValue, calculatorFactory.getIndianFormatType())).toString();
    }

    private void setFontSizeToAllTextViews(int i) {
        this.tvOperation.setTextSize(2, (float) i);
        this.tvAnswer.setTextSize(2, (float) (i + 10));
    }

    private void setcolorToAllTextViews(int fontColor) {
        this.tvOperation.setTextColor(fontColor);
        this.tvStep.setTextColor(fontColor);
        this.tvAnswer.setTextColor(fontColor);
        this.tvmemory.setTextColor(fontColor);
        this.tvtax.setTextColor(fontColor);
    }

    private void setThemeColor(int r, int g, int b) {
        setBgColorToTextViews(Color.argb(0, r, g, b));
    }

    private void setBgColorToTextViews(int themeColor) {
        this.tvAnswer.setBackgroundColor(themeColor);
        this.tvOperation.setBackgroundColor(themeColor);
        this.tvStep.setBackgroundColor(themeColor);
        this.tvmemory.setBackgroundColor(themeColor);
        this.tvtax.setBackgroundColor(themeColor);
    }

    void setFontToAllElements(Typeface font) {
        this.tvOperation.setTypeface(font);
        this.tvtax.setTypeface(font);
        this.tvmemory.setTypeface(font);
        this.tvStep.setTypeface(font);
        this.tvAnswer.setTypeface(font);
    }

    private void setAllButtonListener(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                setAllButtonListener((ViewGroup) v);
            } else if (v instanceof Button) {
                Button b = (Button) v;
                b.setOnClickListener(this);
                b.setTypeface(this.robotoBold);
                b.setTextSize(2, getSizeForButton(b));
                setLongclicklistener(b);
            }
        }
    }

    private void setLongclicklistener(Button b) {
        switch (b.getId()) {
            case R.id.fbuttontaxplus /*2131427445*/:
            case R.id.fbuttontaxminus /*2131427446*/:
            case R.id.fbuttonpercentage /*2131427447*/:
                b.setOnLongClickListener(this);
            default:
        }
    }

    private float getSizeForButton(Button b) {
        switch (b.getId()) {
            case R.id.fbuttontaxplus /*2131427445*/:
            case R.id.fbuttontaxminus /*2131427446*/:
            case R.id.fbuttonmemrecall /*2131427451*/:
                return 18.0f;
            case R.id.fbuttonmemplus /*2131427449*/:
            case R.id.fbuttonmemminus /*2131427450*/:
            case R.id.fbuttonClear /*2131427469*/:
            case R.id.fbuttonBackspace /*2131427474*/:
                return 20.0f;
            case R.id.fbuttonEquals /*2131427467*/:
            case R.id.fbuttonMultiply /*2131427470*/:
            case R.id.fbuttonDivide /*2131427471*/:
            case R.id.fbuttonMinus /*2131427472*/:
            case R.id.fbuttonPlus /*2131427473*/:
                return 30.0f;
            default:
                return 25.0f;
        }
    }
}
