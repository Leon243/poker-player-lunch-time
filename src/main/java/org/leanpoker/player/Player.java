package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

  static final String VERSION = "3";

  public static int betRequest(JsonElement request) {
    JsonObject object = request.getAsJsonObject();
    JsonArray players = object.get("players").getAsJsonArray();
    boolean isPair = false;
    for (JsonElement p : players) {
      JsonElement holeCardsElement = p.getAsJsonObject().get("hole_cards");
      if (holeCardsElement != null) {
        JsonArray holeCards = holeCardsElement.getAsJsonArray();
        isPair =
            holeCards
                .get(0)
                .getAsJsonObject()
                .get("rank")
                .getAsString()
                .equals(holeCards.get(0).getAsJsonObject().get("rank").getAsString());
      }
    }
    if (isPair) {
      return 200;
    }
    return 0;
  }

  public static void showdown(JsonElement game) {}
}
