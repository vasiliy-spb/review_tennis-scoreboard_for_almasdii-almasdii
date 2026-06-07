package TableTennis.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class GameTest {
    private Game game;

    @BeforeEach
    void setUp(){
        game = new Game();
    }
    private void winOneGame(PlayerNumber playerNumber){
        assertThat(game.pointWonBy(playerNumber)).isFalse();
        assertThat(game.pointWonBy(playerNumber)).isFalse();
        assertThat(game.pointWonBy(playerNumber)).isFalse();
        assertThat(game.pointWonBy(playerNumber)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Гейм заканчивается когда игрок выйгрывает четыре пойнта")
    @EnumSource(PlayerNumber.class)
    void playerWinWhenGetScoreFourTimes(PlayerNumber playerNumber){
        winOneGame(playerNumber);
        assertThat(game.isGameEnded()).isTrue();
    }


    @Test
    void scoreProgressesCorrectly(){
        assertThat(game.getFirstPlayerPoint()).isEqualTo(Point.LOVE);

        game.pointWonBy(PlayerNumber.FIRST_PLAYER);
        assertThat(game.getFirstPlayerPoint()).isEqualTo(Point.FIFTEEN);

        game.pointWonBy(PlayerNumber.FIRST_PLAYER);
        assertThat(game.getFirstPlayerPoint()).isEqualTo(Point.THIRTY);

        game.pointWonBy(PlayerNumber.FIRST_PLAYER);
        assertThat(game.getFirstPlayerPoint()).isEqualTo(Point.FORTY);
    }

    @ParameterizedTest
    @MethodSource("getScoreProgress")
    void winOnlyWhenFortyAndLoveFortyFIFTEEN(int firstPlayerPoint, int secondPlayerPoint, boolean isGameFinished){
        for (int i = 0; i < firstPlayerPoint; i++) {
            game.pointWonBy(PlayerNumber.FIRST_PLAYER);
        }
        for (int i = 0; i < secondPlayerPoint; i++) {
            game.pointWonBy(PlayerNumber.SECOND_PLAYER);
        }

        assertThat(game.isGameEnded()).isEqualTo(isGameFinished);
    }
    static Stream<Arguments> getScoreProgress(){
        return Stream.of(
                Arguments.of(1,2,false),
                Arguments.of(2,2,false),
                Arguments.of(3,2,false),
                Arguments.of(1,3,false),
                Arguments.of(4,0,true),
                Arguments.of(1,4,true)
        );
    }

    @Test
    @DisplayName("должен бросить исключения если после того как гейм закончился вызвался pointWonBy ")
    void throwExceptionWhenPointWinAfterFinished(){
        winOneGame(PlayerNumber.FIRST_PLAYER);
        assertThatThrownBy(()->game.pointWonBy(PlayerNumber.FIRST_PLAYER))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Game is already finished");
    }

    @Nested
    @DisplayName("Everything related with deuce")
    @Tag("Deuce logic")
    class Deuce{
        @BeforeEach
        void bringToDeuce(){
            for (int i = 0; i < 3; i++) {
                assertThat(game.pointWonBy(PlayerNumber.SECOND_PLAYER)).isFalse();
                assertThat(game.pointWonBy(PlayerNumber.FIRST_PLAYER)).isFalse();
            }
        }

        @Test
        void deuceRestoredWhenOpponentEqualizes() {
            game.pointWonBy(PlayerNumber.FIRST_PLAYER);
            game.pointWonBy(PlayerNumber.SECOND_PLAYER);

            assertThat(game.isDeuce()).isTrue();
            assertThat(game.pointWonBy(PlayerNumber.FIRST_PLAYER)).isFalse();
        }

        @Test
        void deuceShouldStartWhenScoresForty(){
            assertThat(game.getFirstPlayerPoint()).isEqualTo(Point.FORTY);
            assertThat(game.getSecondPlayerPoint()).isEqualTo(Point.FORTY);
            assertThat(game.isDeuce()).isTrue();
        }
        @Test
        void playerShouldWinInDeuceAfterWinTwice(){
            assertThat(game.pointWonBy(PlayerNumber.FIRST_PLAYER)).isFalse();
            assertThat(game.pointWonBy(PlayerNumber.FIRST_PLAYER)).isTrue();
        }
        @Test
        void advantagePlayerOneAfterDeuce(){
            game.pointWonBy(PlayerNumber.FIRST_PLAYER);
            assertThat(game.getAdvantage()).isEqualTo(PlayerNumber.FIRST_PLAYER);
        }

    }
}
