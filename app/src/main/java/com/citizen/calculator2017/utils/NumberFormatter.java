package com.citizen.calculator2017.utils;

import android.support.v4.widget.DrawerLayout;

import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.calclib.IappConstants;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static android.widget.Spinner.MODE_DIALOG;
import static android.widget.Spinner.MODE_DROPDOWN;

public class NumberFormatter implements IappConstants {
    private static String getPatternString(int type) {
        switch (type) {
            case  MODE_DIALOG /*0*/:
                return "#,##,###.######";
            case  MODE_DROPDOWN /*1*/:
                return "###,###.######";
            case DrawerLayout.STATE_SETTLING /*2*/:
                return "###,###.00";
            default:
                return "#,##,###.######";
        }
    }

    public static String formatDecStr(double decNum, int formatType) {
        String str;
        DecimalFormat decimalFormat = new DecimalFormat(getPatternString(formatType));
        if (decNum != 0.0d) {
            str = decimalFormat.format(decNum);
        } else {
            str = "0";
        }
        int comma = str.length() - str.replace(",", BuildConfig.FLAVOR).length();
        if (str.replace(",", BuildConfig.FLAVOR).length() - 10 > 0) {
            return str.substring(0, comma + 10);
        }
        return str;
    }

    public static String formatDecStr(String str, int formatType) {
        return formatDecStr(Double.parseDouble(str), formatType);
    }

    public static String formatDecStr(String str, boolean formatType) {
        return formatDecStr(Double.parseDouble(str), formatType ? 0 : 1);
    }

    public static String formatDecStr(double d, boolean formatType) {
        return formatDecStr(d, formatType ? 0 : 1);
    }

    public static String formatDoubleWithoutScientificNotation(String d) {
        DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        decimalFormatter.applyPattern("############.##");
        return decimalFormatter.format(Double.parseDouble(d)).toLowerCase(Locale.ENGLISH);
    }

    public static String formatDoubleWithoutScientificNotation(double d) {
        DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        decimalFormatter.applyPattern("############.##");
        return decimalFormatter.format(d);
    }
}
