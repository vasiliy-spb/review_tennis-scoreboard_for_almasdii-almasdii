package TableTennis.model;

public class TennisSet {
    private int firstPlayerGames;
    private int secondPlayerGames;
    private Game game;
    private TieBreak taiBreak;
    public static final int WIN_SCORE = 6;
    public static final int TAI_BREAK_WIN_SCORE = 7;
    public static final int WIN_DIFF = 2;
    public TennisSet(){
        game = new Game();
    }
    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(firstPlayerGames == WIN_SCORE && secondPlayerGames == WIN_SCORE){
            if(taiBreak == null){
                taiBreak = new TieBreak();
            }
            boolean isFinished = taiBreak.pointWonBy(playerNumber);
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
        boolean isGameWon = game.pointWonBy(playerNumber);
        if(isGameWon){
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

        return isSetFinished();
    }

    protected boolean isSetFinished(){
        if (firstPlayerGames == TAI_BREAK_WIN_SCORE || secondPlayerGames == TAI_BREAK_WIN_SCORE) return true;

        return (firstPlayerGames >= WIN_SCORE || secondPlayerGames >= WIN_SCORE)
                && Math.abs(firstPlayerGames - secondPlayerGames) >= WIN_DIFF;
    }

}
