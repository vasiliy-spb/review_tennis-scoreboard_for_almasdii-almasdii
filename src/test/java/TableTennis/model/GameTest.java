package TableTennis.model;

import org.apache.logging.log4j.core.util.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class GameTest {
    private Game game;


    @BeforeAll
    static void init(){

    }

    @BeforeEach
    void newGame(){
        game = new Game();
    }
    @Test
    void firstPlayerWinWhenGetScoreFourTimes(){
        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
        assertTrue(game.pointWonBy(PlayerNumber.FIRST_PLAYER));

        Assertions.assertThat(game.pointWonBy(PlayerNumber.FIRST_PLAYER)).isTrue();
    }
    @Test
    void secondPlayerWinWhenGetScoreFourTimes(){
        assertFalse(game.pointWonBy(PlayerNumber.SECOND_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.SECOND_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.SECOND_PLAYER));
        assertTrue(game.pointWonBy(PlayerNumber.SECOND_PLAYER));
    }
    @Test
    void firstPlayerShouldWinAfterDeuce(){
        assertFalse(game.pointWonBy(PlayerNumber.SECOND_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.SECOND_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.SECOND_PLAYER));

        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));



        assertFalse(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
        assertTrue(game.pointWonBy(PlayerNumber.FIRST_PLAYER));
    }
    
    @AfterEach
    void clean(){

    }
    @AfterAll
    static void dest(){

    }
}
