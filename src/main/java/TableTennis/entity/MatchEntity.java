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
    @Column(name = "player1")
    private Long firstPlayerId;
    @Column(name = "player2")
    private Long secondPlayerId;
    @Column(name = "winner")
    private Long winnerId;
    public MatchEntity(Long firstPlayerId,Long secondPlayerId,Long winnerId){
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.winnerId = winnerId;
    }
}


