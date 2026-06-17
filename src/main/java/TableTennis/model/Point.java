package TableTennis.model;

import lombok.Getter;

@Getter
public enum Point {
    LOVE(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40);


    private final int score;

    // Можно использовать @RequiredArgsConstructor
    Point(int score) {
        this.score = score;
    }

    public Point next(){
        return switch (this){
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> throw new IllegalArgumentException("asdfgh"); // Лучше использовать информативные сообщения в исключениях.

        };
    }
}