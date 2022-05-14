package com.examples.numberseriesgame;

import com.examples.numberseriesgame.Utils.Question;
import com.examples.numberseriesgame.Utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private int score;
    private String fullName;
    private String gameDate;
    private List<Question> listQuestion;

    public Game() {
    }

    public Game(int score, String fullName, String gameDate) {
        this.score = score;
        this.fullName = fullName;
        this.gameDate = gameDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public List<Question> getListQuestion() {
        listQuestion = new ArrayList<>();
        listQuestion.add(Util.generateQuestion());
        listQuestion.add(Util.generateQuestion());
        listQuestion.add(Util.generateQuestion());
        listQuestion.add(Util.generateQuestion());
        listQuestion.add(Util.generateQuestion());
        return listQuestion;
    }
}
