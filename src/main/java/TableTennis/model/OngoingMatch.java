package TableTennis.model;

import TableTennis.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OngoingMatch {

    private Player player1;
    private Player player2;

    @Builder.Default
    private boolean advantagePlayer1 = false;
    @Builder.Default
    private boolean advantagePlayer2 = false;

    private int player1Sets;
    private int player2Sets;

    private int player1Games;
    private int player2Games;
    @Builder.Default
    private Points player1Points = Points.LOVE;
    @Builder.Default
    private Points player2Points = Points.LOVE;

    private Player winner;
    public boolean isFinished(){
        return winner != null;
    }
    private TaiBreak taiBreak;
}