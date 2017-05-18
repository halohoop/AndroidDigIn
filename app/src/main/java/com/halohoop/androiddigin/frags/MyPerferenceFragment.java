package com.halohoop.androiddigin.frags;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pooholah on 2017/5/18.
 */

/**
 * 代表一组设置
 */
public class MyPerferenceFragment extends PreferenceFragment {

    private final static String XML_RESIDS_COUNT = "xml_resids_count";
    private int mXmlResIdsCount = 0;
    private final static String XML_RESIDS = "xml_resids";
    private int[] mXmlResIds;

    public static MyPerferenceFragment newInstance(int... xmlResIds) {

        Bundle args = new Bundle();
        args.putInt(XML_RESIDS_COUNT, xmlResIds != null ? xmlResIds.length : 0);
        args.putIntArray(XML_RESIDS, xmlResIds);
        MyPerferenceFragment fragment = new MyPerferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mXmlResIdsCount = arguments.getInt(XML_RESIDS_COUNT);
            mXmlResIds = arguments.getIntArray(XML_RESIDS);
            if (mXmlResIdsCount > 0) {
                for (int i = 0; i < mXmlResIdsCount; i++) {
                    addPreferencesFromResource(mXmlResIds[i]);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);
        return view;
    }
}
