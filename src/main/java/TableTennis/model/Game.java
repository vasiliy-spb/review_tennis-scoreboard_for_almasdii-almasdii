package TableTennis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Game {
    private Point firstPlayerPoint = Point.LOVE;
    private Point secondPlayerPoint = Point.LOVE;
    private PlayerNumber advantage;
    private boolean isGameEnded;

    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(isGameEnded){
            throw new IllegalStateException("Game is already finished");
        }
        boolean isFirstPlayer = playerNumber == PlayerNumber.FIRST_PLAYER;

        if (isDeuce()) {
            return advantageCase(playerNumber);
        }

        if(firstPlayerPoint == Point.FORTY && isFirstPlayer || secondPlayerPoint == Point.FORTY && !isFirstPlayer){
            isGameEnded = true;
            return true;
        }

        nextPoint(playerNumber);
        return false;
    }
    private void nextPoint(PlayerNumber playerNumber){
        if (playerNumber == PlayerNumber.FIRST_PLAYER) {
            firstPlayerPoint = firstPlayerPoint.next();
        }
        else {
            secondPlayerPoint = secondPlayerPoint.next();
        }
    }

    private boolean advantageCase(PlayerNumber playerNumber){
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
    public boolean isDeuce(){
        return firstPlayerPoint == Point.FORTY && secondPlayerPoint == Point.FORTY;
    }
}
