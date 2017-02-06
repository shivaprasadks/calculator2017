package com.citizen.calculator2017;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.ProductOut;
import com.citizen.calculator2017.utils.UIappConstants;
import com.citizen.calculator2017.utils.UIsystemStates;

public class NewActivity extends AppCompatActivity implements View.OnClickListener, UIsystemStates, View.OnLongClickListener, UIappConstants {
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
    Typeface outline;
    int systemState;
    int themeB;
    int themeG;
    int themeR;
    TextView tvAnswer, ct512;
    TextView tvOperation;
    TextView tvStep;
    TextView tvcheck;
    TextView tvcorrect;
    TextView tvmemory;
    ImageView opt_btn;
    int tvthemeB;
    int tvthemeG;
    int tvthemeR;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        persistencyManager = PersistencyManager.getPersistencyManager(this.getApplicationContext());
        allButtons = new Button[25];
        allimageButtons = new ImageButton[2];
        bindAllButtons();
        opt_btn = (ImageView) findViewById(R.id.optionButton);
        tvAnswer = (TextView) findViewById(R.id.tvAnswer);
        tvOperation = (TextView) findViewById(R.id.tvOperation);
        tvStep = (TextView) findViewById(R.id.tvStep);
        tvcheck = (TextView) findViewById(R.id.tvCheck);
        tvcorrect = (TextView) findViewById(R.id.tvCorrect);
        tvmemory = (TextView) findViewById(R.id.tvMemory);
        ct512 = (TextView) findViewById(R.id.ct512);
        setOnClickListenersForAllButtons();
        Typeface typeface = robotoFont;

        robotoFont = Typeface.createFromAsset(this.getAssets(), "fonts/robotothin.ttf");
        typeface = sevenfont;
        sevenfont = Typeface.createFromAsset(this.getAssets(), "fonts/Digital7commaDot.ttf");
        typeface = keyfont;
        keyfont = Typeface.createFromAsset(this.getAssets(), "fonts/keyfont.ttf");
        typeface = robotoRegular;
        robotoRegular = Typeface.createFromAsset(this.getAssets(), "fonts/robotoregular.ttf");
        typeface = robotoBold;
        robotoBold = Typeface.createFromAsset(this.getAssets(), "fonts/robotobold.ttf");
        setFontToAllElements(robotoFont);

        outline = Typeface.createFromAsset(this.getAssets(), "fonts/outline.ttf");
        ct512.setTypeface(outline);

