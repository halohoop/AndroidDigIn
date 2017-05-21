package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.halohoop.androiddigin.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Pooholah on 2017/5/18.
 */

/**
 * 和布局文件nested_scroll_layout.xml是一套，
 * 往container中放入一个NestedScrollingChild的view，即可实现嵌套滑动
 * 21以及以后，View默认默认实现 NestedScrollingParent 和 NestedScrollingChild
 * 中的方法。
 */
public class NestedScrollLayout extends LinearLayout implements NestedScrollingParent {

    private ViewGroup container;
    private TextView tvHeader;
    private TextView tvMid;
    private int mTopViewHeight;
    private OverScroller mScroller;

    public NestedScrollLayout(@NonNull Context context) {
        this(context, null);
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvHeader = (TextView) findViewById(R.id.tv_header);
        tvMid = (TextView) findViewById(R.id.tv_mid);
        container = (ViewGroup) findViewById(R.id.container);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - tvMid.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(),
                tvHeader.getMeasuredHeight()
                        + tvMid.getMeasuredHeight() + container.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = tvHeader.getMeasuredHeight();
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > mTopViewHeight)
        {
            y = mTopViewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //只允许纵向嵌套滑动
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
//        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    {
        super.onNestedPreScroll(target, dx, dy, consumed);
        Log.e(TAG, "onNestedPreScroll");
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop)
        {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    public void fling(int velocityY)
    {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
    {
        super.onNestedPreFling(target,velocityX,velocityY);
        Log.e(TAG, "onNestedPreFling");
        //down - //up+
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }
}
