package com.citizen.calculator2017;

import ThemeHandlers.ThemeManager;
import ThemeHandlers.ThemePageAdapter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.citizen.calculator2017.utils.PersistencyManager;

public class ThemeNavigator extends FragmentActivity implements OnClickListener {
    static Toast mtoast;
    Button bselect;
    ThemePageAdapter mAdapter;
    ViewPager mPager;
    PersistencyManager persistencyMgr;

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.theme_navigator);
        this.mPager = (ViewPager) findViewById(R.id.themepager);
        this.mAdapter = new ThemePageAdapter(getSupportFragmentManager());
        this.mPager.setAdapter(this.mAdapter);
        this.bselect = (Button) findViewById(R.id.bSelectTheme);
        this.bselect.setOnClickListener(this);
    }

    protected void onResume() {
        this.mAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (VERSION.SDK_INT >= 19 && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(5126);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bSelectTheme) {
            ThemeManager.getThemeManager().setThemeFromIndex(this.mPager.getCurrentItem());
            PersistencyManager.setTheme(ThemeManager.getThemeManager().getCurrentTheme());
            finish();
        }
    }
}
