package com.fantalab.models;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class Player {
    private String playerId;
    private String strategyId;
    private String userId;
    private int fascia;
    private double price;
    private String comment;
    private List<String> mantraRoles;
    private List<String> notes;

    public Player(JSONObject json) {
        this.playerId = json.optString("player_id");
        this.strategyId = json.optString("strategy_id");
        this.userId = json.optString("user_id");
        this.fascia = json.optInt("fascia");
        this.price = json.optDouble("price");
        this.comment = json.optString("comment");
        this.mantraRoles = extractMantraRoles(json);
        this.notes = extractNotes(json);
    }
    // Metodo per estrarre le note
    private List<String> extractNotes(JSONObject json) {
        List<String> notesList = new ArrayList<>();
        try {
            if (json.has("notes") && !json.isNull("notes")) {
                JSONArray notesArray = json.getJSONArray("notes");
                for (int i = 0; i < notesArray.length(); i++) {
                    notesList.add(notesArray.getString(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Errore nell'estrazione notes per player: " + json.optString("player_id"));
        }
        return notesList;
    }

    // Metodo per estrarre i ruoli mantra (già esistente ma migliorato)
    private List<String> extractMantraRoles(JSONObject json) {
        List<String> roles = new ArrayList<>();
        try {
            if (json.has("player")) {
                JSONObject playerInfo = json.getJSONObject("player");
                if (playerInfo.has("mantra_roles")) {
                    JSONArray mantraRoles = playerInfo.getJSONArray("mantra_roles");
                    for (int i = 0; i < mantraRoles.length(); i++) {
                        roles.add(mantraRoles.getString(i));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore nell'estrazione mantra_roles per player: " + json.optString("player_id"));
        }
        return roles;
    }
}