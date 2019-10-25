package org.leanpoker.player;

import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

  static final String VERSION = new Date().toString();

  public static int betRequest(JsonElement request) {
    JsonObject object = request.getAsJsonObject();
    int pot = object.get("pot").getAsInt();
    int inAction = object.get("in_action").getAsInt();
    int round = object.get("round").getAsInt();

    if (round == 0 && Math.random() * 3 < 1) {
      return 0;
    }

    JsonObject player = object.get("players").getAsJsonArray().get(inAction).getAsJsonObject();
    int stack = player.get("stack").getAsInt();

    int min = (int) Math.floor(stack / 10) + 1;
    int max = (int) Math.floor(stack) + 1;

    if (pot < max) {
      return Math.max(min, pot);
    }
    return 0;
  }

  public static void showdown(JsonElement game) {}
}
