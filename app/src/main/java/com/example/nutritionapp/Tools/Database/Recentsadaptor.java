package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nutritionapp.Modals.Recentmeals;
import com.example.nutritionapp.R;

import java.util.List;

public class Recentsadaptor extends BaseAdapter {
    List<Recentmeals> recentlist;
    LayoutInflater inflater;
    Context context;
    ListView listView;
    TextView name,amount,calorie;

    public Recentsadaptor(List<Recentmeals> recent, Context context,TextView name,TextView amount,TextView calorie,ListView listView){
        this.recentlist=recent;
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.listView=listView;
        this.name=name;
        this.amount=amount;
        this.calorie=calorie;



    }


    @Override
    public int getCount() {
        return recentlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view=inflater.inflate(R.layout.recent_list_view_item,null);
        TextView recentlistname=view.findViewById(R.id.recent_list_view_name);
        TextView recentlistamount=view.findViewById(R.id.recent_list_view_amount);
        TextView recentlistcalorie=view.findViewById(R.id.recent_list_view_calorie);

        recentlistname.setText(recentlist.get(i).getRecent_food_name());
        recentlistamount.setText(recentlist.get(i).getFood_amount()+"");
        recentlistcalorie.setText(recentlist.get(i).getfood_calorie()+"");




        return view;
    }
}