        setThemeColor(themeR, this.themeG, this.themeB);
        setfontSizeToAllButtons(20);
        setFontSizeToAllTextViews(25);
        setcolorToAllTextViews();
        mCalcFactory = calculatorFactory.getCalcFactory();
        mFactoryResult = new ProductOut();
        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        opt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// Created a new Dialog
                Dialog dialog = new Dialog(NewActivity.this);

// Set the title
                dialog.setTitle("Dialog Title");

// inflate the layout
                dialog.setContentView(R.layout.dialog_view);

// Set the dialog text -- this is better done in the XML
             //   TextView text = (TextView) dialog.findViewById(R.id.dialog_text_view);
              //  text.setText("This is the text that does in the dialog box");

// Display the dialog
                dialog.show();
            }
        });
    }

    public void onClick(View arg0) {
       /* PersistencyManager persistencyManager =  persistencyManager;
        if (this.getVibrateFlag()) {
             vibe.vibrate(75);
        }
        persistencyManager =  persistencyManager;
        if (PersistencyManager.getButtonSoundFlag()) {
             am.playSoundEffect(0, 0.5f);
        } else {
             am.playSoundEffect(0, 0.0f);
        } */
        switch (arg0.getId()) {
            case R.id.buttonCheckRight /*2131427397*/:
                mCalcFactory.handleCheckPlus();
                break;
            case R.id.buttonDelete /*2131427398*/:
                mCalcFactory.backspace();
                break;
            case R.id.buttonAllClear /*2131427399*/:
                mCalcFactory.handleClearAllButton();
                break;
            case R.id.buttonMemPlus /*2131427400*/:
                handleMemButtonPress();
                mCalcFactory.handlememPlus();
                break;
            case R.id.buttonMemMinus /*2131427401*/:
                handleMemButtonPress();
                mCalcFactory.handleMemMinus();
                break;
            case R.id.buttonMemRecall /*2131427402*/:
                mCalcFactory.handlememRecall();
                break;
            case R.id.buttonSqrt /*2131427403*/:
                mCalcFactory.handleSqrt();
                break;
            case R.id.buttonMarkUp /*2131427404*/:
                mCalcFactory.handleOperation(8);
                break;
            case R.id.button7 /*2131427405*/:
                mCalcFactory.handleInput('7');
                break;
            case R.id.button8 /*2131427406*/:
                mCalcFactory.handleInput('8');
                break;
            case R.id.button9 /*2131427407*/:
                mCalcFactory.handleInput('9');
                break;
            case R.id.buttonDivide /*2131427408*/:
                mCalcFactory.handleOperation(3);
                break;
            case R.id.buttonPlusMinus /*2131427409*/:
                mCalcFactory.handleNegation();
                break;
            case R.id.button4 /*2131427410*/:
                mCalcFactory.handleInput('4');
                break;
            case R.id.button5 /*2131427411*/:
                mCalcFactory.handleInput('5');
                break;
            case R.id.button6 /*2131427412*/:
                mCalcFactory.handleInput('6');
                break;
            case R.id.buttonMultiply /*2131427413*/:
                mCalcFactory.handleOperation(4);
                break;
            case R.id.buttonPercentage /*2131427414*/:
                mCalcFactory.handlepercentage();
                break;
            case R.id.button1 /*2131427415*/:
                mCalcFactory.handleInput('1');
                break;
            case R.id.button0 /*2131427416*/:
                mCalcFactory.handleInput('0');
                break;
            case R.id.button2 /*2131427417*/:
                mCalcFactory.handleInput('2');
                break;
            case R.id.button00 /*2131427418*/:
                mCalcFactory.handleInput('\u0006');
                break;
            case R.id.button3 /*2131427419*/:
                mCalcFactory.handleInput('3');
                break;
            case R.id.buttonDecimalAll /*2131427420*/:
                mCalcFactory.handleDecimalPoint();
                break;
            case R.id.buttonPlus /*2131427421*/:
                mCalcFactory.handleOperation(1);
                break;
            case R.id.buttonMinus /*2131427422*/:
                mCalcFactory.handleOperation(2);
                break;
            case R.id.buttonEquals /*2131427423*/:
                mCalcFactory.handleEquals();
                break;
        }
        mFactoryResult = mCalcFactory.getResult();
        // ListViewFullScreen.listAdapter.notifyDataSetChanged();
        if (!false) {
            updateTextViews();
        }
    }

    private void handleMemButtonPress() {
        if (mCalcFactory.getOperationMode() == 1) {
            mCalcFactory.handleEquals();
        }
    }

    public boolean onLongClick(View arg0) {
        if (arg0.getId() == R.id.buttonDelete) {
            mCalcFactory.handleClearCurrent();
            mFactoryResult = mCalcFactory.getResult();
            updateTextViews();
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
        //   super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.mCalcFactory != null) {
            calculatorFactory com_browndwarf_calclib_calculatorFactory = this.mCalcFactory;
            PersistencyManager persistencyManager = this.persistencyManager;
            com_browndwarf_calclib_calculatorFactory.setIndianFormat(PersistencyManager.getIndianFormat());
            this.mFactoryResult = this.mCalcFactory.getResult();
            updateTextViews();
          /*  if (ListViewFullScreen.listAdapter != null) {
                ListViewFullScreen.listAdapter.notifyDataSetChanged();
            } */
        }
    }

    private void updateTextViews() {
        tvOperation.setText(mFactoryResult.operation);
        tvStep.setText(mFactoryResult.StepNum);
        tvAnswer.setText(mFactoryResult.answer);
        tvmemory.setText(getMemText());
        if (mFactoryResult.checkMode == 1) {
            highlightChecktv(true);
            highlightCorrecttv(false);
        }
        if (mFactoryResult.checkMode == 2) {
            highlightCorrecttv(true);
        }
        if (mFactoryResult.checkMode == 0) {
            highlightChecktv(false);
            highlightCorrecttv(false);
        }
    }

    private String getMemText() {
        String mem = "M=";
        if (!mCalcFactory.isMemoryPresent()) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder append = new StringBuilder().append(mem);
        double memValue = mCalcFactory.getMemValue();
        calculatorFactory com_browndwarf_calclib_calculatorFactory = mCalcFactory;
        return append.append(NumberFormatter.formatDecStr(memValue, calculatorFactory.getIndianFormatType())).toString();
    }

    private void highlightCorrecttv(boolean b) {
        String str;
        if (b) {
            str = "CORRECT";
        } else {
            str = BuildConfig.FLAVOR;
        }
        tvcorrect.setText(str);
    }

    private void highlightChecktv(boolean b) {
        String str;
        if (b) {
            str = "CHECK";
        } else {
            str = BuildConfig.FLAVOR;
        }
        tvcheck.setText(str);
    }

    private void bindAllButtons() {
        int i;
        for (i = 0; i < 25; i++) {
            allButtons[i] = (Button) findViewById(allButtonIds[i]);
        }
        for (i = 0; i < 2; i++) {
            allimageButtons[i] = (ImageButton) findViewById(allImageButtonsId[i]);
        }
    }

    private void setOnClickListenersForAllButtons() {
        int i;
        for (i = 0; i < 25; i++) {
            allButtons[i].setOnClickListener(this);
        }
        for (i = 0; i < 2; i++) {
            allimageButtons[i].setOnClickListener(this);
        }
        allimageButtons[0].setOnLongClickListener(this);
    }

    private void setFontSizeToAllTextViews(int i) {
        tvOperation.setTextSize(2, (float) i);
        tvStep.setTextSize(2, (float) i);
        tvmemory.setTextSize(2, (float) (i - 10));
        tvcheck.setTextSize(2, (float) (i - 10));
        tvcorrect.setTextSize(2, (float) (i - 10));
        tvAnswer.setTextSize(2, 45.0f);
    }

    private void setcolorToAllTextViews() {
        int fontColor = Color.argb(MotionEventCompat.ACTION_MASK, 65, 65, 65);
        tvOperation.setTextColor(fontColor);
        tvStep.setTextColor(fontColor);
        tvAnswer.setTextColor(fontColor);
    }

    private void setfontSizeToAllButtons(int j) {
        int i;
        for (i = 0; i < 11; i++) {
            allButtons[i].setTextSize(2, (float) j);
        }
        for (i = 15; i < 25; i++) {
            allButtons[i].setTextSize(2, (float) j);
        }
        allButtons[19].setTextSize(2, (float) (j + 10));
        for (i = 11; i < 15; i++) {
            allButtons[i].setTextSize(2, (float) j);
        }
    }

    private void setThemeColor(int r, int g, int b) {
        int textViewColor = Color.argb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        setBgColorToTextViews(Color.rgb(130, 162, 124));
    }

    private void setBgColorToTextViews(int themeColor) {
        tvAnswer.setBackgroundColor(themeColor);
        tvOperation.setBackgroundColor(themeColor);
        tvStep.setBackgroundColor(themeColor);
        tvmemory.setBackgroundColor(themeColor);
        tvcheck.setBackgroundColor(themeColor);
        tvcorrect.setBackgroundColor(themeColor);
    }

    void setFontToAllElements(Typeface font) {
        int i;
        font = keyfont;
        for (i = 0; i < 11; i++) {
            allButtons[i].setTypeface(font);
        }
        allButtons[21].setTypeface(font);
        font = robotoBold;
        for (i = 15; i < 25; i++) {
            allButtons[i].setTypeface(font);
        }
        font = robotoRegular;
        for (i = 11; i < 15; i++) {
            allButtons[i].setTypeface(font);
        }
        tvOperation.setTypeface(font);
        tvcheck.setTypeface(font);
        tvcorrect.setTypeface(font);
        tvmemory.setTypeface(font);
        font = sevenfont;
        tvStep.setTypeface(font);
        tvAnswer.setTypeface(font);
    }

    public void onResume() {
        super.onResume();
        calculatorFactory com_browndwarf_calclib_calculatorFactory = this.mCalcFactory;
        PersistencyManager persistencyManager = this.persistencyManager;
        com_browndwarf_calclib_calculatorFactory.setIndianFormat(PersistencyManager.getIndianFormat());
        this.mFactoryResult = this.mCalcFactory.getResult();
        updateTextViews();
    }
}
