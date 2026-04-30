package TableTennis.service;

import TableTennis.model.OngoingMatch;
import TableTennis.entity.Player;
import TableTennis.model.Points;
import TableTennis.model.TaiBreak;

public class MatchScoreCalculationService {
    public void won(OngoingMatch match,String playerName){
        if(match.getTaiBreak() != null){
            taiBreak(match,playerName);
            return;
        }
        wonPoints(match,playerName);

    }

    public void wonPoints(OngoingMatch match, String playerName) {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();
        Points points1 = match.getPlayer1Points();
        Points points2 = match.getPlayer2Points();

        boolean isPlayer1 = playerName.equals(player1.getName());
        if(points1 == Points.FORTY && points2 == Points.FORTY){
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
            if(points1 == Points.FORTY){
                wonGames(match,player1.getName());
            }else {
                match.setPlayer1Points(match.getPlayer1Points().next());
            }
        }
        else {
            if(points2 == Points.FORTY){
                wonGames(match,player2.getName());
            }else {
                match.setPlayer2Points(match.getPlayer2Points().next());
            }
        }
    }

    public void wonGames(OngoingMatch match,String playerName){
        match.setPlayer1Points(Points.LOVE);
        match.setPlayer2Points(Points.LOVE);
        match.setAdvantagePlayer1(false);
        match.setAdvantagePlayer2(false);

        boolean isPlayer1 = playerName.equals(match.getPlayer1().getName());


        if(isPlayer1){
            match.setPlayer1Games(match.getPlayer1Games() + 1);
        }
        else {
            match.setPlayer2Games(match.getPlayer2Games() + 1);
        }
        if((match.getPlayer1Games() >= 6 || match.getPlayer2Games() >= 6)
                && Math.abs(match.getPlayer1Games() - match.getPlayer2Games()) >= 2){
            wonSet(match,playerName);
            return;
        }
        if(match.getPlayer1Games() == 6 && match.getPlayer2Games() == 6){
            if(match.getTaiBreak() == null){
                match.setTaiBreak(new TaiBreak(0,0));
            }else {
                taiBreak(match,playerName);
            }
        }

    }

    public void taiBreak(OngoingMatch match ,String playerName){
        TaiBreak taiBreak = match.getTaiBreak();

        boolean isFirstPlayer = playerName.equals(match.getPlayer1().getName());


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
        match.setPlayer1Games(0);
        match.setPlayer2Games(0);
        match.setTaiBreak(null);

        boolean isFirstPlayer = playerName.equals(match.getPlayer1().getName());
        if(isFirstPlayer){
            match.setPlayer1Sets(match.getPlayer1Sets() + 1);
        }
        else {
            match.setPlayer2Sets(match.getPlayer2Sets() + 1);
        }
        if(match.getPlayer1Sets() >= 2 && isFirstPlayer){
            match.setWinner(match.getPlayer1());
            return;
        }
        if(match.getPlayer2Sets() >= 2 && !isFirstPlayer){
            match.setWinner(match.getPlayer2());
        }
    }
}
