package com.citizen.calculator2017.Adapters;

import ThemeHandlers.ThemeManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.ListViewFullScreen;
import com.citizen.calculator2017.MainActivity;
import com.citizen.calculator2017.OptionsFragment;
import com.citizen.calculator2017.utils.UIappConstants;

public class LauncherPageAdapter extends FragmentStatePagerAdapter implements IappConstants, UIappConstants {
    int curSkinIndex;
    Fragment main;
    Fragment taxPlus;
    public static final int MODE_DIALOG = 0;
    public static final int MODE_DROPDOWN = 1;

    public LauncherPageAdapter(FragmentManager fm) {
        super(fm);
        this.curSkinIndex = 0;
    }

    public Fragment getItem(int pos) {
        switch (pos) {
            case MODE_DIALOG /*0*/:
                return new OptionsFragment();
            case MODE_DROPDOWN /*1*/:
                return ThemeManager.getThemeManager().getCurrentTheme().getFragment();
            case DrawerLayout.STATE_SETTLING /*2*/:
                return new ListViewFullScreen();
            default:
                return new MainActivity();
        }
    }

    public int getCount() {
        return 3;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public CharSequence getPageTitle(int pos) {
        switch (pos) {
            case MODE_DIALOG /*0*/:
                return "<<";
            case MODE_DROPDOWN /*1*/:
                return BuildConfig.FLAVOR;
            case DrawerLayout.STATE_SETTLING /*2*/:
                return ">>";
            default:
                return null;
        }
    }
}
