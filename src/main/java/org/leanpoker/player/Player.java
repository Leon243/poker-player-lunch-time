package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Player {

  static final String VERSION = "2";

  public static int betRequest(JsonElement request) {
    if (Math.random() * 4 > 3) {
      return 1;
    }
    return 0;
  }

  public static void showdown(JsonElement game) {}
}
