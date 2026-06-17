package TableTennis.model;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Tag("unit")
public class TennisSetTest {

    // В идеале придерживаться принципа "один тест — одна проверка"

    // Можно вводить вспомогательные методы для настройки состояний

    // Для большей наглядности можно в каждом методе использовать @DisplayName

    // Класс тестирует не только сет (о чём говорит его название), но и гейм и тай-брейк. Лучше делать это в разных классах.

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

    private void winOneSet(PlayerNumber playerNumber) {
        for (int i = 0; i < 6; i++) {
            winOneGame(playerNumber);
        }
    }

    @Test
    void playerWinSetAfterSixGames() {
        assertThat(tennisSet.isSetFinished()).isFalse();
        winOneSet(PlayerNumber.FIRST_PLAYER);
        assertThat(tennisSet.isSetFinished()).isTrue();
    }

    @Test
    void setContinueWhenSixAndFive() {
        for (int i = 0; i < 5; i++) {
            winOneGame(PlayerNumber.FIRST_PLAYER);
            winOneGame(PlayerNumber.SECOND_PLAYER);
        }
        winOneGame(PlayerNumber.FIRST_PLAYER);
        assertThat(tennisSet.isSetFinished()).isFalse();
    }

    @Test
    void setIsFinishedWhenSevenAndFive() {
        for (int i = 0; i < 5; i++) {
            winOneGame(PlayerNumber.FIRST_PLAYER);
            winOneGame(PlayerNumber.SECOND_PLAYER);
        }
        winOneGame(PlayerNumber.FIRST_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);

        assertThat(tennisSet.isSetFinished()).isTrue();
    }


    @Test
    void throwExceptionWhenWinAfterSetIsFinished() {
        winOneSet(PlayerNumber.FIRST_PLAYER);
        assertThatThrownBy(() -> tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Set is already finished");
    }

    @Nested
    @Tag("TieBreak logic") // Теги в JUnit 5 не могут содержать пробелы, поэтому сейчас при запуске этих тестов в консоли видно сообщение о некорректном синтаксисе. Можно использовать kebab-case.
    @DisplayName("TieBreak class")
    class TieBreak {

        private void bringToTieBreak() {
            for (int i = 0; i < 5; i++) {
                winOneGame(PlayerNumber.FIRST_PLAYER);
                winOneGame(PlayerNumber.SECOND_PLAYER);
            }

            winOneGame(PlayerNumber.SECOND_PLAYER);
            winOneGame(PlayerNumber.FIRST_PLAYER);
        }

        private void winTieBreak(PlayerNumber playerNumber) {
            for (int i = 0; i < 6; i++) {
                assertThat(tennisSet.pointWonBy(playerNumber)).isFalse();
            }
            assertThat(tennisSet.pointWonBy(playerNumber)).isTrue();
        }

        @Test
        void playerWinInTieBreak() {
            assertThat(tennisSet.isSetFinished()).isFalse();
            bringToTieBreak();
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
        void tieBreakStartsWhenBothPlayersGamesIsSix() {
            assertThat(tennisSet.isTieBreakStarted()).isFalse();
            bringToTieBreak();
            assertThat(tennisSet.isTieBreakStarted()).isTrue();

        }

        @Test
        void returnFalseWhenTieBreakNotStarted() {
            assertThat(tennisSet.isTieBreakStarted()).isFalse();
            winOneSet(PlayerNumber.FIRST_PLAYER);
            assertThat(tennisSet.isTieBreakStarted()).isFalse();

        }

    }
}
