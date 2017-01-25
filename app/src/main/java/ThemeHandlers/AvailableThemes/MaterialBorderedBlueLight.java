package ThemeHandlers.AvailableThemes;

import ThemeHandlers.ThemeType;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import com.citizen.calculator2017.MaterialFragment;
import com.citizen.calculator2017.R;

public class MaterialBorderedBlueLight extends ThemeType {
    public MaterialBorderedBlueLight() {
        this.imageId = R.drawable.material_blue_light;
    }

    public Fragment getFragment() {
        return new MaterialFragment();
    }

    public int getOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 32, 168, 218);
    }

    public int getOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 230, 230, 230);
    }

    public int getNumPadBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 230, 230, 230);
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
        return R.layout.material_bordered_buttons;
    }
}
