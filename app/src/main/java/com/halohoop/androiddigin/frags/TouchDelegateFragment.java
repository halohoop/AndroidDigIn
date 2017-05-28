package com.halohoop.androiddigin.frags;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.widgets.TouchDelegateViewGroup;

/**
 * Created by Pooholah on 2017/5/28.
 */

public class TouchDelegateFragment extends ShowFragment {

    private TouchDelegateViewGroup delegateContainer;
    private RadioButton rtn;

    public static TouchDelegateFragment newInstance(int resId) {
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID_KEY, resId);
        TouchDelegateFragment fragment = new TouchDelegateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        delegateContainer = (TouchDelegateViewGroup) view.findViewById(R.id.delegate_container);
        rtn = (RadioButton) view.findViewById(R.id.rtn);
        Rect rect = new Rect(500, 500, 800, 800);
        delegateContainer.setDelegateAreaColor(Color.RED, rect);
        delegateContainer.setTouchDelegate(new TouchDelegate(rect, rtn));
        return view;
    }
}
