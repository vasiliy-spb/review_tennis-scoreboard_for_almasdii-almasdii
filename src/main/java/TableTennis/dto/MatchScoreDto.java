package TableTennis.dto;

public record MatchScoreDto(
        String firstPlayerName,
        String secondPlayerName,
        int point1,
        int point2,
        int games1,
        int games2,
        int sets1,
        int sets2,
        boolean isTieBreakStarted,
        int firstPlayerTieBreakScore,
        int secondPlayerTieBreakScore
) {
}
