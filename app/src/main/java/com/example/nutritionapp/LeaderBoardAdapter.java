package com.example.nutritionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutritionapp.Modals.QuizRankList;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>{
    Context context;
    ArrayList<QuizRankList> rankList;

    public LeaderBoardAdapter(Context context, ArrayList<QuizRankList> rankList) {
        this.context = context;
        this.rankList = rankList;
    }


    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LeaderBoardViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.leaderboard_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {

        holder.nameText.setText(rankList.get(position).getName());
        holder.rankText.setText((position+1)+"");
        if(position==0){
            holder.rankText.setVisibility(View.GONE);
            holder.badge.setImageResource(R.drawable.gold_medal);
        }
        else if(position==1){
            holder.rankText.setVisibility(View.GONE);
            holder.badge.setImageResource(R.drawable.silver_medal);
        }
        else if(position==2){
            holder.rankText.setVisibility(View.GONE);
            holder.badge.setImageResource(R.drawable.bronze_medal);
        }
        else {
            holder.badge.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    public static class LeaderBoardViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        ImageView badge;
        TextView rankText;
        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText=itemView.findViewById(R.id.leaderboard_item_name);
            badge=itemView.findViewById(R.id.leaderboard_item_badge);
            rankText=itemView.findViewById(R.id.leaderboard_item_rank);

        }
    }
}
