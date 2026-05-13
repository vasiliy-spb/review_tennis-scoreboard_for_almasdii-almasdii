package TableTennis;
import TableTennis.dto.MatchScoreModel;
import TableTennis.mapper.MatchScoreMapper;
import TableTennis.model.Match;
import TableTennis.entity.Player;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Player player1 = new Player("Almas");
        Player player2 = new Player("anonymous");
        UUID uuid = UUID.randomUUID();
        Match match = new Match(uuid,player1,player2);

        Scanner scanner = new Scanner(System.in);
        MatchScoreMapper matchScoreMapper = new MatchScoreMapper();
        String isZero = "";
        while (!isZero.equals("0")){
            isZero = scanner.nextLine();
            if(isZero.equals("1")){
                match.pointWonBy(player1);
            } else if (isZero.equals("2")) {
                match.pointWonBy(player2);
            }else {
                System.out.println("Enter Correct number");
            }
            MatchScoreModel matchScoreModel = matchScoreMapper.mapFrom(match);
            System.out.println(matchScoreModel);
        }
    }
}
