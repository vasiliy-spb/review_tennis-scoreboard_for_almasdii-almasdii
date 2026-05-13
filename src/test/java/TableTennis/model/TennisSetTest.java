package TableTennis.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Tag("unit")
public class TennisSetTest {
    private TennisSet tennisSet;

    @BeforeEach
    void setUp() {
        tennisSet = new TennisSet();
    }

    private void winOneGame(PlayerNumber playerNumber) {
        tennisSet.pointWonBy(playerNumber);
        tennisSet.pointWonBy(playerNumber);
        tennisSet.pointWonBy(playerNumber);
        tennisSet.pointWonBy(playerNumber);
    }

    private void winTieBreak(PlayerNumber playerNumber) {
        for (int i = 0; i < 6; i++) {
            assertThat(tennisSet.pointWonBy(playerNumber)).isFalse();
        }
        assertThat(tennisSet.pointWonBy(playerNumber)).isTrue();
    }

    private void winOneSet(PlayerNumber playerNumber) {
        for (int i = 0; i < 6; i++) {
            winOneGame(playerNumber);
        }
    }


    @Test
    @Tag("Set")
    void playerWinSetAfterSixGames() {
        assertThat(tennisSet.isSetFinished()).isFalse();
        winOneSet(PlayerNumber.FIRST_PLAYER);
        assertThat(tennisSet.isSetFinished()).isTrue();
    }

    private void tieBreakCondition() {
        for (int i = 0; i < 5; i++) {
            winOneGame(PlayerNumber.FIRST_PLAYER);
            winOneGame(PlayerNumber.SECOND_PLAYER);
        }

        winOneGame(PlayerNumber.SECOND_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);
    }

    @Test
    @Tag("Set")
    void tieBreakStartsWhenBothPlayersGamesIsSix() {
        assertThat(tennisSet.isTieBreakStarted()).isFalse();
        tieBreakCondition();
        assertThat(tennisSet.isTieBreakStarted()).isTrue();

    }

    @Test
    @Tag("TieBreak")
    void firstPlayerWinInTieBreak() {
        assertThat(tennisSet.isSetFinished()).isFalse();
        tieBreakCondition();
        winTieBreak(PlayerNumber.FIRST_PLAYER);
        assertThat(tennisSet.isSetFinished()).isTrue();

    }

    @Test
    void throwExceptionWhenTieBreakNotStarted() {
        assertThatThrownBy(() -> tennisSet.firstPlayerTieBreakScore()).
                isInstanceOf(IllegalStateException.class)
                .hasMessage("Tie break not started you cant get Tiebreaks score");

    }

    @Test
    void throwExceptionWhenWinAfterSetIsFinished() {
        winOneSet(PlayerNumber.FIRST_PLAYER);
        assertThatThrownBy(() -> tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Set is already finished");
    }
}
