package com.citizen.calculator2017.calclib.HofH;

import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.HistoryManager;
import java.util.ArrayList;

public class HofHManager {
    static boolean objectCreated;
    static HofHManager onlyObject;
    private HofHtype historyOfHistory;

    private HofHManager() {
        this.historyOfHistory = new HofHtype();
    }

    static {
        objectCreated = false;
    }

    public static HofHManager getHofHManager() {
        if (onlyObject != null) {
            return onlyObject;
        }
        HofHManager hofHManager = new HofHManager();
        onlyObject = hofHManager;
        return hofHManager;
    }

    public void setHofH(HofHtype ht) {
        if (ht != null) {
            this.historyOfHistory = ht;
        }
    }

    public HofHtype getHofH() {
        return this.historyOfHistory;
    }

    public void addToHistory(ArrayList<HistoryElements> h) {
        if (this.historyOfHistory != null) {
            this.historyOfHistory.history.add(0, new HofHListItem(h, this.historyOfHistory.history.size()));
        } else {
            HistoryManager.traceLog("HofH is null");
        }
    }

    private void printhofh() {
        for (int i = 0; i < this.historyOfHistory.history.size(); i++) {
            HistoryManager.traceLog("hof item " + i + " = " + ((HofHListItem) this.historyOfHistory.history.get(i)).name);
        }
    }

    public void clearAll() {
        this.historyOfHistory.history.clear();
    }

    public void deleteItem(int i) {
        if (i >= 0 && i < this.historyOfHistory.history.size()) {
            this.historyOfHistory.history.remove(i);
        }
    }
}
