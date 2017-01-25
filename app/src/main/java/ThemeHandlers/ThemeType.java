package ThemeHandlers;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import com.citizen.calculator2017.MainActivity;

public class ThemeType {
    protected int UID;
    protected int imageId;

    public Fragment getFragment() {
        return new MainActivity();
    }

    public int getImageId() {
        return this.imageId;
    }

    public int getUID() {
        return this.UID;
    }

    public int getOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 65, 65, 65);
    }

    public int getOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getBasicOperatorsBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 35, 35, 35);
    }

    public int getBasicOperatorsFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getNumPadBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 35, 35, 35);
    }

    public int getNumPadFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 200, 200, 200);
    }

    public int getDisplayBg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 182, 185, 114);
    }

    public int getDisplayFg() {
        return Color.argb(MotionEventCompat.ACTION_MASK, 35, 35, 35);
    }

    public int getLayout() {
        return 0;
    }
}
