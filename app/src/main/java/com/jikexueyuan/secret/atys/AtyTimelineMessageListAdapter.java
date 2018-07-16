package com.jikexueyuan.secret.atys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jikexueyuan.secret.net.Message;

import java.util.ArrayList;
import java.util.List;

import secret.jikexueyuan.com.secret.R;

public class AtyTimelineMessageListAdapter extends BaseAdapter {

    public AtyTimelineMessageListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell,null);
            convertView.setTag(new listCell((TextView) convertView.findViewById(R.layout.aty_timeline_list_cell)));
        }
        listCell lc = (listCell) convertView.getTag();

        Message msg = getItem(position);

        lc.getTvCelllabel().setText(msg.getMsg());
        TextView tvCelllabel = (TextView) convertView.findViewById(R.id.tvCelllabel);
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<Message> data){
        data.addAll(data);

        //更新
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    private List<Message> data = new ArrayList<Message>();
    private Context context = null;

    private static class listCell{
        private TextView tvCelllabel;

        public listCell(TextView tvCelllabel){
            this.tvCelllabel =tvCelllabel;
        }

        public TextView getTvCelllabel() {
            return tvCelllabel;
        }
    }
}
