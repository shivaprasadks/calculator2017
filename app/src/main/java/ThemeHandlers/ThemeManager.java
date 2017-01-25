package ThemeHandlers;

import ThemeHandlers.AvailableThemes.MaterialCyanDark;
import ThemeHandlers.AvailableThemes.MaterialGreenDark;
import ThemeHandlers.AvailableThemes.MaterialRed;
import ThemeHandlers.AvailableThemes.MaterialTheme;
import ThemeHandlers.AvailableThemes.TaxPlusTheme;
import ThemeHandlers.AvailableThemes.ThemeNormalCiti;
import java.util.ArrayList;
import java.util.Iterator;

public class ThemeManager {
    static ArrayList<ThemeType> AvailableThemesList;
    static ThemeType currentTheme;
    static ThemeManager theObject;

    private ThemeManager() {
        initArrayList();
    }

    public static ThemeManager getThemeManager() {
        if (theObject == null) {
            theObject = new ThemeManager();
        }
        return theObject;
    }

    private void initArrayList() {
        AvailableThemesList = new ArrayList();
        AvailableThemesList.add(new ThemeNormalCiti());
        AvailableThemesList.add(new TaxPlusTheme());
        AvailableThemesList.add(new MaterialTheme());
        AvailableThemesList.add(new MaterialCyanDark());
        AvailableThemesList.add(new MaterialRed());
        AvailableThemesList.add(new MaterialGreenDark());
    }

    public ThemeType getCurrentTheme() {
        if (currentTheme == null) {
            currentTheme = new ThemeNormalCiti();
        }
        try {
            currentTheme.getFragment();
        } catch (Exception e) {
            currentTheme = new ThemeNormalCiti();
        }
        return currentTheme;
    }

    public void setCurrentTheme(ThemeType t) {
        if (t == null) {
            t = new ThemeNormalCiti();
        }
        try {
            currentTheme = getObject(t);
            t.getFragment();
        } catch (Exception e) {
            currentTheme = new ThemeNormalCiti();
        }
    }

    private ThemeType getObject(ThemeType t) {
        if (AvailableThemesList == null) {
            initArrayList();
        }
        Iterator<ThemeType> itr = AvailableThemesList.iterator();
        while (itr.hasNext()) {
            ThemeType t1 = (ThemeType) itr.next();
            if (t1.getUID() == t.getUID()) {
                return t1;
            }
        }
        return new ThemeNormalCiti();
    }

    public void setThemeFromIndex(int i) {
        try {
            currentTheme = (ThemeType) AvailableThemesList.get(i);
            currentTheme.getImageId();
        } catch (Exception e) {
            currentTheme = new ThemeNormalCiti();
        }
    }

    public int getNumberOFThemes() {
        return AvailableThemesList.size();
    }

    public ThemeType getThemeAt(int index) {
        if (index < 0 || index > AvailableThemesList.size()) {
            return currentTheme;
        }
        return (ThemeType) AvailableThemesList.get(index);
    }

    public int getOperatorsBg() {
        return currentTheme.getOperatorsBg();
    }

    public int getOperatorsFg() {
        return currentTheme.getOperatorsFg();
    }

    public int getNumPadBg() {
        return currentTheme.getNumPadBg();
    }

    public int getNumPadFg() {
        return currentTheme.getNumPadFg();
    }

    public int getDisplayBg() {
        return currentTheme.getDisplayBg();
    }

    public int getDisplayFg() {
        return currentTheme.getDisplayFg();
    }

    public int getLayout() {
        return currentTheme.getLayout();
    }
}
