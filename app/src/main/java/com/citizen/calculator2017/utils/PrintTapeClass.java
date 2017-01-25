package com.citizen.calculator2017.utils;

import android.support.v4.widget.DrawerLayout;
import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.BuildConfig;

import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

import static android.widget.Spinner.MODE_DROPDOWN;

public class PrintTapeClass implements IappConstants {
    private static ArrayList<HistoryElements> hist;
    static ArrayList<ListElement> list;

    static {
        list = new ArrayList();
    }

    public static String buildStringFromHistory(ArrayList<HistoryElements> h1) {
        String str = BuildConfig.FLAVOR;
        ArrayList<ListElement> h = getListFromHistory(h1);
        int maxOperandLen = 0;
        Iterator it = h.iterator();
        while (it.hasNext()) {
            int oprLen = ((ListElement) it.next()).operand.length();
            if (oprLen > maxOperandLen) {
                maxOperandLen = oprLen;
            }
        }
        it = h.iterator();
        while (it.hasNext()) {
            ListElement he = (ListElement) it.next();
            String operand = he.operand;
            int spacesAfterOperation = (maxOperandLen + 4) - getEffectiveStrLen(he.operand);
            if (he.operation.isEmpty()) {
                str = str + "\n  ";
            }
            if (he.isAnswer) {
                str = str + "- - - - - - - \n";
            }
            str = str + he.operation + getSpacesString(spacesAfterOperation) + operand + getPercentageString(he.percentMode) + "  " + he.comment + "\n";
        }
        return str;
    }

    private static String getPercentageString(int percentMode, double val) {
        String valstr = NumberFormatter.formatDecStr(val, getInadianFormat());
        switch (percentMode) {
            case MODE_DROPDOWN /*1*/:
                return "% [" + valstr + "]";
            case DrawerLayout.STATE_SETTLING /*2*/:
                return "% [Tax =" + valstr + "]";
            default:
                return BuildConfig.FLAVOR;
        }
    }

    private static int getInadianFormat() {
        return calculatorFactory.getIndianFormatType();
    }

    private static int getNumOfCommas(String line) {
        return line.length() - line.replace(".", BuildConfig.FLAVOR).length();
    }

    private static int getEffectiveStrLen(String str) {
        return str.length() - (getNumOfCommas(str) / 2);
    }

    private static String getPercentageString(int mode) {
        if (mode != 0) {
            return "%";
        }
        return BuildConfig.FLAVOR;
    }

    private static String getSpacesString(int spacesAfterOperation) {
        String str = BuildConfig.FLAVOR;
        int spacesAfterOperation2 = spacesAfterOperation;
        while (true) {
            spacesAfterOperation = spacesAfterOperation2 - 1;
            if (spacesAfterOperation2 <= 0) {
                return str;
            }
            str = str + "  ";
            spacesAfterOperation2 = spacesAfterOperation;
        }
    }

    public static ArrayList<ListElement> getListFromHistory(ArrayList<HistoryElements> h) {
        hist = h;
        int len = hist.size();
        list.clear();
        if (len == 0) {
            return list;
        }
        int j = 0;
        for (int i = 0; i < len; i++) {
            ListElement item = new ListElement();
            HistoryElements hitem = (HistoryElements) hist.get(i);
            item.operand = NumberFormatter.formatDecStr(hitem.operand.doubleValue(), getInadianFormat());
            item.operation = hitem.prevOperation;
            item.comment = hitem.comment;
            item.indexInHistory = i;
            item.val = hitem.operand.doubleValue();
            item.percentageAmount = hitem.percentageAmount;
            item.percentMode = hitem.percentageMode;
            item.lineNumber = (j + 1) + BuildConfig.FLAVOR;
            list.add(j, item);
            j = (j + addAnswerNode(item.indexInHistory)) + 1;
        }
        printlist(list);
        return list;
    }

    private static void printlist(ArrayList<ListElement> history) {
        if (history != null) {
            System.out.println("history size: " + history.size());
            for (int i = 0; i < history.size(); i++) {
                ListElement item = (ListElement) history.get(i);
                System.out.println(i + ": " + item.operation + "  " + item.operand + "  IndexInhistory = " + item.indexInHistory);
            }
        }
    }

    private static int addAnswerNode(int histIndex) {
        HistoryElements hitem = (HistoryElements) hist.get(histIndex);
        if (!hitem.isAnswer) {
            return 0;
        }
        ListElement item = new ListElement();
        item.isAnswer = true;
        item.operation = "=";
        item.comment = hitem.commentOnAnswerNode;
        item.indexInHistory = histIndex;
        item.operand = NumberFormatter.formatDecStr(hitem.computedAnswer, getInadianFormat());
        item.lineNumber = (list.size() + 1) + BuildConfig.FLAVOR;
        list.add(list.size(), item);
        return 1;
    }
}
