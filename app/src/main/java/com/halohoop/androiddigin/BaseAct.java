package com.halohoop.androiddigin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halohoop.androiddigin.categoris.Contents;
import com.halohoop.androiddigin.frags.ListDataFragment;
import com.halohoop.androiddigin.frags.MyDialogFragment;
import com.halohoop.androiddigin.frags.ShowFragment;
import com.halohoop.androiddigin.showacts.ColorMatrixActivity;
import com.halohoop.androiddigin.showacts.MenuUsageActivity;
import com.halohoop.androiddigin.showacts.RadialGradientActivity;
import com.halohoop.androiddigin.showacts.RevealActivity;
import com.halohoop.androiddigin.showacts.SweepGradientActivity;
import com.halohoop.androiddigin.utils.Utils;

/**
 * Created by Pooholah on 2017/5/17.
 */

public abstract class BaseAct extends AppCompatActivity implements ListDataFragment.OnListFragmentInteractionListener {

    protected final static String IS_BEENT_HROUGH_ONSAVEINSTANCESTATE
            = "is_beent_hrough_onSaveInstanceState";
    protected boolean mIsBeenThroughOnSaveInstanceState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsBeenThroughOnSaveInstanceState = savedInstanceState != null ? savedInstanceState.getBoolean(IS_BEENT_HROUGH_ONSAVEINSTANCESTATE) : false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_BEENT_HROUGH_ONSAVEINSTANCESTATE, mIsBeenThroughOnSaveInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    protected void startAct(Intent intent) {
        startActivity(intent);
    }

    final protected void showFragment(int resId) {
        ShowFragment showFragment = ShowFragment.newInstance(resId);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(android.R.id.content, showFragment)
                .commit();
    }

    final protected void showDialogFragment(MyDialogFragment.ModeData modeData,
                                            MyDialogFragment.OnShowEntityCreateListener onShowEntityCreateListener,
                                            MyDialogFragment.OnDialogClickListener onDialogClickListener) {
        MyDialogFragment myDialogFragment = MyDialogFragment.newInstance(modeData);
        myDialogFragment.setOnShowEntityCreateListener(onShowEntityCreateListener);
        myDialogFragment.setOnDialogClickListener(onDialogClickListener);
        myDialogFragment.show(getSupportFragmentManager(),"");
    }

    @Override
    public void onListFragmentInteraction(Contents.ItemBean itemBean, int clickIndex) {
        Intent intent;
        if (itemBean.getItemtype() == 1) {
            switch (itemBean.index) {
                case 11://DialogFragment怎么用,
                    if (clickIndex == 0) {
                        MyDialogFragment.ModeData modeData =
                                new MyDialogFragment.ModeData(MyDialogFragment.MODE.CUSTOM_DIALOG,
                                        false,
                                        "方式1","确认","取消");
                        showDialogFragment(modeData, null,
                                new MyDialogFragment.OnDialogClickListener() {
                                    @Override
                                    public void onNegClick(DialogInterface dialog, int which) {
                                        Utils.showToast(BaseAct.this, "onNegClick");
                                    }

                                    @Override
                                    public void onPosClick(DialogInterface dialog, int which) {
                                        Utils.showToast(BaseAct.this, "onPosClick");

                                    }
                                });
                    }else if (clickIndex == 1) {
                        MyDialogFragment.ModeData modeData =
                                new MyDialogFragment.ModeData(MyDialogFragment.MODE.CUSTOM_VIEW,
                                        R.layout.dialog_fragment,false);
                        showDialogFragment(modeData, new MyDialogFragment.OnShowEntityCreateListener() {
                            @Override
                            public void onViewInflateFinish(View viewInflated) {
                                viewInflated.findViewById(R.id.iv1).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        
                                    }
                                });
                                viewInflated.findViewById(R.id.iv1).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                Utils.showToast(BaseAct.this, "onViewInflateFinish");
                            }
                        }, null);
                    }
                    break;
            }
            return;
        }
        switch (itemBean.index) {
            case 0://放大镜
//                Intent intent = new Intent(this, MagnifierActivity.class);
//                startAct(intent);
                showFragment(R.layout.activity_magnifier);
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
//                intent = new Intent(this, ScratchActivity.class);
//                startAct(intent);
                showFragment(R.layout.activity_scratch);
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
                Utils.showToast(this, "我是单例吐司");
                break;
            case 9://ListFragment
                Utils.showToast(this, "本身这个列表页面就是ListFragment");
                break;
            case 10://FragmentStatePagerAdapter
                intent = new Intent(this, NavMainActivity.class);
                startAct(intent);
                finish();
                break;
        }
    }

}
