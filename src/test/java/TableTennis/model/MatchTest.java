package TableTennis.model;

import TableTennis.entity.Player;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Tag("unit")
public class MatchTest {

    // Тестирование основной бизнес-логики должно быть возможным без классов JPA Entity

    private Match match;
    private Player firstPlayer;
    private Player secondPlayer;
    @BeforeEach
    void setUp(){
        firstPlayer = new Player("mock1");
        secondPlayer = new Player("mock2");
        UUID uuid = UUID.randomUUID();
        match = new Match(uuid,firstPlayer,secondPlayer);
    }

    private void winOneGame(Player player){
        for (int i = 0; i < 4; i++) {
            match.pointWonBy(player);
        }
    }
    private void winOneSet(Player player){
        for (int i = 0; i < 6; i++) {
            winOneGame(player);
        }
    }

    @Test
    @DisplayName("Первый игрок передаётся как FIRST_PLAYER")
    void firstPlayerMapsToFirstPlayerNumber() {
        match.pointWonBy(firstPlayer);
        assertSoftly(softly -> {
            softly.assertThat(match.getFirstPlayerPoints()).isEqualTo(15);
            softly.assertThat(match.getSecondPlayerPoints()).isEqualTo(0);
        });
    }

    @Test
    @DisplayName("Второй игрок передаётся как SECOND_PLAYER")
    void secondPlayerMapsToSecondPlayerNumber() {
        match.pointWonBy(secondPlayer);
        assertSoftly(softly -> {
            softly.assertThat(match.getFirstPlayerPoints()).isEqualTo(0);
            softly.assertThat(match.getSecondPlayerPoints()).isEqualTo(15);
        });
    }

    @Nested
    @DisplayName("Normal Gameplay")
    class MatchLogic{

        @Test
        @DisplayName("Winner is null до конца матча")
        void winnerIsNullBeforeMatchEnds(){
            assertThat(match.getWinner()).isNull();
            winOneSet(firstPlayer);
            assertThat(match.getWinner()).isNull();

        }
        @Test
        @DisplayName("После выйгранного одного сета сбрасывается счет геймов")
        void newSetStartsFromZero() {
            winOneSet(firstPlayer);

            match.pointWonBy(firstPlayer);

            assertSoftly(softly -> {
                softly.assertThat(match.getFirstPlayerGames()).isEqualTo(0);
                softly.assertThat(match.getSecondPlayerGames()).isEqualTo(0);
                softly.assertThat(match.getFirstPlayerPoints()).isEqualTo(15);
            });
        }

        @Test
        @DisplayName("игрок зарабатывает один set после того как выйграл 6 геймов со счетом 6-0")
        void playerWinOneSetCorrectly(){
            winOneSet(firstPlayer);
            assertThat(match.getFirstPlayerSets()).isEqualTo(1);
        }

        @Test
        @DisplayName("Игрок выйгрывает матч после того как выйграл два сета")
        void playerWinMatchWhenWinTwoSets(){
            winOneSet(firstPlayer);
            winOneSet(firstPlayer);
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(match.getFirstPlayerSets()).isEqualTo(2);
                softAssertions.assertThat(match.getWinner()).isEqualTo(firstPlayer);
                softAssertions.assertThat(match.isFinished()).isTrue();
            });
        }
        @Test
        @DisplayName("Матч продолжается если счета у обоих игроков 1")
        void matchContinueIfBothSetsAreOne(){
            winOneSet(firstPlayer);
            winOneSet(secondPlayer);
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(match.getFirstPlayerSets()).isEqualTo(1);
                softAssertions.assertThat(match.isFinished()).isFalse();
                softAssertions.assertThat(match.getWinner()).isNull();
                softAssertions.assertThat(match.getSecondPlayerSets()).isEqualTo(1);
            });
        }
    }
    @Nested
    class ExceptionCase{
        @Test
        @DisplayName("should throw IllegalStateException when we call pointWonBy for already finished match")
        void throwExceptionWhenCallAlreadyFinishedMatch(){
            winOneSet(firstPlayer);
            winOneSet(firstPlayer);
            assertThatThrownBy(()->match.pointWonBy(firstPlayer))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Match is already finished");
        }
        @Test
        @DisplayName("Should throw IllegalArgumentException if Given Player for pointWonBy does not Match")
        void throwExceptionIfGivenPlayerNotMatch(){
            Player player = new Player("mock3");
            assertThatThrownBy(()-> match.pointWonBy(player))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Player is not part of this match");
        }

    }
    @Test
    void newSetStartsFromZeroGames(){
        assertThat(match.getFirstPlayerGames()).isEqualTo(0);
        assertThat(match.getSecondPlayerGames()).isEqualTo(0);
        winOneSet(firstPlayer);
        assertThat(match.getFirstPlayerGames()).isEqualTo(0);
        assertThat(match.getSecondPlayerGames()).isEqualTo(0);
    }

}
