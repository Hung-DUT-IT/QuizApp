package com.example.quizapp.Model;

import java.util.ArrayList;

public class User {
    public String UID;
    public ArrayList<String> friendIDs;
    public String name;
    public String pass;
    public int prev_score;
    public int score;
    public String username;

    public User(String id, ArrayList<String> friendIDs, String username,
                String pass, String name, int prev_score, int score) {
        this.UID = id;
        this.friendIDs = friendIDs;
        this.username = username;
        this.pass = pass;
        this.name = name;
        this.prev_score = prev_score;
        this.score = score;
    }
    public User(String username,String pass){
        this.username = username;
        this.pass = pass;
        this.name = username;
        this.prev_score = 0;
        this.score = 0;

    }

    public User(String username,String pass, String UID){
        this.UID = UID;
        this.friendIDs = null;
        this.name = "name";
        this.pass = pass;
        this.prev_score = 0;
        this.score = 0;
        this.username = username;
    }

    public User(){

    }


    public String getId() {
        return UID;
    }

    public void setId(String id) {
        this.UID = id;
    }

    public ArrayList<String> getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(ArrayList<String> friendIDs) {
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
