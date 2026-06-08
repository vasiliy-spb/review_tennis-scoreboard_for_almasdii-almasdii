package TableTennis.model;

import lombok.Getter;

@Getter
public class TieBreak {
    public static final int WIN_SCORE = 7;
    private int firstPlayerScore;
    private int secondPlayerScore;


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
