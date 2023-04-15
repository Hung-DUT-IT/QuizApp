package com.example.quizapp.Model.Entity;

import java.util.HashMap;

public class Room {
    private String roomCode;
    private String name;
    private String owner;
    private HashMap<String, Integer> IdPlayers;
    private boolean start;

    public Room() {
        this.roomCode = "######";
        this.name = "######";
        this.IdPlayers = new HashMap<>();
        this.start = false;
    }

    public Room(String roomCode, String name, String owner) {
        this.roomCode = roomCode;
        this.name = name;
        this.owner = owner;
        this.IdPlayers = new HashMap<>();
        this.IdPlayers.put(owner, 0);
        this.start = false;
    }

    public Room(String roomCode, String name, String owner, HashMap<String, Integer> idPlayers, boolean start) {
        this.roomCode = roomCode;
        this.name = name;
        this.owner = owner;
        this.IdPlayers = idPlayers;
        this.start = start;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, Integer> getIdPlayers() {
        return IdPlayers;
    }

    public void setIdPlayers(HashMap<String, Integer> idPlayers) {
        IdPlayers = idPlayers;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}