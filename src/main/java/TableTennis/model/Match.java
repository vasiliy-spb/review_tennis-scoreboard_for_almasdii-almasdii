package TableTennis.model;

import TableTennis.entity.Player;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Match {
    @Getter private UUID id;
    @Getter private final Player firstPlayer;
    @Getter private final Player secondPlayer;
    @Getter private Player winner;
    @Getter private int firstPlayerSets;
    @Getter private int secondPlayerSets;
    private TennisSet currentSet;
    private final static int WIN_SCORE = 2;



    public Match(UUID id,Player firstPlayer, Player secondPlayer){
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentSet = new TennisSet();
    }

    public boolean pointWonBy(Player player){
        if(isFinished()){
            throw new IllegalStateException("Match is already finished");
        }
        if(!player.equals(firstPlayer) && !player.equals(secondPlayer)){
            throw new IllegalArgumentException("Player is not part of this match");
        }
        PlayerNumber playerNumber = (player.equals(firstPlayer))
                ? PlayerNumber.FIRST_PLAYER
                : PlayerNumber.SECOND_PLAYER;

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
    public int getFirstPlayerGames(){
        return currentSet.getFirstPlayerGames();
    }
    public int getSecondPlayerGames(){
        return currentSet.getSecondPlayerGames();
    }
    public int getFirstPlayerPoints(){
        return currentSet.getFirstPlayerPoints().getScore();
    }
    public int getSecondPlayerPoints(){
        return currentSet.getSecondPlayerPoints().getScore();
    }
    public int firstPlayerTieBreakScore(){
        return currentSet.firstPlayerTieBreakScore();
    }
    public int secondPlayerTieBreakScore(){
        return currentSet.secondPlayerTieBreakScore();
    }
    public boolean isTieBreakStarted(){
        return currentSet.isTieBreakStarted();
    }


}
