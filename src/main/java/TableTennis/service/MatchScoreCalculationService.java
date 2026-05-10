package TableTennis.service;

import TableTennis.model.OngoingMatch;
import TableTennis.entity.Player;
import TableTennis.model.Point;
import TableTennis.model.TieBreak;

public class MatchScoreCalculationService {
    public void won(OngoingMatch match,String playerName){
        if(match.getTaiBreak() != null){
            taiBreak(match,playerName);
            return;
        }
        wonPoints(match,playerName);

    }

    public void wonPoints(OngoingMatch match, String playerName) {
        Player player1 = match.getFirstPlayer();
        Player player2 = match.getPlayer2();
        Point point1 = match.getFirstPlayerPoints();
        Point points2 = match.getSecondPlayerPoints();

        boolean isPlayer1 = playerName.equals(player1.getName());
        if(point1 == Point.FORTY && points2 == Point.FORTY){
            if(isPlayer1){
                if(match.isAdvantagePlayer1()){
                    wonGames(match,player1.getName());
                }else {
                    match.setAdvantagePlayer1(true);
                    match.setAdvantagePlayer2(false);
                }
            }
            else {
                if(match.isAdvantagePlayer2()){
                    wonGames(match,player2.getName());
                }else {
                    match.setAdvantagePlayer1(false);
                    match.setAdvantagePlayer2(true);
                }
            }
            return;
        }
        if(isPlayer1){
            if(point1 == Point.FORTY){
                wonGames(match,player1.getName());
            }else {
                match.setFirstPlayerPoints(match.getFirstPlayerPoints().next());
            }
        }
        else {
            if(points2 == Point.FORTY){
                wonGames(match,player2.getName());
            }else {
                match.setSecondPlayerPoints(match.getSecondPlayerPoints().next());
            }
        }
    }

    public void wonGames(OngoingMatch match,String playerName){
        match.setFirstPlayerPoints(Point.LOVE);
        match.setSecondPlayerPoints(Point.LOVE);
        match.setAdvantagePlayer1(false);
        match.setAdvantagePlayer2(false);

        boolean isPlayer1 = playerName.equals(match.getFirstPlayer().getName());


        if(isPlayer1){
            match.setFirstPlayerGames(match.getFirstPlayerGames() + 1);
        }
        else {
            match.setSecondPlayerGames(match.getSecondPlayerGames() + 1);
        }
        if((match.getFirstPlayerGames() >= 6 || match.getSecondPlayerGames() >= 6)
                && Math.abs(match.getFirstPlayerGames() - match.getSecondPlayerGames()) >= 2){
            wonSet(match,playerName);
            return;
        }
        if(match.getFirstPlayerGames() == 6 && match.getSecondPlayerGames() == 6){
            if(match.getTaiBreak() == null){
                match.setTaiBreak(new TieBreak(0,0));
            }else {
                taiBreak(match,playerName);
            }
        }

    }

    public void taiBreak(OngoingMatch match ,String playerName){
        TieBreak taiBreak = match.getTaiBreak();

        boolean isFirstPlayer = playerName.equals(match.getFirstPlayer().getName());


        if(isFirstPlayer){
            taiBreak.firstPlayerWin();
        }
        else {
            taiBreak.secondPlayerWin();

        }

        if(taiBreak.isTaiBreakEnded()){
            wonSet(match,playerName);
        }


    }

    public void wonSet(OngoingMatch match,String playerName){
        match.setFirstPlayerGames(0);
        match.setSecondPlayerGames(0);
        match.setTaiBreak(null);

        boolean isFirstPlayer = playerName.equals(match.getFirstPlayer().getName());
        if(isFirstPlayer){
            match.setPlayer1Sets(match.getPlayer1Sets() + 1);
        }
        else {
            match.setSecondPlayerSets(match.getSecondPlayerSets() + 1);
        }
        if(match.getPlayer1Sets() >= 2 && isFirstPlayer){
            match.setWinner(match.getFirstPlayer());
            return;
        }
        if(match.getSecondPlayerSets() >= 2 && !isFirstPlayer){
            match.setWinner(match.getPlayer2());
        }
    }
}
