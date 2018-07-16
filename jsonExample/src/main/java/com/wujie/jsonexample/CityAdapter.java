package com.wujie.jsonexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by wujie on 2018/7/16/016.
 */

public class CityAdapter extends BaseAdapter{

    private List<City> cities = Collections.emptyList();
    public CityAdapter() {

    }
    public void setDatas(List<City> datas) {
        this.cities = datas;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public City getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .city_listitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.votes = (TextView) convertView.findViewById(R.id.vote);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.name.setText(cities.get(position).getName());
        viewHolder.votes.setText(String.format("%d", cities.get(position).getVotes()));
        return convertView;
    }

   private class ViewHolder  {
        private TextView name;
        private TextView votes;
    }
}
