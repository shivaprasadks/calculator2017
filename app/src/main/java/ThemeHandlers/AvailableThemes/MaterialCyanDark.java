package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import com.citizen.calculator2017.MaterialFragment;
import com.citizen.calculator2017.R;

public class MaterialCyanDark extends ThemeType {
    public MaterialCyanDark() {
        this.imageId = R.drawable.material_cyan;
        this.UID = 3;
    }

    public Fragment getFragment() {
        return new MaterialFragment();
    }

    public int getOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 0, 150, 135);
    }

    public int getOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getBasicOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getBasicOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getNumPadBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getNumPadFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getDisplayBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 40, 40, 40);
    }

    public int getDisplayFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 0, 150, 135);
    }

    public int getLayout() {
        return R.layout.material_layout;
    }
}
