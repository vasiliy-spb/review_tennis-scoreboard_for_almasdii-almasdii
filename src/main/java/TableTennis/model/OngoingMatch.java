package TableTennis.model;

import TableTennis.entity.Player;
import lombok.*;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OngoingMatch {

    private Player firstPlayer;
    private Player secondPlayer;

    @Builder.Default
    private boolean advantagePlayer1 = false;
    @Builder.Default
    private boolean advantagePlayer2 = false;

    private int firstPlayerSets;
    private int secondPlayerSets;


    private int firstPlayerGames;
    private int secondPlayerGames;
    @Builder.Default
    private Point firstPlayerPoints = Point.LOVE;
    @Builder.Default
    private Point secondPlayerPoints = Point.LOVE;

    private Player winner;
    public boolean isFinished(){
        return winner != null;
    }
}