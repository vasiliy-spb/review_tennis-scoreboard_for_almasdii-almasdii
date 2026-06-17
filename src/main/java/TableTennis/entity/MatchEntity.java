package TableTennis.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "matches") // "Matches" является зарезервированным словом в некоторых СУБД.
    // Здесь проблем не будет, но лучше не выбирать такие названия. (см. файл "sql-keywords.md" в этом же пакете)
@Entity
public class MatchEntity extends BaseEntity<Long>{

    // Стоит добавить проверки, что игроки разные и победитель один из игроков. Например, через аннотацию org.hibernate.annotations.Check над классом.

    // Можно назвать просто Match (контекст того, что это JPA Entity понятен из названия пакета)

    // Если equals и hashCode не используются в проекте, их можно не переопределять.

    // Если @ToString не используется в проекте, его можно не переопределять.

    // Колонки игроков и победителя в `@JoinColumn` названы `player1`, `player2`, `winner`.
        // Для колонок, хранящих внешний ключ, уместно добавлять суффикс `_id`, чтобы было очевидно, что в них хранится идентификатор, а не какая-то другая информация.

    // Поля игроков можно расположить в более логичном порядке: firstPlayer —> secondPlayer —> winner

    // Для обязательных полей стоит добавить `optional = false` в `@ManyToOne` или `nullable = false` в `@JoinColumn` (можно добавить оба параметра).
        // Целостность данных должна обеспечиваться на всех уровнях: в приложении (валидация) и в БД (constraints). Отсутствие ограничений в БД означает,
        // что данные могут быть испорчены из-за ошибок в приложении или при прямом доступе к БД.
        //
        // А также можно добавить атрибут `updatable = false`. Это атрибут запрещает изменять колонку после её первоначальной вставки.
        // Игроки матча и победитель не должны меняться, поэтому эти колонки можно защитить от обновлений.

    @ManyToOne(fetch = FetchType.LAZY) // Стоит добавить optional = false
    @JoinColumn(name = "player1") // Можно добавить nullable = false и updatable = false
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY) // Стоит добавить optional = false
    @JoinColumn(name = "winner") // Можно добавить nullable = false и updatable = false
    private Player winner;


    @ManyToOne(fetch = FetchType.LAZY) // Стоит добавить optional = false
    @JoinColumn(name = "player2") // Можно добавить nullable = false и updatable = false
    private Player secondPlayer;
    public MatchEntity(Player firstPlayer, Player secondPlayer, Player winner){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }

    // При такой реализации equals все несохранённые матчи (которым БД ещё не присвоила ID) будут считаться одинаковыми.
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
