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
    JsonObject player = object.get("players").getAsJsonArray().get(inAction).getAsJsonObject();
    int stack = player.get("stack").getAsInt();
    return Math.max((int) Math.floor(stack / 4), pot);
  }

  public static void showdown(JsonElement game) {}
}
