package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import com.citizen.calculator2017.MaterialFragment;
import com.citizen.calculator2017.R;

public class MaterialTheme extends ThemeType {
    public MaterialTheme() {
        this.imageId = R.drawable.material_blue_light;
        this.UID = 2;
    }

    public Fragment getFragment() {
        return new MaterialFragment();
    }

    public int getOperatorsBg() {
        return Color.argb(100, 32, 168, 218);
    }

    public int getOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getBasicOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 150, 150, 150);
    }

    public int getBasicOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 54, 69, 76);
    }

    public int getNumPadBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 190, 190, 190);
    }

    public int getNumPadFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 54, 69, 76);
    }

    public int getDisplayBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 28, 45, 55);
    }

    public int getDisplayFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 34, 170, 218);
    }

    public int getLayout() {
        return R.layout.material_layout;
    }
}
