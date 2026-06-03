package TableTennis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "matches")
@Entity
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1")
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2")

    private Player secondPlayer;
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "winner")
    private Player winner;
    public MatchEntity(Player firstPlayer, Player secondPlayer, Player winner){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        MatchEntity that = (MatchEntity) o;
        return that.id != null && that.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "MatchEntity{" +
                "id=" + id +
                ", firstPlayer=" + (firstPlayer != null ? firstPlayer.getId() : null) +
                ", secondPlayer=" + (secondPlayer != null ? secondPlayer.getId() : null) +
                ", winner=" + (winner != null ? winner.getId() : null) +
                '}';
    }
}
