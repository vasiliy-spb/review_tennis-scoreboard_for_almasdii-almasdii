package TableTennis.model;

import lombok.Getter;

public class Game {
    @Getter
    private Point firstPlayerPoint = Point.LOVE;
    @Getter
    private Point secondPlayerPoint = Point.LOVE;
    private PlayerNumber advantage;
    public Game(){

    }
    protected boolean pointWonBy(PlayerNumber playerNumber){
        boolean isFirstPlayer = playerNumber == PlayerNumber.FIRST_PLAYER;

        if (firstPlayerPoint == Point.FORTY && secondPlayerPoint == Point.FORTY) {
            if (advantage == playerNumber) {
                return true;
            } else if (advantage != null) {
                advantage = null;
            } else {
                advantage = playerNumber;
            }
            return false;
        }

        if(firstPlayerPoint == Point.FORTY && isFirstPlayer || secondPlayerPoint == Point.FORTY && !isFirstPlayer){
            return true;
        }

        if (playerNumber == PlayerNumber.FIRST_PLAYER) {
            firstPlayerPoint = firstPlayerPoint.next();
        }
        else {
            secondPlayerPoint = secondPlayerPoint.next();
        }
        return false;
    }
}
