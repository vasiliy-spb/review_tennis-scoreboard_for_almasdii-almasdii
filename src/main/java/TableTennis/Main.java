package TableTennis;
import TableTennis.dto.MatchScoreModel;
import TableTennis.mapper.MatchScoreMapper;
import TableTennis.model.Match;
import TableTennis.entity.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println(100/20);
        System.out.println(Math.ceil(9.0/20));
    }
}

//Player player1 = new Player("Almas");
//        Player player2 = new Player("anonymous");
//        UUID uuid = UUID.randomUUID();
//        Match match = new Match(uuid,player1,player2);
//
//        Scanner scanner = new Scanner(System.in);
//        MatchScoreMapper matchScoreMapper = new MatchScoreMapper();
//        String isZero = "";
//        while (!isZero.equals("0")){
//            isZero = scanner.nextLine();
//            if(isZero.equals("1")){
//                match.pointWonBy(player1);
//            } else if (isZero.equals("2")) {
//                match.pointWonBy(player2);
//            }else {
//                System.out.println("Enter Correct number");
//            }
//            MatchScoreModel matchScoreModel = matchScoreMapper.mapFrom(match);
//            System.out.println(matchScoreModel);
//        }