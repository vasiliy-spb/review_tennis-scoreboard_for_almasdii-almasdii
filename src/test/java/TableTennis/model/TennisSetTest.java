package TableTennis.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import TableTennis.Exception.TieBreakIsNullException;
import TableTennis.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TennisSetTest {
    private TennisSet tennisSet;
    @BeforeEach
    void setUp(){
        tennisSet = new TennisSet();
    }

    private void winOneGame(PlayerNumber playerNumber){
        assertThat(tennisSet.pointWonBy(playerNumber)).isFalse();
        assertThat(tennisSet.pointWonBy(playerNumber)).isFalse();
        assertThat(tennisSet.pointWonBy(playerNumber)).isFalse();
        assertThat(tennisSet.pointWonBy(playerNumber)).isTrue();
    }


    @Test
    @Tag("Set")
    void playerWinSetAfterSixGames(){
        winOneGame(PlayerNumber.FIRST_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);
        winOneGame(PlayerNumber.FIRST_PLAYER);

        assertThat(tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER)).isTrue();

    }
    @Test
    @Tag("Set")
    void tieBreakStartsWhenUsersGamesIsSix(){
        assertThat(tennisSet.isTieBreakStarted()).isFalse();
        for (int i = 1;i<6;i++){
            assertThat(tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER)).isFalse();
            assertThat(tennisSet.pointWonBy(PlayerNumber.SECOND_PLAYER)).isFalse();
        }
        assertThat(tennisSet.isTieBreakStarted()).isTrue();
    }

    @Test
    @Tag("TieBreak")
    void firstPlayerWinInTieBreak(){
        for (int i = 1;i<6;i++){
            assertThat(tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER)).isFalse();
            assertThat(tennisSet.pointWonBy(PlayerNumber.SECOND_PLAYER)).isFalse();
        }
        for (int i = 1;i<7;i++){
            assertThat(tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER)).isFalse();
        }
        assertThat(tennisSet.pointWonBy(PlayerNumber.FIRST_PLAYER)).isTrue();
    }
    @Test
    void throwExceptionWhenTieBreakNotStarted(){
        assertAll(
                ()->
                {
                    String message = assertThrows(TieBreakIsNullException.class,
                            () -> tennisSet.firstPlayerTieBreakScore()).getMessage();
                    assertEquals("tie break is not started", message);
                },
                ()->{
                    assertThatThrownBy(()-> tennisSet.firstPlayerTieBreakScore()).
                            isInstanceOf(TieBreakIsNullException.class)
                            .hasMessage("tie break is not started");
                }
        );

    }
}
