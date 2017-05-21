package com.halohoop.androiddigin.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halohoop.androiddigin.materialdesign.datas.Cheeses;

/**
 * Created by Pooholah on 2017/5/21.
 */

public class RecyclerDataFragment extends RecyclerFragment {
    // TODO: Customize parameter argument names
    private static final String ITEM_DATAS = "item_datas";
    // TODO: Customize parameters
    private OnRecyclerFragmentInteractionListener mListener;

    public RecyclerDataFragment() {
    }

    public static RecyclerDataFragment newInstance() {

        Bundle args = new Bundle();

        RecyclerDataFragment fragment = new RecyclerDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecyclerFragmentInteractionListener) {
            mListener = (OnRecyclerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecyclerFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setLayoutManager(createLayoutManager());
        setRecyclerAdapter(createAdapter());
        return view;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new Adapter();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    public interface OnRecyclerFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int clickIndex);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(Cheeses.NAMES[position]);
            final ViewHolder holderTmp = holder;
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null) {
                        mListener.onListFragmentInteraction(holderTmp.getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return Cheeses.NAMES.length;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
