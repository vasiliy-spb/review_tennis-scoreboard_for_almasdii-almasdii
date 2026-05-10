package TableTennis.model;

import lombok.Getter;

public class TieBreak {
    @Getter
    private int firstPlayerScore;
    @Getter
    private int secondPlayerScore;
    public static final int WIN_SCORE = 7;
    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(playerNumber == PlayerNumber.FIRST_PLAYER){
            firstPlayerScore++;
        }
        else {
            secondPlayerScore++;
        }
        return (firstPlayerScore >= WIN_SCORE || secondPlayerScore >= WIN_SCORE) && Math.abs(firstPlayerScore - secondPlayerScore) >= 2;
    }
}
