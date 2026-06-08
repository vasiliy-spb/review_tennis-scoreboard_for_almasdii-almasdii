package TableTennis.mapper;

import TableTennis.dto.MatchResponse;
import TableTennis.entity.MatchEntity;

public class MatchResponseMapper implements Mapper<MatchResponse, MatchEntity>{
    @Override
    public MatchResponse mapFrom(MatchEntity matchEntity) {
        return new MatchResponse(matchEntity.getFirstPlayer().getName(),
                matchEntity.getSecondPlayer().getName(),
                matchEntity.getWinner().getName());
    }
}
