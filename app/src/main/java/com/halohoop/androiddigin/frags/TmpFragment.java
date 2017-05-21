package com.halohoop.androiddigin.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pooholah on 2017/5/19.
 */

public class TmpFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private List<String> mDatas = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }


    public static TmpFragment newInstance(String title) {
        TmpFragment TmpFragment = new TmpFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        TmpFragment.setArguments(bundle);
        return TmpFragment;
    }
}
