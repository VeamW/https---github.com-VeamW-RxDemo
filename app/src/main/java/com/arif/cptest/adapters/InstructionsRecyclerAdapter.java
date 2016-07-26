package com.arif.cptest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arif.cptest.R;

/**
 * Created by arifnadeem on 11/5/15.
 */
public class InstructionsRecyclerAdapter extends RecyclerView.Adapter<InstructionsRecyclerAdapter.ViewHolder> {

    private String[] mInstructionItems;
    private Context mContext;

    public InstructionsRecyclerAdapter(Context cxt, String[] items) {
        mInstructionItems = items;
        mContext = cxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.instruction_layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mInstructionItems[position] != null)
            holder.mTvInstructionItem.setText(mInstructionItems[position]);
    }

    @Override
    public int getItemCount() {
        return mInstructionItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvInstructionItem;

        public ViewHolder(View base) {
            super(base);
            mTvInstructionItem = (TextView) base.findViewById(R.id.tvInstructionItem);
        }
    }

}
