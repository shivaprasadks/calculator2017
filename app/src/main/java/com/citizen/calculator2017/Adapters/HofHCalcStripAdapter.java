package com.citizen.calculator2017.Adapters;

import ThemeHandlers.ThemeManager;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citizen.calculator2017.BuildConfig;
import com.citizen.calculator2017.CalculationStripView;
import com.citizen.calculator2017.R;
import com.citizen.calculator2017.utils.PersistencyManager;
import java.util.ArrayList;
import com.citizen.calculator2017.utils.ListElement;

public class HofHCalcStripAdapter extends BaseAdapter implements OnClickListener {
    private static LayoutInflater inflater;
    private Activity activity;
    private ArrayList<ListElement> data;
    int f0i;
    ListElement listItemValues;
    public Resources res;

    private class OnItem2ClickListener implements OnClickListener {
        private int mPosition;

        OnItem2ClickListener(int position) {
            this.mPosition = position;
        }

        public void onClick(View v) {
            CalculationStripView sct = (CalculationStripView) HofHCalcStripAdapter.this.activity;
        }
    }

    private class OnItemClickListener implements OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            this.mPosition = position;
        }

        public void onClick(View arg0) {
            CalculationStripView sct = (CalculationStripView) HofHCalcStripAdapter.this.activity;
        }
    }

    public static class ViewHolder {
        public TextView comment;
        public TextView dispStr;
        public TextView lineNum;
        public TextView operation;
        public LinearLayout rowLayoutHolder;
    }

    static {
        inflater = null;
    }

    public HofHCalcStripAdapter(Activity a, ArrayList d, Resources resLocal) {
        this.listItemValues = null;
        this.f0i = 0;
        this.activity = a;
        this.data = d;
        this.res = resLocal;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            holder.lineNum = (TextView) vi.findViewById(R.id.tvLineNumber);
            holder.operation = (TextView) vi.findViewById(R.id.tvPrevOperation);
            holder.dispStr = (TextView) vi.findViewById(R.id.tvDisplay);
            holder.rowLayoutHolder = (LinearLayout) vi.findViewById(R.id.rowLayoutHolder);
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
        }
        return vi;
    }

    private void trace(String str) {
        PersistencyManager.traceLog(str);
    }

    public void onClick(View v) {
    }
}
