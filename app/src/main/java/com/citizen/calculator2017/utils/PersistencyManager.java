package com.citizen.calculator2017.utils;

import ThemeHandlers.ThemeManager;
import ThemeHandlers.ThemeType;
import android.content.Context;
import android.util.Log;
import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.HistoryManager;
import com.citizen.calculator2017.calclib.HofH.HofHListItem;
import com.citizen.calculator2017.calclib.HofH.HofHManager;
import com.citizen.calculator2017.calclib.HofH.HofHtype;
import com.citizen.calculator2017.calclib.calculatorFactory;
import java.util.ArrayList;

public class PersistencyManager {
    static boolean ThemeChangedByUser;
    static ArrayList<HistoryElements> historyFromPrefs;
    private static boolean historySetFromHofH;
    private static boolean hofhcleared;
    static PersistableValues persistentValues;
    static ThemeType savedTheme;
    static PersistencyManager theObject;
    private final String allValues;
    private final String currentTheme;
    private final String histWhilePausing;
    private final String hofh;
    HofHtype hofhfromprefs;
    HofHManager hofhmgr;
    private calculatorFactory mCalcFactory;
    boolean startUp;
    TinyDB tinydb;

    static {
        ThemeChangedByUser = false;
        historySetFromHofH = false;
        hofhcleared = false;
    }

    private PersistencyManager() {
        this.allValues = "AllValues";
        this.hofh = "hofh";
        this.histWhilePausing = "histWhilePausing";
        this.currentTheme = "CurrentTheme";
        this.startUp = false;
    }

    private PersistencyManager(Context context) {
        this.allValues = "AllValues";
        this.hofh = "hofh";
        this.histWhilePausing = "histWhilePausing";
        this.currentTheme = "CurrentTheme";
        this.startUp = false;
        this.tinydb = new TinyDB(context);
        this.mCalcFactory = calculatorFactory.getCalcFactory();
        persistentValues = new PersistableValues();
        this.hofhmgr = HofHManager.getHofHManager();
    }

    public static PersistencyManager getPersistencyManager(Context context) {
        if (theObject == null) {
            theObject = new PersistencyManager(context);
        }
        return theObject;
    }

    public void saveParameters() {
        persistentValues.gt = this.mCalcFactory.getGt();
        this.tinydb.putObject("AllValues", persistentValues);
        this.tinydb.putObject("hofh", this.hofhmgr.getHofH());
        if (!HistoryManager.getHistoryManager().conatinsInfinity()) {
            HofHListItem ha = new HofHListItem();
            ha.calculationStrip = this.mCalcFactory.getHistory();
            this.tinydb.putObject("histWhilePausing", ha);
        }
        this.tinydb.putObject("CurrentTheme", ThemeManager.getThemeManager().getCurrentTheme());
    }

    public void restoreParameters() {
        persistentValues = (PersistableValues) this.tinydb.getObject("AllValues", PersistableValues.class);
        if (persistentValues == null) {
            persistentValues = new PersistableValues();
        }
        if (!hofhcleared) {
            this.hofhfromprefs = (HofHtype) this.tinydb.getObject("hofh", HofHtype.class);
            this.hofhmgr.setHofH(this.hofhfromprefs);
        }
        if (historySetFromHofH) {
            historySetFromHofH = false;
            this.mCalcFactory.setHistory(historyFromPrefs);
        } else {
            HofHListItem ha = (HofHListItem) this.tinydb.getObject("histWhilePausing", HofHListItem.class);
            if (ha != null) {
                historyFromPrefs = ha.calculationStrip;
                this.mCalcFactory.setHistory(historyFromPrefs);
            }
        }
        this.mCalcFactory.setTaxRate(persistentValues.taxRate);
        this.mCalcFactory.setGt(persistentValues.gt);
        restoreTheme();
    }

    public void restoreTheme() {
        if (!ThemeChangedByUser) {
            savedTheme = (ThemeType) this.tinydb.getObject("CurrentTheme", ThemeType.class);
        }
        ThemeChangedByUser = false;
        ThemeManager.getThemeManager().setCurrentTheme(savedTheme);
    }

    public static void sethofhCleared(boolean b) {
        hofhcleared = b;
    }

    public static boolean getIndianFormat() {
        return persistentValues.indianFormat;
    }

    public static void setIndianFormat(boolean b) {
        persistentValues.indianFormat = b;
    }

    public static boolean getVibrateFlag() {
        return persistentValues.mVibrateFlag;
    }

    public static boolean getButtonSoundFlag() {
        return persistentValues.mButtonSoundFlag;
    }

    public static void setVibrateFlag(boolean b) {
        persistentValues.mVibrateFlag = b;
    }

    public static void setButtonSoundFlag(boolean b) {
        persistentValues.mButtonSoundFlag = b;
    }

    public static void setTaxRate(double d) {
        persistentValues.taxRate = d;
    }

    public static double getTaxRate() {
        return persistentValues.taxRate;
    }

    public static void setHistoryFromHofH(ArrayList<HistoryElements> hist) {
        historySetFromHofH = true;
        historyFromPrefs = hist;
    }

    public static void traceLog(String str) {
        Log.d("ABG", str);
    }

    public static void setTheme(ThemeType t) {
        ThemeChangedByUser = true;
        savedTheme = t;
    }

    public static boolean getStartup() {
        boolean ret = persistentValues.startup;
        persistentValues.startup = false;
        return ret;
    }
}
