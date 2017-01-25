package ThemeHandlers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.citizen.calculator2017.R;
import com.citizen.calculator2017.calclib.HofH.IHofHConstants;

public class ThemeNavigatorFragment extends Fragment implements IHofHConstants {
    int imageId;
    ImageView imgview;
    int selectionIndex;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theme_item, container, false);
        this.imgview = (ImageView) view.findViewById(R.id.themeImage);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.selectionIndex = bundle.getInt(IHofHConstants.SELECTION_INDEX);
        } else {
            this.selectionIndex = 0;
        }
        this.imageId = ThemeManager.getThemeManager().getThemeAt(this.selectionIndex).imageId;
        this.imgview.setImageDrawable(getActivity().getResources().getDrawable(this.imageId));
        return view;
    }
}
