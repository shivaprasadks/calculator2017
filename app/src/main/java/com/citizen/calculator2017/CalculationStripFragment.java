package com.citizen.calculator2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import com.citizen.calculator2017.calclib.HistoryElements;
import com.citizen.calculator2017.calclib.HofH.HofHListItem;
import com.citizen.calculator2017.calclib.HofH.HofHManager;
import com.citizen.calculator2017.calclib.HofH.IHofHConstants;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.Adapters.HofHCalcStripAdapter;
import com.citizen.calculator2017.utils.PersistencyManager;
import java.util.ArrayList;
import com.citizen.calculator2017.utils.ListElement;
import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.PrintTapeClass;

public class CalculationStripFragment extends Fragment implements IHofHConstants, OnClickListener, IappConstants {
    HofHCalcStripAdapter cadapter;
    ListView calcStrip;
    calculatorFactory factory;
    ArrayList<HistoryElements> hist;
    private HistoryElements hitem;
    HofHManager hofhmgr;
    private ListElement item;
    ArrayList<ListElement> list;
    int selectionIndex;

    public CalculationStripFragment() {
        this.selectionIndex = 0;
    }

    public boolean getIndianFormat() {
        return PersistencyManager.getIndianFormat();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_strip_fragment, container, false);
        this.calcStrip = (ListView) view.findViewById(R.id.listcalcStrip);
        this.hofhmgr = HofHManager.getHofHManager();
        Bundle bundle = getArguments();
        this.list = new ArrayList();
        if (bundle != null) {
            this.selectionIndex = bundle.getInt(IHofHConstants.SELECTION_INDEX);
            this.hist = ((HofHListItem) this.hofhmgr.getHofH().history.get(this.selectionIndex)).calculationStrip;
            buildListFromHistory();
        } else {
            this.list.add(new ListElement("No Item"));
            this.selectionIndex = 0;
        }
        this.cadapter = new HofHCalcStripAdapter(getActivity(), this.list, null);
        this.calcStrip.setAdapter(this.cadapter);
        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.cadapter != null) {
            this.hist = ((HofHListItem) this.hofhmgr.getHofH().history.get(this.selectionIndex)).calculationStrip;
            buildListFromHistory();
            this.cadapter.notifyDataSetChanged();
        }
    }

    private void buildListFromHistory() {
        int len = this.hist.size();
        this.list.clear();
        int j = 0;
        for (int i = 0; i < len; i++) {
            this.item = new ListElement();
            this.hitem = (HistoryElements) this.hist.get(i);
            this.item.operand = NumberFormatter.formatDecStr(this.hitem.operand.doubleValue(), getIndianFormat());
            this.item.operation = this.hitem.prevOperation;
            this.item.indexInHistory = i;
            this.item.val = this.hitem.operand.doubleValue();
            this.item.percentMode = this.hitem.percentageMode;
            this.item.lineNumber = (j + 1) + BuildConfig.FLAVOR;
            this.list.add(j, this.item);
            j = (j + addAnswerNode(i, this.item.indexInHistory)) + 1;
        }
    }

    private int addAnswerNode(int i, int histIndex) {
        this.hitem = (HistoryElements) this.hist.get(i);
        if (!this.hitem.operation.isEmpty()) {
            return 0;
        }
        this.item = new ListElement();
        this.item.isAnswer = true;
        this.item.operation = "=";
        this.item.indexInHistory = histIndex;
        this.item.operand = NumberFormatter.formatDecStr(this.hitem.computedAnswer, getIndianFormat());
        this.item.lineNumber = (this.list.size() + 1) + BuildConfig.FLAVOR;
        this.list.add(this.list.size(), this.item);
        return 1;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bShareTape /*2131427427*/:
                String shareString = PrintTapeClass.buildStringFromHistory(this.hist);
                try {
                    Intent i1 = new Intent("android.intent.action.SEND");
                    i1.setType("text/plain");
                    i1.putExtra("android.intent.extra.SUBJECT", "Tape Calc by BrownDwarf");
                    i1.putExtra("android.intent.extra.TEXT", BuildConfig.FLAVOR + shareString);
                    startActivity(Intent.createChooser(i1, "Choose one"));
                } catch (Exception e) {
                }
            case R.id.bLoadTape /*2131427428*/:
                PersistencyManager.setHistoryFromHofH(this.hist);
                startActivity(new Intent(getActivity(), LauncherActivity.class));
            default:
        }
    }

    private void deleteItem(int i) {
        this.hofhmgr.deleteItem(i);
    }

    private void selectNext() {
        if (this.selectionIndex + 1 < this.hofhmgr.getHofH().history.size()) {
            ArrayList arrayList = this.hofhmgr.getHofH().history;
            int i = this.selectionIndex + 1;
            this.selectionIndex = i;
            this.hist = ((HofHListItem) arrayList.get(i)).calculationStrip;
            buildListFromHistory();
            this.cadapter.notifyDataSetChanged();
        }
    }

    private void selectPrev() {
        if (this.selectionIndex > 0) {
            ArrayList arrayList = this.hofhmgr.getHofH().history;
            int i = this.selectionIndex - 1;
            this.selectionIndex = i;
            this.hist = ((HofHListItem) arrayList.get(i)).calculationStrip;
            buildListFromHistory();
            this.cadapter.notifyDataSetChanged();
        }
    }
}
