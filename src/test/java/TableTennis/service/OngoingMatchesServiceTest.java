package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class OngoingMatchesServiceTest {

    @Mock private PlayerDao playerDao;
    @Mock private FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    @InjectMocks private OngoingMatchesService ongoingMatchesService;

    @Test
    @Disabled
    void returnsExistingPlayerByName() {
        Player player = new Player("Almas");

        doReturn(Optional.of(player)).when(playerDao).findByName("Almas");

//        Optional<Player> almas = ongoingMatchesService.("Almas");
//        assertThat(almas).isPresent().contains(player);
    }

    @Test
    void returnAllWhenFindAll(){

    }
}
