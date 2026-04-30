package TableTennis;

import TableTennis.dto.MatchResponse;
import TableTennis.model.OngoingMatch;
import TableTennis.entity.Player;
import TableTennis.model.Points;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static OngoingMatch match = OngoingMatch.builder()
            .player1(new Player(1,"1"))
            .player2(new Player(2,"2"))
            .player1Points(Points.LOVE)
            .player2Points(Points.LOVE).build();
    boolean advantagePlayer1 = false;
    boolean advantagePlayer2 = false;

    static Player player1 = match.getPlayer1();
    static Player player2 = match.getPlayer2();
    public static void main(String[] args) {
        MatchResponse response = new MatchResponse("almas","awert","werty");
        System.out.println(response.firstPlayerName());
        System.out.println(response.secondPlayerName());
        System.out.println(response.winnerName());
    }
    public static void wonPoints(Player player){
        if (match.getPlayer1Points() == Points.FORTY && match.getPlayer2Points() == Points.FORTY){
            if(player1.getName().equals(player.getName())){
                if(match.isAdvantagePlayer1()){
                    wonGames(player1);
                }else {
                    match.setAdvantagePlayer1(true);
                    match.setAdvantagePlayer2(false);
                }
            }
            if (player2.getName().equals(player.getName())){
                if(match.isAdvantagePlayer2()){
                    wonGames(player2);
                }
                else {
                    match.setAdvantagePlayer1(false);
                    match.setAdvantagePlayer2(true);
                }
            }
            return;
        }
        Points point1 = match.getPlayer1Points();
        Points point2 = match.getPlayer2Points();

        if(player.getName().equals(player1.getName())){
            if(point1 == Points.FORTY){
                wonGames(player1);
            }
            else {
                match.setPlayer1Points(point1.next());
            }
        }
        else {
            if(point2 == Points.FORTY){
                wonGames(player2);
            }
            else {
                match.setPlayer2Points(point2.next());
            }
        }
    }
    public static void wonGames(Player player){
        match.setPlayer1Points(Points.LOVE);
        match.setPlayer2Points(Points.LOVE);
        match.setAdvantagePlayer1(false);
        match.setAdvantagePlayer2(false);


        int player1game = match.getPlayer1Games();
        int player2game = match.getPlayer2Games();

        boolean isPlayer1 = player == player1;

        if(isPlayer1){
            player1game++;
            match.setPlayer1Games(player1game);
        }
        else{
            player2game++;
            match.setPlayer2Games(player2game);
        }

        if(player1game >= 6 || player2game >= 6){
            if(Math.abs(player1game - player2game) >= 2){
                wonSets(player);
            }
        }

        if(match.getPlayer1Games() == 6 && match.getPlayer2Games() == 6){
            String winner = easyGame();
            if(winner.equals("1")){
                wonSets(player1);
            }
            if(winner.equals("2")){
                wonSets(player2);
            }

        }
    }
    public static String easyGame(){
        Scanner scanner = new Scanner(System.in);
        int player1points = 0;
        int player2points = 0;
        while(true){
            String point = scanner.nextLine();
            if(point.equals("1")){
                player1points++;
            }
            if (point.equals("2")){
                player2points++;
            }
            if(player1points >= 7 && Math.abs(player1points - player2points) >= 2){
                return "1";
            }
            if(player2points >= 7 && Math.abs(player2points - player1points) >= 2){
                return "2";
            }
        }
    }
    public static void wonSets(Player player){
        match.setPlayer1Games(0);
        match.setPlayer2Games(0);
        int player1set = match.getPlayer1Sets();
        int player2set = match.getPlayer2Sets();


        if(player.getName().equals(player1.getName())){
            match.setPlayer1Sets(player1set+1);
            if (match.getPlayer1Sets() == 2){
                match.setWinner(player1);
            }
        }else {
            match.setPlayer2Sets(player2set+1);
            if (match.getPlayer2Sets() == 2){
                match.setWinner(player2);
            }
        }
    }
}
