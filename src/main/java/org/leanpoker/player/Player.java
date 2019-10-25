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
    int dealer = object.get("dealer").getAsInt();

    if (round == 0 && inAction == dealer) {
      return 0;
    }

    JsonObject player = object.get("players").getAsJsonArray().get(inAction).getAsJsonObject();
    int stack = player.get("stack").getAsInt();

    int min = (int) Math.floor(stack / 5) + 1;
    int max = pot; //(int) Math.floor(stack / 2) + 1;

    if (pot <= max) {
      return Math.max(min, pot);
    }
    return 0;
  }

  public static void showdown(JsonElement game) {}
}
