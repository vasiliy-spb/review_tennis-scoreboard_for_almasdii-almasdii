package TableTennis.model;

import lombok.Getter;

@Getter
public class TaiBreak{
    private int score1;
    private int score2;

    public TaiBreak(int score1, int score2) {
        this.score1 = score1;
        this.score2 = score2;
    }

    public void firstPlayerWin(){
        score1++;
    }
    public void secondPlayerWin(){
        score2++;
    }
    public boolean isTaiBreakEnded(){
        return (score1 >= 7 || score2 >= 7) && Math.abs(score1 - score2) >= 2;
    }

}
