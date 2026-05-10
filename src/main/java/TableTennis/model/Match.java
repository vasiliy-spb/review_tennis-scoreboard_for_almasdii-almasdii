package TableTennis.model;

import TableTennis.entity.Player;
import lombok.Getter;

@Getter
public class Match {
    @Getter private int id;
    @Getter private final Player firstPlayer;
    @Getter private final Player secondPlayer;
    @Getter private Player winner;
    @Getter private int firstPlayerSets;
    @Getter private int secondPlayerSets;
    private TennisSet currentSet;
    private final static int WIN_SCORE = 2;



    public Match(int id,Player firstPlayer, Player secondPlayer){
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentSet = new TennisSet();
    }

    public boolean pointWonBy(Player player){
        PlayerNumber playerNumber = (player.equals(firstPlayer))
                ? PlayerNumber.FIRST_PLAYER
                : PlayerNumber.SECOND_PLAYER;
        if(isFinished()){
            return true;
        }

        boolean isSetWon = currentSet.pointWonBy(playerNumber);
        if(isSetWon){
            if(playerNumber == PlayerNumber.FIRST_PLAYER) firstPlayerSets++;
            else secondPlayerSets++;

            if(firstPlayerSets >= WIN_SCORE) winner = firstPlayer;
            else if(secondPlayerSets >= WIN_SCORE) winner = secondPlayer;
            else currentSet = new TennisSet();
        }
        return isFinished();

    }
    public boolean isFinished(){
        if(winner != null) return true;
        return false;
    }



}
