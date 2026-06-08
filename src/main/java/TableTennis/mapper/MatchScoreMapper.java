package TableTennis.mapper;

import TableTennis.dto.MatchScoreDto;
import TableTennis.model.Match;

public class MatchScoreMapper implements Mapper<MatchScoreDto,Match>{


    @Override
    public MatchScoreDto mapFrom(Match match) {
        int firstPlayerTieBreakScore = 0;
        int secondPlayerTieBreakScore = 0;
        if(match.isTieBreakStarted()){
            firstPlayerTieBreakScore = match.firstPlayerTieBreakScore();
            secondPlayerTieBreakScore = match.secondPlayerTieBreakScore();
        }
        return new MatchScoreDto(
                match.getFirstPlayer().getName(),
                match.getSecondPlayer().getName(),
                match.getFirstPlayerPoints(),
                match.getSecondPlayerPoints(),
                match.getFirstPlayerGames(),
                match.getSecondPlayerGames(),
                match.getFirstPlayerSets(),
                match.getSecondPlayerSets(),
                match.isTieBreakStarted(),
                firstPlayerTieBreakScore,
                secondPlayerTieBreakScore
        );
    }
}
