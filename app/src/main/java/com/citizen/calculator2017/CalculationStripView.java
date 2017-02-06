package com.citizen.calculator2017;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.citizen.calculator2017.calclib.HofH.HofHListItem;
import com.citizen.calculator2017.calclib.HofH.HofHManager;
import com.citizen.calculator2017.calclib.HofH.IHofHConstants;
import com.citizen.calculator2017.Adapters.HofHPageAdapter;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.PrintTapeClass;

public class CalculationStripView extends FragmentActivity implements OnClickListener, IHofHConstants {
    static HofHPageAdapter mAdapter;
    Button bDeleteAll;
    Button bLoad;
    Button bShare;
    HofHManager hofhmgr;
    ViewPager mPager;

    /* renamed from: com.browndwarf.checkcalculator.CalculationStripView.1 */
    class C01271 implements DialogInterface.OnClickListener {
        C01271() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /* renamed from: com.browndwarf.checkcalculator.CalculationStripView.2 */
    class C01282 implements DialogInterface.OnClickListener {
        C01282() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            CalculationStripView.this.deleteAllHistoryItems();
            dialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_strip_view);
        mAdapter = new HofHPageAdapter(getSupportFragmentManager());
        this.mPager = (ViewPager) findViewById(R.id.pager);
        this.mPager.setAdapter(mAdapter);
        this.bShare = (Button) findViewById(R.id.bShareTape);
        this.bLoad = (Button) findViewById(R.id.bLoadTape);
        this.bDeleteAll = (Button) findViewById(R.id.bDeleteAll);
        this.bShare.setOnClickListener(this);
        this.bLoad.setOnClickListener(this);
        this.bDeleteAll.setOnClickListener(this);
        this.hofhmgr = HofHManager.getHofHManager();
    }

    private void deleteItem(int i) {
        this.hofhmgr.deleteItem(i);
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        this.hofhmgr = HofHManager.getHofHManager();
        int selectionIndex = this.mPager.getCurrentItem();
        switch (v.getId()) {
            case R.id.bShareTape /*2131427427*/:
                if (this.hofhmgr.getHofH().history.size() != 0) {
                    String shareString = PrintTapeClass.buildStringFromHistory(((HofHListItem) this.hofhmgr.getHofH().history.get(selectionIndex)).calculationStrip);
                    try {
                        Intent i1 = new Intent("android.intent.action.SEND");
                        i1.setType("text/plain");
                        i1.putExtra("android.intent.extra.SUBJECT", "CT 500 by BrownDwarf");
                        i1.putExtra("android.intent.extra.TEXT", BuildConfig.FLAVOR + shareString);
                        startActivity(Intent.createChooser(i1, "Choose one"));
                    } catch (Exception e) {
                    }
                }
            case R.id.bLoadTape /*2131427428*/:
                if (this.hofhmgr.getHofH().history.size() != 0) {
                    PersistencyManager.setHistoryFromHofH(((HofHListItem) this.hofhmgr.getHofH().history.get(selectionIndex)).calculationStrip);
                    finish();
                }
            case R.id.bDeleteAll /*2131427429*/:
                AskOption().show();
            default:
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (VERSION.SDK_INT >= 19 && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(5126);
        }
    }

    private AlertDialog AskOption() {
        return new Builder(this).setTitle("Confirm Delete All").setMessage("Are you sure?").setPositiveButton("Delete", new C01282()).setNegativeButton("cancel", new C01271()).create();
    }

    protected void deleteAllHistoryItems() {
        this.hofhmgr.clearAll();
        mAdapter.notifyDataSetChanged();
        PersistencyManager.sethofhCleared(true);
        showToast("History Cleared");
        finish();
    }
}
