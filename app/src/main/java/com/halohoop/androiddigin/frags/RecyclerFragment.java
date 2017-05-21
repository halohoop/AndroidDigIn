package com.halohoop.androiddigin.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.halohoop.androiddigin.R;

/**
 * Created by Pooholah on 2017/5/21.
 */

public abstract class RecyclerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView mStandardEmptyView;
    private FrameLayout mRecyclerContainer;
    private CharSequence mEmptyText;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public RecyclerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getContext();

        FrameLayout root = new FrameLayout(context);

        // ------------------------------------------------------------------

        mRecyclerContainer = new FrameLayout(context);
        mRecyclerContainer.setId(R.id.recycler_container_id);

        mStandardEmptyView = new TextView(context);
        mStandardEmptyView.setId(R.id.recycler_empty_id);
        mStandardEmptyView.setGravity(Gravity.CENTER);
        mStandardEmptyView.setVisibility(View.GONE);
        mRecyclerContainer.addView(mStandardEmptyView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setId(R.id.recycler);
        mRecyclerContainer.addView(mRecyclerView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        root.addView(mRecyclerContainer, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // ------------------------------------------------------------------

        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return root;
    }    /**
     * Attach to list view once the view hierarchy has been created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureRecycler();
    }

    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView() {
        mStandardEmptyView = null;
        mRecyclerView = null;
        mRecyclerContainer = null;
        super.onDestroyView();
    }

    private void ensureRecycler() {
        if (mRecyclerView != null) {
            mRecyclerView.setNestedScrollingEnabled(true);
            return;
        }
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (root instanceof ListView) {
            mRecyclerView = (RecyclerView) root;
        } else {
            if (mStandardEmptyView == null) {
                mStandardEmptyView = (TextView) root.findViewById(R.id.recycler_empty_id);
            }
            View rawRecyclerView = root.findViewById(R.id.recycler);
            if (!(rawRecyclerView instanceof ListView)) {
                if (rawRecyclerView == null) {
                    throw new RuntimeException(
                            "Your content must have a ListView whose id attribute is " +
                                    "'R.id.recycler'");
                }
                throw new RuntimeException(
                        "Content has view with id attribute 'R.id.recycler' "
                                + "that is not a RecyclerView class");
            }
            mRecyclerView = (RecyclerView) rawRecyclerView;
            mRecyclerView.setNestedScrollingEnabled(true);
            mLayoutManager = createLayoutManager();
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = createAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * 如果在子类中返回null，那么可以调用setRecyclerAdapter方法设置adapter
     *
     * @return
     */
    protected abstract RecyclerView.Adapter createAdapter();

    /**
     * 如果在子类中返回null，那么可以调用setLayoutManager方法设置adapter
     *
     * @return
     */
    protected abstract RecyclerView.LayoutManager createLayoutManager();

    /**
     * Provide the layoutManager for the recycler view.
     */
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
//        boolean hadLayoutManager = mLayoutManager != null;
        mLayoutManager = layout;
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layout);
        }
    }

    /**
     * Provide the cursor for the recycler view.
     */
    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {
//        boolean hadAdapter = mAdapter != null;
        mAdapter = adapter;
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public RecyclerView getRecyclerView() {
        ensureRecycler();
        return mRecyclerView;
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void setEmptyText(CharSequence text) {
        ensureRecycler();
        if (mStandardEmptyView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        mStandardEmptyView.setText(text);
        mEmptyText = text;
    }
}
