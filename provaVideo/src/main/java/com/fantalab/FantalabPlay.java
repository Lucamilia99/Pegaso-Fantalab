package com.fantalab;

import com.fantalab.Services.PlayerService;
import com.fantalab.models.Player;
import com.fantalab.models.PlayerInfo;

import java.util.List;
import java.util.Map;

public class FantalabPlay {
    private static final String AUTH_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkbkJjblVDSE5adU9OQkRLMHprNVhpQnA3cXdrZl8tT3lXMTNMVTdoel9ZIn0.eyJleHAiOjE5MzAyMjM5NzksImlhdCI6MTc1NzQyMzk3OSwianRpIjoiYzllNWFkMDQtZTM0Zi00MDg5LWIzMGYtOGIwNDdiM2RkYjY4IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5hdXRoLmZhbnRhbGFiLml0L3JlYWxtcy9mYW50YWxhYiIsImF1ZCI6ImZhbnRhbGFiLXdlYnNpdGUiLCJzdWIiOiIxZTc2NDA4Ny1hNzE1LTQ2NjAtYjAyYi1kZTMwYmEwNTZjY2YiLCJ0eXAiOiJJRCIsImF6cCI6ImZhbnRhbGFiLXdlYnNpdGUiLCJzaWQiOiI1MGQ2NGY2MC03NTYxLTRiNDQtOTVmOC05M2Q5MTJmZTBlYWUiLCJhdF9oYXNoIjoia1FRQ0taUG0xeFFtRUhmRGlmVWpsdyIsImFjciI6IjEiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6Im1pbGlrODRAb3V0bG9vay5jb20iLCJlbWFpbCI6Im1pbGlrODRAb3V0bG9vay5jb20ifQ.kg0OLNUrv_ZsVn0EZFP_aeLYkQbEzAeEFvELAyAm_s7aNEHfZO0ws2rDn5OBQkaAPUuA1y0eDOMotIsxCH3TQUPGHEzCp11cj4AObq3eQQsWLIw44BOQTqmfk6LrT1SMpP-xr-tckbFcnngI0THCsljs1ou9O8dyfGtlkpgUZduopRDiUcASy5opZ8wTBYd56A2_xpHScQd4qL40XZPtJrlbypnY0RRiXn95RS77Hg9wIFgZHmdRADNsoJMW6wDQweyXqVUhkF-FG2in1mU0rrbgb-15a1ruj35KiQYv0n6RV7r7PTkizruf6Oe2YLUIL-8HgdWc-1KVfvKOYpWgQA";

    public static void main(String[] args) {
        try {
            PlayerService playerService = new PlayerService(AUTH_TOKEN);

            System.out.println("Recupero dati giocatori...");
            List<Player> players = playerService.getPlayersWithInfo();
            Map<String, PlayerInfo> playerInfoMap = playerService.getPlayerInfoMap();

            System.out.println("Giocatori trovati: " + players.size());
            System.out.println("Dati anagrafici disponibili: " + playerInfoMap.size());

            playerService.printPlayersWithNames(players, playerInfoMap);


        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }
}