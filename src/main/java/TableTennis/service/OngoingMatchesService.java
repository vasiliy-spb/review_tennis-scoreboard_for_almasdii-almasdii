package TableTennis.service;

import TableTennis.Exception.MatchNotFoundException;
import TableTennis.dto.MatchRequest;
import TableTennis.dto.MatchScoreModel;
import TableTennis.entity.MatchEntity;
import TableTennis.mapper.MatchScoreMapper;
import TableTennis.model.Match;
import TableTennis.entity.Player;
import TableTennis.validator.MatchValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OngoingMatchesService {
    private final Map<UUID, Match> currentMatches = new ConcurrentHashMap<>();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private final MatchValidator validator;
    private final PlayerService playerService;
    private final MatchScoreMapper matchScoreMapper = new MatchScoreMapper();

    public OngoingMatchesService(FinishedMatchesPersistenceService finishedMatchesPersistenceService
            ,MatchValidator validator,PlayerService playerService){
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        this.validator =  validator;
        this.playerService = playerService;

    }

    public UUID createMatch(MatchRequest request) {
        Player firstPlayer = playerService.findByNameOrCreate(request.firstPlayerName());
        Player secondPlayer = playerService.findByNameOrCreate(request.secondPlayerName());
        validator.validateNames(firstPlayer.getName(),secondPlayer.getName());

        log.debug("first player : {} , second player : {}",firstPlayer,secondPlayer);
        UUID uuid = UUID.randomUUID();
        Match match = new Match(uuid,firstPlayer,secondPlayer);
        currentMatches.put(uuid,match);
        return uuid;
    }
    public Optional<MatchScoreModel> getMatchScoreById(UUID uuid){
        validator.validateMatchId(uuid);
        Match match = currentMatches.get(uuid);
        if(match == null){
            throw new MatchNotFoundException("Match is not found");
        }
        MatchScoreModel matchScoreModel = matchScoreMapper.mapFrom(match);
        return Optional.ofNullable(matchScoreModel);
    }

    public boolean wonPoint(UUID matchId, String playerName) {
        Player player = playerService.findByNameOrCreate(playerName);
        Match match = currentMatches.get(matchId);
        if(match == null){
            throw new MatchNotFoundException("Match is not found");
        }

        boolean isMatchEnd = match.pointWonBy(player);
        log.debug("point won player : {} , for match : {} ",player,match);
        if(isMatchEnd){
            MatchEntity matchEntity = new MatchEntity(match.getFirstPlayer().getId()
                    ,match.getSecondPlayer().getId()
                    ,match.getWinner().getId());

            log.debug("Match is saving : {}",matchEntity);

            finishedMatchesPersistenceService.save(matchEntity);
            currentMatches.remove(matchId);
        }
        return isMatchEnd;
    }
}

