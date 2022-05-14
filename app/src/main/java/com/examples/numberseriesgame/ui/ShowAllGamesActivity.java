package com.examples.numberseriesgame.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.examples.numberseriesgame.AdapterGames;
import com.examples.numberseriesgame.Game;
import com.examples.numberseriesgame.R;
import com.examples.numberseriesgame.data.MyDatabase;

import java.util.ArrayList;

public class ShowAllGamesActivity extends AppCompatActivity {
    private RecyclerView recAllGames;
    private AdapterGames adapterGames;
    private ArrayList<Game> listGames;
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_games);
        setTitle(getResources().getString(R.string.str_showAllGames));
        initViews();
        database=MyDatabase.getINSTANCE(this);
        listGames=database.getAllGames();
        adapterGames=new AdapterGames(this,listGames);
        recAllGames.setAdapter(adapterGames);
        recAllGames.setLayoutManager(new LinearLayoutManager(this));
        recAllGames.setHasFixedSize(true);

    }

    private void initViews() {
        recAllGames = findViewById(R.id.recAllGames);
    }


}