package com.halohoop.androiddigin.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.widgets.VpTransformer;

/**
 * Created by Pooholah on 2017/5/17.
 */

public class VpTransformerFragment extends ShowFragment {

    public VpTransformerFragment() {
    }

    public static VpTransformerFragment newInstance() {
        Bundle args = new Bundle();
        VpTransformerFragment fragment = new VpTransformerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View inflate = inflater.inflate(R.layout.fragment_vp_transformer, null);
        ViewPager vp = (ViewPager) inflate.findViewById(R.id.vp);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getActivity().getSupportFragmentManager());
        vp.setPageTransformer(true, new VpTransformer());
        vp.setAdapter(adapter);

        return inflate;
    }

    private int[] layouts = {
            R.layout.welcome1,
            R.layout.welcome2,
            R.layout.welcome3
    };
    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ShowFragment.newInstance(layouts[position]);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
