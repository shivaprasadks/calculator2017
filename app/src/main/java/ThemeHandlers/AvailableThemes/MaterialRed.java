package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import com.citizen.calculator2017.MaterialFragment;
import com.citizen.calculator2017.R;

public class MaterialRed extends ThemeType {
    public MaterialRed() {
        this.imageId = R.drawable.material_red;
        this.UID = 4;
    }

    public Fragment getFragment() {
        return new MaterialFragment();
    }

    public int getDisplayBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 34, 49, 63);
    }

    public int getDisplayFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 210, 77, 87);
    }

    public int getOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 210, 77, 87);
    }

    public int getOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 170, 170, 170);
    }

    public int getBasicOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 34, 49, 63);
    }

    public int getBasicOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 170, 170, 170);
    }

    public int getNumPadBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 34, 49, 63);
    }

    public int getNumPadFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 170, 170, 170);
    }

    public int getLayout() {
        return R.layout.material_layout;
    }
}
