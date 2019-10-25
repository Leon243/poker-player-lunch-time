package org.leanpoker.player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

  static final String VERSION = new Date().toString();

  public static int betRequest(JsonElement request) {
    JsonObject object = request.getAsJsonObject();
    int currentBuyIn = object.get("current_buy_in").getAsInt();
    int inAction = object.get("in_action").getAsInt();
    int round = object.get("round").getAsInt();
    int dealer = object.get("dealer").getAsInt();

    if (round == 0 && inAction == dealer) {
      return 0;
    }

    JsonObject player = object.get("players").getAsJsonArray().get(inAction).getAsJsonObject();
    int stack = player.get("stack").getAsInt();

    Map<String, Integer> rankCount = new HashMap<>();
    JsonArray comunityCards = object.get("community_cards").getAsJsonArray();
    for (JsonElement card : comunityCards) {
      String rank = card.getAsJsonObject().get("rank").getAsString();
      rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
    }

    //default
    int newBet = (int) Math.floor(stack / 10);

    JsonArray holeCards = player.get("hole_cards").getAsJsonArray();
    for (JsonElement card : holeCards) {
      String rank = card.getAsJsonObject().get("rank").getAsString();
      rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);

      // high card
      if ("A".equals(rank) || "K".equals(rank)) {
        newBet = currentBuyIn;
      }
    }

    //having a pair
    for (Entry<String, Integer> entry : rankCount.entrySet()) {
      if (entry.getValue() >= 2) {
        newBet = currentBuyIn;
      }
      if (entry.getValue() >= 3) {
        newBet = stack;
      }
    }

    return Math.min(currentBuyIn, newBet);
  }

  public static void showdown(JsonElement game) {}
}
