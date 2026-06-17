package TableTennis.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString // Если @ToString не используется в проекте, его можно не переопределять.
@EqualsAndHashCode // Если equals и hashCode не используются в проекте, их можно не переопределять.
public class BaseEntity<K> {

    // Создавать базовый класс ради одного общего поля — избыточно

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;
}
