package ThemeHandlers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.citizen.calculator2017.calclib.HofH.IHofHConstants;

public class ThemePageAdapter extends FragmentStatePagerAdapter implements IHofHConstants {
    public ThemePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int pos) {
        ThemeNavigatorFragment frag = new ThemeNavigatorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IHofHConstants.SELECTION_INDEX, pos);
        frag.setArguments(bundle);
        return frag;
    }

    public int getCount() {
        return ThemeManager.getThemeManager().getNumberOFThemes();
    }

    public CharSequence getPageTitle(int position) {
        return (position + 1) + "/" + ThemeManager.getThemeManager().getNumberOFThemes();
    }
}
