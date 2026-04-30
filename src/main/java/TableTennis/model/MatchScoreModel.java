package TableTennis.model;

public record MatchScoreModel(
        String firstPlayerName,
        String secondPlayerName,
        int point1,
        int point2,
        int games1,
        int games2,
        int sets1,
        int sets2,
        int score1,
        int score2
) {
    public MatchScoreModel(String firstPlayerName, String secondPlayerName, int point1, int point2, int games1, int games2, int sets1, int sets2) {
        this(firstPlayerName, secondPlayerName, point1, point2, games1, games2, sets1, sets2, -1, -1);
    }
}
