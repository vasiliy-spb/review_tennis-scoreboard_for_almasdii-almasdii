package TableTennis.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "matches")
@Entity
public class MatchEntity extends BaseEntity<Long>{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1")
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner")
    private Player winner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2")
    private Player secondPlayer;
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
        return that.getId() != null && that.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "MatchEntity{" +
                "id=" + this.getId() +
                ", firstPlayer=" + (firstPlayer != null ? firstPlayer.getId() : null) +
                ", secondPlayer=" + (secondPlayer != null ? secondPlayer.getId() : null) +
                ", winner=" + (winner != null ? winner.getId() : null) +
                '}';
    }
}
