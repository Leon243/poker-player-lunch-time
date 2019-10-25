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

    JsonObject player = object.get("players").getAsJsonArray().get(inAction).getAsJsonObject();
    int stack = player.get("stack").getAsInt();

    Map<String, Integer> rankCount = new HashMap<>();
    Map<String, Integer> suitCount = new HashMap<>();
    JsonArray comunityCards = object.get("community_cards").getAsJsonArray();
    for (JsonElement card : comunityCards) {
      String rank = card.getAsJsonObject().get("rank").getAsString();
      rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);

      String suit = card.getAsJsonObject().get("suit").getAsString();
      suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);
    }

    //default
    int newBet = stack / 10;

    JsonArray holeCards = player.get("hole_cards").getAsJsonArray();
    Map<String, Integer> holeRankCount = new HashMap<>();
    for (JsonElement card : holeCards) {
      String rank = card.getAsJsonObject().get("rank").getAsString();
      rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
      holeRankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);

      String suit = card.getAsJsonObject().get("suit").getAsString();
      suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);
    }

    // high cards
    int highCardCound =
        holeRankCount.getOrDefault("A", 0)
            + holeRankCount.getOrDefault("K", 0)
            + holeRankCount.getOrDefault("Q", 0);
    if (highCardCound == 2) {
      newBet = Math.min(currentBuyIn, stack / 2);
    }

    //having a pair or more
    for (Entry<String, Integer> entry : rankCount.entrySet()) {
      if (entry.getValue() >= 2) {
        newBet = Math.min(currentBuyIn, stack / 2);
      }
      if (entry.getValue() >= 3) {
        newBet = stack;
      }
    }

    //straight
    String[] straight = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    int seqLen = 0;
    for (int i = 0; i < straight.length; i++) {
      if (rankCount.containsKey(straight[i])) {
        seqLen += 1;
        if (seqLen == 5) {
          newBet = stack;
        }
      } else {
        seqLen = 0;
      }
    }

    //full house
    boolean fullHouseTwo = false;
    boolean fullHouseThree = false;
    for (Entry<String, Integer> entry : rankCount.entrySet()) {
      if (entry.getValue() == 2) {
        fullHouseTwo = true;
      }
      if (entry.getValue() >= 3) {
        fullHouseThree = true;
      }
    }
    if (fullHouseTwo && fullHouseThree) {
      newBet = stack;
    }

    //flush
    for (Entry<String, Integer> entry : suitCount.entrySet()) {
      if (entry.getValue() >= 5) {
        newBet = currentBuyIn;
      }
    }

    //all-in random
    int playersCount = object.get("players").getAsJsonArray().size();
    if (playersCount == 2 && Math.random() * 10 < 1) {
      newBet = currentBuyIn;
    }

    return Math.min(currentBuyIn, newBet);
  }

  public static void showdown(JsonElement game) {}
}
