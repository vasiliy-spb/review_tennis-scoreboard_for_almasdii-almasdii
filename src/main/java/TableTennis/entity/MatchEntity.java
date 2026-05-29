package TableTennis.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
@Builder
@Table(name = "matches")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany()
    @JoinColumn(name = "player1")
    private Player firstPlayer;
    @OneToMany
    @JoinColumn(name = "player2")
    private Player secondPlayer;
    @OneToMany()
    @JoinColumn(name = "winner")
    private Player winner;
    public MatchEntity(Player firstPlayer, Player secondPlayer, Player winner){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }
}


