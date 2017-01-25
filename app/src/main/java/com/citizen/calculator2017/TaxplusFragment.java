package com.citizen.calculator2017;

import EditItem.EditItemDialogUI;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.UIsystemStates;
import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.ProductOut;

public class TaxplusFragment extends Fragment implements OnClickListener, UIsystemStates, OnLongClickListener, IappConstants {
    static Toast mtoast;
    public static Typeface sevenfont;
    Button[] allButtons;
    ImageButton[] allimageButtons;
    AudioManager am;
    private final float buttonFontSize;
    EditItemDialogUI editDialog;
    Typeface keyfont;
    calculatorFactory mCalcFactory;
    ProductOut mFactoryResult;
    private PersistencyManager persistencyManager;
    Typeface robotoBold;
    Typeface robotoFont;
    Typeface robotoRegular;
    boolean showTax;
    int systemState;
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

    public TaxplusFragment() {
        this.systemState = -1;
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
        View v = inflater.inflate(R.layout.taxplus_fragment, container, false);
        this.tvAnswer = (TextView) v.findViewById(R.id.tvAnswer);
        this.tvOperation = (TextView) v.findViewById(R.id.tvOperation);
        this.tvStep = (TextView) v.findViewById(R.id.tvStep);
        this.tvtax = (TextView) v.findViewById(R.id.tvTax);
        this.tvmemory = (TextView) v.findViewById(R.id.tvMemory);
        setAllButtonListener((ViewGroup) v.findViewById(R.id.taxPlusLayout));
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
        setThemeColor(this.themeR, this.themeG, this.themeB);
        setFontSizeToAllTextViews(25);
        setcolorToAllTextViews();
        this.mCalcFactory = calculatorFactory.getCalcFactory();
        this.mFactoryResult = new ProductOut();
        this.vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        this.am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        return v;
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
            case R.id.buttonCheckRight /*2131427397*/:
                this.mCalcFactory.handleCheckPlus();
                break;
            case R.id.buttonDelete /*2131427398*/:
                this.mCalcFactory.backspace();
                break;
            case R.id.buttonAllClear /*2131427399*/:
                this.mCalcFactory.handleClearAllButton();
                break;
            case R.id.buttonMemPlus /*2131427400*/:
                handleMemButtonPress();
                this.mCalcFactory.handlememPlus();
                break;
            case R.id.buttonMemMinus /*2131427401*/:
                handleMemButtonPress();
                this.mCalcFactory.handleMemMinus();
                break;
            case R.id.buttonMemRecall /*2131427402*/:
                this.mCalcFactory.handlememRecall();
                break;
            case R.id.buttonSqrt /*2131427403*/:
                this.mCalcFactory.handleSqrt();
                break;
            case R.id.buttonMarkUp /*2131427404*/:
                this.mCalcFactory.handleOperation(8);
                break;
            case R.id.button7 /*2131427405*/:
                this.mCalcFactory.handleInput('7');
                break;
            case R.id.button8 /*2131427406*/:
                this.mCalcFactory.handleInput('8');
                break;
            case R.id.button9 /*2131427407*/:
                this.mCalcFactory.handleInput('9');
                break;
            case R.id.buttonDivide /*2131427408*/:
                this.mCalcFactory.handleOperation(3);
                break;
            case R.id.buttonPlusMinus /*2131427409*/:
                this.mCalcFactory.handleNegation();
                break;
            case R.id.button4 /*2131427410*/:
                this.mCalcFactory.handleInput('4');
                break;
            case R.id.button5 /*2131427411*/:
                this.mCalcFactory.handleInput('5');
                break;
            case R.id.button6 /*2131427412*/:
                this.mCalcFactory.handleInput('6');
                break;
            case R.id.buttonMultiply /*2131427413*/:
                this.mCalcFactory.handleOperation(4);
                break;
            case R.id.buttonPercentage /*2131427414*/:
                this.mCalcFactory.handlepercentage();
                break;
            case R.id.button1 /*2131427415*/:
                this.mCalcFactory.handleInput('1');
                break;
            case R.id.button0 /*2131427416*/:
                this.mCalcFactory.handleInput('0');
                break;
            case R.id.button2 /*2131427417*/:
                this.mCalcFactory.handleInput('2');
                break;
            case R.id.button00 /*2131427418*/:
                this.mCalcFactory.handleInput('\u0006');
                break;
            case R.id.button3 /*2131427419*/:
                this.mCalcFactory.handleInput('3');
                break;
            case R.id.buttonDecimalAll /*2131427420*/:
                this.mCalcFactory.handleDecimalPoint();
                break;
            case R.id.buttonPlus /*2131427421*/:
                this.mCalcFactory.handleOperation(1);
                break;
            case R.id.buttonMinus /*2131427422*/:
                this.mCalcFactory.handleOperation(2);
                break;
            case R.id.buttonEquals /*2131427423*/:
                this.mCalcFactory.handleEquals();
                break;
            case R.id.buttonTaxPlus /*2131427489*/:
                this.showTax = true;
                this.mCalcFactory.handletaxplus();
                break;
            case R.id.buttonTaxMinus /*2131427490*/:
                this.showTax = true;
                this.mCalcFactory.handleTaxminus();
                break;
            case R.id.buttonClearCurrent /*2131427491*/:
                this.mCalcFactory.handleClearCurrent();
                break;
            case R.id.buttonGT /*2131427492*/:
                this.mCalcFactory.handleGTbutton();
                break;
        }
        this.mFactoryResult = this.mCalcFactory.getResult();
        ListViewFullScreen.listAdapter.notifyDataSetChanged();
        if (!false) {
            updateTextViews();
        }
    }

    private void handleMemButtonPress() {
        if (this.mCalcFactory.getOperationMode() == 1) {
            this.mCalcFactory.handleEquals();
        }
    }

    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPercentage /*2131427414*/:
            case R.id.buttonTaxPlus /*2131427489*/:
            case R.id.buttonTaxMinus /*2131427490*/:
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

    private void setcolorToAllTextViews() {
        int fontColor = Color.argb(MotionEventCompat.ACTION_MASK, 65, 65, 65);
        this.tvOperation.setTextColor(fontColor);
        this.tvStep.setTextColor(fontColor);
        this.tvAnswer.setTextColor(fontColor);
    }

    private void setThemeColor(int r, int g, int b) {
        int textViewColor = Color.argb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        setBgColorToTextViews(Color.rgb(182, 185, 114));
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
            case R.id.buttonPercentage /*2131427414*/:
            case R.id.buttonTaxPlus /*2131427489*/:
            case R.id.buttonTaxMinus /*2131427490*/:
                b.setOnLongClickListener(this);
            default:
        }
    }

    private float getSizeForButton(Button b) {
        switch (b.getId()) {
            case R.id.buttonDelete /*2131427398*/:
            case R.id.buttonPlus /*2131427421*/:
            case R.id.buttonMinus /*2131427422*/:
                return 30.0f;
            case R.id.buttonAllClear /*2131427399*/:
            case R.id.buttonMemPlus /*2131427400*/:
            case R.id.buttonMemMinus /*2131427401*/:
            case R.id.buttonMarkUp /*2131427404*/:
            case R.id.buttonDecimalAll /*2131427420*/:
            case R.id.buttonClearCurrent /*2131427491*/:
            case R.id.buttonGT /*2131427492*/:
                return 20.0f;
            case R.id.buttonMemRecall /*2131427402*/:
            case R.id.buttonTaxPlus /*2131427489*/:
            case R.id.buttonTaxMinus /*2131427490*/:
                return 18.0f;
            default:
                return 25.0f;
        }
    }
}
