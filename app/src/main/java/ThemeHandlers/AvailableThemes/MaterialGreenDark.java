package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import com.citizen.calculator2017.MaterialFragment;
import com.citizen.calculator2017.R;

public class MaterialGreenDark extends ThemeType {
    public MaterialGreenDark() {
        this.imageId = R.drawable.material_green;
        this.UID = 5;
    }

    public Fragment getFragment() {
        return new MaterialFragment();
    }

    public int getDisplayBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getDisplayFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 1, 200, 83);
    }

    public int getOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 1, 200, 83);
    }

    public int getOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getBasicOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getBasicOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 170, 170, 170);
    }

    public int getNumPadBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getNumPadFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 170, 170, 170);
    }

    public int getLayout() {
        return R.layout.material_layout;
    }
}
