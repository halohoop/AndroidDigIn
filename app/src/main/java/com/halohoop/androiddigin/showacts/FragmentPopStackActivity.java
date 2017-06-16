package com.halohoop.androiddigin.showacts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.halohoop.androiddigin.R;

import java.util.List;

public class FragmentPopStackActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_pop_stack);
        findViewById(R.id.btn_pop).setOnClickListener(this);
        findViewById(R.id.btn_push_lt).setOnClickListener(this);
        findViewById(R.id.btn_push_rt).setOnClickListener(this);
        findViewById(R.id.btn_push_lb).setOnClickListener(this);
        findViewById(R.id.btn_push_rb).setOnClickListener(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_push_lt:
                FragmentPopStackFragment fragment = FragmentPopStackFragment.newInstance("左上");
                int container = R.id.container_lt;
                addFragment(fragment, container,"lt","lt");
                break;
            case R.id.btn_push_rt:
                fragment = FragmentPopStackFragment.newInstance("右上");
                container = R.id.container_rt;
                addFragment(fragment, container,"rt","rt");
                break;
            case R.id.btn_push_lb:
                fragment = FragmentPopStackFragment.newInstance("左下");
                container = R.id.container_lb;
                addFragment(fragment, container,"lb","lb");
                break;
            case R.id.btn_push_rb:
                fragment = FragmentPopStackFragment.newInstance("右下");
                container = R.id.container_rb;
                addFragment(fragment, container,"rb","rb");
                break;
            case R.id.btn_pop:
                //自己以上所有，不包括自己
//                getSupportFragmentManager().popBackStack("rt", 0);
                //自己以上所有，包括自己
//                getSupportFragmentManager().popBackStack("rt", 1);
                //最上层的一个
//                getSupportFragmentManager().popBackStack(null, 0);
                //最上层的一个
//                getSupportFragmentManager().popBackStack();
                //全部pop
                getSupportFragmentManager().popBackStack(null, 1);
        }
    }

    private static final String TAG = "FragmentPopStackActivit";

    private void addFragment(FragmentPopStackFragment fragment, int container, String tag, String name) {
        getSupportFragmentManager().beginTransaction()
                .add(container,fragment,tag)
                .addToBackStack(name)
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        Log.i(TAG, "FragmentPopStackFragment: onBackStackChanged: ");
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getSupportFragmentManager().getFragments();
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.i(TAG, "FragmentPopStackFragment: backStackEntryCount:"+backStackEntryCount);
        Log.i(TAG, "FragmentPopStackFragment: fragments.size():"+(fragments!=null?fragments.size():0));
    }
}
