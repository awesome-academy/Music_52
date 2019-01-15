package com.vn.kienphung.music_52.ui.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected List<T> mData;

    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;
    protected int mLastItemClickPosition = RecyclerView.NO_POSITION;

    public BaseAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
    }

    public void addData(List<T> data) {
        if (mData == null) {
            mData = data;
        } else {
            mData.addAll(data);
        }
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void clear() {
        if (mData != null)
            mData.clear();
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public int getLastItemClickPosition() {
        return mLastItemClickPosition;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return mItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }
}
