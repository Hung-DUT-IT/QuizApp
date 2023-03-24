package com.example.quizapp.Model;

import java.util.ArrayList;

public class User {
    public int id;
    public ArrayList<Integer> friendIDs;
    public String username;
    public String pass;
    public String name;
    public int prev_score;
    public int score;


    public User(int id, ArrayList<Integer> friendIDs, String username,
                String pass, String name, int prev_score, int score) {
        this.id = id;
        this.friendIDs = friendIDs;
        this.username = username;
        this.pass = pass;
        this.name = name;
        this.prev_score = prev_score;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(ArrayList<Integer> friendIDs) {
        this.friendIDs = friendIDs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrev_score() {
        return prev_score;
    }

    public void setPrev_score(int prev_score) {
        this.prev_score = prev_score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
