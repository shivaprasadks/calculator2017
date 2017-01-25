package com.citizen.calculator2017.calclib.HofH;

import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.HistoryManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.citizen.calculator2017.utils.HtmlFormatter;

public class HofHListItem {
    public ArrayList<HistoryElements> calculationStrip;
    String commentColor;
    String correctionColor;
    public String dateAndTime;
    public String name;
    String opRedColor;
    String operandColor;

    public HofHListItem(ArrayList<HistoryElements> h, int numberOfElements) {
        this.operandColor = "66D9EF";
        this.commentColor = "E6DB74";
        this.opRedColor = "FF851B";
        this.correctionColor = "F92672";
        this.calculationStrip = h;
        this.name = "Calculation " + numberOfElements;
        setDateTimeString();
        HistoryManager.traceLog("adding to hofh " + this.name);
    }

    public HofHListItem() {
        this.operandColor = "66D9EF";
        this.commentColor = "E6DB74";
        this.opRedColor = "FF851B";
        this.correctionColor = "F92672";
    }

    public void setDateTimeString() {
        this.dateAndTime = new SimpleDateFormat("EEE, MMM d, HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return HtmlFormatter.htmlcolor(this.name, this.operandColor);
    }

    public ArrayList<HistoryElements> getCalculationStrip() {
        return this.calculationStrip;
    }

    public String getDateAndTime() {
        return this.dateAndTime;
    }
}
