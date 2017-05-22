package com.halohoop.androiddigin.frags;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.utils.Utils;

/**
 * Created by Pooholah on 2017/5/21.
 */

public class OneClickFragment extends Fragment {

    private ClickListener mClickListener;
    private final static String WHICH = "WHICH";
    private final static String BG_COLOR = "BG_COLOR";
    private int mWhich = -1;
    private int mBgColor = -1;

    public static OneClickFragment newInstance(int which,int bgColor) {

        Bundle args = new Bundle();
        args.putInt(WHICH, which);
        args.putInt(BG_COLOR, bgColor);
        OneClickFragment fragment = new OneClickFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ClickListener) {
            mClickListener = (ClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mWhich = arguments.getInt(WHICH, -1);
            mBgColor = arguments.getInt(BG_COLOR, Color.CYAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView inflate = (TextView) inflater.inflate(R.layout.one_click_frag, container, false);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClickInOneClick(v, mWhich);
                }
            }
        });
        inflate.setBackgroundColor(mBgColor);
//        inflate.setTextColor(Utils.getComplimentColor(mBgColor));
        //the better
        inflate.setTextColor(Utils.getContrastColor(mBgColor));
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface ClickListener {
        void onClickInOneClick(View v, int which);
    }
}
