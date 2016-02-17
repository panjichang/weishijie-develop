package com.pan.simplepicture.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pan.simplepicture.utils.ContextUtils;
import com.pan.simplepicture.view.holder.BaseHolder;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    private List<T> mDatas;
    private int mResLayout;
    private Class<? extends BaseHolder<T>> mClazz;

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void addAll(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyItemRangeInserted(this.mDatas.size() - mDatas.size(), this.mDatas.size());
    }

    public void add(T data) {
        this.mDatas.add(data);
        notifyItemInserted(this.mDatas.size() - 1);
    }

    public void add(int index, T data) {
        this.mDatas.add(index, data);
        notifyItemInserted(index);
    }

    public T getItem(int position) {
        try {
            return this.mDatas.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public BaseRecyclerAdapter(List<T> mDatas, int mResLayout, Class<? extends BaseHolder<T>> mClazz) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mDatas = mDatas;
        this.mResLayout = mResLayout;
        this.mClazz = mClazz;
    }

    public HashMap<Integer, Object> tags = new HashMap<>();

    public BaseRecyclerAdapter setTag(int tag, Object mObject) {
        if (mObject != null) {
            tags.put(tag, mObject);
        }
        return this;
    }

    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                ContextUtils.inflate(parent.getContext(), mResLayout);
        if (tags != null && tags.size() > 0) {
            for (int tag : tags.keySet()) {
                view.setTag(tag, tags.get(tag));
            }
        }
        try {
            Constructor<? extends BaseHolder<T>> mClazzConstructor = mClazz.getConstructor(View.class);
            if (mClazzConstructor != null) {
                return mClazzConstructor.newInstance(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final BaseHolder<T> holder, int position) {
        holder.setData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
