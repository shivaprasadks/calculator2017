package com.citizen.calculator2017;

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

import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.UIappConstants;
import com.citizen.calculator2017.utils.UIsystemStates;

import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.ProductOut;

public class MainActivity extends Fragment implements OnClickListener, UIsystemStates, OnLongClickListener, UIappConstants {
    static Toast mtoast;
    public static Typeface sevenfont;
    Button[] allButtons;
    ImageButton[] allimageButtons;
    AudioManager am;
    Typeface keyfont;
    calculatorFactory mCalcFactory;
    ProductOut mFactoryResult;
    private PersistencyManager persistencyManager;
    Typeface robotoBold;
    Typeface robotoFont;
    Typeface robotoRegular;
    int systemState;
    int themeB;
    int themeG;
    int themeR;
    TextView tvAnswer;
    TextView tvOperation;
    TextView tvStep;
    TextView tvcheck;
    TextView tvcorrect;
    TextView tvmemory;
    int tvthemeB;
    int tvthemeG;
    int tvthemeR;
    Vibrator vibe;

    public MainActivity() {
        this.systemState = -1;
        this.tvthemeR = 129;
        this.tvthemeG = 212;
        this.tvthemeB = 250;
        this.themeR = 38;
        this.themeG = 50;
        this.themeB = 56;
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
        View v = inflater.inflate(R.layout.activity_main, container, false);
        this.allButtons = new Button[25];
        this.allimageButtons = new ImageButton[2];
        bindAllButtons(v);
        this.tvAnswer = (TextView) v.findViewById(R.id.tvAnswer);
        this.tvOperation = (TextView) v.findViewById(R.id.tvOperation);
        this.tvStep = (TextView) v.findViewById(R.id.tvStep);
        this.tvcheck = (TextView) v.findViewById(R.id.tvCheck);
        this.tvcorrect = (TextView) v.findViewById(R.id.tvCorrect);
        this.tvmemory = (TextView) v.findViewById(R.id.tvMemory);
        setOnClickListenersForAllButtons();
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
        setFontToAllElements(this.robotoFont);
        setThemeColor(this.themeR, this.themeG, this.themeB);
        setfontSizeToAllButtons(20);
        setFontSizeToAllTextViews(25);
        setcolorToAllTextViews();
        this.mCalcFactory = calculatorFactory.getCalcFactory();
        this.mFactoryResult = new ProductOut();
        this.vibe = (Vibrator) getActivity().getSystemService("vibrator");
        this.am = (AudioManager) getActivity().getSystemService("audio");
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

    public boolean onLongClick(View arg0) {
        if (arg0.getId() == R.id.buttonDelete) {
            this.mCalcFactory.handleClearCurrent();
            this.mFactoryResult = this.mCalcFactory.getResult();
            updateTextViews();
        }
        return true;
    }

    private void showToast(String str, Context c) {
        if (mtoast != null) {
            mtoast.cancel();
        }
        Toast toast = mtoast;
        Toast.makeText(c, str, 0).show();
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
        if (this.mFactoryResult.checkMode == 1) {
            highlightChecktv(true);
            highlightCorrecttv(false);
        }
        if (this.mFactoryResult.checkMode == 2) {
            highlightCorrecttv(true);
        }
        if (this.mFactoryResult.checkMode == 0) {
            highlightChecktv(false);
            highlightCorrecttv(false);
        }
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

    private void highlightCorrecttv(boolean b) {
        String str;
        if (b) {
            str = "CORRECT";
        } else {
            str = BuildConfig.FLAVOR;
        }
        this.tvcorrect.setText(str);
    }

    private void highlightChecktv(boolean b) {
        String str;
        if (b) {
            str = "CHECK";
        } else {
            str = BuildConfig.FLAVOR;
        }
        this.tvcheck.setText(str);
    }

    private void bindAllButtons(View v) {
        int i;
        for (i = 0; i < 25; i++) {
            this.allButtons[i] = (Button) v.findViewById(allButtonIds[i]);
        }
        for (i = 0; i < 2; i++) {
            this.allimageButtons[i] = (ImageButton) v.findViewById(allImageButtonsId[i]);
        }
    }

    private void setOnClickListenersForAllButtons() {
        int i;
        for (i = 0; i < 25; i++) {
            this.allButtons[i].setOnClickListener(this);
        }
        for (i = 0; i < 2; i++) {
            this.allimageButtons[i].setOnClickListener(this);
        }
        this.allimageButtons[0].setOnLongClickListener(this);
    }

    private void setFontSizeToAllTextViews(int i) {
        this.tvOperation.setTextSize(2, (float) i);
        this.tvStep.setTextSize(2, (float) i);
        this.tvmemory.setTextSize(2, (float) (i - 10));
        this.tvcheck.setTextSize(2, (float) (i - 10));
        this.tvcorrect.setTextSize(2, (float) (i - 10));
        this.tvAnswer.setTextSize(2, 45.0f);
    }

    private void setcolorToAllTextViews() {
        int fontColor = Color.argb(MotionEventCompat.ACTION_MASK, 65, 65, 65);
        this.tvOperation.setTextColor(fontColor);
        this.tvStep.setTextColor(fontColor);
        this.tvAnswer.setTextColor(fontColor);
    }

    private void setfontSizeToAllButtons(int j) {
        int i;
        for (i = 0; i < 11; i++) {
            this.allButtons[i].setTextSize(2, (float) j);
        }
        for (i = 15; i < 25; i++) {
            this.allButtons[i].setTextSize(2, (float) j);
        }
        this.allButtons[19].setTextSize(2, (float) (j + 10));
        for (i = 11; i < 15; i++) {
            this.allButtons[i].setTextSize(2, (float) j);
        }
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
        this.tvcheck.setBackgroundColor(themeColor);
        this.tvcorrect.setBackgroundColor(themeColor);
    }

    void setFontToAllElements(Typeface font) {
        int i;
        font = this.keyfont;
        for (i = 0; i < 11; i++) {
            this.allButtons[i].setTypeface(font);
        }
        this.allButtons[21].setTypeface(font);
        font = this.robotoBold;
        for (i = 15; i < 25; i++) {
            this.allButtons[i].setTypeface(font);
        }
        font = this.robotoRegular;
        for (i = 11; i < 15; i++) {
            this.allButtons[i].setTypeface(font);
        }
        this.tvOperation.setTypeface(font);
        this.tvcheck.setTypeface(font);
        this.tvcorrect.setTypeface(font);
        this.tvmemory.setTypeface(font);
        font = sevenfont;
        this.tvStep.setTypeface(font);
        this.tvAnswer.setTypeface(font);
    }
}
