package com.examples.numberseriesgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterGames extends RecyclerView.Adapter<AdapterGames.GamesViewHolder> {
    private Context context;
    private List<Game> listGame;

    public AdapterGames(Context context, List<Game> listGame) {
        this.context = context;
        this.listGame = listGame;
    }

    @NonNull
    @Override

    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);

        return new GamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesViewHolder holder, int position) {
        Game game = listGame.get(position);
        holder.tv_fullName.setText(game.getFullName());
        holder.tv_Score.setText(String.valueOf(game.getScore()));
        holder.tv_Date.setText(String.valueOf(game.getGameDate()));
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }

    public class GamesViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Score, tv_fullName, tv_Date;

        public GamesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Score = itemView.findViewById(R.id.tv_score_item);
            tv_fullName = itemView.findViewById(R.id.tv_fullNameItem);
            tv_Date = itemView.findViewById(R.id.tv_Date);
        }
    }
}
