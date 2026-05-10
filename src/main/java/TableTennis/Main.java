package TableTennis;
import TableTennis.model.Match;
import TableTennis.model.OngoingMatch;
import TableTennis.entity.Player;
import TableTennis.model.Point;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Almas");
        Player player2 = new Player("anonymous");
        Match match1 = new Match(1,player1,player2);
        match1.pointWonBy(player1);

    }
}
