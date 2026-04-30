package TableTennis.model;

public enum Points {
    LOVE(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40);

    private final int score;

    Points(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
    public static Points fromValue(int value) {
        for (Points p : Points.values()) {
            if (p.score == value) {
                return p;
            }
        }
        throw new IllegalArgumentException("No such value");
    }
    public Points next(){
        return switch (this){
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> FORTY;
        };
    }
}