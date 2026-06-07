package TableTennis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@ToString
@Table(name = "players")
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
