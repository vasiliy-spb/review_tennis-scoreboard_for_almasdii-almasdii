package TableTennis.model;

import lombok.Getter;

@Getter
public enum PlayerNumber {
    FIRST_PLAYER(1),
    SECOND_PLAYER(2);
    private final int value;
    PlayerNumber(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
