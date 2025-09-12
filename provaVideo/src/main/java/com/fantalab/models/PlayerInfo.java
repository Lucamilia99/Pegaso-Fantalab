package com.fantalab.models;

import lombok.Data;
import lombok.Getter;
import org.json.JSONObject;

@Data
@Getter
public class PlayerInfo {
    private String playerId;
    private String name;
    private String team;
    private String role;
    private String season;
    private Integer quotazione;
    private Integer fvm;

    public PlayerInfo(JSONObject json) {
        this.playerId = json.optString("player_id");
        this.name = json.optString("name");
        this.team = json.optString("team_name_short");
        this.role = json.optString("role");
        this.season = json.optString("season");
        this.fvm = json.optInt("fvm");
        this.quotazione = json.optInt("quotazione");
    }
}