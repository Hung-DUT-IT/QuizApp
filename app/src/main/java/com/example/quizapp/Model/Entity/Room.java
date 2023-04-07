package com.example.quizapp.Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Room {

    String id;
    String name;
    List<String> IdPlayers;

    public Room(String id, String name, List<String> idPlayers) {
        this.id = id;
        this.name = name;
        IdPlayers = idPlayers;
    }
    public Room(String name) {
        this.id = null;
        this.name = name;
        IdPlayers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIdPlayers() {
        return IdPlayers;
    }

    public void setIdPlayers(List<String> idPlayers) {
        IdPlayers = idPlayers;
    }

}