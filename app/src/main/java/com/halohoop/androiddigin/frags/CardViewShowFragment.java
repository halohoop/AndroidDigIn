package com.halohoop.androiddigin.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.SeekBar;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.utils.Utils;

/**
 * Created by Pooholah on 2017/5/22.
 */

public class CardViewShowFragment extends ShowFragment {

    private CardView mCardView1;
    private SeekBar mRadiusSeekBar;
    private SeekBar mElevationSeekBar;
    private CardView mCardView2;
    private CardView mCardView3;
    private CardView mCardView4;
    private CardView mCardView5;

    public static CardViewShowFragment newInstance(int resId) {
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID_KEY, resId);
        CardViewShowFragment fragment = new CardViewShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCardView1 = (CardView) view.findViewById(R.id.cardview1);
        mRadiusSeekBar = (SeekBar) view.findViewById(R.id.cardview_radius_seekbar);
        mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Utils.log(String.format("SeekBar Radius progress : %d", progress));
                mCardView1.setRadius(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }
        });

        mElevationSeekBar = (SeekBar) view.findViewById(R.id.cardview_elevation_seekbar);
        mElevationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Utils.log(String.format("SeekBar Elevation progress : %d", progress));
//                mCardView.setElevation(progress);//api require 21
                mCardView1.setCardElevation(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }
        });
        mCardView2 = (CardView) view.findViewById(R.id.cardview2);
        mCardView3 = (CardView) view.findViewById(R.id.cardview3);
        mCardView4 = (CardView) view.findViewById(R.id.cardview4);
        mCardView5 = (CardView) view.findViewById(R.id.cardview5);
    }
}
