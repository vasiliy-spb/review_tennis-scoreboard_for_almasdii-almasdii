package TableTennis.model;

import lombok.Getter;

@Getter
public enum PlayerNumber {

    // Значение поля value нигде не используется в проекте, поэтому его можно удалить.

    FIRST_PLAYER(1),
    SECOND_PLAYER(2);
    private final int value;

    // Можно использовать @RequiredArgsConstructor
    PlayerNumber(int value){
        this.value = value;
    }

    // Не нужно переопределять метод только чтобы вызвать в нём метод родительского класса.
    @Override
    public String toString() {
        return super.toString();
    }
}
