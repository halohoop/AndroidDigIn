package com.halohoop.androiddigin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halohoop.androiddigin.categoris.Contents;
import com.halohoop.androiddigin.frags.ListDataFragment;
import com.halohoop.androiddigin.frags.MyDialogFragment;
import com.halohoop.androiddigin.frags.MyPerferenceFragment;
import com.halohoop.androiddigin.frags.ShowFragment;
import com.halohoop.androiddigin.frags.TouchDelegateFragment;
import com.halohoop.androiddigin.materialdesign.MDMainActivity;
import com.halohoop.androiddigin.showacts.ColorMatrixActivity;
import com.halohoop.androiddigin.showacts.MenuUsageActivity;
import com.halohoop.androiddigin.showacts.RadialGradientActivity;
import com.halohoop.androiddigin.showacts.RevealActivity;
import com.halohoop.androiddigin.showacts.ShowActivity;
import com.halohoop.androiddigin.showacts.SweepGradientActivity;
import com.halohoop.androiddigin.utils.Utils;

/**
 * Created by Pooholah on 2017/5/17.
 * 这个类需要热更新替换
 */

public abstract class BaseAct extends AppCompatActivity
        implements ListDataFragment.OnListFragmentInteractionListener {

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

    final protected void startAct(Intent intent) {
        startActivity(intent);
    }

    final protected void showActivity(int resId, boolean showActionBar) {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra(ShowActivity.RES_ID, resId);
        startActivity(intent);
    }

    /**
     * clz 必须为 ShowActivity的子类
     * @param resId
     * @param clz
     * @param showActionBar
     */
    final protected void showActivity(int resId, Class clz, boolean showActionBar) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(ShowActivity.RES_ID, resId);
        startActivity(intent);
    }

    final protected void showActivity(Class clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    final protected void showFragment(int resId) {
        ShowFragment showFragment = ShowFragment.newInstance(resId);
        showFragment(showFragment, false);
    }

    final protected void showFragment(int resId, boolean replaceOrNot) {
        ShowFragment showFragment = ShowFragment.newInstance(resId);
        showFragment(showFragment, replaceOrNot);
    }

    final protected void showFragment(ShowFragment showFragment, boolean replaceOrNot) {
        if (replaceOrNot) {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(android.R.id.content, showFragment)
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(android.R.id.content, showFragment)
                    .commit();
        }
    }
    final protected void showFragment(ShowFragment showFragment) {
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
        myDialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onListFragmentInteraction(Contents.ItemBean itemBean, int clickIndex) {
        if (itemBean.getItemtype() == 1) {
            way1(itemBean, clickIndex);
            return;
        }
        way2(itemBean);
    }

    private void way2(Contents.ItemBean itemBean) {
        Intent intent;
        switch (itemBean.getIndex()) {
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
            case 13://ViewDragHelper的使用
                showFragment(R.layout.fragment_drag);
                Utils.showToast(this, "ViewDragHelper的使用");
                break;
            case 14://Material Design
                showActivity(MDMainActivity.class);
                break;
            case 15://TouchDelegate怎么使用
                showFragment(TouchDelegateFragment.newInstance(R.layout.touch_delegate_layout),true);
                Utils.showToast(this, "TouchDelegate怎么使用");
                break;
            case 16://波浪，水涨起来
                showFragment(R.layout.layout_wave);
                Utils.showToast(this, "波浪，水涨起来");
                break;
        }
    }

    private void way1(Contents.ItemBean itemBean, int clickIndex) {
        switch (itemBean.getIndex()) {
            case 11://DialogFragment怎么用,
                if (clickIndex == 0) {
                    MyDialogFragment.ModeData modeData =
                            new MyDialogFragment.ModeData(MyDialogFragment.MODE.CUSTOM_DIALOG,
                                    false,
                                    "方式1", "确认", "取消");
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
                } else if (clickIndex == 1) {
                    MyDialogFragment.ModeData modeData =
                            new MyDialogFragment.ModeData(MyDialogFragment.MODE.CUSTOM_VIEW,
                                    R.layout.dialog_fragment, false);
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
            case 12://PreferenceFragment怎么用,
                if (clickIndex == 0) {
                    MyPerferenceFragment myPerferenceFragment = MyPerferenceFragment.newInstance(
                            R.xml.demo1,
                            R.xml.demo2);
                    //这里就不能使用v4包的了，因为PerferenceFragment不属于v4
                    getFragmentManager().beginTransaction()
                            .addToBackStack("")
                            .replace(android.R.id.content, myPerferenceFragment)
                            .commit();
                } else if (clickIndex == 1) {
                    SharedPreferences defaultSp = PreferenceManager.getDefaultSharedPreferences(this);
                    String string;
                    if (System.currentTimeMillis() % 2 == 0) {
                        string = defaultSp.getString(getResources().getString(R.string.sp_usename_key),
                                "nothing1 yet");
                    } else {
                        string = defaultSp.getString(getResources().getString(R.string.sp_password_key),
                                "nothing2 yet");
                    }
                    Utils.showToast(this, string);
                }
                break;
        }
    }
}
