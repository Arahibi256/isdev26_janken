package oit.is.z3384.kaizi.janken.model;

enum Hand {
  ROCK("グー", 0),
  SCISSORS("チョキ", 1),
  PAPER("パー", 2);

  private final String hand;
  private final int value;

  Hand(String hand, int value) {
    this.hand = hand;
    this.value = value;
  }

  public String getHand() {
    return hand;
  }

  public int getValue() {
    return value;
  }
}

enum Result {
  DRAW("あいこ"),
  LOSE("負け"),
  WIN("勝ち");

  private final String result;

  Result(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }
}

public class Janken {
  private Hand playerHand;
  private Hand computerHand;
  private Result result;

  public Janken(String playerHand) {
    this.playerHand = Hand.valueOf(playerHand.toUpperCase());
    this.computerHand = Hand.ROCK; // コンピュータの手をグーに固定

    // 勝敗を判定
    switch ((this.playerHand.getValue() - this.computerHand.getValue() + 3) % 3) {
      case 0:
        this.result = Result.DRAW;
        break;
      case 1:
        this.result = Result.LOSE;
        break;
      case 2:
        this.result = Result.WIN;
        break;
    }
  }

  public String getPlayerHand() {
    return playerHand.getHand();
  }

  public void setPlayerHand(String playerHand) {
    this.playerHand = Hand.valueOf(playerHand.toUpperCase());
  }

  public String getComputerHand() {
    return computerHand.getHand();
  }

  public void setComputerHand(String computerHand) {
    this.computerHand = Hand.valueOf(computerHand.toUpperCase());
  }

  public String getResult() {
    return result.getResult();
  }
}
