package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.support.v4.app.Fragment;
import com.citizen.calculator2017.MaterialFragment;
import com.citizen.calculator2017.R;
import com.citizen.calculator2017.TaxplusFragment;

public class TaxPlusTheme extends ThemeType {
    public TaxPlusTheme() {
        this.imageId = R.drawable.theme_tax_plus;
        this.UID = 1;
    }

    public Fragment getFragment() {
        return new TaxplusFragment();
    }
}
