package com.citizen.calculator2017.Adapters;

import EditItem.EditItemDialogUI;
import ThemeHandlers.ThemeManager;
import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.R;
import com.citizen.calculator2017.calclib.IappConstants;
import com.citizen.calculator2017.LauncherActivity;
import com.citizen.calculator2017.utils.PersistencyManager;
import java.util.ArrayList;
import com.citizen.calculator2017.utils.ListElement;

public class CustomAdapterFullsScreen extends BaseAdapter implements OnClickListener, IappConstants {
    private static LayoutInflater inflater;
    private Activity activity;
    private ArrayList<ListElement> data;
    EditItemDialogUI editDialog;
    private Fragment fragment;
    int f5i;
    ListElement listItemValues;
    public Resources res;

    private class OnItemClickListener implements OnClickListener {
        private Fragment fragment;
        private int mPosition;

        OnItemClickListener(int position, Fragment f) {
            this.mPosition = position;
            this.fragment = f;
        }

        public void onClick(View arg0) {
            if (this.mPosition < CustomAdapterFullsScreen.this.data.size()) {
                if (((ListElement) CustomAdapterFullsScreen.this.data.get(this.mPosition)).isAnswer) {
                    LauncherActivity.showToast("Answer node cannot be edited", CustomAdapterFullsScreen.this.activity);
                    return;
                }
                CustomAdapterFullsScreen.this.editDialog = new EditItemDialogUI();
                CustomAdapterFullsScreen.this.editDialog.showDialog(CustomAdapterFullsScreen.this.activity, ((ListElement) CustomAdapterFullsScreen.this.data.get(this.mPosition)).indexInHistory, this.fragment, 1);
            }
        }
    }

    public static class ViewHolder {
        public TextView dispStr;
        public TextView lineNum;
        public TextView operation;
        public LinearLayout rowLayoutHolder;
    }

    static {
        inflater = null;
    }

    public CustomAdapterFullsScreen(Activity a, Fragment f, ArrayList d, Resources resLocal) {
        this.listItemValues = null;
        this.f5i = 0;
        this.activity = a;
        this.data = d;
        this.res = resLocal;
        this.fragment = f;
        inflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        if (this.data.size() <= 0) {
            return 1;
        }
        return this.data.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.tabitem, null);
            holder = new ViewHolder();
            holder.rowLayoutHolder = (LinearLayout) vi.findViewById(R.id.rowLayoutHolder);
            holder.lineNum = (TextView) vi.findViewById(R.id.tvLineNumber);
            holder.operation = (TextView) vi.findViewById(R.id.tvPrevOperation);
            holder.dispStr = (TextView) vi.findViewById(R.id.tvDisplay);
            int bgcolor = ThemeManager.getThemeManager().getCurrentTheme().getDisplayBg();
            holder.lineNum.setBackgroundColor(bgcolor);
            holder.operation.setBackgroundColor(bgcolor);
            holder.dispStr.setBackgroundColor(bgcolor);
            holder.rowLayoutHolder.setBackgroundColor(bgcolor);
            int fgcolor = ThemeManager.getThemeManager().getCurrentTheme().getDisplayFg();
            holder.lineNum.setTextColor(fgcolor);
            holder.operation.setTextColor(fgcolor);
            holder.dispStr.setTextColor(fgcolor);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        if (this.data.size() <= 0) {
            holder.dispStr.setText(BuildConfig.FLAVOR);
        } else {
            this.listItemValues = null;
            this.listItemValues = (ListElement) this.data.get(position);
            holder.operation.setText(Html.fromHtml(this.listItemValues.getPrevOperation()));
            holder.dispStr.setText(Html.fromHtml(this.listItemValues.getDispStr()));
            holder.lineNum.setText(Html.fromHtml(this.listItemValues.getLineNum()));
            vi.setOnClickListener(new OnItemClickListener(position, this.fragment));
        }
        return vi;
    }

    private void trace(String str) {
        PersistencyManager.traceLog(str);
    }

    public void onClick(View v) {
    }
}
