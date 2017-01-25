package com.citizen.calculator2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.citizen.calculator2017.calclib.calculatorFactory;
import com.citizen.calculator2017.Adapters.CustomAdapterFullsScreen;
import com.citizen.calculator2017.utils.PrintTapeClass;

public class ListViewFullScreen extends Fragment {
    public static CustomAdapterFullsScreen listAdapter;
    calculatorFactory factory;
    private ListView list;
    private Toast mToast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listfullscreen, container, false);
        this.factory = calculatorFactory.getCalcFactory();
        this.list = (ListView) v.findViewById(R.id.listfs);
        listAdapter = new CustomAdapterFullsScreen(getActivity(), this, PrintTapeClass.getListFromHistory(calculatorFactory.getCalcFactory().getHistory()), getResources());
        this.list.setAdapter(listAdapter);
        return v;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshList();
            showToast("Tap on an item to correct");
            return;
        }
        cancelToast();
    }

    private void showToast(String str) {
        if (this.mToast != null) {
            this.mToast.cancel();
        }
        this.mToast = Toast.makeText(getActivity(), str, 0);
        this.mToast.show();
    }

    private void cancelToast() {
        if (this.mToast != null) {
            this.mToast.cancel();
        }
    }

    public void onItemClick(int mPosition) {
    }

    public void onItemCommentClick(int mPosition) {
    }

    public void onResume() {
        listAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void refreshList() {
        PrintTapeClass.getListFromHistory(calculatorFactory.getCalcFactory().getHistory());
        listAdapter.notifyDataSetChanged();
    }
}
