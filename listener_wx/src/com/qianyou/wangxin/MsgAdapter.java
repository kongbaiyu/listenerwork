package com.qianyou.wangxin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import com.qianyou.listener5.R;

public class MsgAdapter extends BaseAdapter {
    private LinkedList<msgMessage> mData;
    private Context mContext;

    MsgAdapter(LinkedList<msgMessage> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }


	@Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.message, viewGroup, false);
        }
        TextView txt =  view.findViewById(R.id.msgadpter);
        txt.setText(mData.get(i).getMsg());
        return view;
    }
    //添加一个元素
    void add(msgMessage data){
        if(mData == null){
            mData = new LinkedList<msgMessage>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    void add(int position, msgMessage data){
        if(mData == null){
            mData = new LinkedList<msgMessage>();
        }
        mData.add(position,data);
        notifyDataSetChanged();
    }

    //删除一个元素
    void remove(msgMessage data){
        if(mData != null){
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    void remove(int position){
        if(mData != null){
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    void clear(){
        if(mData != null){
            mData.clear();
        }
        notifyDataSetChanged();
    }

}
