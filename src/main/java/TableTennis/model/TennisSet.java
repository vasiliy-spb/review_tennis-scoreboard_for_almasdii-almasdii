package TableTennis.model;

import lombok.Getter;

public class TennisSet {
    public static final int WIN_SCORE = 6;
    public static final int TAI_BREAK_WIN_SCORE = 7;
    public static final int WIN_DIFF = 2;
    @Getter private int firstPlayerGames;
    @Getter private int secondPlayerGames;
    private Game game;
    private TieBreak tieBreak;
    public TennisSet(){
        game = new Game();
    }
    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(isSetFinished()){
            throw new IllegalStateException("Set is already finished");
        }
        if(isTieBreakStarted()){
            return tieBreakCase(playerNumber);
        }

        boolean isGameWon = game.pointWonBy(playerNumber);
        if(isGameWon){
            nextGame(playerNumber);
        }
        if(firstPlayerGames == WIN_SCORE && secondPlayerGames == WIN_SCORE){
            tieBreak = new TieBreak();
        }
        return isSetFinished();
    }

    private void nextGame(PlayerNumber playerNumber){
        if(playerNumber == PlayerNumber.FIRST_PLAYER){
            firstPlayerGames++;
        }
        else {
            secondPlayerGames++;
        }
        if (!isSetFinished()) {
            game = new Game();
        }
    }

    private boolean tieBreakCase(PlayerNumber playerNumber){
        boolean isFinished = tieBreak.pointWonBy(playerNumber);
        if (isFinished){
            if(playerNumber == PlayerNumber.FIRST_PLAYER){
                firstPlayerGames++;
            }
            else {
                secondPlayerGames++;
            }
            return isSetFinished();
        }
        return false;
    }

    protected boolean isSetFinished(){
        if (firstPlayerGames == TAI_BREAK_WIN_SCORE || secondPlayerGames == TAI_BREAK_WIN_SCORE) return true;

        return (firstPlayerGames >= WIN_SCORE || secondPlayerGames >= WIN_SCORE)
                && Math.abs(firstPlayerGames - secondPlayerGames) >= WIN_DIFF;
    }
    public Point getFirstPlayerPoints(){
        return game.getFirstPlayerPoint();
    }
    public Point getSecondPlayerPoints(){
        return game.getSecondPlayerPoint();
    }
    public int firstPlayerTieBreakScore(){
        if(tieBreak == null){
            throw new IllegalStateException("Tie break not started you cant get Tiebreaks score");
        }
        return tieBreak.getFirstPlayerScore();
    }
    public int secondPlayerTieBreakScore(){
        if(tieBreak == null){
            throw new IllegalStateException("Tie break not started you cant get Tiebreaks score");
        }
        return tieBreak.getSecondPlayerScore();
    }
    public boolean isTieBreakStarted(){
        return tieBreak != null;
    }

}
