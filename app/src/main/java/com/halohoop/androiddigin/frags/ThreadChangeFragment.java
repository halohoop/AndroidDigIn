package com.halohoop.androiddigin.frags;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.frags.callbacks.ProgressListener;

/**
 * Created by Pooholah on 2017/6/3.
 * 当你再一个独立的类中如何拿到主线程
 */

public class ThreadChangeFragment extends ShowFragment implements View.OnClickListener, ProgressListener {
    private ProgressBar progressBar;
    private TextView tvProCount;

    public static ThreadChangeFragment newInstance(int resId) {
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID_KEY, resId);
        ThreadChangeFragment fragment = new ThreadChangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.pro_count);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        tvProCount = (TextView) view.findViewById(R.id.tv_progress_count);
        view.findViewById(R.id.btn_do_sth).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        progressBar.setProgress(0);
        new MyThread(this).start();
    }

    @Override
    public void onUpdateProgress(int progress) {
        progressBar.setProgress(progress);
        tvProCount.setText("进度:" + progress);
    }

    public static class MyThread extends Thread {
        Handler handler = new Handler(Looper.getMainLooper());//线程切换
        ProgressListener progressListener = null;

        public MyThread(ProgressListener progressListener) {
            this.progressListener = progressListener;
        }

        @Override
        public void run() {
            int count = 100;
            int delta = 5;
            int progress = 0;
            while (true) {
                final int progressFinal = progress;
                SystemClock.sleep(250);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (progressListener != null) {
                            progressListener.onUpdateProgress(progressFinal);
                        }
                    }
                });
                progress += delta;
                if (progress > count) {
                    break;
                }
            }
        }
    }
}
