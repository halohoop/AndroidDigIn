package com.halohoop.androiddigin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.halohoop.androiddigin.categoris.Contents;
import com.halohoop.androiddigin.frags.ListDataFragment;

/**
 * Created by Pooholah on 2017/5/17.
 */

public abstract class BaseAct extends AppCompatActivity implements ListDataFragment.OnListFragmentInteractionListener {

    protected void startAct(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Contents.ItemBean itemBean) {
        switch (itemBean.index) {
            case 0://放大镜
                Intent intent = new Intent(this, MagnifierActivity.class);
                startAct(intent);
                break;
            case 1://ColorMatrix
                intent = new Intent(this, ColorMatrixActivity.class);
                startAct(intent);
                break;
            case 2://Reveal效果
                intent = new Intent(this, RevealActivity.class);
                startAct(intent);
                break;
            case 3://RadialGradient水波纹
                intent = new Intent(this, RadialGradientActivity.class);
                startAct(intent);
                break;
            case 4://SweepGradient制作Radar雷达效果效果
                intent = new Intent(this, SweepGradientActivity.class);
                startAct(intent);
                break;
            case 5://刮刮纸Xfermode
                intent = new Intent(this, ScratchActivity.class);
                startAct(intent);
                break;
            case 6://menu怎么用
                intent = new Intent(this, MenuUsageActivity.class);
                startAct(intent);
                break;
            case 7://FloatingActionButton和Snackbar怎么用
                intent = new Intent(this, MenuUsageActivity.class);
                startAct(intent);
                break;
            case 8://"单例吐司Toast，不需要等待上一个消失"
                intent = new Intent(this, MenuUsageActivity.class);
                startAct(intent);
                break;
        }
    }
}
