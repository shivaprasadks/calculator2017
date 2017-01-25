package com.citizen.calculator2017;

import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.citizen.calculator2017.calclib.HofH.HofHManager;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.utils.InfoScreenData;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.PrintTapeClass;

public class OptionsFragment extends Fragment implements OnClickListener {
    Button bEmailDev;
    Button bHelp;
    Button bHistory;
    Button bRateApp;
    Button bShareApp;
    Button bShareTape;
    Button bThemes;
    CheckBox cbFormat;
    CheckBox cbSound;
    CheckBox cbVibrate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.options_fragment, container, false);
        this.bHelp = (Button) v.findViewById(R.id.bHelp);
        this.bShareTape = (Button) v.findViewById(R.id.bShareTape);
        this.bHistory = (Button) v.findViewById(R.id.bHistory);
        this.bRateApp = (Button) v.findViewById(R.id.bRate);
        this.bShareApp = (Button) v.findViewById(R.id.bShareApp);
        this.bEmailDev = (Button) v.findViewById(R.id.bEmailDev);
        this.bThemes = (Button) v.findViewById(R.id.bThemes);
        this.cbFormat = (CheckBox) v.findViewById(R.id.cbFormat);
        this.cbVibrate = (CheckBox) v.findViewById(R.id.cbVibrate);
        this.cbSound = (CheckBox) v.findViewById(R.id.cbSound);
        this.bHelp.setOnClickListener(this);
        this.bHistory.setOnClickListener(this);
        this.bShareTape.setOnClickListener(this);
        this.bShareApp.setOnClickListener(this);
        this.bRateApp.setOnClickListener(this);
        this.bEmailDev.setOnClickListener(this);
        this.bThemes.setOnClickListener(this);
        this.cbFormat.setOnClickListener(this);
        this.cbVibrate.setOnClickListener(this);
        this.cbSound.setOnClickListener(this);
        this.cbFormat.setChecked(PersistencyManager.getIndianFormat());
        this.cbVibrate.setChecked(PersistencyManager.getVibrateFlag());
        this.cbSound.setChecked(PersistencyManager.getButtonSoundFlag());
        return v;
    }

    public void onClick(View v) {
        String shareString;
        Intent i1;
        switch (v.getId()) {
            case R.id.bShareTape /*2131427427*/:
                calculatorFactory mCalcFactory = calculatorFactory.getCalcFactory();
                PrintTapeClass ptape = new PrintTapeClass();
                shareString = PrintTapeClass.buildStringFromHistory(mCalcFactory.getHistory());
                try {
                    i1 = new Intent("android.intent.action.SEND");
                    i1.setType("text/plain");
                    i1.putExtra("android.intent.extra.SUBJECT", "Citizen Calculator");
                    i1.putExtra("android.intent.extra.TEXT", BuildConfig.FLAVOR + shareString);
                    startActivity(Intent.createChooser(i1, "Choose one"));
                } catch (Exception e) {
                }
            case R.id.bHistory /*2131427475*/:
                if (HofHManager.getHofHManager().getHofH().history.size() > 0) {
                    startActivity(new Intent(getActivity(), CalculationStripView.class));
                } else {
                    showToast("History Empty");
                }
            case R.id.bThemes /*2131427476*/:
                startActivity(new Intent(getActivity(), ThemeNavigator.class));
            case R.id.cbFormat /*2131427477*/:
                PersistencyManager.setIndianFormat(this.cbFormat.isChecked());
            case R.id.cbVibrate /*2131427478*/:
                PersistencyManager.setVibrateFlag(this.cbVibrate.isChecked());
            case R.id.cbSound /*2131427479*/:
                PersistencyManager.setButtonSoundFlag(this.cbSound.isChecked());
            case R.id.bHelp /*2131427480*/:
                showInfoDialog(new InfoScreenData().info);
            case R.id.bRate /*2131427481*/:
                ContextWrapper context = getActivity();
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
                } catch (ActivityNotFoundException e2) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
            case R.id.bShareApp /*2131427482*/:
                String linkPrefix = "https://play.google.com/store/apps/details?id=";
                shareString = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                try {
                    i1 = new Intent("android.intent.action.SEND");
                    i1.setType("text/plain");
                    i1.putExtra("android.intent.extra.SUBJECT", "TapeCalc : Android");
                    i1.putExtra("android.intent.extra.TEXT", ("\n Hey try out this awesome app\n\n" + "Video Link : https://applink.com  \n\n\n App Link: ") + shareString);
                    startActivity(Intent.createChooser(i1, "Choose one"));
                } catch (Exception e3) {
                }
            case R.id.bEmailDev /*2131427483*/:
                Intent i = new Intent("android.intent.action.SEND");
                i.setType("plain/text");
                i.putExtra("android.intent.extra.EMAIL", new String[]{"myownmail@google.com"});
                i.putExtra("android.intent.extra.SUBJECT", "Check Calc Customer");
                i.putExtra("android.intent.extra.TEXT", BuildConfig.FLAVOR);
                startActivity(Intent.createChooser(i, BuildConfig.FLAVOR));
            default:
        }
    }

    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    private void showInfoDialog(String str) {
        new Builder(new ContextThemeWrapper(getActivity(), 16973935)).setTitle("Help").setMessage("Features of app").setNegativeButton(17039370, null).create().show();
    }
}
