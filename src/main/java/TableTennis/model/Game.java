package TableTennis.model;

import lombok.Getter;

@Getter
public class Game {
    private Point firstPlayerPoint = Point.LOVE;
    private Point secondPlayerPoint = Point.LOVE;
    private PlayerNumber advantage;
    private boolean isGameEnded;

    public Game(){

    }
    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(isGameEnded){
            throw new IllegalStateException("Game is already finished");
        }
        boolean isFirstPlayer = playerNumber == PlayerNumber.FIRST_PLAYER;

        if (isDeuce()) {
            if (advantage == playerNumber) {
                isGameEnded = true;
                return true;
            } else if (advantage != null) {
                advantage = null;
            } else {
                advantage = playerNumber;
            }
            return false;
        }

        if(firstPlayerPoint == Point.FORTY && isFirstPlayer || secondPlayerPoint == Point.FORTY && !isFirstPlayer){
            isGameEnded = true;
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


    public boolean isDeuce(){
        return firstPlayerPoint == Point.FORTY && secondPlayerPoint == Point.FORTY;
    }
}
