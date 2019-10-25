package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

  static final String VERSION = "5";

  public static int betRequest(JsonElement request) {
    JsonObject object = request.getAsJsonObject();
    JsonArray players = object.get("players").getAsJsonArray();
    for (JsonElement p : players) {
      JsonElement holeCardsElement = p.getAsJsonObject().get("hole_cards");
      if (holeCardsElement != null) {
        int stack = p.getAsJsonObject().get("stack").getAsInt();
        return stack / 4;
      }
    }
    return 0;
  }

  public static void showdown(JsonElement game) {}
}
