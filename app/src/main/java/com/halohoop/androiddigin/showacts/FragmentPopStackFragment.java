package com.halohoop.androiddigin.showacts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halohoop.androiddigin.R;

/**
 * Created by Pooholah on 2017/6/15.
 */

public class FragmentPopStackFragment extends Fragment {

    private TextView tv;

    public static FragmentPopStackFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        FragmentPopStackFragment fragment = new FragmentPopStackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pop_stack, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.tv);
        String name = getArguments().getString("name");
        tv.setText(name);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "FragmentPopStackFragment: onDetach:"+getTag());
    }

    private static final String TAG = "FragmentPopStackFragmen";

//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }
}
