package TableTennis.model;

import lombok.Getter;

@Getter
public enum Point {
    LOVE(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40);


    private final int score;

    Point(int score) {
        this.score = score;
    }

    public static Point fromValue(int value) {
        for (Point p : Point.values()) {
            if (p.score == value) {
                return p;
            }
        }
        throw new IllegalArgumentException("No such value");
    }
    public Point next(){
        return switch (this){
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> throw new IllegalArgumentException("asdfgh");

        };
    }
}