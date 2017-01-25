package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.support.v4.app.Fragment;

import com.citizen.calculator2017.MainActivity;
import com.citizen.calculator2017.R;

public class ThemeNormalCiti extends ThemeType {
    public ThemeNormalCiti() {
        this.imageId = R.drawable.normal_citi;
        this.UID = 0;
    }

    public Fragment getFragment() {
        return new MainActivity();
    }
}
