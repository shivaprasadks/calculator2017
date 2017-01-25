package com.citizen.calculator2017;

import ThemeHandlers.ThemeManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.Adapters.LauncherPageAdapter;
import com.citizen.calculator2017.utils.PersistencyManager;
import com.citizen.calculator2017.utils.UIappConstants;

public class LauncherActivity extends FragmentActivity implements IappConstants, UIappConstants {
    static Toast mtoast;
    LauncherPageAdapter mAdapter;
    ViewPager mPager;
    PersistencyManager persistencyMgr;
    PagerTitleStrip titleStrip;

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.launcher_view_pager);
        this.mPager = (ViewPager) findViewById(R.id.launcherPager);
        this.mAdapter = new LauncherPageAdapter(getSupportFragmentManager());
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setCurrentItem(1);
        this.persistencyMgr = PersistencyManager.getPersistencyManager(getApplicationContext());
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.titleStrip = (PagerTitleStrip) findViewById(R.id.lpager_title_strip);
    }

    protected void onResume() {
        this.persistencyMgr.restoreParameters();
        this.titleStrip.setBackgroundColor(ThemeManager.getThemeManager().getCurrentTheme().getDisplayBg());
        this.titleStrip.setTextColor(ThemeManager.getThemeManager().getCurrentTheme().getDisplayFg());
        PersistencyManager persistencyManager = this.persistencyMgr;
        if (PersistencyManager.getStartup()) {
            startActivity(new Intent(this, ThemeNavigator.class));
        }
        this.mAdapter.notifyDataSetChanged();
        this.mPager.setCurrentItem(1);
        super.onResume();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (VERSION.SDK_INT >= 19 && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(5126);
        }
    }

    public static void showToast(String string, Activity activity) {
        if (mtoast != null) {
            mtoast.cancel();
        }
        Toast toast = mtoast;
        Toast.makeText(activity, string, 0).show();
    }

    protected void onPause() {
        this.persistencyMgr.saveParameters();
        super.onPause();
    }
}
