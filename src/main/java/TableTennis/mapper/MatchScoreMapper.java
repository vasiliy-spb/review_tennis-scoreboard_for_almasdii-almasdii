package TableTennis.mapper;

import TableTennis.dto.MatchScoreModel;
import TableTennis.model.Match;

public class MatchScoreMapper implements Mapper<MatchScoreModel,Match>{

    @Override
    public MatchScoreModel mapFrom(Match match) {
        return new MatchScoreModel(
                match.getFirstPlayer().getName(),
                match.getSecondPlayer().getName(),
                match.getFirstPlayerPoints(),
                match.getSecondPlayerPoints(),
                match.getFirstPlayerGames(),
                match.getSecondPlayerGames(),
                match.getFirstPlayerSets(),
                match.getSecondPlayerSets()
        );
    }
}
