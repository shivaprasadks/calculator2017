package com.citizen.calculator2017.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.citizen.calculator2017.calclib.HofH.HofHManager;
import com.citizen.calculator2017.calclib.HofH.IHofHConstants;
import com.citizen.calculator2017.CalculationStripFragment;

public class HofHPageAdapter extends FragmentStatePagerAdapter implements IHofHConstants {
    HofHManager hmgr;
    int size;

    public HofHPageAdapter(FragmentManager fm) {
        super(fm);
        this.hmgr = HofHManager.getHofHManager();
    }

    public Fragment getItem(int pos) {
        CalculationStripFragment frag = new CalculationStripFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IHofHConstants.SELECTION_INDEX, pos);
        frag.setArguments(bundle);
        return frag;
    }

    public int getCount() {
        this.size = this.hmgr.getHofH().history.size();
        return this.size;
    }

    public CharSequence getPageTitle(int position) {
        this.size = this.hmgr.getHofH().history.size();
        return (position + 1) + "/" + this.size;
    }
}
