package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

  static final String VERSION = "4";

  public static int betRequest(JsonElement request) {
    JsonObject object = request.getAsJsonObject();
    JsonArray players = object.get("players").getAsJsonArray();
    int maxBet = 0;
    for (JsonElement p : players) {
      int bet = p.getAsJsonObject().get("bet").getAsInt();
      if (bet > maxBet) {
        maxBet = bet;
      }
    }
    return maxBet;
  }

  public static void showdown(JsonElement game) {}
}
