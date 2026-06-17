package TableTennis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"}, callSuper = false) // Если equals и hashCode не используются в проекте, их можно не переопределять.
@ToString // Использование @ToString с @Entity может создавать проблемы (см. файл "tostring-entity.md" в этом же пакете),
            // поэтому если он не используется в проекте, его можно не переопределять.
@Table(name = "players") // можно задать индекс через аннотацию, чтобы у него было понятное имя — @Table(name = "players", indexes = @Index(...))
@Entity
public class Player extends BaseEntity<Long>{

    @Column(name = "name",unique = true,nullable = false)
    @NotBlank
    @Size(min = 1,max = 20)
    private String name;
    public Player(String name){
        this.name = name;
    }
}
