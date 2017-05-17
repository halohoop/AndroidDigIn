package com.halohoop.androiddigin.frags;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.utils.Utils;

import java.io.Serializable;

import static com.halohoop.androiddigin.frags.MyDialogFragment.MODE.CUSTOM_VIEW;

/**
 * Created by Pooholah on 2017/5/17.
 */

public class MyDialogFragment extends DialogFragment {

    /**
     * 传入自定义对话框
     * 或者
     * 传入自定义View
     * 两种模式
     */
    public enum MODE {
        CUSTOM_DIALOG,
        CUSTOM_VIEW
    }

    public static class ModeData implements Serializable {
        private MODE mode;
        private boolean cancelable;

        private int layoutResId;

        public ModeData(MODE mode, int layoutResId, boolean cancelable) {
            if (mode != CUSTOM_VIEW) {
                throw new RuntimeException("使用这3个参数的构造请传入CUSTOM_VIEW模式");
            }
            this.mode = mode;
            this.layoutResId = layoutResId;
            this.cancelable = cancelable;
        }

        public MODE getMode() {
            return mode;
        }

        public void setMode(MODE mode) {
            this.mode = mode;
        }

        public int getLayoutResId() {
            return layoutResId;
        }

        public void setLayoutResId(int layoutResId) {
            this.layoutResId = layoutResId;
        }

        public boolean isCancelable() {
            return cancelable;
        }

        public void setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
        }

        private String title;
        private String posText;
        private String negText;

        public ModeData(MODE mode, boolean cancelable, String title, String posText, String negText) {
            this.mode = mode;
            this.cancelable = cancelable;
            this.title = title;
            this.posText = posText;
            this.negText = negText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosText() {
            return posText;
        }

        public void setPosText(String posText) {
            this.posText = posText;
        }

        public String getNegText() {
            return negText;
        }

        public void setNegText(String negText) {
            this.negText = negText;
        }
    }

    private final static String MODE_DATA_KEY = "mode";
    private ModeData mModeData = null;

    public MyDialogFragment() {
    }

    public static MyDialogFragment newInstance(ModeData modeData) {
        Bundle args = new Bundle();
        args.putSerializable(MODE_DATA_KEY, modeData);
        MyDialogFragment fragment = new MyDialogFragment();
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            mModeData = (ModeData) arguments.getSerializable(MODE_DATA_KEY);
        }
        Utils.log("onCreate");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Utils.log("onCreateDialog");
        if (mModeData.getMode() == MODE.CUSTOM_DIALOG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(mModeData.getTitle())
                    .setIcon(R.drawable.ic_home_black_24dp);
            //设置能否取消是要设置DialogFragment的setCancelable方法才会生效
            setCancelable(mModeData.isCancelable());
            if (!TextUtils.isEmpty(mModeData.getNegText())) {
                builder.setNegativeButton(mModeData.getNegText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnDialogClickListener != null)
                            mOnDialogClickListener.onNegClick(dialog, which);
                    }
                });
            }
            if (!TextUtils.isEmpty(mModeData.getPosText())) {
                builder.setPositiveButton(mModeData.getPosText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnDialogClickListener != null)
                            mOnDialogClickListener.onPosClick(dialog, which);
                    }
                });
            }
            return builder.create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Utils.log("onCreateView");
        if (mModeData.getMode() == CUSTOM_VIEW) {
            setCancelable(mModeData.isCancelable());
            View inflate = inflater.inflate(mModeData.getLayoutResId(), null);
            inflate.findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            //设置点击事件的接口
            if (mOnShowEntityCreateListener != null)
                mOnShowEntityCreateListener.onViewInflateFinish(inflate);
            return inflate;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface OnShowEntityCreateListener {
        /**
         * 可以在这里拿到viewInflated，然后fvb，来设置自己的点击事件
         *
         * @param viewInflated
         */
        void onViewInflateFinish(View viewInflated);
    }

    private OnShowEntityCreateListener mOnShowEntityCreateListener;

    public void setOnShowEntityCreateListener(OnShowEntityCreateListener onShowEntityCreateListener) {
        this.mOnShowEntityCreateListener = onShowEntityCreateListener;
    }

    public interface OnDialogClickListener {
        void onNegClick(DialogInterface dialog, int which);

        void onPosClick(DialogInterface dialog, int which);
    }

    private OnDialogClickListener mOnDialogClickListener;

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.mOnDialogClickListener = onDialogClickListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
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
